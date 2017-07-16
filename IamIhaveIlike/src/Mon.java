/**
 * Created by suh on 2015-04-01.
 */
public class Mon extends Days {
    Mon(){
        date = "월요일";
        this.time = 0;
        this.schedules = new Schedule[1];
        schedules[0] = new Schedule(1, 12 ,2);
    }
}
