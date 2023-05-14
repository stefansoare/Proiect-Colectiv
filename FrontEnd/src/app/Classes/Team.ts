import { Grade } from "./Grade";
import { Student } from "./Student";

export interface Team {
    id: number;
    name:string;
    students: string[];
    leader: String;
    mentorID: number;
    activityID: number;
    grade1: number;
}
