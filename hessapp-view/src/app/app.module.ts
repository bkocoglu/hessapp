
import { NgModule } from '@angular/core';
import {CommonModule} from "@angular/common"
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from "./shared/shared.module";
import { ToastModule, ToastOptions } from 'ng2-toastr/ng2-toastr';
import { AgmCoreModule } from '@agm/core';
import { AppComponent } from './app.component';
import { ContentLayoutComponent } from "./layouts/content/content-layout.component";
import { FullLayoutComponent } from "./layouts/full/full-layout.component";

import { CustomOption } from "./shared/toastr/custom-option";

import * as $ from 'jquery';
import { from } from 'rxjs/observable/from';
import {ToastrService} from "./services/toastr.service";
import {FormValidationService} from "./services/form-validation.service";
import {ApiClientService} from "./services/api-client.service";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TfaQrService} from "./services/tfa-qr.service";
import {LoginUserInfoService} from "./services/login-user-info.service";
import {AuthService, AuthServiceConfig, FacebookLoginProvider, GoogleLoginProvider} from "angularx-social-login";
import {ChatService} from "./services/chat.service";
import {GroupIdManagementService} from "./services/group-id-management.service";


const config = new AuthServiceConfig([
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('2207267489508754')
  },
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider('545226503531-c0groq7kjsi3d54o3k4e8pv6n98d1qhs.apps.googleusercontent.com')
  }
]);

export function provideConfig() {
  return config;
}

@NgModule({
    declarations: [
        AppComponent,
        FullLayoutComponent,
        ContentLayoutComponent
    ],
    imports: [
        BrowserAnimationsModule,
        AppRoutingModule,
        CommonModule,
        AngularFontAwesomeModule,
        SharedModule,
        HttpClientModule,
        ToastModule.forRoot(),
        NgbModule.forRoot(),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyBr5_picK8YJK7fFR2CPzTVMj6GG1TtRGo'
        })
    ],
    providers: [
        //Toastr providers
        { provide: ToastOptions, useClass: CustomOption },
      ToastrService,
      FormValidationService,
      ApiClientService,
      HttpClient,
      ChatService,
      TfaQrService,
      GroupIdManagementService,
      LoginUserInfoService,
      {
        provide : AuthServiceConfig,
        useFactory: provideConfig
      },
      AuthService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
