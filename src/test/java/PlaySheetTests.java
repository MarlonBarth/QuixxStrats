import org.darkmarlin.quixx.game.GameMain;
import org.darkmarlin.quixx.game.PlaySheet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlaySheetTests {
    private PlaySheet playSheet;
    private GameMain gameMain;

    @BeforeEach
    public void setUp(){
        gameMain = new GameMain();
        playSheet = new PlaySheet(gameMain);
    }

    @Test
    public void testPlaySheetAvailableMoves() {
        playSheet.setCross(PlaySheet.Rows.RED,2);
        playSheet.setCross(PlaySheet.Rows.RED,3);
        playSheet.setCross(PlaySheet.Rows.RED,4);
        playSheet.setCross(PlaySheet.Rows.RED,5);
        playSheet.setCross(PlaySheet.Rows.RED,7);
        playSheet.setCross(PlaySheet.Rows.RED,12);
        print2D(playSheet.getAllowedMoves());
        System.out.println(playSheet.getPoints());
        System.out.println(gameMain.isRedLock());
    }
    @Test
    public void testPlaySheetPoints(){

    }

    static void print2D(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
