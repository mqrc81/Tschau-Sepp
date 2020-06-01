package gui;

import objects.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public abstract class NewGame extends JFrame {
    protected final Card[] cards = new Card[104];
    protected final Player[] players = new Player[4];

    protected int currentPlayer = whoStarts();
    protected int cardCounter = -1;
    protected boolean singlePlayer = isSinglePlayer();
    protected Card discard;

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

    public final Color lightBlue = new Color(229, 244, 255);
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
        discard = aCard();
        discardPileButton.setIcon(getImg(discard.getName(), false, 180, 270));
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
        for (int x = 0; x < 4; x++) {
            players[x] = new Player();
        }
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
            System.out.println("Player " + (x + 1));
            for (int y = 0; y < 7; y++) {
                Card c = aCard();
                System.out.println("\t" + (x + 1) + ": [" + c.getNumber() + ", " + c.getSymbol() + "]");
                players[x].addCard(c);
                newButton(x);
            }
            handPanel[x] = new JPanel();
            updateHand(x, (!singlePlayer && x != currentPlayer) || (singlePlayer && x != 0));
        }
    }

    public abstract void nextPlayer();

    public abstract boolean isSinglePlayer();

    public abstract int whoStarts();

    public abstract void doAlgorithm();

    public Card aCard() {
        cardCounter++;
        return cards[cardCounter];
    }

    public boolean validCard(int number, int symbol) {
        boolean valid = false;
        for (Card c: players[currentPlayer].getCards()) {
            if (c.getSymbol() == symbol || c.getNumber() == number) {
                valid = true;
                break;
            } else if (c.getNumber() == 11 && symbol != 0) {
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
                newButton(lastPlayer);
            }
            drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
            updateHand(lastPlayer, !singlePlayer || lastPlayer != 0);
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

    public void newButton(int p) {
        JButton b = new JButton();
        b.setPreferredSize(new Dimension(70, 105));
        b.setBackground(lightBlue);
        b.addActionListener(new PlayCardListener(p));
        players[p].addButton(b);
    }

    public void updateHand(int player, boolean back) {
        handPanel[player].removeAll();
        handPanel[player].setBackground(lightBlue);
        //
        if (player % 2 == 0) { //Spieler oben oder unten -> 7 Karten -> 7x1
            if (players[currentPlayer].handSize() == 0) {
                handPanel[player].setSize(75, 105);
                handPanel[player].setLayout(new GridLayout(1, 1, 5, 5));
                JButton emptyButton = new JButton("1");
                emptyButton.setBackground(lightBlue);
                emptyButton.setForeground(lightBlue);
                emptyButton.setFocusable(false);
                emptyButton.setEnabled(false);
                emptyButton.setBorder(null);
                handPanel[player].add(emptyButton);
            } else {
                handPanel[player].setSize(players[player].handSize() * 75 - 5, 105);
                handPanel[player].setLayout(new GridLayout(1, players[player].handSize(), 5, 5));
                for (int x = 0; x < players[player].handSize(); x++) {
                    players[player].getButtons().get(x).setActionCommand(x + "");
                    players[player].getButtons().get(x).setIcon(getImg(players[player].getCards().get(x).getName(), back, 70, 105));
                    handPanel[player].add(players[player].getButtons().get(x));
                }
            }
        } else { //Spieler links oder rechts -> 7 Karten -> 2x4
            handPanel[player].setSize(145, ((players[player].handSize() + 1) / 2) * 110 - 5);
            handPanel[player].setLayout(new GridLayout(((players[player].handSize() + 1) / 2), 2, 5, 5));
            for (int x = 0; x < players[player].handSize(); x++) {
                players[player].getButtons().get(x).setActionCommand(x + "");
                players[player].getButtons().get(x).setIcon(getImg(players[player].getCards().get(x).getName(), back, 70, 105));
                if (player == 1 && players[player].handSize() % 2 == 1 && (x + 1) == players[player].handSize()) {
                    JButton emptyButton = new JButton();
                    emptyButton.setBackground(lightBlue);
                    emptyButton.setFocusable(false);
                    emptyButton.setEnabled(false);
                    emptyButton.setBorder(null);
                    handPanel[player].add(emptyButton);
                }
                handPanel[player].add(players[player].getButtons().get(x));
            }
        }
        //
        handPanel[player].revalidate();
        handPanel[player].repaint();
    }

    public ImageIcon getImg(String name, boolean back, int width, int height) {
        if (back) {
            name = "back";
        }
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/cards/" + name + ".png"));
        Image img = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
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

    //LISTENERS: -----------------------------------------------------------------------------------
    public class PlayCardListener implements ActionListener {

        int player;
        public PlayCardListener(int player) { this.player = player; }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (currentPlayer == player) {
                Card clicked = players[currentPlayer].getCards().get(Integer.parseInt(ae.getActionCommand()));
                Card played;
                if (seven != 0) {
                    played = new Card(7, 0);
                } else {
                    played = discard;
                }
                if (clicked.getNumber() == 11) {
                    isTschauOrSepp();
                    if (ace) { ace = false; }
                    players[currentPlayer].removeButton(players[currentPlayer].getButtons().get(players[currentPlayer].removeCard(clicked)));
                    new ChooseJack();
                } else {
                    if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                        isTschauOrSepp();
                        if (ace) { ace = false; }
                        System.out.println("Correct Card");
                        discardPileButton.setIcon(getImg(clicked.getName(), false, 180, 270));
                        discard = clicked;
                        players[currentPlayer].removeButton(players[currentPlayer].getButtons().get(players[currentPlayer].removeCard(clicked)));
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
                wrongPlayer.restart();
            }
        }
    }

    class DrawCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!validCard(discard.getNumber(), discard.getSymbol())) {
                isTschauOrSepp();
                players[currentPlayer].addCard(aCard());
                newButton(currentPlayer);
                drawPileButton.setIcon(getImg(cards[cardCounter + 1].getName(), true, 90, 135));
                System.out.println("Card received");
                if (!validCard(discard.getNumber(), discard.getSymbol())) {
                    updateHand(currentPlayer, !singlePlayer || currentPlayer != 0);
                    nextPlayer();
                } else {
                    updateHand(currentPlayer, singlePlayer && currentPlayer != 0);
                    if (singlePlayer) {
                        doAlgorithm();
                    }
                }
            } else {
                System.out.println("Error: Player has valid Card");
            }
        }
    }

    class ChooseJackListener implements ActionListener {
        ChooseJack csf;
        int x;
        public ChooseJackListener(int x, ChooseJack csf) {
            this.x = x;
            this.csf = csf;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            discard = new Card(11, (x + 1));
            discardPileButton.setIcon(getImg(discard.getName(), false, 180, 270));
            csf.dispose();
            nextPlayer();
        }
    }

    class ChooseJack extends JDialog {
        public ChooseJack() {
            setTitle("Choose Symbol");
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
                jacks[x].addActionListener(new ChooseJackListener(x, this));
            }
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    new ChooseJack();
                }
            });
            setLocationRelativeTo(null);
            setVisible(true);
            pack();
            //
            if (singlePlayer && currentPlayer != 0) {
                AtomicBoolean doJackTimer = new AtomicBoolean(true);
                new Timer(500, (ActionEvent e) -> {
                    if (doJackTimer.get()) {
                        doJackTimer.set(false);
                        int[] whatSymbol = new int[4];
                        for (Card c : players[currentPlayer].getCards()) {
                            whatSymbol[c.getSymbol() - 1]++;
                        }
                        int max = 0;
                        int thisSymbol = 0;
                        for (int x = 0; x < 4; x++) {
                            if (whatSymbol[x] > max) {
                                max = whatSymbol[x];
                                thisSymbol = x;
                            }
                        }
                        jacks[thisSymbol].doClick();
                    }
                }).restart();
            }
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