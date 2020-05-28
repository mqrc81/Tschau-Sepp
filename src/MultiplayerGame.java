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
                if (clicked.getNumber() == 11) {
                    players[currentPlayer].removeCard(clicked);
                    new ChooseSymbolFrame();
                } else {
                    if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                        System.out.println("Correct Card");
                        discardPileButton.setIcon(img(clicked.getName(), 180, 270));
                        players[currentPlayer].removeCard(clicked);
                        nextPlayer();
                    } else {
                        System.out.println("Error: Invalid Card");
                    }
                }
            } else {
                System.out.println("Error: Wrong Player");
            }
        }
    }


    public class DrawCard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Card played = whatCard(discardPileButton);
            if (!validCard(played)) {
                players[currentPlayer].addCard(aCard());
                drawPileButton.setIcon(img(cardBack(), true, 90, 135));
                updateHand(players[currentPlayer].getCards(), currentPlayer);
                System.out.println("Card received");
                if (cards[cardCounter].getNumber() != played.getNumber() && cards[cardCounter].getSymbol() != played.getSymbol()) {
                    nextPlayer();
                }
            } else {
                System.out.println("Error: Player has valid Card");
            }
        }
    }

    public class ChooseSymbol implements ActionListener {
        ChooseSymbolFrame csf;
        int x;
        public ChooseSymbol(int x, ChooseSymbolFrame csf) {
            this.x = x;
            this.csf = csf;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            discardPileButton.setIcon(img("11_" + (x + 1), 180, 270));
            csf.dispose();
            nextPlayer();
        }
    }

    public class ChooseSymbolFrame extends JFrame {
        public ChooseSymbolFrame() {
            super("Choose Symbol");
            setLayout(new GridBagLayout());
            setIconImage(MultiplayerGame.this.getIconImage());
            setSize(new Dimension(330, 125));
            setResizable(false);
            getContentPane().setBackground(lightBlue);
            JPanel csPanel = new JPanel(new GridLayout(1, 4, 10 ,10));
            csPanel.setBackground(lightBlue);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            add(csPanel, gbc);
            JButton[] jacks = new JButton[4];
            for (int x = 0; x < 4; x++) {
                jacks[x] = new JButton();
                jacks[x].setPreferredSize(new Dimension(70, 105));
                jacks[x].setBackground(lightBlue);
                jacks[x].setIcon(img("11_" + (x + 1), 70, 105));
                csPanel.add(jacks[x]);
                jacks[x].addActionListener(new ChooseSymbol(x, this));
            }
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    new ChooseSymbolFrame();
                }
            });
            setLocationRelativeTo(null);
            setVisible(true);
            pack();
        }
    }

}
