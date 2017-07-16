/**
 * Created by suh on 2015-04-01.
 */
public class Tues extends Days {
    Tues(){
        date = "화요일";
        this.time = 0;
        this.schedules = new Schedule[2];
        schedules[0] = new Schedule(3, 9, 2);
        schedules[1] = new Schedule(2, 11, 2);
    }
}
