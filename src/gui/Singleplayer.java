package gui;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Singleplayer extends NewGame {

    boolean onlyClickOnce = true;
    int toClick;

    public Singleplayer() {
        setTitle("Singleplayer | Tschau Sepp Premium");
        playerLabel[currentPlayer].setBackground(green);
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
    }

    public void nextPlayer() {
        wrongPlayer.stop();
        if (players[currentPlayer].handSize() == 0) {
            sepp = true;
            if (currentPlayer != 0) {
                onlyClickOnce = true;
                doSeppTimer.restart();
            } else {
                lastPlayer = currentPlayer;
            }
        } else if (players[currentPlayer].handSize() == 1) {
            if (currentPlayer != 0) {
                onlyClickOnce = true;
                doTschauTimer.restart();
                doTschauTimer.stop();
            } else {
                tschau = true;
                lastPlayer = currentPlayer;
            }
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

        doClickTimer.stop();
        if (currentPlayer != 0) {
            System.out.println("++++++++" + currentPlayer);
            doAlgorithm();
        }
    }

    public void doAlgorithm() {
        System.out.println("Doing algorithm");
        if (!validCard(discard.getNumber(), discard.getSymbol())) {
            System.out.println("No matching Card");
            toClick = -1;
            onlyClickOnce = true;
            doClickTimer.restart();
        } else if (seven != 0) {
            playCard(7, 0);
        } else {
            playCard(discard.getNumber(), discard.getSymbol());
            playCard(11, 0);
        }
    }

    public void playCard(int number, int symbol) {
        for (int x = 0; x < players[currentPlayer].handSize(); x++) {
            if (players[currentPlayer].getCards().get(x).getNumber() == number || players[currentPlayer].getCards().get(x).getSymbol() == symbol) {
                System.out.println("Played Card matching [" + number + ", " + symbol + "]");
                toClick = x;
                onlyClickOnce = true;
                doClickTimer.restart();
            }
        }
        System.out.println("No Card matching [" + number + ", " + symbol + "] | Jack played");
    }

    public boolean isSinglePlayer() {
        return true;
    }

    public int whoStarts() {
        return 0;
    }

    public void stopTimer(Timer t) {
        t.stop();
    }

    public Timer doClickTimer = new Timer(2500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (onlyClickOnce) {
                if (toClick == -1) {
                    onlyClickOnce = false;
                    drawPileButton.doClick();
                } else {
                    onlyClickOnce = false;
                    players[currentPlayer].getButtons().get(toClick).doClick();
                }
            } else {
                stopTimer(doClickTimer);
            }
        }
    });

    public Timer doTschauTimer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (onlyClickOnce) {
                onlyClickOnce = false;
                tschauButton.doClick();
            } else {
                stopTimer(doTschauTimer);
            }
        }
    });

    public Timer doSeppTimer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (onlyClickOnce) {
                onlyClickOnce = false;
                seppButton.doClick();
            } else {
                Singleplayer.this.stopTimer(doSeppTimer);
            }
        }
    });
}
