import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-transport',
  templateUrl: './transport.component.html',
  styleUrls: ['./transport.component.scss']
})
export class TransportComponent implements OnInit {

  private groupId = "";

  constructor(private router: Router) {
    const urlParse = this.router.url.split('/');
    this.groupId = urlParse[3];
  }

  ngOnInit() {
    document.getElementById("transport").click();
  }

  route(path: string) {
    this.router.navigate(['/dashboard/group/' + this.groupId + '/'+path]);
  }

}
