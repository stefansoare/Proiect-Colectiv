import { Component, OnInit } from '@angular/core';
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
export class MentorPageComponent implements OnInit {
  navigateToComponent(componentName: string) {
    this.router.navigate([componentName]);
  }

  showTable: boolean = false;
  member: Student | null = null;
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
  dataSource: MatTableDataSource<Activity> = new MatTableDataSource<Activity>([]);
  activities: Activity[] = [];

  constructor(
    private studentService: StudentService,
    private activitiesService: ActivitiesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.getActivities();
  }

  toggleTableVisibility() {
    this.showTable = !this.showTable;
  }

  getActivities() {
    this.activitiesService.getActivities().subscribe(
      activities => {
        this.activities = activities;
        this.dataSource.data = this.activities;
      },
      error => {
        console.log('Error fetching activities:', error);
      }
    );
  }

  navigateToDetails(activityId: number) {
    this.router.navigate(['activity', activityId]);
  }
}
