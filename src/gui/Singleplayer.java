package gui;

import objects.Card;

import javax.swing.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Singleplayer extends NewGame {

    public Singleplayer() {
        setTitle("Singleplayer | Tschau Sepp Premium");
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
        updateHand(currentPlayer, currentPlayer != 0);
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
                updateHand(currentPlayer, currentPlayer != 0);
            }
        }
        playerLabel[currentPlayer].setBackground(green);

        if (currentPlayer != 0) {
            computerAlgorithm();
        }

    }

    public void computerAlgorithm() {
        if (!validCard(discard.getNumber(), discard.getSymbol())) {
            discardPileButton.doClick(1000);
        } else {
            if (validCard(7, 0)) {
                //TODO
            } else {
                //TODO
            }
        }
    }

    public boolean isSinglePlayer() {
        return true;
    }
}
