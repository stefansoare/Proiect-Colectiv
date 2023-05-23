import { Component } from '@angular/core';
import { Team } from '../Classes/Team';
import { Student } from '../Classes/Student';
import { MatTableDataSource } from '@angular/material/table';
import { Observable, Subject } from 'rxjs';
import { StudentService } from '../Services/student.service';
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
  member: Student | null=null;
  displayedColumns: string[] = ['index', 'id', 'members'];
  name = '';
  position = 0;
  weight = 0;
  symbol = '';
  selectedTeam: any = null;
  selectedStudent: any = null;
  private filterSubject = new Subject<string>();
  filterValue = '';
  expandedElement: any;
  dataSource: MatTableDataSource<Team>; // Adjust the type to 'Team' if it contains the required properties
  students: Student[] = [];
  teams: Team[]=[];
  constructor(private studentService: StudentService) {
    this.dataSource = new MatTableDataSource<Team>(this.teams);

  }

  ngOnInit() {
    this.studentService.getTeams().subscribe(
      teams => {
        this.teams = teams;
        this.dataSource.data = this.teams;
        this.populateTeamMembersNames(); // Add this line to populate the member names
      }
    );
  }
  populateTeamMembersNames() {
    this.teams.forEach(team => {
      this.studentService.getTeamMembers(team.id).subscribe(
        members => {
          team.students = members;
        }
      );
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

  applyFilter(filterValue: string) {
    this.filterValue = filterValue.trim().toLowerCase();
  
    this.dataSource.data = this.teams.filter(team => {
      // Check if the team id or any of the team members' names contains the filter value
      return (
        team.id.toString().includes(this.filterValue) ||
        team.students.some(student => student.name.toLowerCase().includes(this.filterValue))
      );
    });
  }

  onFilterKeyUp(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.applyFilter(filterValue);
  }

}
