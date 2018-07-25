import { Component, OnInit } from '@angular/core';
import { User } from '../../interfaces/user';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  user: User;
  loginResult: string;
  validated: boolean = false;

  constructor(private loginService: LoginService) { }

  ngOnInit() {
    this.user = {
      id: '',
      name: '',
      password: ''
    };

    this.loginResult = '';
  }

  validate(){
    if (this.user.name=='' || this.user.password==''){
      this.validated = false;
    } else {
      this.validated = true;
    }
  }

  onSubmit(){
    this.user.id=='';
    console.log(`submitted: ${this.user.id} ${this.user.name} ${this.user.password}`);

    this.loginService.login(this.user).subscribe(
      user => {
        if (user.id == '0'){
          alert(`User ${this.user.name} not found.`)
        } else {
          alert(`User ID ${user.id} loaded successfuly.`)
        }
        this.user = user;
        console.log(`API call done: ${this.user.id} ${this.user.name} ${this.user.password}`);
      });
  }
}
