import { Grade } from "./Grade";
import { Student } from "./Student";

export interface Team {
    id: number;
    name:string;
    students: Student[];
    leader: String;
    mentorID: number;
    activityID: number;
    status: number;
}
