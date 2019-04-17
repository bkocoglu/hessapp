import {EventEmitter, Injectable, Output} from '@angular/core';
import {LoginUserInfoService} from "./login-user-info.service";

@Injectable()
export class GroupIdManagementService {
  @Output() selectedGroupChange = new EventEmitter();

  public static selectedGroupId = "";
  public static selectedGroup = null;
  public static selectedMessages = [];

  constructor() { }

  public setSelectedGroup(groupId: string) {
    if (GroupIdManagementService.selectedGroupId != groupId) {
      GroupIdManagementService.selectedGroupId = groupId;

      LoginUserInfoService.groups.forEach((grp) => {
        if (grp.groupID == groupId) {
          GroupIdManagementService.selectedGroup = grp;
          GroupIdManagementService.selectedMessages = grp.messages;
        }
      });
      this.selectedGroupChange.emit();
    }
  }

}
