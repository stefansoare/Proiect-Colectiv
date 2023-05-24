import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentsComponent } from './Students-component/students.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StudentDetailComponent } from './student-detail/student-detail.component';
import { GradesComponent } from './grades/grades.component';
import { MentorPageComponent } from './mentor-page/mentor-page.component';
import { StatsComponent } from './stats/stats.component';
import { StudentListComponent } from './student-list/student-list.component';
import { MentorAccountComponent } from './mentor-account/mentor-account.component';
import { TasksComponent } from './tasks/tasks.component';

const routes: Routes = [
  { path: 'students', component: StudentsComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'detail/:id', component: StudentDetailComponent },
  { path: 'grades', component: GradesComponent},
  { path: 'mentor-page', component: MentorPageComponent},
  { path: 'stats', component: StatsComponent},
  { path: 'lista', component: StudentListComponent},
  { path: 'mentor-account' , component: MentorAccountComponent},
  { path: 'tasks', component: TasksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }