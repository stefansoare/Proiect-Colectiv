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
teams = TEAMS;

selectedTeam?: Team;

selectedName?: string;

onSelect(team: Team): void {
  this.selectedTeam = team;

}
onSelect1(name: string ): void {
  this.selectedName = name;
  
}
names: string[] = ['John', 'Jane', 'Alice', 'Bob'];

}
