import {AfterContentInit, Component, OnInit} from '@angular/core';
import { RouteInfo } from "./sidebar.metadata";
import { Router, ActivatedRoute } from "@angular/router";
import {LoginPageComponent} from "../../pages/content-pages/login/login-page.component";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {ChatService} from "../../services/chat.service";
import {Message} from "../../dto/Message";
import {Group} from "../../dto/Group";
import {GroupIdManagementService} from "../../services/group-id-management.service";
import {ToastrService} from "../../services/toastr.service";

declare var $: any;

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})

export class SidebarComponent implements OnInit {

    public menuItems: any[];

    nickname = LoginUserInfoService.nickname;
    groups = LoginUserInfoService.groups;
    photoUrl = LoginUserInfoService.photoUrl;

    private selectedGroupId = 0;

    constructor(private router: Router,
                private route: ActivatedRoute,
                private chatService: ChatService,
                private groupIdService: GroupIdManagementService,
                private toastr: ToastrService) {
      if (!(LoginUserInfoService.nickname == null) || !(LoginUserInfoService.nickname == '')){
        this.chatService.initializeWebSocketConnection(LoginUserInfoService.nickname, router, toastr);
      }
      LoginUserInfoService.addingMessage.subscribe(() => {
        this.groups = LoginUserInfoService.groups;
        document.getElementById("reset").click();
      });
      LoginUserInfoService.gotoDashboard.subscribe(() => {
        this.router.navigate(['/dashboard/general-status']);
      });
      console.log(this.nickname);
    }

    ngOnInit() {
        $.getScript('./assets/js/app-sidebar.js');
      if (LoginUserInfoService.nickname == null || LoginUserInfoService.nickname == ""){
        this.router.navigate(['/pages/login']);
      }
    }

    printNick(){
      console.log(this.nickname);
      return this.nickname;
    }

  routeGroup(groupId: string) {
      this.groupIdService.setSelectedGroup(groupId);
      LoginUserInfoService.groups.forEach((grp) => {
        if (grp.groupID == groupId) {
          grp.unaspiringMessageCount = 0;
        }
      });
      this.router.navigate(['/dashboard/group/'+ groupId + '/transport']);
  }

  logOut() {
      LoginUserInfoService.groups = [];
      LoginUserInfoService.nickname = "";
      LoginUserInfoService.logOutRun();
      this.chatService.disconnectSocket();
      this.router.navigate(['/']);
  }

  getEndMessage(messages: Message[], groupId: string): string {
      if (messages != null || messages != []) {
        const message = messages[messages.length-1];
        if (message == null) {
          return '';
        }
        const messageBody = message.body;
        const mfrom = message.from;
        var loginUserName;
        var from;
        LoginUserInfoService.groups.forEach((grp) => {
          if (grp.groupID === groupId) {
            grp.participants.forEach((prt) => {
              if (prt.nickname === LoginUserInfoService.nickname){
                loginUserName = prt.name;
              }
            });
          }
        });
        if (mfrom === loginUserName) {
          from = "Sen";
        } else {
          from = mfrom.split(" ")[0];
        }
        if (messageBody.length > 12) {
          var txt = messageBody.slice(0,11);
          txt += '...';
          return from + ': ' + txt;
        }
        return from + ': ' + messageBody;
      }
      return '';
  }

  getUsername(): string{
      console.log(LoginUserInfoService.nickname);
      console.log(this.nickname);
      return this.nickname;
  }

  getEndMessageDate(messages: Message[]): string {
    if (messages != null || messages != []) {
      const endMessage: Message = messages[messages.length-1];
      if (endMessage == null) {
        return '';
      }
      const yearAndHours = endMessage.sendDate.toString().split('T');
      const hoursAndMinutes = yearAndHours[1].split(":");
      return hoursAndMinutes[0] + ':' + hoursAndMinutes[1];
    }
    return '';
  }

  getCustomClass(groupId: string) {
    const urlParse = this.router.url.split('/');
    if (urlParse[3] == null) {
      return "bg-blue-grey bg-lighten-5";
    } else {
      if (urlParse[3] == groupId) {
        return "bg-blue-grey bg-lighten-5 border-right-primary border-right-3";
      } else {
        return "bg-blue-grey bg-lighten-5";
      }
    }
  }

  goToDashboard() {
      GroupIdManagementService.selectedGroupId = "";
      GroupIdManagementService.selectedGroup = null;
      GroupIdManagementService.selectedMessages= [];
      this.router.navigate(["/dashboard/general-status"]);
  }

  goToProfile(){
    GroupIdManagementService.selectedGroupId = "";
    GroupIdManagementService.selectedGroup = null;
    GroupIdManagementService.selectedMessages= [];
    this.router.navigate(["/dashboard/profile"]);
  }

  goToGroupCreate() {
    GroupIdManagementService.selectedGroupId = "";
    GroupIdManagementService.selectedGroup = null;
    GroupIdManagementService.selectedMessages= [];
    this.router.navigate(["/dashboard/create-group"]);
  }

  reset() {
      return null;
  }

    //NGX Wizard - skip url change
    ngxWizardFunction(path: string) {
        if (path.indexOf('forms/ngx') !== -1)
            this.router.navigate(['forms/ngx/wizard'], { skipLocationChange: false });
    }

    getMessage(groupId: string) {
        this.router.navigate(['/dashboard/chat', groupId]);
    }
}
