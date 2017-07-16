/**
 * Created by suh on 2015-04-01.
 */
public class Fri extends Days {
    Fri(){
        date = "금요일";
        this.time = 0;
        this.schedules = new Schedule[3];
        schedules[0] = new Schedule(5, 11, 2);
        schedules[1] = new Schedule(6, 13, 2);
        schedules[2] = new Schedule(4, 16, 2);
    }
}
