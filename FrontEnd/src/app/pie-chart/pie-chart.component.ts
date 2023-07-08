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

  teams: Team[] = [];

  constructor(private mentorService: MentorService) {}

  ngOnInit() {
    const ctx = this.myChartRef.nativeElement.getContext('2d');
    const myChart = new Chart(ctx, {
      type: 'line', // Change the chart type to 'line'
      data: {
        labels: [] as string[],
        datasets: [
          {
            label: 'Team activity',
            data: [] as number[],
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1,
            pointBackgroundColor: 'rgba(54, 162, 235, 1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(54, 162, 235, 1)',
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
              (myChart.data.labels as string[]).push(`Team ${team.id}`);
              (myChart.data.datasets[0].data as number[]).push(teamStats);
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
