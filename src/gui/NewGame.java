package gui;

import objects.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public abstract class NewGame extends JFrame {
    protected final Card[] cards = new Card[104];
    protected final Player[] players = new Player[4];

    protected int currentPlayer = (int)(Math.random() * 4);
    protected int cardCounter = -1;

    protected int seven = 0;
    protected boolean eight = false;
    protected int ten = 1;
    protected boolean ace = false;
    protected boolean tschau = false;
    protected boolean sepp = false;
    protected int lastPlayer;

    protected JPanel[] handPanel = new JPanel[4];
    protected JLabel[] playerLabel = new JLabel[4];
    protected JButton tschauButton, seppButton, drawPileButton, discardPileButton;

    private JPanel topPanel, bottomPanel, leftPanel, rightPanel, centerPanel, discardPilePanel, extraButtonsPanel;

    protected final Color lightBlue = new Color(229, 244, 255);
    protected final Color turquoise = new Color(0, 127, 127);
    protected final Color lightYellow = new Color(255, 255, 153);
    protected final Color green = new Color(100, 255, 100);
    protected final Color lightRed = new Color(255, 153, 153);
    protected final Color darkRed = new Color(124, 9, 15);
    protected final Font font = new Font("Dubai Medium", Font.PLAIN, 24);
    protected GridBagConstraints gbc = new GridBagConstraints();

    public NewGame() {
        gui1();
        createGame();
        gui2();
    }

    public void gui1() {
        //BASICS: -----------------------------------------------------------------------------------
        setSize(1200, 1000);
        setMinimumSize(new Dimension(1250, 1050));
        setLayout(new GridBagLayout());
        getContentPane().setBackground(lightBlue);
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/icons/icon2.png"));
        setIconImage(icon.getImage());

        //COMPONENTS: -----------------------------------------------------------------------------------
        topPanel = new JPanel(new GridBagLayout());
        add(createPanel(topPanel, lightBlue, 1200, 180), c(0, 0));
        centerPanel = new JPanel(new GridBagLayout());
        add(createPanel(centerPanel, lightBlue, 1200, 640), c(0, 1));
        bottomPanel = new JPanel(new GridBagLayout());
        add(createPanel(bottomPanel, lightBlue, 1200, 180), c(0, 2));
        //
        leftPanel = new JPanel(new BorderLayout());
        centerPanel.add(createPanel(leftPanel, lightBlue, 190, 640), c(0, 0));
        JPanel centerCenterPanel = new JPanel(new GridBagLayout());
        centerPanel.add(createPanel(centerCenterPanel, lightBlue, 820, 640), c(1, 0));
        rightPanel = new JPanel(new BorderLayout());
        centerPanel.add(createPanel(rightPanel, lightBlue, 190, 640), c(2, 0));
        //
        extraButtonsPanel = new JPanel(new GridBagLayout());
        centerCenterPanel.add(createPanel(extraButtonsPanel, lightBlue, 820, 230), c(0, 1));
        discardPilePanel = new JPanel(new GridBagLayout());
        centerCenterPanel.add(createPanel(discardPilePanel, lightBlue, 820, 410), c(0, 0));
    }

    public void gui2() {
        //COMPONENTS: -----------------------------------------------------------------------------------
        tschauButton = new JButton("Tschau");
        tschauButton.setPreferredSize(new Dimension(170, 115));
        tschauButton.setFont(font);
        tschauButton.setBackground(lightRed);
        tschauButton.setFocusable(false);
        tschauButton.addActionListener(new TschauListener());
        extraButtonsPanel.add(tschauButton, c(15, 100, 0, 100, 0, 0));
        seppButton = new JButton("Sepp");
        seppButton.setPreferredSize(new Dimension(170, 115));
        seppButton.setFont(font);
        seppButton.setBackground(lightRed);
        seppButton.setFocusable(false);
        seppButton.addActionListener(new SeppListener());
        extraButtonsPanel.add(seppButton, c(15, 100, 100, 0, 2, 0));
        //
        drawPileButton = new JButton();
        drawPileButton.setPreferredSize(new Dimension(90, 135));
        drawPileButton.setBackground(lightBlue);
        drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
        drawPileButton.setMargin(new Insets(0, 0, 0, 0));
        drawPileButton.setBorder(null);
        drawPileButton.setFocusable(false);
        drawPileButton.addActionListener(new DrawCardListener());
        extraButtonsPanel.add(drawPileButton, c(0, 85, 0, 0, 1, 0));
        //
        discardPileButton = new JButton();
        discardPileButton.setPreferredSize(new Dimension(180, 270));
        discardPileButton.setBackground(lightBlue);
        discardPileButton.setIcon(getImg(aCard().getName(), false, 180, 270));
        discardPileButton.setMargin(new Insets(0, 0, 0, 0));
        discardPileButton.setBorder(null);
        discardPileButton.setFocusable(false);
        discardPilePanel.add(discardPileButton, c(60, 60, 0, 0, 0, 0));
        //
        for (int x = 0; x < 4; x++) {
            playerLabel[x] = new JLabel("Player " + (x + 1), SwingConstants.CENTER);
            playerLabel[x].setFont(font.deriveFont(18f));
            playerLabel[x].setOpaque(true);
            playerLabel[x].setBackground(lightYellow);
            playerLabel[x].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            playerLabel[x].repaint();
            playerLabel[x].setPreferredSize(new Dimension(180, 50));
        }
        JPanel topLabelsPanel = new JPanel(new GridBagLayout());
        topLabelsPanel.add(playerLabel[1], c(0, 0, 320, 0, 2, 0));
        topLabelsPanel.add(playerLabel[2], c(1, 0));
        topLabelsPanel.add(playerLabel[3], c(0, 0, 0, 320, 0, 0));
        topLabelsPanel.setBackground(lightBlue);
        topPanel.add(handPanel[2], c(5, 0, 0, 0, 0, 0));
        topPanel.add(topLabelsPanel, c(10, 0, 0, 0, 0, 1));
        JPanel bottomLabelsPanel = new JPanel(new GridBagLayout());
        bottomLabelsPanel.add(playerLabel[0], c(0, 0));
        bottomPanel.add(bottomLabelsPanel, c(0, 10, 0, 0, 0, 0));
        bottomPanel.add(handPanel[0], c(0, 10, 0, 0, 0, 1));
        JPanel leftPanel2 = new JPanel(new GridBagLayout());
        leftPanel2.setBackground(lightBlue);
        leftPanel2.add(handPanel[3], c(5, 0, 0, 0, 0, 0));
        leftPanel.add(leftPanel2, BorderLayout.NORTH);
        JPanel rightPanel2 = new JPanel(new GridBagLayout());
        rightPanel2.setBackground(lightBlue);
        rightPanel2.add(handPanel[1], c(5, 0, 0, 0, 0, 0));
        rightPanel.add(rightPanel2, BorderLayout.NORTH);

        //LISTENERS: -----------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.out.println("Window closed");
                new Menu();
                we.getWindow().dispose();
            }
        });

        //BASICS: -----------------------------------------------------------------------------------
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, -5);
        setVisible(true);
        pack();
    }

    public void createGame() {
        int y = 0;
        for (int x = 0; x < 2; x++) {
            for (int number = 2; number <= 14; number++) {
                for (int symbol = 1; symbol <= 4; symbol++) {
                    cards[y] = new Card(number, symbol);
                    y++;
                }
            }
        }
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();
        shuffleCards(cards);
        createHand();
    }

    public void shuffleCards(Card[] cards) {
        Random rnd = new Random();
        for (int x = cards.length - 1; x > 0; x--) {
            int y = rnd.nextInt(x + 1);
            Card temp = cards[y];
            cards[y] = cards[x];
            cards[x] = temp;
        }
    }

    public void createHand() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 7; y++) {
                players[x].addCard(aCard());
            }
            handPanel[x] = new JPanel();
            boolean back = true;
            if (x == currentPlayer) {
                back = false;
            }
            updateHand(players[x].getCards(), x, back);
        }
    }

    public abstract void nextPlayer();

    public Card whatCard(JButton b) {
        String desc = ((ImageIcon)b.getIcon()).getDescription();
        String[] values = desc.split("_", 2);
        return new Card(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    public Card aCard() {
        cardCounter++;
        return cards[cardCounter];
    }

    public ImageIcon getImg(String name, boolean back, int width, int height) {
        String s = "cards/";
        if (back) {
            s = "cardsback/";
        }
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/" + s + name + ".png"));
        Image img = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img, name);
    }

    public JPanel createPanel(JPanel panel, Color color, int width, int height) {
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    public GridBagConstraints c(int x, int y) {
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public GridBagConstraints c(int top, int bottom, int left, int right, int gridx, int gridy) {
        gbc.insets = new Insets(top, left, bottom, right);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        return gbc;
    }

    public boolean validCard(Card played) {
        boolean valid = false;
        for (Card c: players[currentPlayer].getCards()) {
            if (c.getSymbol() == played.getSymbol() || c.getNumber() == played.getNumber()) {
                valid = true;
                break;
            } else if (c.getNumber() == 11 && played.getSymbol() != 0) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    public void isTschauOrSepp() {
        if (tschau || sepp) {
            int amount;
            if (tschau) {
                amount = 2;
                tschau = false;
            } else {
                amount = 4;
                sepp = false;
            }
            for (int x = 0; x < amount; x++) {
                players[lastPlayer].addCard(aCard());
            }
            drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
            updateHand(players[lastPlayer].getCards(), lastPlayer, true);
        }
    }

    public void whosNext() {
        if (ten == 1) {
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
    }

    public void updateHand(List<Card> cards, int player, boolean back) {
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
                button[x].setIcon(getImg(card.getName(), back, 70, 105));
                handPanel[player].add(button[x]);
                button[x].addActionListener(new PlayCardListener(player));
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
                button[x].setIcon(getImg(card.getName(), back, 70, 105));
                if (player == 1 && cards.size() % 2 == 1 && (x + 1) == cards.size()) {
                    JButton emptyButton = new JButton();
                    emptyButton.setBackground(lightBlue);
                    emptyButton.setFocusable(false);
                    emptyButton.setEnabled(false);
                    emptyButton.setBorder(null);
                    handPanel[player].add(emptyButton);
                }
                handPanel[player].add(button[x]);
                button[x].addActionListener(new PlayCardListener(player));
                x++;
            }
        }
        //
        handPanel[player].revalidate();
        handPanel[player].repaint();
    }

    //LISTENERS: -----------------------------------------------------------------------------------
    class PlayCardListener implements ActionListener {

        int player;
        public PlayCardListener(int player) { this.player = player; }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (currentPlayer == player) {
                Card clicked = whatCard((JButton)ae.getSource());
                Card played;
                if (seven != 0) {
                    played = new Card(7, 0);
                } else {
                    played = whatCard(discardPileButton);
                }
                if (clicked.getNumber() == 11) {
                    isTschauOrSepp();
                    if (ace) { ace = false; }
                    players[currentPlayer].removeCard(clicked);
                    new ChooseSymbol();
                } else {
                    if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                        isTschauOrSepp();
                        if (ace) { ace = false; }
                        System.out.println("Correct Card");
                        discardPileButton.setIcon(getImg(clicked.getName(), false, 180, 270));
                        players[currentPlayer].removeCard(clicked);
                        if (clicked.getNumber() == 10) {
                            ten = 3 - ten;
                        } else if (clicked.getNumber() == 8) {
                            eight = true;
                        } else if (clicked.getNumber() == 14) {
                            ace = true;
                        } else if (clicked.getNumber() == 7) {
                            seven += 2;
                        }
                        nextPlayer();
                    } else {
                        System.out.println("Error: Invalid Card");
                    }
                }
            } else {
                System.out.println("Error: Wrong Player");
                playerLabel[currentPlayer].setBackground(darkRed);
                playerLabel[currentPlayer].setForeground(Color.WHITE);
                wrongPlayer.start();
            }
        }
    }

    class DrawCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Card played = whatCard(discardPileButton);
            if (!validCard(played)) {
                isTschauOrSepp();
                players[currentPlayer].addCard(aCard());
                drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
                System.out.println("Card received");
                if (!validCard(played)) {
                    updateHand(players[currentPlayer].getCards(), currentPlayer, true);
                    nextPlayer();
                } else {
                    updateHand(players[currentPlayer].getCards(), currentPlayer, false);
                }
            } else {
                System.out.println("Error: Player has valid Card");
            }
        }
    }

    class ChooseSymbolListener implements ActionListener {
        ChooseSymbol csf;
        int x;
        public ChooseSymbolListener(int x, ChooseSymbol csf) {
            this.x = x;
            this.csf = csf;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            discardPileButton.setIcon(getImg("11_" + (x + 1), false, 180, 270));
            csf.dispose();
            nextPlayer();
        }
    }

    public class ChooseSymbol extends JFrame {
        public ChooseSymbol() {
            super("Choose Symbol");
            setLayout(new GridBagLayout());
            setIconImage(NewGame.this.getIconImage());
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
                jacks[x].setIcon(getImg("11_" + (x + 1), false, 70, 105));
                csPanel.add(jacks[x]);
                jacks[x].addActionListener(new ChooseSymbolListener(x, this));
            }
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    new ChooseSymbol();
                }
            });
            setLocationRelativeTo(null);
            setVisible(true);
            pack();
        }
    }

    class TschauListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (tschau) {
                tschau = false;
                System.out.println("Spieler " + (lastPlayer + 1) +" says \"Tschau\"");
            } else {
                System.out.println("Error: Tschau not possible");
            }
        }
    }

    class SeppListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (sepp) {
                System.out.println("Spieler " + (lastPlayer + 1) +" says \"Sepp\"");
                if (ace) {
                    sepp = false;
                } else {
                    new GameOver(players, NewGame.this);
                }
            } else {
                System.out.println("Error: Sepp not possible");
            }
        }
    }

    Timer wrongPlayer = new Timer(1000, ae -> {
        playerLabel[currentPlayer].setBackground(green);
        playerLabel[currentPlayer].setForeground(Color.BLACK);
    });

}
