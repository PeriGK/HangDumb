import perigk.hangdumb.core.*;
public class Main {

    public static void main(String[] args) {
        String wordsFileName = "resources/english.txt";
        Player p = new Player("Peri", 0, 0, 0);
        int totalChances = 5;
        Game g = new Game(p, wordsFileName, totalChances);
        g.start();
    }
}
