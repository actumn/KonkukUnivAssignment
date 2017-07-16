/**
 * Created by suh on 2015-04-01.
 */
public class Sun extends Days {

    @Override
    public void live() {
        me.happyEnd();
    }

    Sun(){
        date = "월요일";
        schedules = null;
    }

}
