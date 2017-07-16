/**
 * Created by suh on 2015-04-01.
 */
public class Thur extends Days {
    Thur(){
        date = "목요일";
        this.time = 0;
        this.schedules = new Schedule[4];
        schedules[0] = new Schedule(2, 9, 2);
        schedules[1] = new Schedule(5, 11, 2);
        schedules[2] = new Schedule(3, 13, 2);
        schedules[3] = new Schedule(7, 18, 6);
    }
}
