import {EventEmitter, Injectable, Output} from '@angular/core';
import {Group} from "../dto/Group";

@Injectable()
export class LoginUserInfoService {number
  @Output()
  static addingMessage = new EventEmitter();

  @Output()
  static gotoDashboard = new EventEmitter();

  @Output()
  static logOut = new EventEmitter();

  public static username: string = "";
  public static nickname: string = "";
  public static surname: string = "";
  public static email: string = "";
  public static gender: string = "";
  public static photoUrl: string = "";
  public static birthYear: number =0;
  public static groups: Group[] = [];

  constructor() { }

  public static changeGroup() {
    this.addingMessage.emit();
  }

  public static goToDashboard() {
    this.gotoDashboard.emit();
  }

  public static logOutRun() {
    this.logOut.emit();
  }
}
