import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Dashboard2Component } from "./dashboard2/dashboard2.component";
import { ChatComponent } from './chat/chat.component';
import { VerticalTimelinePageComponent } from './vertical/vertical-timeline-page.component';
import { StatusComponent } from './status/status.component';
import { SettingsComponent } from './settings/settings.component';
import { GroupComponent } from './group/group.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import {TransportComponent} from "./transport/transport.component";
import {ProfileComponent} from "./profile/profile.component";
import {ImageUploadComponent} from "./image-upload/image-upload.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'general-status',
        component: Dashboard2Component,
        data: {
          title: 'General Status'
        }
      },
      {
        path: 'create-group',
        component: CreateGroupComponent,
        data: {
          title: 'Create Group'
        }
      },
      {
        path: 'profile',
        component: ProfileComponent,
        data: {
          title: 'Profile'
        }
      },
      {
        path: 'image-upload',
        component: ImageUploadComponent,
        data: {
          title: 'Upload'
        }
      },
      {
        path: 'group/:groupId',
        component: GroupComponent,
        children:[
          {
            path: 'chat',
            component: ChatComponent,
            data: {
              title: 'Chat'
            }
          },
          {
            path: 'transport',
            component: TransportComponent,
            data: {
              title: 'Transport'
            }
          },
          {
            path: 'timeline',
            component: VerticalTimelinePageComponent,
            data: {
              title: 'Timeline'
            }
          },
          {
            path: 'status',
            component: StatusComponent,
            data: {
              title: 'Status'
            }
          },
          {
            path: 'settings',
            component: SettingsComponent,
            data: {
              title: 'Settings'
            }
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule { }
