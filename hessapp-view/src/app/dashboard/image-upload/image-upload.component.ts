import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ApiClientService} from "../../services/api-client.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.scss']
})
export class ImageUploadComponent implements OnInit {

  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private router: Router,
              private apiClient: ApiClientService) { }

  ngOnInit() {
  }

  onFileSelected(event){
    console.log(event);
    this.currentFileUpload = event.target.files[0];
  }

  upload(){
    this.progress.percentage = 0;
    this.apiClient.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if(event.type === HttpEventType.UploadProgress){
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      }else if(event instanceof HttpResponse){
        console.log(event);
      }
    });
    this.currentFileUpload = null;
  }

  goBack() {
    this.router.navigate(['/dashboard/profile']);
  }

}
