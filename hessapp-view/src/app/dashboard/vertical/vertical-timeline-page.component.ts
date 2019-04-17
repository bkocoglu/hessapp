import {Component, OnInit, Input, ViewChild} from '@angular/core';
import { NgbModal, ModalDismissReasons, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from "@angular/forms";
import {FormValidationService} from "../../services/form-validation.service";
import {ToastrService} from "../../services/toastr.service";
import {Router} from "@angular/router";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {Group} from "../../dto/Group";
import {Spend} from "../../dto/Spend";
import {ApiClientService} from "../../services/api-client.service";
import {CreateSpendReqDTO} from "../../dto/CreateSpendReqDTO";
declare var require: any;


@Component({
    selector: 'app-vertical-timeline-page',
    templateUrl: './vertical-timeline-page.component.html',
    styleUrls: ['./vertical-timeline-page.component.scss']
})



export class VerticalTimelinePageComponent implements OnInit {

  totalAmount;
  spendDesc;
  spends : Spend[];
  group: Group = null;

  loading = false;

  private groupId = "";

    closeResult: string;
    ngOnInit() {
        // Vertical Timeline JS
        $.getScript('./vertical-timeline.js');


      LoginUserInfoService.groups.forEach((grp) => {
        if (grp.groupID === this.groupId) {

          this.spends = grp.spends;
          this.group = grp;

          this.spends.forEach((spn, index) => {
            const dateString: string = spn.date;

            var parseDate: string[] = dateString.split('T');

            if (parseDate.length>1) {
              var mainDate = parseDate[0];
              var mainDateParse: string[] = mainDate.split("-");
              var anaDate: string = mainDateParse[2] + '.' + mainDateParse[1] + '.' + mainDateParse[0];
              spn.date = anaDate;
            } else {
              var mainDateParse:string[] = parseDate[0].split(" ");
              spn.date = mainDateParse[0];
            }
          });

          console.log(this.spends);
        }
      });
    }

    constructor(private modalService: NgbModal,
                private formValidation: FormValidationService,
                private toastr: ToastrService,
                private router: Router,
                private apiClient: ApiClientService) {
      const urlParse = this.router.url.split('/');
      this.groupId = urlParse[3];

      LoginUserInfoService.gotoDashboard.subscribe(() => {
        console.log("Dashboarda git");
        this.router.navigate(['/dashboard/general-status']);
      });

      LoginUserInfoService.addingMessage.subscribe(() => {
        var urlSplit = this.router.url.split("/");
        if (urlSplit[urlSplit.length-1] === "timeline") {
          document.getElementById("rst").click();
        }
      });
    }

    addSpend(customContent){
      if (!this.loading) {
        this.loading = true;
        if( this.totalAmount == null || this.formValidation.isEmptyOrSpace(this.spendDesc)) {
          this.loading = false;
          this.toastr.typeError("Add Spend Error", "Bilgileri eksiksiz doldurmanız gerekir !");
          return;
        }

        const createSpendReqDTO: CreateSpendReqDTO = new CreateSpendReqDTO();
        createSpendReqDTO.from = LoginUserInfoService.nickname;
        createSpendReqDTO.description = this.spendDesc;
        createSpendReqDTO.totalAmount = this.totalAmount;
        createSpendReqDTO.groupId = this.groupId;

        this.spendDesc = null;
        this.totalAmount = null;

        this.apiClient.createSpendClient(createSpendReqDTO).subscribe(
          response => {
            if (response.status == 200) {
              this.loading = false;
              console.log(response);
              this.toastr.typeSuccess("Success Add Spend", "Harcama başarıyla eklendi !");

              document.getElementById("closeModalButton").click();
            }
          },
          error2 => {
            this.loading = false;
            this.toastr.typeError("Error Add Spend", "Harcama eklenemedi !");

            this.router.navigate(['/dashboard/group/' + this.groupId + '/timeline']);
          }
        )
      }
      this.loading = false;
    }



    open(content) {
        this.modalService.open(content).result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

    // Open modal with dark section
    openModal(customContent) {
        this.modalService.open(customContent, { windowClass: 'dark-modal' });
    }

    // Open content with dark section
    openContent() {
        const modalRef = this.modalService.open(NgbdModalContent);
        modalRef.componentInstance.name = 'World';
    }

  reset() {
    return null;
  }
}
export class NgbdModalContent {
    @Input() name;
    constructor(public activeModal: NgbActiveModal) { }
}

