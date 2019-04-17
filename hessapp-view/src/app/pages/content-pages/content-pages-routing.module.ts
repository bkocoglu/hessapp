import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ComingSoonPageComponent } from "./coming-soon/coming-soon-page.component";
import { ForgotPasswordPageComponent } from "./forgot-password/forgot-password-page.component";
import { LoginPageComponent } from "./login/login-page.component";
import { RegisterPageComponent } from "./register/register-page.component";
import { TfaQrComponent } from './tfa-qr/tfa-qr.component';
import { TfaComponent } from './tfa/tfa.component';
import {AccountActivateComponent} from "./account-activate/account-activate.component";


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'comingsoon',
        component: ComingSoonPageComponent,
        data: {
          title: 'Coming Soon page'
        }
      },
      {
        path: 'forgotpassword',
        component: ForgotPasswordPageComponent,
        data: {
          title: 'Forgot Password Page'
        }
      },
      {
        path: 'tfa-qr',
        component: TfaQrComponent,
        data: {
          title: 'TFA QR Sayfası'
        }
      },
      {
        path: 'tfa',
        component: TfaComponent,
        data: {
          title: 'TFA Sayfası'
        }
      },
      {
        path: 'login',
        component: LoginPageComponent,
        data: {
          title: 'Login Page'
        }
      },
      {
        path: 'register',
        component: RegisterPageComponent,
        data: {
          title: 'Register Page'
        }
      },
      {
        path: 'account-activate/:activationCode',
        component: AccountActivateComponent,
        data: {
          title: 'Account Activate Page'
        }
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContentPagesRoutingModule { }
