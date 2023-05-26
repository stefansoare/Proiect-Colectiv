import { Component } from '@angular/core';
import { Activity } from '../Classes/Activity';
import { ActivitiesService } from '../Services/activities.service';

@Component({
  selector: 'app-register-activity',
  templateUrl: './register-activity.component.html',
  styleUrls: ['./register-activity.component.css']
})
export class RegisterActivityComponent {
  activities: Activity[] = [];

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit() {
    this.activitiesService.getActivities().subscribe(
      activities => {
        this.activities = activities;
      }
    );
  }

}
