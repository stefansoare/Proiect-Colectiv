import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { StudentsComponent } from './Students-component/students.component';
import { FormsModule } from '@angular/forms';
import { StudentDetailComponent } from './Student-detail/student-detail.component';
import { MessagesComponent } from './Messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './Dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './Services/in-memory-data.service';
import { StudentSearchComponent } from './Student-search/student-search.component';
import { GradesComponent } from './Grades/grades.component';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatMenuModule} from '@angular/material/menu';
import { MentorPageComponent } from './Mentor-page/mentor-page.component';
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
import { StudentFormComponent } from './student-form/student-form.component';
import { StudentService } from './Services/student.service';


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
    StudentFormComponent,
    
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
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false })
    
  ],
  providers: [StudentService],
  bootstrap: [AppComponent]
})
export class AppModule { }