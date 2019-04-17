import { NgModule } from '@angular/core';
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';
import { ContentLayoutComponent } from "./layouts/content/content-layout.component";
import { CONTENT_ROUTES } from "./shared/routes/content-layout.routes";
import { Full_ROUTES } from "./shared/routes/full-layout.routes";
import { FullLayoutComponent } from "./layouts/full/full-layout.component";
const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'pages/login',
    pathMatch: 'full',
  },
  { path: '', component: FullLayoutComponent, data: { title: 'full Views' }, children: Full_ROUTES },
  { path: '', component: ContentLayoutComponent, data: { title: 'content Views' }, children: CONTENT_ROUTES }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})

export class AppRoutingModule {
}