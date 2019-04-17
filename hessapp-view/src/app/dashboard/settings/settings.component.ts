import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLinkActive} from "@angular/router";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {DeleteGroupReqDTO} from "../../dto/DeleteGroupReqDTO";
import {ApiClientService} from "../../services/api-client.service";
import {ToastrService} from "../../services/toastr.service";
import index from "@angular/cli/lib/cli";
import {Participant} from "../../dto/Participant";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit, AfterContentInit {

  private groupId = "";
  participants: Participant[] = [];
  group = null;
  loginUserNick = LoginUserInfoService.nickname;

  loading = false;

  constructor(private routeActive: ActivatedRoute,
              private router: Router,
              private apiClient: ApiClientService,
              private toastr: ToastrService) {
    const urlParse = this.router.url.split('/');
    this.groupId = urlParse[3];

    LoginUserInfoService.gotoDashboard.subscribe(() => {
      console.log("Dashboarda git");
      this.router.navigate(['/dashboard/general-status']);
    });
  }

  ngOnInit() {
    LoginUserInfoService.groups.forEach((grp) => {
      if (grp.groupID === this.groupId) {
        this.participants = grp.participants;
        this.group = grp;
      }
    });

    //console.log(LoginUserInfoService.groups);
  }

  ngAfterContentInit(): void {

  }

  deleteGroup() {
    this.loading = true;
    const deleteGroupReq: DeleteGroupReqDTO = new DeleteGroupReqDTO();
    deleteGroupReq.nickname = LoginUserInfoService.nickname;
    deleteGroupReq.groupId = this.groupId;

    this.apiClient.deleteGroupClient(deleteGroupReq).subscribe(
      response => {
        if (response.status == 200) {
          this.loading = false;
        }
      },
      error2 => {
        this.loading = false;
      }
    )
  }

}
