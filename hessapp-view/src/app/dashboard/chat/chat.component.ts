import {
  Component, ViewChild, ElementRef, OnInit, ChangeDetectionStrategy, AfterContentInit,
  OnChanges, SimpleChanges
} from '@angular/core';
import { Chat } from './chat.model';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {GroupIdManagementService} from "../../services/group-id-management.service";
import {Message} from "../../dto/Message";
import {Group} from "../../dto/Group";
import {FormValidationService} from "../../services/form-validation.service";
import {ChatService} from "../../services/chat.service";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit{

  groupId: string = GroupIdManagementService.selectedGroupId;
  chatMessages: Message[] = GroupIdManagementService.selectedMessages;
  group: Group = GroupIdManagementService.selectedGroup;
  service = GroupIdManagementService;
  messageDate = "";
  private today = "";

  chat: Chat[];
  @ViewChild('messageInput') messageInputRef: ElementRef;



  messages = new Array();
  item: number = 0;
  constructor(private elRef: ElementRef,
              private chatService: ChatService,
              private router: Router,
              private groupIdManagement: GroupIdManagementService,
              private formValidation: FormValidationService) {
    $.getScript('./chat.js');
    LoginUserInfoService.addingMessage.subscribe(() => {
      this.chatMessages = GroupIdManagementService.selectedMessages;
      this.group = GroupIdManagementService.selectedGroup;
      this.service = GroupIdManagementService;
      //document.getElementById("send").click();
      var urlSplit = this.router.url.split("/");
      if (urlSplit[urlSplit.length-1] === "chat") {
        document.getElementById("rst").click();
      }
    });

    LoginUserInfoService.gotoDashboard.subscribe(() => {
      console.log("Dashboarda git");
      this.router.navigate(['/dashboard/general-status']);
    });
  }

  ngOnInit() {
    this.messageInputRef.nativeElement.focus();
    var now = new Date();
    this.today = now.getDate() + "." + (now.getMonth()+1) + "." + now.getFullYear();
    this.messageDate = "";
  }

  isChangeDate(dateTime: string): boolean {
    const date = dateTime.split("T")[0];
    if (this.messageDate != date) {
      this.messageDate = date;
      return true;
    } else {
      return false;
    }
  }

  getMessageDate(): string {
    const dateFormat = this.messageDate.split("-");
    const mDate = dateFormat[2] + '.' + dateFormat[1] + '.' + dateFormat[0];
    if (mDate === this.today) {
      return "Bugün";
    } else {
      return mDate;
    }
  }

  getMessageTime(dateTime: string) {
    const time = dateTime.split("T")[1].split(":");
    return time[0] + ":" + time[1];
  }

  getMessageFromName(from: string): string {
    var loginUserName;
    this.group.participants.forEach((prt) => {
     if (prt.nickname == LoginUserInfoService.nickname) {
       loginUserName = prt.name;
     }
    });
    if (from === loginUserName) {
      return "Sen";
    } else {
      return from.split(" ")[0];
    }
  }

  getCustomMessageClass(fromName: string) {
    let className = "chat chat-left";
    this.group.participants.forEach((prt) => {
      if (fromName == prt.name) {
        if (LoginUserInfoService.nickname == prt.nickname){
          className = "chat";
        }
      }
    });
    return className;
  }

  reset() {
    return null;
  }

//send button function calls
  onAddMessage() {
    var msg = this.messageInputRef.nativeElement.value;
    if (!this.formValidation.isEmptyOrSpace(msg)) {
      console.log(msg);
      this.chatService.sendMessage(msg, GroupIdManagementService.selectedGroupId);
    }
    this.messageInputRef.nativeElement.value = "";
    this.messageInputRef.nativeElement.focus();
  }
}
