import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-ErrorDialog',
  templateUrl: './ErrorDialog.component.html',
  styleUrls: ['./ErrorDialog.component.css']
})
export class ErrorDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: { message: string }) { }


  ngOnInit() {
  }

}
