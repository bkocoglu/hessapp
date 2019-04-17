import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from "@angular/router";
import {ToastrService} from "../../../services/toastr.service";
import {FormValidationService} from "../../../services/form-validation.service";
import {LoginRequestDTO} from "../../../dto/LoginRequestDTO";
import {ApiClientService} from "../../../services/api-client.service";
import {TfaQrService} from "../../../services/tfa-qr.service";
import {
  AuthService, AuthService as SocialAuthService, FacebookLoginProvider,
  GoogleLoginProvider
} from 'angularx-social-login';
import {SocialLoginRequest} from "../../../dto/SocialLoginRequest";
import {LoginUserInfoService} from "../../../services/login-user-info.service";
import {ChatService} from "../../../services/chat.service";
import {phone} from "ng2-validation/dist/phone";

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})

export class LoginPageComponent {

    @ViewChild('f') loginForm: NgForm;

    loading = false;
    fbloading = false;
    googleLoading = false;

    constructor(private router: Router,
                private route: ActivatedRoute,
                private toastr: ToastrService,
                private validationService: FormValidationService,
                private apiClient: ApiClientService,
                private authService: AuthService,
                private socialAuthService: SocialAuthService,
                private chatService: ChatService) {
      this.chatService.disconnectSocket();
      LoginUserInfoService.logOut.subscribe(() => {
        this.socialAuthService.signOut().then(
          (userData) => {
          }
        );
      });
    }

    // On submit button click
    onSubmit() {
      this.loading = true;
      if(this.validationService.isEmptyOrSpace(this.loginForm.value.email)
        || this.validationService.isEmptyOrSpace(this.loginForm.value.password)){
        this.loginForm.reset();
        this.toastr.typeError('Validation Error', "Tüm alanları eksiksiz doldurmanız gerekir !");
        this.loading = false;
        return;
      }

      if (this.validationService.isValidMailFormat(this.loginForm.value.email)){
        this.loginForm.reset();
        this.toastr.typeError('Validation Error', "Doğru bir mail adresi girmeniz gerekmektedir !");
        this.loading = false;
        return;
      }

      if (!this.validationService.isValidLength(this.loginForm.value.password,5)) {
        this.loginForm.reset();
        this.toastr.typeError('Validation Error', "Şifrenin 5 karakterden uzun olması gerekmektedir !");
        this.loading = false;
        return;
      }

      const loginDTO: LoginRequestDTO = new LoginRequestDTO();
      loginDTO.email = this.loginForm.value.email;
      loginDTO.password = this.loginForm.value.password;

      this.apiClient.loginClient(loginDTO).subscribe(
        response => {
          if ( response.status == 200){
            this.loginForm.reset();
            console.log(response);
            LoginUserInfoService.nickname = response.body.nickname;
            LoginUserInfoService.groups = response.body.groups;
            LoginUserInfoService.username = response.body.name;
            LoginUserInfoService.surname = response.body.surname;
            LoginUserInfoService.email = response.body.email;
            LoginUserInfoService.gender = response.body.gender;
            LoginUserInfoService.photoUrl = response.body.photoUrl;
            LoginUserInfoService.birthYear = response.body.birthYear;
            console.log(LoginUserInfoService.birthYear);
            this.loading = false;
            this.router.navigate(['/dashboard/general-status']);
          }
        },
        error2 => {
          this.loginForm.reset();
          this.loading = false;
          this.toastr.typeError("Login Error", error2.error.message);
        }
      )

    }

  connectGoogle(){
    this.googleLoading = true;

    var socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;

    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        var socialRequest = new SocialLoginRequest();

        socialRequest.firstName = userData.firstName;
        socialRequest.surname = userData.lastName;
        socialRequest.email = userData.email;
        socialRequest.photoUrl = userData.photoUrl;
        socialRequest.mediaType = "GOOGLE";

        this.apiClient.socialLoginClient(socialRequest).subscribe(
          response => {
            if (response.status == 200){
              console.log(response);
              this.toastr.typeSuccess("Google", "Giriş Başarılı !");
              LoginUserInfoService.nickname = response.body.nickname;
              LoginUserInfoService.groups = response.body.groups;
              LoginUserInfoService.username = response.body.name;
              LoginUserInfoService.surname = response.body.surname;
              LoginUserInfoService.email = response.body.email;
              LoginUserInfoService.gender = response.body.gender;
              LoginUserInfoService.photoUrl = response.body.photoUrl;
              LoginUserInfoService.birthYear = response.body.birthYear;
              this.googleLoading = false;
              this.router.navigate(['/dashboard/general-status']);
            }
          },
          error2 => {
              this.googleLoading = false;
              this.toastr.typeError("Google", error2.error.message);
          }
        );
      }
    );
  }

  connectFace(){
    this.fbloading = true;

    var socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;

    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {

        var socialRequest = new SocialLoginRequest();
        socialRequest.firstName = userData.firstName;
        socialRequest.surname = userData.lastName;
        socialRequest.email = userData.email;
        socialRequest.photoUrl = userData.photoUrl;
        socialRequest.mediaType = "FACEBOOK";


        this.apiClient.socialLoginClient(socialRequest).subscribe(
          response => {
            if (response.status == 200){
              console.log(response);
              this.toastr.typeSuccess("Facebook", "Giriş Başarılı !");
              LoginUserInfoService.nickname = response.body.nickname;
              LoginUserInfoService.groups = response.body.groups;
              LoginUserInfoService.username = response.body.name;
              LoginUserInfoService.surname = response.body.surname;
              LoginUserInfoService.email = response.body.email;
              LoginUserInfoService.gender = response.body.gender;
              LoginUserInfoService.photoUrl = response.body.photoUrl;
              LoginUserInfoService.birthYear = response.body.birthYear;

              this.fbloading = false;
              this.router.navigate(['/dashboard/general-status']);
            }
          },
          error2 => {
              this.fbloading = false;
              this.toastr.typeError("Facebook", error2.error.message);
          }
        );
      }
    );
  }
}
