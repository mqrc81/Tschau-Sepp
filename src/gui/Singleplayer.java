package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * main "Game Table" window, specifically for "Singleplayer" gamemode
 *
 * @author: Marc Schmidt
 * @since: 2020-05-14
 */
public class Singleplayer extends NewGame {

    boolean onlyClickOnce = true; //safety brakes, so that a Timer only occurs once
    int toClick;

    /**
     * constructor method
     */
    public Singleplayer() {
        setTitle("Singleplayer | Tschau Sepp Premium");
        playerLabel[currentPlayer].setBackground(green);
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
    }

    /**
     * everything that happens in between two players' moves
     */
    public void nextPlayer() {
        wrongPlayer.stop();
        playerLabel[currentPlayer].setBackground(green);
        playerLabel[currentPlayer].setForeground(Color.BLACK);
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
        //if Ace got played, it doesn't move on to the next player because it's still the same player's turn
        if (!ace) {
            whosNext();
        }
        //if 8 got played, it skips a turn
        if (eight) {
            whosNext();
            eight = false;
        } else if (seven != 0) {
            //if player has a 7, he can (and has to) respond with the 7 instead of receiving penalty cards
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
            doAlgorithm();
        }
    }

    /**
     * evaluates, what the CPU's next move (play/draw card) will be
     */
    public void doAlgorithm() {
        System.out.println("Doing algorithm");
        //if CPU has no valid card to play
        if (!validCard(discard.getNumber(), discard.getSymbol())) {
            toClick = -1;
            onlyClickOnce = true;
            doClickTimer.restart();
        } else if (seven != 0) {
            playCard(7, 0);
        } else {
            playCard(discard.getNumber(), discard.getSymbol());
            //if playing a valid card according to number and symbol isn't successful (in which case it would have moved on to the next player), CPU
            // must have a remaining Jack
            playCard(11, 0);
        }
    }

    /**
     * card gets displayed in the center and removed from the player's hand
     *
     * @param number number (value) of the card played
     * @param symbol symbol (color) of the card played
     */
    public void playCard(int number, int symbol) {
        for (int x = 0; x < players[currentPlayer].handSize(); x++) {
            if (players[currentPlayer].getCards().get(x).getNumber() == number || players[currentPlayer].getCards().get(x).getSymbol() == symbol) {
                toClick = x; //index of card to click
                onlyClickOnce = true;
                doClickTimer.restart();
            }
        }
    }

    /**
     * determines gamemode being played
     *
     * @return gamemode being played (true = "Singleplayer")
     */
    public boolean isSinglePlayer() {
        return true;
    }

    /**
     * determines starting player
     *
     * @return starting player (0 in case of "Singleplayer")
     */
    public int whoStarts() {
        return 0;
    }

    /**
     * Timer that creates a delay in between CPU's moves
     */
    Timer doClickTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (onlyClickOnce) {
                onlyClickOnce = false;
                if (toClick == -1) {
                    drawPileButton.doClick();
                } else {
                    players[currentPlayer].getButtons().get(toClick).doClick();
                }
            } else {
                doClickTimer.stop();
            }
        }
    });

    /**
     * Timer that creates a delay after CPU selects "Tschau"
     */
    Timer doTschauTimer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (onlyClickOnce) {
                onlyClickOnce = false;
                tschauButton.doClick();
            } else {
                doTschauTimer.stop();
            }
        }
    });

    /**
     * Timer that creates a delay after CPU selects "Sepp"
     */
    Timer doSeppTimer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (onlyClickOnce) {
                onlyClickOnce = false;
                seppButton.doClick();
            } else {
                doSeppTimer.stop();
            }
        }
    });
}
