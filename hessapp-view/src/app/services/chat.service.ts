import {EventEmitter, Injectable, Output} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from '@stomp/stompjs';
import {LoginUserInfoService} from './login-user-info.service';
import {Message} from '../dto/Message';
import {Router} from '@angular/router';
import {GroupIdManagementService} from './group-id-management.service';
import { Howl } from 'howler';
import {ToastrService} from './toastr.service';
import {Group} from '../dto/Group';
import {Spend} from '../dto/Spend';

@Injectable()
export class ChatService {
  // localUrl = http://localhost:8364/socket
  // herokuUrl = https://hessapp-api-chat.herokuapp.com/socket
  private static stompClient;
  private serverUrl = 'https://hessapp-api-chat.herokuapp.com/socket';

  constructor(private router: Router) { }

  public initializeWebSocketConnection(nickname: string, router: Router, toastr: ToastrService) {
    const ws = new SockJS(this.serverUrl);
    ChatService.stompClient = Stomp.Stomp.over(ws);
    const that = this;
    ChatService.stompClient.connect({'nickname': nickname}, function (frame) {
      ChatService.stompClient.subscribe('/chat', (message) => {
        if (message.body) {
          const body = JSON.parse(message.body);
          console.log(body);
          if (body.messageType === 'MESSAGE') {
            const msg: Message = new Message();
            msg.id = body.message.id;
            msg.from = body.message.from;
            msg.sendDate = body.message.sendDate;
            msg.body = body.message.body;
            msg.groupId = body.message.groupId;

            const sound = new Howl({
              src: ['../../assets/sounds/newMessageNotification.mp3'],
              volume: 0.7
            });

            LoginUserInfoService.groups.forEach((grp) => {
              if (grp.groupID === msg.groupId) {
                if (msg.from !== LoginUserInfoService.nickname) {
                  sound.play();
                }
                grp.participants.forEach((prt) => {
                  if (prt.nickname === msg.from) {
                    msg.from = prt.name;
                  }
                });
                grp.messages.push(msg);
                if (GroupIdManagementService.selectedGroupId !== grp.groupID) {
                  grp.unaspiringMessageCount++;
                }
                LoginUserInfoService.changeGroup();
              }
            });
          }
          if (body.messageType === 'GROUPDELETE') {
            console.log('Group Delete');
            // tslint:disable-next-line:prefer-const
            let groupId: string = body.groupId;
            const sound = new Howl({
              src: ['../../assets/sounds/deleteGroupNotification.mp3'],
              volume: 0.7
            });
            LoginUserInfoService.groups.forEach((grp, index) => {
              if (grp.groupID === groupId) {

                if (grp.moderator === LoginUserInfoService.nickname) {
                  toastr.typeSuccess('Delete Group Success', 'Grup başarıyla silindi.');
                  LoginUserInfoService.groups.splice(index, 1);
                  sound.play();
                  LoginUserInfoService.changeGroup();
                  LoginUserInfoService.goToDashboard();
                } else {
                  toastr.typeInfo('Delete Group', grp.name + ' grubu yönetici tarafından silindi !');
                  if (grp.groupID === GroupIdManagementService.selectedGroupId) {
                    LoginUserInfoService.groups.splice(index, 1);
                    sound.play();
                    LoginUserInfoService.changeGroup();
                    LoginUserInfoService.goToDashboard();
                  } else {
                    LoginUserInfoService.groups.splice(index, 1);
                    sound.play();
                    LoginUserInfoService.changeGroup();
                  }
                }
              }
            });
          }
          if (body.messageType === 'GROUPCREATE') {
            const group: Group = body.group;
            group.spends = [];
            const sound = new Howl({
              src: ['../../assets/sounds/createGroupNotification.mp3'],
              volume: 0.7
            });
            group.participants.forEach((prt, index) => {
              if (prt.nickname === LoginUserInfoService.nickname) {
                if (group.moderator === LoginUserInfoService.nickname) {
                  LoginUserInfoService.groups.push(group);
                  sound.play();
                  LoginUserInfoService.changeGroup();
                } else {
                  let moderatorName: string;
                  group.participants.forEach((part) => {
                    if (part.nickname === group.moderator) {
                      moderatorName = part.name;
                    }
                  });
                  toastr.typeSuccess('Yeni Grup !', moderatorName + ' tarafından ' + group.name + ' grubuna eklendiniz !');
                  LoginUserInfoService.groups.push(group);
                  sound.play();
                  LoginUserInfoService.changeGroup();
                }
              }
            })
          }
          if (body.messageType === 'SPENDCREATE') {
            const sound = new Howl({
              src: ['../../assets/sounds/createSpendNotification.mp3'],
              volume: 0.7
            });
            const spend: Spend = new Spend();
            spend.groupId = body.spend.groupId;
            spend.date = body.spend.date;
            spend.description = body.spend.description;
            spend.from = body.spend.from;
            spend.totalAmount = body.spend.totalAmount;

            const dateString: string = spend.date;
            const parseDate: string[] = dateString.split('T');

            if (parseDate.length > 1) {
              const mainDate = parseDate[0];
              const mainDateParse: string[] = mainDate.split('-');
              const anaDate: string = mainDateParse[2] + '.' + mainDateParse[1] + '.' + mainDateParse[0];
              spend.date = anaDate;
            } else {
              const mainDateParse: string[] = parseDate[0].split(' ');
              spend.date = mainDateParse[0];
            }

            LoginUserInfoService.groups.forEach((grp) => {
              if (grp.groupID === spend.groupId) {
                sound.play();
                let moderatorNickname: string;
                grp.participants.forEach((prt) => {
                  if (prt.name === spend.from) {
                    moderatorNickname = prt.nickname;
                  }
                });
                if (moderatorNickname === LoginUserInfoService.nickname) {
                  grp.spends.push(spend);
                  LoginUserInfoService.changeGroup();
                } else {
                  // tslint:disable-next-line:max-line-length
                  toastr.typeInfo('Harcama Bilgisi', grp.name + ' grubuna ' + spend.from + ' tarafından ' + spend.totalAmount + ' tutarında harcama eklendi !');
                  grp.spends.push(spend);
                  LoginUserInfoService.changeGroup();
                }
              }
            })
          }
        }
      });
    });
  }

  public disconnectSocket() {
    console.log('Disconnect Socket Run');
    if (ChatService.stompClient != null) {
      ChatService.stompClient.disconnect();
    }
  }

  public sendMessage(body: string, groupId: string) {
    if (ChatService.stompClient != null) {
      ChatService.stompClient.send('/app/send/message', {'from': LoginUserInfoService.nickname, 'group': groupId}, body);
    }
  }

}
