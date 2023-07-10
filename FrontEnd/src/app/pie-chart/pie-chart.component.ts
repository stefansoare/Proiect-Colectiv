import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { MentorService } from '../Services/mentor.service';
import { Team } from '../Classes/Team';
import { TasksService } from '../Services/tasks.service';
import { Task} from '../Classes/Task'
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { GradeService } from '../Services/grade.service';
@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
})
export class PieChartComponent implements OnInit {
  @ViewChild('myChart', { static: true }) myChartRef!: ElementRef;

  students: Student[] = [];
  activityId=5;
  tasks: Task[]=[];
  constructor(private mentorService: MentorService, private taskService: TasksService, private studentService: StudentService, private gradeService: GradeService) {}

  ngOnInit() {
    const ctx = this.myChartRef.nativeElement.getContext('2d');
    const myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: [] as string[],
        datasets: [],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  
    // Fetch teams from MentorService
    this.studentService.getTeamMembers(1).subscribe(
      (students: Student[]) => {
        this.students = students;
  
        // Get task deadlines
        this.taskService.getTasksByActivity(this.activityId).subscribe(
          (tasks: Task[]) => {
            tasks.forEach((task) => {
              (myChart.data.labels as string[]).push(task.deadline);
            });
            myChart.data.labels = (myChart.data.labels as string[]).reverse();
            // Get student mean grades for each task
            this.students.forEach((student) => {
              const color=this.getRandomColor;
              const dataset = {
                label: student.name,
                data: [] as number[],
                backgroundColor: color,
                borderColor:color,
                borderWidth: 1,
                pointBackgroundColor: color,
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: color,
              };
  
              tasks.forEach((task) => {
                this.gradeService.getStudentMeanGrade(task.id, student.id).subscribe(
                  (studentGrade: number) => {
                    (dataset.data as number[]).push(studentGrade);
                    myChart.update();
                  },
                  (error: any) => {
                    console.error(error);
                  }
                );
              });
  
              myChart.data.datasets.push(dataset);
            });
            myChart.update();
          },
          (error: any) => {
            console.error(error);
          }
        );
      },
      (error: any) => {
        console.error(error);
      }
    );
  }
  
  // Utility method to generate random color
  getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
  
  
  loadTasks() {
    this.taskService.getTasksByActivity(this.activityId).subscribe(
      tasks => {
        this.tasks = tasks;
      },
      error => {
        if (error.status === 302 && error.error) {
          this.tasks = error.error;
        }

      }
    );
  }
}
