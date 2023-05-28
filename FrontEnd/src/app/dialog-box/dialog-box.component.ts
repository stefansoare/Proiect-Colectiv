import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';

@Component({
  selector: 'app-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.css']
})
export class DialogBoxComponent implements OnInit {
  student: Student | undefined;

  constructor(
    private dialogRef: MatDialogRef<DialogBoxComponent>,
    private studentService: StudentService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {
    const studentId = this.data.studentId;
    this.getStudent(studentId);
  }

  getStudent(studentId: number) {
    this.studentService.getStudent(studentId).subscribe(
      (student) => {
        this.student = student;
      },
      (error) => {
        console.error(error);
        if (error.status === 302 && error.error) {
          this.student = error.error;
        }
      }
    );
  }
}
