/**
 * Created by suh on 2015-04-01.
 */
public class Phone {
    private int Movies;

    public void downloadMovie() {
        this.Movies += 3;
    }

    private void call(String Someone){
    }

    Phone(){
        this.Movies = 0;
    }

    public int getMovies(){
        return Movies;
    }

    public void removeMovies(){
        Movies -= 1;
    }
}
