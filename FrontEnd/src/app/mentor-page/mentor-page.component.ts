import { Component } from '@angular/core';
import {Team} from '../team';
import { TEAMS } from '../mock-students';
import { Student } from '../Student.1'
@Component({
  selector: 'app-mentor-page',
  templateUrl: './mentor-page.component.html',
  styleUrls: ['./mentor-page.component.css']
})
export class MentorPageComponent {
teams = TEAMS;

selectedTeam?: Team;

onSelect(team: Team): void {
  this.selectedTeam = team;

}

names: string[] = ['John', 'Jane', 'Alice', 'Bob'];

}
