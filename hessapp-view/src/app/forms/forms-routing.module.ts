import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ValidationFormsComponent } from "./validation/validation-forms.component";

const routes: Routes = [
  {
    path: '',    
    children: [
      {
        path: 'validation',
        component: ValidationFormsComponent,
        data: {
          title: 'Validation Forms'
        }
      }    
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FormsRoutingModule { }
