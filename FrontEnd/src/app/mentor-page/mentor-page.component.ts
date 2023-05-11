import { Component } from '@angular/core';
import {Team} from '../Classes/Team';
import { TEAMS } from '../mock-students';
import { Student } from '../Classes/Student'
import { StudentDetailComponent } from '../Student-detail/student-detail.component';
@Component({
  selector: 'app-mentor-page',
  templateUrl: './mentor-page.component.html',
  styleUrls: ['./mentor-page.component.css']
})
export class MentorPageComponent {

selectedTeam: any = null;
selectedStudent: any = null;

  teams: any[] = [
    { id: 1, students: ['Student 1', 'Student 2', 'Student 3'] },
    { id: 2, students: ['Student 4', 'Student 5', 'Student 6'] },
    { id: 3, students: ['Student 7', 'Student 8', 'Student 9'] }
  ];

  onSelect(team: any) {
    if (this.selectedTeam === team) {
      // If the selected button is clicked again, clear the selectedTeam
      this.selectedTeam = null;
    } else {
      // Otherwise, set the selectedTeam to the clicked team
      this.selectedTeam = team;
    }
  }

  onSelectStudent(student: any) {
    if (this.selectedStudent === student) {
      // If the selected button is clicked again, clear the selectedStudent
      this.selectedStudent = null;
    } else {
      // Otherwise, set the selectedStudent to the clicked student
      this.selectedStudent = student;
    }
  }

}
