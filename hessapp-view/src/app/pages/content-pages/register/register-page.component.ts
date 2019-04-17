import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {FormValidationService} from "../../../services/form-validation.service";
import {ToastrService} from "../../../services/toastr.service";
import {RegisterRequestDTO} from "../../../dto/RegisterRequestDTO";
import {ApiClientService} from "../../../services/api-client.service";
import {Router} from "@angular/router";
import {TfaQrService} from "../../../services/tfa-qr.service";
import {City} from "../../../dto/City";


@Component({
    selector: 'app-register-page',
    templateUrl: './register-page.component.html',
    styleUrls: ['./register-page.component.scss']
})

export class RegisterPageComponent {
    @ViewChild('f') registerForm: NgForm;

  loading = false;

  birthyears: number[] = [];
  cities: City[] = [];
  recaptcha = true;

  constructor(private formValidationService: FormValidationService,
              private toastr: ToastrService,
              private apiClient: ApiClientService,
              private router: Router,
              private tfaQrService: TfaQrService) {

    var year = Number.parseInt(new Date().getFullYear().toString());
    var years: number[] = [];
    for (year; year>1950; year--){
      years.push(year);
    }

    this.birthyears = years;

    this.apiClient.getAllCity().subscribe(
      response => {
        this.cities = response;
      },
      error1 =>{
        console.log(error1);
        this.toastr.typeError("Warning !", error1.message);
      }
    )
  }


  resolved(captchaResponse: string) {
    console.log('Resolved captcha with response ' + captchaResponse);
    this.recaptcha = true;
  }

                //  On submit click, reset field value
  onSubmit() {
      this.loading = true;

      if (this.recaptcha == false) {
        this.toastr.typeError('Warning !', "reCAPTCHA Doğrulaması Yapılmadı !");
        this.loading = false;
        return;
      }

      if (
        this.formValidationService.isEmptyOrSpace(this.registerForm.value.name)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.surname)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.email)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.password)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.repeatPass)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.birthyear)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.city)
        || this.formValidationService.isEmptyOrSpace(this.registerForm.value.gender)
      ) {
        this.toastr.typeError('Warning !', "Tüm alanları eksiksiz doldurmanız gerekir !");
        this.loading = false;
        return;
      }

      if (this.formValidationService.isValidMailFormat(this.registerForm.value.email)) {
        this.toastr.typeError('Warning !', "Doğru bir mail adresi girmeniz gerekmektedir !");
        this.loading = false;
        return;
      }

      if (!this.formValidationService.isValidLength(this.registerForm.value.password, 5)) {
        this.toastr.typeError('Warning !', "Şifrenin 5 karakterden uzun olması gerekmektedir !");
        this.loading = false;
        return;
      }

      if(this.registerForm.value.password !== this.registerForm.value.repeatPass) {
        this.toastr.typeError('Warning !', "Şifreler birbiriyle uyuşmuyor !");
        this.loading = false;
        return;
      }

      const registerDTO: RegisterRequestDTO = new RegisterRequestDTO();
      registerDTO.firstName = this.registerForm.value.name;
      registerDTO.surName = this.registerForm.value.surname;
      registerDTO.email = this.registerForm.value.email;
      registerDTO.password = this.registerForm.value.password;
      registerDTO.birthyear = +this.registerForm.value.birthyear;
      registerDTO.gender = this.registerForm.value.gender;

      this.cities.forEach((cty) => {
        if (cty.name == this.registerForm.value.city) {
          registerDTO.city = cty;
        }
      });

      console.log(registerDTO);


        this.apiClient.registerClient(registerDTO).subscribe(
          response => {
            console.log(response);
            if ( response.status == 200){
              this.registerForm.reset();
              this.toastr.typeSuccess("Kayıt Başarılı !", "Hesabınızı aktifleştirmek için size mail gönderildi.");
              this.loading = false;
              this.router.navigate(['/pages/login']);
            }
          },
          error2 => {
            this.registerForm.reset();
            this.loading = false;
            this.toastr.typeError("Register Error", error2.error.message);
          }
        )

    }
}
