import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLogged = false;
  isLoginFail = false;

  loginForm: FormGroup;

  constructor(
    private router: Router,
    public fb: FormBuilder,
  ){
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
  }


  ngOnInit(): void {
    
  }

  onLogin(){
    this.router.navigate(['/home-admin']);
  }

}
