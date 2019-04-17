import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from "@angular/router";
import {FormValidationService} from "../../../services/form-validation.service";
import {ToastrService} from "../../../services/toastr.service";
import {ApiClientService} from "../../../services/api-client.service";
import {ResetPasswordRequestDTO} from "../../../dto/ResetPasswordRequestDTO";

@Component({
    selector: 'app-forgot-password-page',
    templateUrl: './forgot-password-page.component.html',
    styleUrls: ['./forgot-password-page.component.scss']
})

export class ForgotPasswordPageComponent {
    @ViewChild('f') forgotPasswordForm: NgForm;

    loading = false;

    constructor(private router: Router,
                private route: ActivatedRoute,
                private formValidation: FormValidationService,
                private toastr: ToastrService,
                private apiClient: ApiClientService) { }

    // On submit click, reset form fields
    onSubmit() {
      this.loading = true;
      if (this.formValidation.isEmptyOrSpace(this.forgotPasswordForm.value.email)){
        this.forgotPasswordForm.reset();
        this.toastr.typeError("Reset Password Error", "Lütfen mail adresinizi doğru giriniz !");
        this.loading = false;
        return;
      }
      if (this.formValidation.isValidMailFormat(this.forgotPasswordForm.value.email)){
        this.forgotPasswordForm.reset();
        this.toastr.typeError("Reset Password Error", "Lütfen mail adresinizi doğru giriniz !");
        this.loading = false;
        return;
      }

      const resetPasDTO: ResetPasswordRequestDTO = new ResetPasswordRequestDTO();
      resetPasDTO.email = this.forgotPasswordForm.value.email;

      this.apiClient.resetPasswordClient(resetPasDTO).subscribe(
        response => {
          if (response.status == 200){
            this.forgotPasswordForm.reset();
            this.toastr.typeSuccess("Reset Password", "Şifreniz başarıyla yenilendi !");
            this.loading = false;
            this.router.navigate(['/pages/login']);
          }
        },
        error2 => {
          this.forgotPasswordForm.reset();
          this.loading = false;
          this.toastr.typeError("Üzgünüz !", error2.error.message);
        }
      )
    }
}
