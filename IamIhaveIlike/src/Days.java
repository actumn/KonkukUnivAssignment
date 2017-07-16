import java.util.Scanner;
/**
 * Created by suh on 2015-04-01.
 */
abstract public class Days {
    protected String date;                      //요일 변수 ex) 월요일의 경우 date = "월요일"

    protected static SunMyeong me = new SunMyeong();    //나

    protected int time;         //하루의 시각 ex) 현재 4시 일경우 time = 4

    protected Schedule[] schedules; //일정

    //일정 클래스 선언
    class Schedule{
        Activity activity;
        int when;
        int timeLength;

        Schedule(int Key, int when, int timeLength){
            switch(Key){
                case 1 :
                    activity = new UKG();
                    break;
                case 2 :
                    activity = new Physics();
                    break;
                case 3 :
                    activity = new PP();
                    break;
                case 4 :
                    activity = new DS();
                    break;
                case 5 :
                    activity = new CAA();
                    break;
                case 6 :
                    activity = new UC();
                    break;
                case 7 :
                    activity = new Rotaract();
                    break;
                default:
                    System.out.println("Key Error");
                    break;
            }
            this.when = when;
            this.timeLength = timeLength;
        }
    }

    //하루의 표현
    public void live() {

        System.out.println("********** "  + getDate() + " **********");
        System.out.println("전 항상 7시에 일어나죠");
        time = 7;       //전 항상 7시에 일어나죠

        while(true){

            //24시가 지나면 다음날로
            if(time >= 24){
                //24시가 되면 집에 가야해요
                if(me.getLocation() == "학교"){
                    me.goHome();
                }
                break;
            }


            //현재 시각과 상태 출력
            System.out.println("현재 시각 : " + time + "시");
            me.PrintStatus();



            //2시간뒤 스케쥴이 있는데 집일경우 학교로
            if(isSchedule((time + 2)) && me.getLocation() == "집"){
                me.goKonkuk();
                time += 2;
            }
            //스케쥴이 있을경우 스케쥴 실행
            else if(isSchedule(time)){
                doSchedule(time);
                time += 2;
            }
            //스케쥴이 없을경우 선택지
            else {
                Menu();
            }

        }
    }

    int choice = 0;
    Scanner sc = new Scanner(System.in);
    protected void Menu(){
        System.out.println("============== 선 택 ==============");
        if(me.getLocation() == "집"){
            System.out.println("0. 오늘의 일정");
            System.out.println("1. 개랑 논다");
            System.out.println("2. 잔다");
            System.out.println("3. 과제를 한다");
            System.out.println("4. 게임을 한다");
            System.out.println("5. 영화를 다운받는다");
            System.out.println("6. 학교에 간다");
            System.out.println("===================================");
            choice = sc.nextInt();
            MenuAtHome(choice);
        }
        else if(me.getLocation() == "학교"){
            System.out.println("0. 오늘의 일정");
            System.out.println("1. 밥을 먹는다");
            System.out.println("2. 도서관을 간다");
            System.out.println("3. 집을 간다");
            System.out.println("===================================");
            choice = sc.nextInt();
            MenuAtKonkuk(choice);
        }
    };

    protected void MenuAtHome(int choice){
        switch (choice){
            case 0 :
                printSchedule();
                break;
            case 1 :
                me.playWithDog();
                time++;
                break;
            case 2 :
                me.sleep();
                time++;
                break;
            case 3 :
                me.doHomework();
                time++;
                break;
            case 4 :
                me.playGame();
                time++;
                break;
            case 5 :
                me.downloadMovie();
                time++;
                break;
            case 6 :
                me.goKonkuk();
                time += 2;
                break;
            default :
                System.out.println("Input integer 0 ~ 6");
                break;
        }

    }
    protected void MenuAtKonkuk(int choice){
        switch (choice){
            case 0 :
                printSchedule();
                break;
            case 1 :
                me.eat();
                time ++;
                break;
            case 2 :
                me.library();
                time ++;
                break;
            case 3 :
                me.goHome();
                time += 2;
                break;
            default :
                System.out.println("Input integer 0 ~ 3");
                break;
        }
    }




    //오늘의 일정 표시
    protected void printSchedule() {
        System.out.println("============오늘의 일정============");
        for(int i = 0; i < schedules.length; i++){
            System.out.println(schedules[i].activity.name + " " + schedules[i].when + "시");
        }
        System.out.println("==================================");
    }



    //일정이 있으면 true, 없으면 false return
    protected boolean isSchedule(int presentTime){

        for(Schedule s : schedules){
            if(s.when == presentTime) {
                return true;
            }
        }
        return false;
    }
    protected void doSchedule(int presentTime){
        for(Schedule s : schedules){
            if(s.when == presentTime) {
                System.out.println("일정 : " + s.activity.name);
                me.schedule(s.activity.key);
            }
        }
    }


    protected String getDate(){
        return date;
    }
}
