import { Component } from '@angular/core';
import { Team } from '../Classes/Team';
import { Student } from '../Classes/Student';
import { Activity } from '../Classes/Activity';
import { MatTableDataSource } from '@angular/material/table';
import { Observable, Subject } from 'rxjs';
import { StudentService } from '../Services/student.service';
import { ActivitiesService } from '../Services/activities.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-mentor-page',
  templateUrl: './mentor-page.component.html',
  styleUrls: ['./mentor-page.component.css']
})
export class MentorPageComponent {

  navigateToComponent(componentName: string) {
    this.router.navigate([componentName]);
  }

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
  dataSource: MatTableDataSource<Activity>; // Adjust the type to 'Team' if it contains the required properties
  students: Student[] = [];
  teams: Team[]=[];
  activities: Activity[]=[];
  constructor(private studentService: StudentService,
              private activitiesService: ActivitiesService,
              private router: Router) {
    this.dataSource = new MatTableDataSource<Activity>(this.activities);

  }

  ngOnInit() {
   /* this.studentService.getTeams().subscribe(
      teams => {
        this.teams = teams;
        this.dataSource.data = this.teams;
        this.populateTeamMembersNames(); // Add this line to populate the member names
      }
    );*/
    this.activitiesService.getActivities().subscribe(
      activities => {
        this.activities = activities;
        this.dataSource.data = this.activities;
      }
    );
  }
  /*populateTeamMembersNames() {
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
  }*/
}
