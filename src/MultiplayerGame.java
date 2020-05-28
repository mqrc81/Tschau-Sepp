import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class MultiplayerGame extends NewGame {

    public MultiplayerGame() {
        setTitle("Multiplayer | Tschau Sepp Premium");
        drawPileButton.addActionListener(new DrawCard());
        //
        playerLabel[currentPlayer].setBackground(green);
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
    }

    public void nextPlayer() {
        updateHand(players[currentPlayer].getCards(), currentPlayer);
        playerLabel[currentPlayer].setBackground(lightYellow);
        if (playerRotation == 1) {
            if (currentPlayer == 3) {
                currentPlayer = 0;
            } else {
                currentPlayer++;
            }
        } else {
            if (currentPlayer == 0) {
                currentPlayer = 3;
            } else {
                currentPlayer--;
            }
        }
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
        playerLabel[currentPlayer].setBackground(green);
    }

}
