import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormsModule } from '@angular/forms';

import { ContentPagesRoutingModule } from "./content-pages-routing.module";

import { ComingSoonPageComponent } from "./coming-soon/coming-soon-page.component";
import { ForgotPasswordPageComponent } from "./forgot-password/forgot-password-page.component";
import { LoginPageComponent } from "./login/login-page.component";
import { RegisterPageComponent } from "./register/register-page.component";
import { TfaQrComponent } from './tfa-qr/tfa-qr.component';
import { TfaComponent } from './tfa/tfa.component';
import {RecaptchaModule} from "ng-recaptcha";
import {RecaptchaFormsModule} from "ng-recaptcha/forms";
import { AccountActivateComponent } from './account-activate/account-activate.component';


@NgModule({
    imports: [
        CommonModule,
        ContentPagesRoutingModule,
        FormsModule,
        RecaptchaFormsModule,
        RecaptchaModule.forRoot()
    ],
    declarations: [
        ComingSoonPageComponent,
        ForgotPasswordPageComponent,
        LoginPageComponent,
        RegisterPageComponent,
        TfaQrComponent,
        TfaComponent,
        AccountActivateComponent
    ]
})
export class ContentPagesModule { }
