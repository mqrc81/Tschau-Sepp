package gui;

import objects.Card;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Multiplayer extends NewGame {

    public Multiplayer() {
        setTitle("Multiplayer | Tschau Sepp Premium");
        playerLabel[currentPlayer].setBackground(green);
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
    }

    public void nextPlayer() {
        wrongPlayer.stop();
        if (players[currentPlayer].handSize() == 0) {
            sepp = true;
            lastPlayer = currentPlayer;
        } else if (players[currentPlayer].handSize() == 1) {
            tschau = true;
            lastPlayer = currentPlayer;
        }
        updateHand(currentPlayer, !singlePlayer || currentPlayer != 0);
        playerLabel[currentPlayer].setBackground(lightYellow);
        if (!ace) {
            whosNext();
            System.out.println("Player " + (currentPlayer + 1) + "'s turn");
        }
        if (eight) {
            whosNext();
            eight = false;
        } else if (seven != 0) {
            if(!validCard(7, 0)) {
                while (seven > 0) {
                    players[currentPlayer].addCard(aCard());
                    newButton(currentPlayer);
                    seven--;
                }
                drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
            }
        }
        updateHand(currentPlayer, singlePlayer && currentPlayer != 0);
        playerLabel[currentPlayer].setBackground(green);
    }

    public boolean isSinglePlayer() {
        return false;
    }
}
