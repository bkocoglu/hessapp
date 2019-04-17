import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ApiClientService} from "../../../services/api-client.service";
import {loadConfigurationFromPath} from "tslint/lib/configuration";

@Component({
  selector: 'app-account-activate',
  templateUrl: './account-activate.component.html',
  styleUrls: ['./account-activate.component.scss']
})
export class AccountActivateComponent implements OnInit {
  result = null;
  errorMessage = null;

  constructor(private route: ActivatedRoute,
              private apiClient: ApiClientService) {
    var activationCode = this.route.snapshot.paramMap.get('activationCode');
    this.apiClient.accountActivateClient(activationCode).subscribe(
      response => {
        console.log(response);
        this.result = true;
      },
      error1 => {
        console.log(error1);
        if (error1.status == 400){
          this.errorMessage = 'İslem gerçekleştirilemedi !';
          this.result = false;
          return;
        }
        this.errorMessage = error1.error.message;
        this.result = false;
      }
    )
  }

  ngOnInit() {
  }

}
