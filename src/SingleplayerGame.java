import java.util.List;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class SingleplayerGame extends NewGame {

    public SingleplayerGame() {
        setTitle("Singleplayer | Tschau Sepp Premium");
    }

    public void nextPlayer() {
        updateHand(players[currentPlayer].getCards(), currentPlayer);
        playerLabel[currentPlayer].setBackground(lightYellow);
        if (currentPlayer == 3) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
        playerLabel[currentPlayer].setBackground(green);
    }

}
