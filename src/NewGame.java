import javax.swing.*;
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
    protected final Font font = new Font("Abadi", Font.BOLD, 20);
    protected GridBagConstraints c = new GridBagConstraints();

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
        extraButtonsPanel.add(tschauButton, c(15, 100, 0, 100, 0, 0));
        seppButton = new JButton("Sepp");
        seppButton.setPreferredSize(new Dimension(170, 115));
        seppButton.setFont(font);
        seppButton.setBackground(lightRed);
        seppButton.setFocusable(false);
        extraButtonsPanel.add(seppButton, c(15, 100, 100, 0, 2, 0));
        //
        drawPileButton = new JButton();
        drawPileButton.setPreferredSize(new Dimension(90, 135));
        drawPileButton.setBackground(lightBlue);
        drawPileButton.setIcon(img(cardBack(), true, 90, 135));
        drawPileButton.setMargin(new Insets(0, 0, 0, 0));
        drawPileButton.setBorder(null);
        drawPileButton.setFocusable(false);
        extraButtonsPanel.add(drawPileButton, c(0, 85, 0, 0, 1, 0));
        //
        discardPileButton = new JButton();
        discardPileButton.setPreferredSize(new Dimension(180, 270));
        discardPileButton.setBackground(lightBlue);
        discardPileButton.setIcon(img(aCard().getName(), 180, 270));
        discardPileButton.setMargin(new Insets(0, 0, 0, 0));
        discardPileButton.setBorder(null);
        discardPileButton.setFocusable(false);
        discardPilePanel.add(discardPileButton, c(60, 60, 0, 0, 0, 0));
        //
        for (int x = 0; x < 4; x++) {
            playerLabel[x] = new JLabel("Player " + (x + 1), SwingConstants.CENTER);
            playerLabel[x].setFont(font.deriveFont(16f));
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
            updateHand(players[x].getCards(), x);
        }
    }

    public void nextPlayer() {
        updateHand(players[currentPlayer].getCards(), currentPlayer);
        playerLabel[currentPlayer].setBackground(lightYellow);
        if (currentPlayer == 3) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        System.out.println("Player " + (currentPlayer + 1) + "'s turn");
        playerLabel[currentPlayer].setBackground(green);
    }

    public Card whatCard(JButton b) {
        String desc = ((ImageIcon)b.getIcon()).getDescription();
        String[] values = desc.split("_", 2);
        return new Card(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    public Card aCard() {
        cardCounter++;
        return cards[cardCounter];
    }

    public String cardBack() {
        return "back" + (cards[cardCounter + 1].getSymbol() + cards[cardCounter + 1].getNumber()) % 6;
    }

    public ImageIcon img(String name, int width, int height) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/cards/" + name + ".png"));
        Image img = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img, name);
    }

    public ImageIcon img(String name, boolean back, int width, int height) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/cardsback/" + name + ".png"));
        Image img = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img, name);
    }

    public JPanel createPanel(JPanel panel, Color color, int width, int height) {
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    public GridBagConstraints c(int x, int y) {
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    public GridBagConstraints c(int top, int bottom, int left, int right, int gridx, int gridy) {
        c.insets = new Insets(top, left, bottom, right);
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    public abstract void updateHand(List<Card> cards, int p);

}
