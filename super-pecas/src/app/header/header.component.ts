import { Component, OnInit } from '@angular/core';
import { HeaderService } from './header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public headerTitle!: string;

  constructor(private headerService: HeaderService) { }

  ngOnInit(): void {
    this.headerService.headerTitle.subscribe(title => {
      this.headerTitle = title;
    });
  }
}
