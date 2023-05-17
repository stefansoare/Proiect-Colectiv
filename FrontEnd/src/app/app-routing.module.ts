import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentsComponent } from './Students-component/students.component';
import { DashboardComponent } from './Dashboard/dashboard.component';
import { StudentDetailComponent } from './Student-detail/student-detail.component';
import { GradesComponent } from './Grades/grades.component';
import { MentorPageComponent } from './Mentor-page/mentor-page.component';
import { StatsComponent } from './stats/stats.component';
import { StudentListComponent } from './student-list/student-list.component';
import { StudentFormComponent } from './student-form/student-form.component';

const routes: Routes = [
  { path: 'students', component: StudentsComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'detail/:id', component: StudentDetailComponent },
  { path: 'grades', component: GradesComponent},
  { path: 'mentor-page', component: MentorPageComponent},
  { path: 'stats', component: StatsComponent},
  { path: 'students', component: StudentListComponent },
  { path: 'addstudent', component: StudentFormComponent },
  { path: 'view', component: StudentListComponent}
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }