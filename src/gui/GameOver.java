package gui;

import objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-29
 * @project: M326
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
    GridBagConstraints gbc = new GridBagConstraints();

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
        //
        int[] rank = new int[4];
        for (int x = 0; x < 4; x++) {
            rank[x] = players[x].getPoints();
        }
        for (int x = 4; x > 1; x--) {
            for (int y = 0; y < x - 1; y++) {
                if (rank[y] > rank[y + 1]) {
                    int temp = rank[y];
                    rank[y] = rank[y + 1];
                    rank[y + 1] = temp;
                }
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (players[y].getPoints() == rank[x]) {
                    if (x == 0) {
                        labels[x] = new JLabel("Winner: objects.Player " + (y + 1) + "!");
                        labels[x].setFont(font.deriveFont(36f).deriveFont(Font.PLAIN));
                        labels[x].setForeground(purple);
                        add(labels[x], gbc);
                        gbc.gridy++;
                        break;
                    } else {
                        labels[x] =
                                new JLabel((x + 1) + ". place: objects.Player " + (y + 1) + " (" + rank[x] + " points)");
                        labels[x].setBackground(lightBlue);
                        labels[x].setFont(font);
                        add(labels[x], gbc);
                        gbc.gridy++;
                        break;
                    }
                }
            }
        }

        JButton menuButton = new JButton("Return to gui.Menu");
        menuButton.setBackground(darkRed);
        menuButton.setForeground(Color.WHITE);
        menuButton.setPreferredSize(new Dimension(300, 50));
        menuButton.setFont(font.deriveFont(20f));
        menuButton.setFocusable(false);
        menuButton.addMouseListener(new HoverListener());
        menuButton.addActionListener(ae -> {
            System.out.println("Returned to Menu");
            new Menu();
            dispose();
        });
        gbc.insets.top = 50;
        gbc.insets.bottom = 20;
        add(menuButton, gbc);

        blinkTimer.start();

        //Basics:
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public class HoverListener implements MouseListener {
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
