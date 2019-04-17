import {City} from "./City";

export class RegisterRequestDTO{
  firstName: string;
  surName: string;
  email: string;
  password: string;
  birthyear: number;
  city: City;
  gender: string;
}
