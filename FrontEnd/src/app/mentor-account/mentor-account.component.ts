import { Component, Input, OnInit } from '@angular/core';
import { Mentor } from '../Classes/Mentor';
import { MentorService } from '../Services/mentor.service';
@Component({
  selector: 'app-mentor-account',
  templateUrl: './mentor-account.component.html',
  styleUrls: ['./mentor-account.component.css']
})
export class MentorAccountComponent {

  mentors: Mentor[] = [];
  mentor: Mentor | null = null;
  
  constructor(private mentorService: MentorService) { }

  ngOnInit() {
    this.mentorService.getMentors().subscribe(
      mentors => this.mentors = mentors
    );

    this.mentorService.getMentor(1).subscribe(
      mentor => this.mentor = mentor,
      error => {
        console.error(error);
        // Handle error, show error message, etc.
        if (error.status === 302 && error.error) {
          this.mentor = error.error;
        }
      }
    );
  }
}
