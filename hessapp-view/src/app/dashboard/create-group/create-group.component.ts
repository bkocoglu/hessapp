import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {FormValidationService} from "../../services/form-validation.service";
import {ToastrService} from "../../services/toastr.service";
import {ApiClientService} from "../../services/api-client.service";
import {User} from "../../dto/User";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {Router} from "@angular/router";
import {CreateGroupReqDTO} from "../../dto/CreateGroupReqDTO";
import {Group} from "../../dto/Group";
import {el} from "@angular/platform-browser/testing/src/browser_util";

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.scss']
})
export class CreateGroupComponent implements OnInit {

  @ViewChild('f') searchForm: NgForm;

  selectedUser: User = null;
  selectionList: User[] = [];

  loading = false;

  constructor(private formValidation: FormValidationService,
              private toastr: ToastrService,
              private apiClient: ApiClientService,
              private router: Router) {

  }

  ngOnInit() {

  }

  searchUser() {
    console.log(this.searchForm.value.isPublic + '.');
    if (this.formValidation.isEmptyOrSpace(this.searchForm.value.nickname)) {
      this.toastr.typeError("Search Error", "Doğru bir nickname giriniz !");
      return;
    }

    if (!this.formValidation.isValidLength(this.searchForm.value.nickname, 5)) {
      this.toastr.typeError("Search Error", "Doğru bir nickname giriniz !");
      return;
    }

    const nick = '@hs-'+this.searchForm.value.nickname;

    this.apiClient.searchUserClient(nick).subscribe(
      response => {
          console.log(response);

          response.birthYear = (new Date()).getFullYear() - response.birthYear;

          if (response.nicname == LoginUserInfoService.nickname) {
            this.toastr.typeError("Search User", "Bu zaten sizsiniz !");
            this.selectedUser = null;
            return;
          } else {
            this.selectedUser = response;
          }
      },
      error2 => {
        console.log(error2);

        this.toastr.typeError("Search Error", "Böyle bir kullanıcı bulunamadı !");
      }
    )
  }

  cancelUser() {
    this.selectedUser = null;
  }

  addUser(user: User) {
    let count = 0;

    this.selectionList.forEach((item) => {
      if (item.nicname == user.nicname) {
        count++;
      }
    });

    if (count == 0) {
      this.selectionList.push(user);
    } else {
      this.toastr.typeError("Add User Error", "Bu kullanıcı zaten ekli");
    }
    this.selectedUser = null;
  }

  removeUser(nickname: string) {
    this.selectionList.forEach( (user, index) => {
      if (user.nicname == nickname) {
        this.selectionList.splice(index,1);
        return;
      }
    })
  }

  createGroup() {
    this.loading = true;
    if (this.formValidation.isEmptyOrSpace(this.searchForm.value.groupName)
        || this.formValidation.isEmptyOrSpace(this.searchForm.value.groupDesc)) {
      this.loading = false;
      this.toastr.typeError("Create Group Error", "Grup adı ve grup açıklamasını doldurmak zorundasınız !");
      return;
    }

    if (this.selectionList == [] || this.selectionList == null || this.selectionList.length == 0) {
      this.loading = false;
      this.toastr.typeError("Create Group Error", "En az 1 grup üyesi eklemelisiniz !");
      return;
    }

    const createGroupReq: CreateGroupReqDTO = new CreateGroupReqDTO();
    createGroupReq.name = this.searchForm.value.groupName;
    createGroupReq.description = this.searchForm.value.groupDesc;
    createGroupReq.moderator = LoginUserInfoService.nickname;

    if(this.searchForm.value.isPublic == true){
      createGroupReq.isPublic = true;
    }else{
      createGroupReq.isPublic = false;
    }

    const nicks: string[] = [];

    this.selectionList.forEach((usr) => {
      nicks.push(usr.nicname);
    });

    nicks.push(LoginUserInfoService.nickname);
    createGroupReq.participants = nicks;

    this.apiClient.createGroupClient(createGroupReq).subscribe(
      response => {
        if (response.status == 200) {
          this.loading = false;
          this.toastr.typeSuccess("Crete Group", "Grup başarıyla oluşturuldu.");
          this.searchForm.reset();
          this.router.navigate(['/dashboard/general-status']);
        }
      },
      error2 => {
        console.log(error2);
        this.loading = false;
        this.toastr.typeError("Create Group Error", error2.error.message);
      }
    )

  }

}
