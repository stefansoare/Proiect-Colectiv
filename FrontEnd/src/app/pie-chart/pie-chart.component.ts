import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { MentorService } from '../Services/mentor.service';
import { Team } from '../Classes/Team';
import { Student } from '../Classes/Student';
@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
})
export class PieChartComponent implements OnInit {
  @ViewChild('myChart', { static: true }) myChartRef!: ElementRef;

  teams: Team[] = []; // Array to store the teams and their student stats

  constructor(private mentorService: MentorService) {}

  ngOnInit() {
    const ctx = this.myChartRef.nativeElement.getContext('2d');
    const myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: [], // Labels will be populated dynamically
        datasets: [
          {
            label: 'Team activity',
            data: [], // Data will be populated dynamically
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
            ],
            borderWidth: 1,
          },
        ],
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
    this.mentorService.getTeams().subscribe(
      (teams: Team[]) => {
        this.teams = teams;
        this.calculateTeamStats(myChart); // Calculate and update team stats
      },
      (error: any) => {
        console.error(error);
      }
    );
  }

  calculateTeamStats(myChart: Chart) {
    const teamLabels: string[] = [];
    const teamStats: number[] = [];
  
    this.teams.forEach((team: Team) => {
      teamLabels.push(`Team ${team.id}`);
  
      this.mentorService.getTeamMembers(team.id).subscribe(
        (members: Student[]) => {
          let teamSum = 0;
          let membersProcessed = 0;
  
          members.forEach((member: Student) => {
            this.mentorService.getStudentStats(member.id).subscribe((stats: number) => {
              teamSum += stats;
              membersProcessed++;
  
              if (membersProcessed === members.length) {
                // All members of the team processed
                teamStats.push(teamSum);
  
                if (teamStats.length === this.teams.length) {
                  // All teams processed, update the chart data
                  myChart.data.labels = teamLabels;
                  myChart.data.datasets[0].data = teamStats;
                  myChart.update();
                }
              }
            });
          });
        },
        (error: any) => {
          console.error(error);
        }
      );
    });
  }
  
  
  
  
}
