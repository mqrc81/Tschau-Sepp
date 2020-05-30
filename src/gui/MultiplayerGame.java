package gui;

import objects.Card;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class MultiplayerGame extends NewGame {

    public MultiplayerGame() {
        setTitle("Multiplayer | Tschau Sepp Premium");
        playerLabel[currentPlayer].setBackground(green);
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
    }

    public void nextPlayer() {
        wrongPlayer.stop();
        if (players[currentPlayer].getCards().size() == 0) {
            sepp = true;
            lastPlayer = currentPlayer;
        } else if (players[currentPlayer].getCards().size() == 1) {
            tschau = true;
            lastPlayer = currentPlayer;
        }
        updateHand(players[currentPlayer].getCards(), currentPlayer, true);
        playerLabel[currentPlayer].setBackground(lightYellow);
        if (!ace) {
            whosNext();
            System.out.println("Player " + (currentPlayer + 1) + "'s turn");
        }
        if (eight) {
            whosNext();
            eight = false;
        } else if (seven != 0) {
            if(!validCard(new Card(7, 0))) {
                while (seven > 0) {
                    players[currentPlayer].addCard(aCard());
                    seven--;
                }
                drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
            }
        }
        updateHand(players[currentPlayer].getCards(), currentPlayer, false);
        playerLabel[currentPlayer].setBackground(green);
    }

}
