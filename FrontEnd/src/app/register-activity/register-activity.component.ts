import { Component } from '@angular/core';
import { Activity } from '../Classes/Activity';
import { ActivitiesService } from '../Services/activities.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogRegisterComponent } from '../dialog-register/dialog-register.component';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';
import { Team } from '../Classes/Team';


@Component({
  selector: 'app-register-activity',
  templateUrl: './register-activity.component.html',
  styleUrls: ['./register-activity.component.css']
})
export class RegisterActivityComponent {
  activities: Activity[] = [];
  dialogRef: MatDialogRef<DialogRegisterComponent> | undefined;
  studentId = 1

  constructor(
    private activitiesService: ActivitiesService,
    public matDialog: MatDialog,
    private tasksService: TasksService
  ) { }

  ngOnInit() {
    this.activitiesService.getActivities().subscribe(
      activities => {
        this.activities = activities;
      }
    );
  }
  

  openDialog(activity: Activity, teamId: number) {
    this.dialogRef = this.matDialog.open(DialogRegisterComponent);
    this.activitiesService.assignActivity(1,activity.id).subscribe(
      (error: any) => {
        console.error(error);
      }
    );
  }

  closeDialog() {
    if (this.dialogRef) {
      this.dialogRef.close();
    }
  }
}
