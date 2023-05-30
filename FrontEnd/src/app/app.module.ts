import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { StudentsComponent } from './Students-component/students.component';
import { FormsModule } from '@angular/forms';
import { StudentDetailComponent } from './student-detail/student-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { StudentSearchComponent } from './student-search/student-search.component';
import { GradesComponent } from './grades/grades.component';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatMenuModule} from '@angular/material/menu';
import { MentorPageComponent } from './mentor-page/mentor-page.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCheckbox, MatCheckboxModule} from '@angular/material/checkbox';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import { NavigationComponent } from './Navigation/navigation.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTableModule} from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { StatsComponent } from './stats/stats.component';
import { NgChartsModule } from 'ng2-charts';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import {MatCardModule} from '@angular/material/card';
import { StudentListComponent } from './student-list/student-list.component';
import { MentorAccountComponent } from './mentor-account/mentor-account.component';
import { TasksComponent } from './tasks/tasks.component';
import { SidebarTasksComponent } from './Sidebars/sidebar-tasks/sidebar-tasks.component';
import { SidebarMentorComponent } from './Sidebars/sidebar-mentor/sidebar-mentor.component';
import { SidebarTeamleaderComponent } from './Sidebars/sidebar-teamleader/sidebar-teamleader.component';
import { SidebarStudentComponent } from './Sidebars/sidebar-student/sidebar-student.component';
import { RegisterActivityComponent } from './register-activity/register-activity.component';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { StudentGradesComponent } from './student-grades/student-grades.component';
import { DialogRegisterComponent } from './dialog-register/dialog-register.component';
import { GradingPageComponent } from './grading-page/grading-page.component';
import { GradingDialogComponent } from './grading-dialog/grading-dialog.component';
import { MentorListComponent } from './mentor-list/mentor-list.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    StudentDetailComponent,
    MessagesComponent,
    DashboardComponent,
    StudentSearchComponent,
    GradesComponent,
    MentorPageComponent,
    NavigationComponent,
    StatsComponent,
    PieChartComponent,
    StudentListComponent,
    MentorAccountComponent,
    TasksComponent,
    SidebarTasksComponent,
    SidebarMentorComponent,
    SidebarTeamleaderComponent,
    SidebarStudentComponent,
    RegisterActivityComponent,
    DialogBoxComponent,
    StudentGradesComponent,
    DialogRegisterComponent,
    GradingPageComponent,
    GradingDialogComponent,
    MentorListComponent
    
    
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatCardModule,
    MatTabsModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatCheckboxModule,
    MatDividerModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    MatTableModule,
    MatInputModule,
    NgChartsModule,
    MatDialogModule,
    
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }