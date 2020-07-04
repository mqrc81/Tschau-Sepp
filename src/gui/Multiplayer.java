package gui;

/**
 * main "Game Table" window, specifically for "Multiplayer" gamemode
 *
 * @author Marc Schmidt
 * @since 2020-05-14
 * @version 1.3
 */
public class Multiplayer extends NewGame {

    /**
     * constructor method
     */
    public Multiplayer() {
        setTitle("Multiplayer | Tschau Sepp Premium");
        playerLabel[currentPlayer].setBackground(green);
    }

    /**
     * everything that happens in between two players' moves
     */
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
            }
        }
        updateHand(currentPlayer, singlePlayer && currentPlayer != 0);
        playerLabel[currentPlayer].setBackground(green);
    }

    /**
     * determines gamemode being played
     *
     * @return gamemode being played (true = "Singleplayer")
     */
    public boolean isSinglePlayer() {
        return false;
    }

    /**
     * determines starting player
     *
     * @return random starting player
     */
    public int whoStarts() {
        return (int)(Math.random() * 4);
    }

    public void doAlgorithm() {}
}
