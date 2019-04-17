import { Injectable } from '@angular/core';

@Injectable()
export class FormValidationService {

  constructor() { }

  isEmptyOrSpace(value: string): boolean {
    return value == null || value.match(/^ *$/) !== null;
  }

  isValidMailFormat(value: string): boolean {
    var emailRegexp = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;

    if ( value.length <= 5 || !emailRegexp.test(value)){
      //true email
      return true;
    }
    return false;
  }

  isValidLength(value: string, minLng: number) {
    if(value.length <= minLng){
      return false;
    }else {
      return true;
    }
  }
}
