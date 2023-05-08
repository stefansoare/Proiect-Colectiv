import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

import { StudentsComponent } from './students/students.component';
import { FormsModule } from '@angular/forms';
import { StudentDetailComponent } from './student-detail/student-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';
import { StudentSearchComponent } from './student-search/student-search.component';
import { GradesComponent } from './grades/grades.component';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatMenuModule} from '@angular/material/menu';
import { MentorPageComponent } from './mentor-page/mentor-page.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCheckbox, MatCheckboxModule} from '@angular/material/checkbox';




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
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatTabsModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatCheckboxModule,
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false })
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }