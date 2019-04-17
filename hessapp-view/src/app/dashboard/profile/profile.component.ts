import { Component, OnInit } from '@angular/core';
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

export interface User {
  photoUrl: string;
  nickname: string;
  name: string;
  surname: string;
  age: number;
  mail: string;
  gender: string;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  data: User;
  photoUrl = LoginUserInfoService.photoUrl;
  nickname = LoginUserInfoService.nickname;
  name = LoginUserInfoService.username;
  surname = LoginUserInfoService.surname;
  birthYear = (new Date()).getFullYear()-LoginUserInfoService.birthYear;

  constructor(private modalService: NgbModal,
              private router: Router) { }

  ngOnInit() {
    this.data = {
      nickname : LoginUserInfoService.nickname,
      photoUrl : LoginUserInfoService.photoUrl,
      name : LoginUserInfoService.username,
      surname : LoginUserInfoService.surname,
      mail : LoginUserInfoService.email,
      gender: LoginUserInfoService.gender,
      age : (new Date()).getFullYear()-LoginUserInfoService.birthYear
    }
  }

  goToUpload(){
    this.router.navigate(['/dashboard/image-upload']);
  }

  openModal(customContent) {
    this.modalService.open(customContent, { windowClass: 'dark-modal' });
  }


}
