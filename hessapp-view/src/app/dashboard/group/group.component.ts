import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {GroupIdManagementService} from "../../services/group-id-management.service";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {

  constructor(private router: Router,
              private routeActive: ActivatedRoute,
              private groupIdService: GroupIdManagementService) {
  }

  ngOnInit() {
    //console.log("Group On Init !");
  }

  getCustomStyle(type: string){
    const urlList = this.router.url.split("/");
    const count = urlList.length;
    const activeUrl = urlList[count-1];

    if (activeUrl === type){
      let myStyles = {
        'background-image': 'linear-gradient(45deg, #843cf7, #38b8f2)',
        'color': 'white'
        }
      return myStyles;
    } else {
      return null;
    }
  }

  route(path: string) {
    const id = this.routeActive.snapshot.paramMap.get("groupId");
    this.router.navigate(['/dashboard/group/' + id + '/'+path]);
  }

}
