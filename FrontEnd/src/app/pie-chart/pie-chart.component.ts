import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { MentorService } from '../Services/mentor.service';
import { Team } from '../Classes/Team';

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
        labels: [] as string[], // Type assertion
        datasets: [
          {
            label: 'Team activity',
            data: [] as number[], // Type assertion
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
  
        // Get team stats and insert them into the graph
        this.teams.forEach((team, index) => {
          this.mentorService.getTeamStats(team.id).subscribe(
            (teamStats: number) => {
              (myChart.data.labels as string[]).push(`Team ${team.id}`); // Type assertion
              (myChart.data.datasets[0].data as number[]).push(teamStats); // Type assertion
              myChart.update();
            },
            (error: any) => {
              console.error(error);
            }
          );
        });
      },
      (error: any) => {
        console.error(error);
      }
    );
  }
  
}
