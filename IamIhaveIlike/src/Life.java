/**
 * Created by suh on 2015-04-01.
 */
public class Life {
    public static void main(String[] args){
        Days[] week = new Days[7];
        week[0] = new Mon();
        week[1] = new Tues();
        week[2] = new Wed();
        week[3] = new Thur();
        week[4] = new Fri();
        week[5] = new Sat();
        week[6] = new Sun();

        for(Days d : week){
            d.live();
        }
    }

    public static void end(){
        System.out.println("The end");
        System.exit(0);
    }
}
