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
        wrongPlayer.stop();
        if (players[currentPlayer].getCards().size() == 0 && !ace) {
            new GameOver(players, this);
        }
        updateHand(players[currentPlayer].getCards(), currentPlayer);
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
                    drawPileButton.doClick();
                    seven--;
                }
                updateHand(players[currentPlayer].getCards(), currentPlayer);
            }
        }
        playerLabel[currentPlayer].setBackground(green);
    }

}
