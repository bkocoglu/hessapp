import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable()
export class TfaQrService {
  public static tfaQrURL: string = "";
  public static tfaKey: string = "";

  public static nickname: string = "";
}
