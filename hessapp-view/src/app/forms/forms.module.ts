import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FormsRoutingModule } from "./forms-routing.module";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CustomFormsModule } from 'ng2-validation';
import { MatchHeightModule } from "../shared/directives/match-height.directive";
import { ValidationFormsComponent } from "./validation/validation-forms.component";

@NgModule({
    imports: [
        CommonModule,
        FormsRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        CustomFormsModule,
        MatchHeightModule,
        NgbModule
    ],
    declarations: [
        ValidationFormsComponent

    ]

})
export class FormModule { }
