import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../interfaces/user';

const httpOptions = {
  headers: new HttpHeaders({ 
    'Content-Type':'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  loginUrl: string = 'http://localhost:60011/samples/login';

  constructor(private httpClient: HttpClient) { }

  login(user: User): Observable<User> {
    return this.httpClient.post<User>(this.loginUrl, user, httpOptions);
  }
}
