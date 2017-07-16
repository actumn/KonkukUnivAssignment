/**
 * Created by suh on 2015-04-01.
 */
public class SunMyeong {
    private int health;                 //체력
    private int pleasure;
    private int hungry;                 //배고픔
    private String location;            //위치
    private Phone myPhone;              //내 폰
    private int stress;                 //스트레스(체력보다 높으면 감기)
    private int intelligence;          //지능
    private int refinement;            //교양
    private int homework;              //과제
    private final String name = "이선명";

    public void PrintStatus() {
        System.out.println("######### Status ##################");
        System.out.print("체력 : ");
        printStat(health);
        System.out.print("즐거움 : ");
        printStat(pleasure);
        System.out.print("배고픔 : ");
        printStat(hungry);
        System.out.print("스트레스 : ");
        printStat(stress);
        System.out.print("지식 : ");
        printStat(intelligence);
        System.out.print("교양 : ");
        printStat(refinement);

        System.out.println("과제 :" + homework);
        System.out.println("####################################");
    }

    private void printStat(int stat){
        if(stat > 100){
            System.out.println("" + stat);
        }
        else{
            int max = stat / 10;
            for(int i = 0; i < 10; i++){
                if(i < max){
                    System.out.print("★");
                }
                else{
                    System.out.print("☆");
                }
            }
        }

        System.out.println();
    }

    SunMyeong(){
        this.health = 70;
        this.pleasure = 50;
        this.hungry = 30;
        this.stress = 10;
        this.intelligence = 100;
        this.refinement = 100;
        homework = 2;
        this.location = "집";
        this.myPhone = new Phone();
    }


    public void schedule(int key) {
        if(key == 7){
            System.out.println("===동아리 주회===");
            System.out.println("즐거움 +20");
        }

        else{
            System.out.println("===수업===");
            System.out.println("지식 +20");
            System.out.println("스트레스 +20");
            stress += 20;
            intelligence += 5;
            if(Math.random() > 0.9){
                System.out.println("운 나쁘게 과제가 + 1");
                homework ++;
            }
        }
    }

    public void doHomework(){
        int efficiency = intelligence * (health + pleasure) / stress;
        if(efficiency > 400){
            System.out.println("===과제 능률이 높다===");
            System.out.println("과제 -2");
            System.out.println("즐거움 +10");
            System.out.println("스트레스 +10");
            homework -= 2;
            pleasure += 10;
            stress += 10;

        }
        else if(efficiency > 200){
            System.out.println("===과제 하나 해결===");
            System.out.println("과제 - 1");
            System.out.println("즐거움 +5");
            System.out.println("스트레스 +5");
            homework -= 1;
            pleasure += 10;
            stress += 10;
        }
        else{
            System.out.println("휴식 요망");
        }

    }


    public void goKonkuk(){
        System.out.println("집 -> 학교");
        watchMovie();
        location = "학교";
    }
    public void goHome(){
        System.out.println("학교 -> 집");
        watchMovie();
        location = "집";
    }




    public void downloadMovie(){
        System.out.println("영화를 다운받았다. 영화 + 3");
        myPhone.downloadMovie();
    }
    public void watchMovie() {
        if(myPhone.getMovies() >= 1) {
            System.out.println("===영화 감상===");
            System.out.println("교양 + 20");
            refinement += 20;
            myPhone.removeMovies();
        }
        else{
            System.out.println("no Movies");
        }
    }



    public void happyEnd(){
        System.out.println("일주일을 무사히 넘겼습니다. good");
        Life.end();
    }

    public void checkSick(){
        if(stress > health){
            sickEnd();
        }
    }
    public void sickEnd(){
        System.out.println("감기 걸렸습니다, bad");
        Life.end();
    }


    public String getLocation(){
        return location;
    }



    public void playWithDog() {
        System.out.println("개랑 놀았다.");
        System.out.println("아무 효과 없다");
    }

    public void sleep() {
        System.out.println("잣다.");
        System.out.println("스트레스 0");
    }

    public void playGame() {
        System.out.println("게임했다.");
        System.out.println("즐거움 + 10");
        System.out.println("스트레스 -10");
        pleasure += 10;
        stress -= 10;
    }

    public void eat() {
        System.out.println("밥을 먹었다.");
        System.out.println("체력 +10");
        System.out.println("배고픔 0");
        health += 10;
        hungry = 0;
    }

    public void library() {
        System.out.println("도서관에 가서 공부를 했다.");
        System.out.println("지능 +20");
        System.out.println("스트레스 +20");
        intelligence += 20;
        stress += 20;
    }
}
