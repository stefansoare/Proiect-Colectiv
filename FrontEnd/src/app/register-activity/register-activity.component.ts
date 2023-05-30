import { Component } from '@angular/core';
import { Activity } from '../Classes/Activity';
import { ActivitiesService } from '../Services/activities.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogRegisterComponent } from '../dialog-register/dialog-register.component';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';

@Component({
  selector: 'app-register-activity',
  templateUrl: './register-activity.component.html',
  styleUrls: ['./register-activity.component.css']
})
export class RegisterActivityComponent {
  activities: Activity[] = [];
  dialogRef: MatDialogRef<DialogRegisterComponent> | undefined;

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

  openDialog(activity: Activity) {
    this.dialogRef = this.matDialog.open(DialogRegisterComponent);

    // Create a new task with the activity ID and student ID = 1
    const newTask: Task = {
      id: 0, // The actual ID will be assigned by the server
      attendence: 0, // Set the attendence value as needed
      comment: '', // Set the comment value as needed
      deadline: '', // Set the deadline value as needed
      description: '', // Set the description
      grade: 0, // Set the grade value as needed
      activity_id: Number(activity.id),
      student_id: 1, // Assuming student ID 1
      student_name:''
    };

    // Call the createTask method to create the task
    this.tasksService.createTask(newTask).subscribe(
      (task: Task) => {
        // Task created successfully
        console.log('Task created:', task);
      },
      (error: any) => {
        // Handle error if necessary
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
