import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sede-restaurante',
  templateUrl: './sede-restaurante.component.html',
  styleUrls: ['./sede-restaurante.component.css']
})
export class SedeRestauranteComponent {
  constructor(private route: ActivatedRoute) { }

  sedeId: any;
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.sedeId = params.get('id');
    });
  }
}
