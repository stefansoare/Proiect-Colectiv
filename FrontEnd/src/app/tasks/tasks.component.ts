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
  task: Task | null = null;
  showTable: boolean = false;
  activityId: number = 0;
  searchQuery: string = '';
  students: { [studentId: number]: string } = {}; // Map to store student names by student ID

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
        this.tasks = tasks.map(task => {
          const studentName = this.students[task.student_id];
          return {
            ...task,
            student_name: studentName
          };
        });
        this.loadStudents(); // Load student details after fetching tasks
      },
      error => {
        // Handle error if necessary
      }
    );
  }

  loadStudents() {
    for (const task of this.tasks) {
      this.taskService.getStudent(task.student_id).subscribe(
        student => {
          task.student_name = student.name; // Populate student_name field for the task
        },
        error => {
          console.error(error);
          // Handle error, show error message, etc.
        }
      );
    }
  }

  get filteredTasks(): Task[] {
    if (!this.searchQuery) {
      return this.tasks; // If search query is empty, return all tasks
    }

    // Filter tasks based on the search query
    return this.tasks.filter(task =>
      task.student_name.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
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
      activity_id: this.activityId, // Use the activityId obtained from the route
      student_id: 1,
      student_name: ''
    };

    this.addTask(newTask);
  }
}
