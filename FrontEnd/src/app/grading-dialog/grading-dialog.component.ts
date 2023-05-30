import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../Classes/Task';

@Component({
  selector: 'app-grading-dialog',
  templateUrl: './grading-dialog.component.html',
  styleUrls: ['./grading-dialog.component.css']
})
export class GradingDialogComponent {
  task: Task;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Task) {
    this.task = data;
  }

  // Implement any grading logic and functionality here
}