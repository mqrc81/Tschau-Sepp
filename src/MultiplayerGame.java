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

    public void updateHand(List<Card> cards, int player) {
        handPanel[player].removeAll();
        handPanel[player].setBackground(lightBlue);
        //
        if (player % 2 == 0) { //Spieler oben oder unten -> 7 Karten -> 7x1
            handPanel[player].setSize(cards.size() * 75 - 5, 105);
            handPanel[player].setLayout(new GridLayout(1, cards.size(), 5, 5));
            JButton[] button = new JButton[cards.size()];
            int x = 0;
            for (Card card : cards) {
                button[x] = new JButton();
                button[x].setPreferredSize(new Dimension(70, 105));
                button[x].setBackground(lightBlue);
                button[x].setIcon(img(card.getName()));
                handPanel[player].add(button[x]);
                button[x].addActionListener(new PlayCard(player));
                x++;
            }
        } else { //Spieler links oder rechts -> 7 Karten -> 2x4
            handPanel[player].setSize(145, ((cards.size() + 1) / 2) * 110 - 5);
            handPanel[player].setLayout(new GridLayout(((cards.size() + 1) / 2), 2, 5, 5));
            int x = 0;
            JButton[] button = new JButton[cards.size()];
            for (Card card : cards) {
                button[x] = new JButton();
                button[x].setPreferredSize(new Dimension(70, 105));
                button[x].setBackground(lightBlue);
                button[x].setIcon(img(card.getName()));
                if (player == 1 && cards.size() % 2 == 1 && (x + 1) == cards.size()) {
                    JButton emptyButton = new JButton();
                    emptyButton.setBackground(lightBlue);
                    emptyButton.setFocusable(false);
                    emptyButton.setEnabled(false);
                    emptyButton.setBorder(null);
                    handPanel[player].add(emptyButton);
                }
                handPanel[player].add(button[x]);
                button[x].addActionListener(new PlayCard(player));
                x++;
            }
        }
        //
        handPanel[player].revalidate();
        handPanel[player].repaint();
    }

    public boolean validCard(Card played) {
        boolean valid = false;
        for (Card c: players[currentPlayer].getCards()) {
            if (c.getSymbol() == played.getSymbol() || c.getNumber() == played.getNumber()) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public ImageIcon img(String name) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/cards/" + name + ".png"));
        Image img = icon.getImage().getScaledInstance(70, 105, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img, name);
    }

    public class PlayCard implements ActionListener {

        int player;

        public PlayCard(int player) {
            this.player = player;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (currentPlayer == player) {
                Card clicked = whatCard((JButton)ae.getSource());
                Card played = whatCard(discardPileButton);
                if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                    System.out.println("Correct Card");
                    discardPileButton.setIcon(img(clicked.getName(), 180, 270));
                    players[currentPlayer].removeCard(clicked);
                    nextPlayer();
                } else {
                    System.out.println("Error: Invalid Card");
                }
            } else {
                System.out.println("Error: Wrong Player");
            }
        }
    }


    public class DrawCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Card played = MultiplayerGame.this.whatCard(discardPileButton);
            if (!MultiplayerGame.this.validCard(played)) {
                players[currentPlayer].addCard(MultiplayerGame.this.aCard());
                drawPileButton.setIcon(MultiplayerGame.this.img(MultiplayerGame.this.cardBack(), true, 90, 135));
                MultiplayerGame.this.updateHand(players[currentPlayer].getCards(), currentPlayer);
                System.out.println("Card received");
                if (cards[cardCounter].getNumber() != played.getNumber() && cards[cardCounter].getSymbol() != played.getSymbol()) {
                    MultiplayerGame.this.nextPlayer();
                }
            } else {
                System.out.println("Error: Player has valid Card");
            }
        }
    }

}
