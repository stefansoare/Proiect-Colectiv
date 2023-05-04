import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { StudentsComponent } from './students/students.component';
import { FormsModule } from '@angular/forms';
import { StudentDetailComponent } from './student-detail/student-detail.component';




@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    StudentDetailComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }