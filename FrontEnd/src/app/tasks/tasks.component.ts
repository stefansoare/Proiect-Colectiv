import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student'; // Import the Student type
import { TasksService } from '../Services/tasks.service';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';

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
  students: { [studentId: number]: Student } = {}; // Map to store student objects by student ID

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
        this.loadStudents(); // Load student details after fetching tasks
      },
      error => {
        if (error.status === 302 && error.error) {
          this.task = error.error;
        // Handle error if necessary
        }
      }
    );
  }

  loadStudents() {
    const studentIds = Array.from(new Set(this.tasks.map(task => task.student_id))); // Get unique student IDs from tasks
  
    const requests = studentIds.map(studentId =>
      this.taskService.getStudent(studentId)
    );
  
    forkJoin(requests).subscribe(
      students => {
        students.forEach(student => {
          this.students[student.id] = student; // Store student objects in the map
        });
  
        // Populate the student_name field for each task
        this.tasks.forEach(task => {
          const student = this.students[task.student_id];
          if (student) {
            task.student_name = student.name;
          }
        });
      },
      error => {
        console.error(error);
        if (error.status === 302 && error.headers.get('location')) {
          const redirectedUrl = error.headers.get('location');
          const redirectedStudentId = parseInt(redirectedUrl.split('/').pop() || '', 10);
          if (!isNaN(redirectedStudentId)) {
            this.taskService.getStudent(redirectedStudentId).subscribe(
              student => {
                this.students[student.id] = student;
                this.tasks.forEach(task => {
                  if (task.student_id === student.id) {
                    task.student_name = student.name;
                  }
                });
              },
              error => {
                console.error(error);
                // Handle error, show error message, etc.
              }
            );
          }
        }
      }
    );
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