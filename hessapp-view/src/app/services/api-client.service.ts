import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {TFAuthRequestDTO} from "../dto/TFAuthRequestDTO";
import {RegisterRequestDTO} from "../dto/RegisterRequestDTO";
import {LoginRequestDTO} from "../dto/LoginRequestDTO";
import {ResetPasswordRequestDTO} from "../dto/ResetPasswordRequestDTO";
import {SocialLoginRequest} from "../dto/SocialLoginRequest";
import {User} from "../dto/User";
import {DeleteGroupReqDTO} from "../dto/DeleteGroupReqDTO";
import {CreateSpendReqDTO} from "../dto/CreateSpendReqDTO";
import {CreateGroupReqDTO} from "../dto/CreateGroupReqDTO";
import {GeneralStatusResDTO} from "../dto/GeneralStatusResDTO";
import {City} from "../dto/City";
import {LoginUserInfoService} from "./login-user-info.service";

@Injectable()
export class ApiClientService {

  private baseUrl = 'http://localhost:8360/api';

  // heroku base  = https://hessapp-api-gateway.herokuapp.com/api
  // local base = http://localhost:8360/api

  constructor(private http: HttpClient) { }

  getAllCity(): Observable<City[]> {
    var subUrl = '/login-service/user/city';
    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get<City[]>(url, httpOptions);
  }

  registerClient(registerRequestDTO: RegisterRequestDTO): Observable<any> {

    var subUrl = '/login-service/register';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, registerRequestDTO, {observe: 'response'});
  }

  accountActivateClient(activationCode: string): Observable<any> {
    var subUrl = "/login-service/register/account-activate/" + activationCode;

    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get(url, httpOptions);
  }

  loginClient(loginRequestDTO: LoginRequestDTO): Observable<any> {

    var subUrl = '/login-service/login/';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, loginRequestDTO, {observe: 'response'});
  }

  tfaClient(tfaReqDTO: TFAuthRequestDTO): Observable<any> {
    var subUrl = '/login-service/login/tfa';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, tfaReqDTO, {observe: 'response'});
  }

  resetPasswordClient(resetPasswordRequestDTO: ResetPasswordRequestDTO): Observable<any>{

    var subUrl = '/login-service/login/resetPassword';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, resetPasswordRequestDTO, {observe: 'response'});
  }

  socialLoginClient(socialRequest: SocialLoginRequest) : Observable<any> {

    var subUrl = '/login-service/login/social';

    var url = this.baseUrl + subUrl;

    return this.http.post(url , socialRequest, {observe: 'response'});
  }

  searchUserClient(nickname: string) : Observable<User> {
    var subUrl = '/login-service/user?nickname=' + nickname;

    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get<User>(url, httpOptions);
  }

  createGroupClient(createGroupReqDTO: CreateGroupReqDTO): Observable<any> {
    var subUrl = '/group-service/group/create';

    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.post(url, createGroupReqDTO, {observe: 'response'});
  }

  deleteGroupClient(deleteGroupReq: DeleteGroupReqDTO) : Observable<any> {
    var subUrl = '/group-service/group/delete';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, deleteGroupReq,{observe: 'response'});
  }

  pushFileToStorage(file: File){

    var subUrl = '/login-service/user/profile-photo';
    var url = this.baseUrl + subUrl;
    const formdata:FormData = new FormData();
    formdata.append('file', file);
    formdata.append('nick', LoginUserInfoService.nickname);
    const req = new HttpRequest('POST', "http://localhost:8360/api/login-service/user/profile-photo", formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }

  createSpendClient(createSpendReqDTO: CreateSpendReqDTO): Observable<any> {
    var subUrl = '/group-service/spend';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, createSpendReqDTO,{observe: 'response'});
  }

  getGroupStatusClient(deleteGroupReq: DeleteGroupReqDTO)  : Observable<any> {
    var subUrl = '/group-service/spend/group-status';

    var url = this.baseUrl + subUrl;

    return this.http.post(url, deleteGroupReq,{observe: 'response'});
  }

  payDebtClient(activityId: number): Observable<number>{
    var subUrl = '/info-service/spend/payDebt/'+activityId;

    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get<number>(url, httpOptions);
  }

  generalStatusClient(nickname: string): Observable<GeneralStatusResDTO>{
    var subUrl = '/info-service/spend/status/general/'+nickname;

    var url = this.baseUrl + subUrl;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };

    return this.http.get<GeneralStatusResDTO>(url, httpOptions);
  }
}
