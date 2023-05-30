import { Component, OnInit } from '@angular/core';
import { Task } from '../Classes/Task';
import { Student } from '../Classes/Student'; 
import { TasksService } from '../Services/tasks.service';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import { GradingDialogComponent } from '../grading-dialog/grading-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  student: Student |null=null;
  task: Task | null = null;
  showTable: boolean = false;
  activityId: number = 0;
  studentId: number=0;
  searchQuery: string = '';
  students: { [studentId: number]: Student } = {}; 

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
        tasks.forEach(task => {
          this.loadStudent(task.student_id);
        });
      },
      error => {
        if (error.status === 302 && error.error) {
          this.task = error.error;
        }

      }
    );
  }

  loadStudent(studentId: number) {
    this.taskService.getStudent(studentId).subscribe(
      student => {
        this.tasks.forEach(task => {
          if (task.student_id === student.id) {
            task.student_name = student.name;
          }
        });
      },
      error => {
        if (error.status === 302 && error.error) {
          this.task = error.error;
        }
      }
    );
  }
  
  get filteredTasks(): Task[] {
    if (!this.searchQuery) {
      return this.tasks;
    }
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
      id: 0, 
      attendance: 0,
      comment: '',
      deadline: '', 
      description: '', 
      grade: 0,
      activity_id: this.activityId, 
      student_id: 1,
      student_name: ''
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
          comment: task.comment,
          deadline:task.deadline, 
          description: task.description, 
          activity_id: task.activity_id, 
          student_id: task.student_id,
          student_name: task.student_name,
          grade: result.grade,
          attendance: result.attendance
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