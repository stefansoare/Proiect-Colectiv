import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { TasksService } from '../Services/tasks.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  showTable: boolean = false;
  activityId: number = 0;

  constructor(
    private taskService: TasksService,
    private route: ActivatedRoute
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
        // Handle error if necessary
      }
    );
  }
  getTasksByActivity(activityId: number) {
    this.taskService.getTasksByActivity(activityId).subscribe(
      tasks => {
        this.tasks = tasks;
      },
      error => {
        console.error(error);
      }
    );
  }

  toggleTableVisibility() {
    this.showTable = !this.showTable;
  }

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
      id: 0, // The actual ID will be assigned by the server
      attendence: 0,
      comment: '',
      deadline: '', // Provide the desired deadline value
      description: '', // Provide the task description
      grade: 0,
      activity_id: this.activityId ,// Use the activityId obtained from the route,
      student_id: 1
    };

    this.addTask(newTask);
  }
}
