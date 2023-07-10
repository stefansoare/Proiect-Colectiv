import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student'; 
import { TasksService } from '../Services/tasks.service';
import { ActivatedRoute } from '@angular/router';
import { GradingDialogComponent } from '../grading-dialog/grading-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  task: Task | null = null;
  showTable: boolean = false;
  activityId: number = 0;

  constructor(
    private taskService: TasksService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.activityId = Number(params['aId']);
      this.loadTasks();
    });
  }

  loadTasks() {
    this.taskService.getTasksByActivity(this.activityId).subscribe(
      tasks => {
        this.tasks = tasks;
      },
      error => {
        if (error.status === 302 && error.error) {
          this.task = error.error;
        }

      }
    );
  }
  
  /*get filteredTasks(): Task[] {
    if (!this.searchQuery) {
      return this.tasks;
    }
    return this.tasks.filter(task =>
      task.student_name.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }*/

  deleteTask(task: Task) {
    this.taskService.deleteTask(task.id).subscribe(
      () => {
        const index = this.tasks.indexOf(task);
        if (index > -1) {
          this.tasks.splice(index, 1);
        }
      },
      (error: any) => {
        console.error(error);

      }
    );
  }

  addTask(newTask: Task) {
    this.taskService.createTask(newTask).subscribe(
      task => {
        this.tasks.push(task);
      },
      (error: any) => {
        console.error(error);
        
      }
    );
  }
  createNewTask() {
    const newTask: Task = {
      id: 0, 
      deadline: '', 
      description: '', 
      activity_id: this.activityId, 
    };

    this.addTask(newTask);
  }

  openGradingDialog(task: Task) {
    const dialogRef = this.dialog.open(GradingDialogComponent, {
      data: task
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const updatedTask: Task = {
          id: task.id, 
          deadline:task.deadline, 
          description: task.description, 
          activity_id: task.activity_id, 
        };
        
        this.taskService.patchTask(result.id, task).subscribe(
          updatedTask => {
            // Perform any necessary actions with the updated task
            console.log('Task updated:', updatedTask);
          },
          error => {
            // Handle the error if necessary
            console.error(error);
          }
        );
      }
    });
  }
}