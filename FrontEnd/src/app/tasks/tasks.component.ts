import { Component } from '@angular/core';
import {Task} from  '../Classes/Task';
import { TasksService } from '../Services/tasks.service';
@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent {
  tasks: Task[] = [];
  showTable: boolean = false;

  constructor(private taskService: TasksService) {}

  ngOnInit() {
    this.taskService.getTasks().subscribe(
      tasks => {
        this.tasks = tasks;
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
        // Handle error if necessary
      }
    );
  }
  addTask(newTask: Task) {
    this.taskService.createTask(newTask).subscribe(
      task => {
        this.tasks.push(task);
      },
      (error: any) => {
        // Handle error if necessary
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
      activity_id: 0 // Provide the activity ID
    };

    this.addTask(newTask);
  }
}
