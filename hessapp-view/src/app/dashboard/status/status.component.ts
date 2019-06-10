import { Component, OnInit } from '@angular/core';
import {ApiClientService} from "../../services/api-client.service";
import {ToastrService} from "../../services/toastr.service";
import {Router} from "@angular/router";
import {DeleteGroupReqDTO} from "../../dto/DeleteGroupReqDTO";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {Debt} from "../../dto/Debt";

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  private groupId: string;
  totalCredit;
  totalDebt;
  totalStatus;
  debtList: Debt[] = [];


  constructor(private apiClient: ApiClientService,
              private toastr: ToastrService,
              private router: Router) {
    const urlParse = this.router.url.split('/');
    this.groupId = urlParse[3];

    this.apiCall();

    LoginUserInfoService.gotoDashboard.subscribe(() => {
      console.log("Dashboarda git");
      this.router.navigate(['/dashboard/general-status']);
    });
  }

  apiCall() {
   const groupStatusReq: DeleteGroupReqDTO = new DeleteGroupReqDTO();
    groupStatusReq.groupId = this.groupId;
    groupStatusReq.participantId = LoginUserInfoService.nickname;

    this.apiClient.getGroupStatusClient(groupStatusReq).subscribe(
      response => {
        if (response.status == 200) {
          this.totalDebt = response.body.totalDebt;
          this.totalCredit = response.body.totalCredit;
          this.totalStatus = this.totalCredit - this.totalDebt;
          this.debtList = response.body.listDebt;
        }
      },
      error2 => {
        console.log(error2);
      }
    )
  }

  payDebt(actId: number) {
    this.apiClient.payDebtClient(actId).subscribe(
      response => {
        this.toastr.typeSuccess("Pay Debt Success", "İşleminiz gerçekleştirildi !");
        this.debtList.forEach((dbt, index) => {
            if (dbt.ActivityId == response) {
              this.debtList.splice(index, 1);
            }
          }
        )

        this.apiCall();
      },
      error2 => {
        console.log(error2);
        this.toastr.typeError("Pay Debt Error", "İşleminiz gerçekleştirilemedi !");
      }
    )
  }


  ngOnInit() {
  }

}
