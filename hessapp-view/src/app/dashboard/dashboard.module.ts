import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { ChartsModule } from 'ng2-charts';
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { ChartistModule } from 'ng-chartist';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatchHeightModule } from "../shared/directives/match-height.directive";
import { Dashboard2Component } from "./dashboard2/dashboard2.component";
import { ChatComponent } from "./chat/chat.component";
import { VerticalTimelinePageComponent } from "./vertical/vertical-timeline-page.component";
import { StatusComponent } from './status/status.component';
import { SettingsComponent } from './settings/settings.component';
import { from } from 'rxjs/observable/from';
import { GroupComponent } from './group/group.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import {FormsModule} from "@angular/forms";
import { TransportComponent } from './transport/transport.component';
import { ProfileComponent } from './profile/profile.component';
import { FileSelectDirective } from "ng2-file-upload";
import { ImageUploadComponent } from './image-upload/image-upload.component';

@NgModule({
    imports: [
        CommonModule,
        DashboardRoutingModule,
        ChartistModule,
        NgbModule,
        MatchHeightModule,
        ChartsModule,
        FormsModule
    ],
    exports: [],
    declarations: [

        Dashboard2Component,
        ChatComponent,
        VerticalTimelinePageComponent,
        StatusComponent,
        SettingsComponent,
        GroupComponent,
        CreateGroupComponent,
        TransportComponent,
        ProfileComponent,
      FileSelectDirective,
      ImageUploadComponent
    ],
    providers: [],
})
export class DashboardModule { }
