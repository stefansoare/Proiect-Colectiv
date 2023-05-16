import { Component } from '@angular/core';
import { Team } from '../Classes/Team';
import { TEAMS } from '../mock-students';
import { Student } from '../Classes/Student';
import { StudentDetailComponent } from '../Student-detail/student-detail.component';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-mentor-page',
  templateUrl: './mentor-page.component.html',
  styleUrls: ['./mentor-page.component.css']
})
export class MentorPageComponent {


  longTextT = `A collaborative effort where individuals work together to achieve a common goal or 
  complete a task. Promotes teamwork and integration of diverse skills and perspectives.`;

  longTextP = `Practical learning and work experience during the summer break. Helps students apply
  theoretical knowledge, gain real-world exposure, and develop professional skills.`;

  longTextI = `Practical training for individuals pursuing a career in informatics. Offers hands-on
  experience, exposure to relevant tools and technologies, and collaboration with industry professionals.
  Bridges the gap between academic knowledge and real-world applications.`;

  // Add the showTable property
showTable: boolean = false;

// Method to toggle the visibility of the table
toggleTableVisibility() {
  this.showTable = !this.showTable;
}
  
  displayedColumns: string[] = ['id', 'name', 'students', 'expandedDetail'];

  name = '';
  position = 0;
  weight = 0;
  symbol = '';
  selectedTeam: any = null;
  selectedStudent: any = null;

  private filterSubject = new Subject<string>();
  filterValue = '';

  expandedElement: any;

  teams: Team[] = [
    { id: 1, name: 'Team 1', students: ['Student 1', 'Student 2', 'Student 3'], leader:'Student 1', mentorID:1, activityID:2, grade1:2},
    { id: 2, name: 'Team 2', students: ['Student 4', 'Student 5', 'Student 6'] , leader:'Student 1', mentorID:1, activityID:2, grade1:2},
    { id: 3, name: 'Team 3', students: ['Student 7', 'Student 8', 'Student 9'], leader:'Student 1', mentorID:1, activityID:2, grade1:2 }
  ];

  dataSource: MatTableDataSource<Team>; // Adjust the type to 'Team' if it contains the required properties

  constructor() {
    this.dataSource = new MatTableDataSource<Team>(this.teams);

    // Subscribe to the filterSubject and apply debounceTime
    this.filterSubject.pipe(debounceTime(1000)).subscribe((value) => {
      this.applyFilter(value);
    });
  }

  

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
  
  applyFilter(filtervalue: string) {
    this.dataSource.filter = filtervalue.trim().toLowerCase();
  }

  onFilterKeyUp(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filterSubject.next(filterValue);
  }
  
  isRowExpanded = (row: any) => this.expandedElement === row;
  onRowClick(row: any) {
    this.expandedElement = this.expandedElement === row ? null : row;
  }
}
