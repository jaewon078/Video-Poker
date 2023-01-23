/*****************************************
 * PokerTest class for Video Poker game
 * Jaewon Lee
 * My PokerTest Class w/ Main Method
 ****************************************/

public class PokerTest {

    // this class must remain unchanged
    // your code must work with this test class

    public static void main(String[] args) {
        if (args.length < 1) {
            Game g = new Game();
            g.play();
        } else {
            Game g = new Game(args);
            g.play();
        }
    }

}
