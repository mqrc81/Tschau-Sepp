package gui;

import objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * "Game Over" window after a player won the game
 *
 * @author Marc Schmidt
 * @since 2020-05-29
 * @version 1.3
 */
public class GameOver extends JFrame {

    private JLabel[] labels = new JLabel[4];

    private boolean blink = true;

    private final Color lightBlue = new Color(229, 244, 255);
    private final Color darkRed = new Color(124, 9, 15);
    private final Color turquoise = new Color(0, 127, 127);
    private final Color purple = new Color(178, 0, 255);
    private final Color gold = new Color(218,165,32);
    private final Font font = new Font("Dubai Medium", Font.PLAIN, 16);
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * constructor method
     *
     * @param players players involved in the game
     * @param ng this class
     */
    public GameOver(Player[] players, NewGame ng) {
        super("Congratulations!");
        getContentPane().setBackground(lightBlue);
        setMinimumSize(new Dimension(600, 400));
        setIconImage(ng.getIconImage());
        setResizable(false);
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridy = 0;
        ng.dispose();
        //rank array with points of each player's combined cards
        int[] rank = new int[4];
        for (int x = 0; x < 4; x++) {
            rank[x] = players[x].getPoints();
        }
        //sorts the players by points in ascending order using BubbleSort
        for (int x = 4; x > 1; x--) {
            for (int y = 0; y < x - 1; y++) {
                if (rank[y] > rank[y + 1]) {
                    int temp = rank[y];
                    rank[y] = rank[y + 1];
                    rank[y + 1] = temp;
                }
            }
        }
        //creates JLabel for each player displaying rank and points
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (players[y].getPoints() == rank[x]) {
                    if (x == 0) {
                        labels[x] = new JLabel("Winner: Player " + (y + 1));
                        labels[x].setFont(font.deriveFont(36f).deriveFont(Font.PLAIN));
                        labels[x].setForeground(purple);
                        add(labels[x], gbc);
                        gbc.gridy++;
                        break;
                    } else {
                        labels[x] = new JLabel((x + 1) + ". place: Player " + (y + 1) + " (" + rank[x] + " points)");
                        labels[x].setBackground(lightBlue);
                        labels[x].setFont(font);
                        add(labels[x], gbc);
                        gbc.gridy++;
                        break;
                    }
                }
            }
        }
        JButton menuButton = new JButton("Return to Menu");
        menuButton.setBackground(darkRed);
        menuButton.setForeground(Color.WHITE);
        menuButton.setPreferredSize(new Dimension(300, 50));
        menuButton.setFont(font.deriveFont(20f));
        menuButton.setFocusable(false);
        menuButton.addMouseListener(new HoverListener());
        menuButton.addActionListener(ae -> {
            new Menu();
            dispose();
        });
        gbc.insets.top = 50;
        gbc.insets.bottom = 20;
        add(menuButton, gbc);

        blinkTimer. start();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    /**
     * Listener that changes color of button when mouse hovers over it
     */
    class HoverListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent me) {}
        @Override
        public void mousePressed(MouseEvent me) {}
        @Override
        public void mouseReleased(MouseEvent me) {}
        @Override
        public void mouseEntered(MouseEvent me) {
            me.getComponent().setBackground(turquoise);
        }
        @Override
        public void mouseExited(MouseEvent me) {
            me.getComponent().setBackground(darkRed);
        }
    }

    /**
     * Timer that constantly changes color of winner announcement
     */
    Timer blinkTimer = new Timer(500, ae -> {
        if (blink) {
            labels[0].setForeground(gold);
            blink = false;
        } else {
            labels[0].setForeground(purple);
            blink = true;
        }
    });
}