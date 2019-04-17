import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {TfaQrService} from "../../../services/tfa-qr.service";

@Component({
  selector: 'app-tfa-qr',
  templateUrl: './tfa-qr.component.html',
  styleUrls: ['./tfa-qr.component.scss']
})
export class TfaQrComponent {

  tfaQrURL: string = TfaQrService.tfaQrURL;
  tfaKey: string = TfaQrService.tfaKey;



}
