import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from 'src/app/services/auth-service.service';

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
    public auth:AuthServiceService
  ){
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
    });
  }


  ngOnInit(): void {
    
  }

  onLogin(){
    this.auth.login(this.loginForm.value);
    // this.router.navigate(['/home-admin']);
  }

}
