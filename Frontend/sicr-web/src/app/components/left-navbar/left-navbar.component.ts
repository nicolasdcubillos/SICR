import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-left-navbar',
  templateUrl: './left-navbar.component.html',
  styleUrls: ['./left-navbar.component.css']
})
export class LeftNavbarComponent implements OnInit {

  constructor(private router:Router) { }


  ngOnInit(): void {
    
  }

  logout(){
    this.router.navigate(['/login']);
  }

}
