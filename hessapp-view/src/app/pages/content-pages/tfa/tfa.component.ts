import {Component, OnInit, ViewChild} from '@angular/core';
import {TfaQrService} from "../../../services/tfa-qr.service";
import {NgForm} from "@angular/forms";
import {FormValidationService} from "../../../services/form-validation.service";
import {ToastrService} from "../../../services/toastr.service";
import {ApiClientService} from "../../../services/api-client.service";
import {TFAuthRequestDTO} from "../../../dto/TFAuthRequestDTO";
import {Router} from "@angular/router";
import {LoginUserInfoService} from "../../../services/login-user-info.service";
import {ChatService} from "../../../services/chat.service";

@Component({
  selector: 'app-tfa',
  templateUrl: './tfa.component.html',
  styleUrls: ['./tfa.component.scss']
})
export class TfaComponent {
  @ViewChild('f') tfaForm: NgForm;

  nickname = TfaQrService.nickname;
  loading = false;

  constructor(private formValidation: FormValidationService,
              private toastr: ToastrService,
              private apiClient: ApiClientService,
              private router: Router,
              private chatService: ChatService) {
    this.chatService.disconnectSocket();
  }

  onSubmit() {
    this.loading = true;
    if (this.formValidation.isEmptyOrSpace(this.tfaForm.value.tfaCode)) {
      this.tfaForm.reset();
      this.toastr.typeError("TFA Error", "Kodu girmeniz gerekmektedir.");
      this.loading = false;
      return;
    }

    if (!this.formValidation.isValidLength(this.tfaForm.value.tfaCode,5)){
      this.tfaForm.reset();
      this.toastr.typeError("TFA Error", "Hatalı Kod Girdiniz !");
      this.loading = false;
      return;
    }

    const tfaDTO: TFAuthRequestDTO = new TFAuthRequestDTO();
    tfaDTO.nickname = this.nickname;
    tfaDTO.pin = this.tfaForm.value.tfaCode;


    this.apiClient.tfaClient(tfaDTO).subscribe(
      response => {
        if ( response.status == 200){
          this.tfaForm.reset();
          LoginUserInfoService.nickname = response.body.nickname;
          LoginUserInfoService.groups = response.body.groups;
          LoginUserInfoService.username = response.body.name;
          this.loading = false;
          this.router.navigate(['/dashboard/general-status']);
        }
      },
      error2 => {
        this.tfaForm.reset();
        this.loading = false;
        this.toastr.typeError("TFA Error", "Hatalı pin girdiniz lütfen tekrar deneyiniz !");
      }
    )

  }

}
