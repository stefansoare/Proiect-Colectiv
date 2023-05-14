import { Grade } from "./Classes/Grade";
import {Team} from './Classes/Team';

export const GRADES: Grade[] = [
    {date: "23.01.2023", grade: 10, attendance: true},
    {date: "25.01.2023", grade: 8, attendance: true},
    {date: "26.01.2023", grade: 6, attendance: false},
    {date: "29.01.2023", grade: 10, attendance: true},




];
export const TEAMS: Team[] = [
    { id: 1, name: 'Team 1', students: ['Student 1', 'Student 2', 'Student 3'], leader:'Student 1', mentorID:1, activityID:2, grade1:2},
    { id: 2, name: 'Team 2', students: ['Student 4', 'Student 5', 'Student 6'] , leader:'Student 1', mentorID:1, activityID:2, grade1:2},
    { id: 3, name: 'Team 3', students: ['Student 7', 'Student 8', 'Student 9'], leader:'Student 1', mentorID:1, activityID:2, grade1:2 }



];