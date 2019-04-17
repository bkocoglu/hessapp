import { Routes, RouterModule } from '@angular/router';

//Route for content layout with sidebar, navbar and footer.

export const Full_ROUTES: Routes = [
  {
    path: 'dashboard',
    loadChildren: './dashboard/dashboard.module#DashboardModule'
  },
  {
    path: 'forms',
    loadChildren: './forms/forms.module#FormModule'
  },
  {
    path: 'components',
    loadChildren: './components/ui-components.module#UIComponentsModule'
  },
  {
    path: 'inbox',
    loadChildren: './inbox/inbox.module#InboxModule'
  }
];