import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { MentorService } from '../Services/mentor.service';
import { Team } from '../Classes/Team';
import { TasksService } from '../Services/tasks.service';
import { Task} from '../Classes/Task'
import { Student } from '../Classes/Student';
import { StudentService } from '../Services/student.service';
import { GradeService } from '../Services/grade.service';
import { Observable, forkJoin } from 'rxjs';
@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
})
export class PieChartComponent implements OnInit {
  @ViewChild('myChart', { static: true }) myChartRef!: ElementRef;

  students: Student[] = [];
  activityId=1;
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
  
    this.studentService.getAllTeams().subscribe(
      (teams: Team[]) => {
        const teamMembersObservables: Observable<Student[]>[] = [];
        teams.forEach((team) => {
          teamMembersObservables.push(this.studentService.getTeamMembers(team.id));
        });
  
        forkJoin(teamMembersObservables).subscribe(
          (teamMembersArray: Student[][]) => {
            const allStudents: Student[] = [];
            teamMembersArray.forEach((teamMembers) => {
              allStudents.push(...teamMembers);
            });
  
            this.students = allStudents;
  
            this.taskService.getTasks().subscribe(
              (tasks: Task[]) => {
                tasks.sort((b, a) => a.deadline.localeCompare(b.deadline)); // Sort
                tasks.forEach((task) => {
                  (myChart.data.labels as string[]).push(task.deadline);
                });
                myChart.data.labels = (myChart.data.labels as string[]).reverse();
                this.students.forEach((student) => {
                  const color = this.getRandomColor();
                  const dataset = {
                    label: student.name,
                    data: [] as number[],
                    backgroundColor: color,
                    borderColor: color,
                    borderWidth: 1,
                    pointBackgroundColor: color,
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: color,
                  };
  
                  tasks.forEach((task, index) => {
                    this.gradeService.getStudentMeanGrade(task.id, student.id).subscribe(
                      (studentGrade: number) => {
                        dataset.data[index] = studentGrade;
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
      },
      (error: any) => {
        console.error(error);
      }
    );
  }
  
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
