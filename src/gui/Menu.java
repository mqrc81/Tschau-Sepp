package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * "Menu" window, in which the user can start a new game
 *
 * @author Marc Schmidt
 * @since 2020-05-14
 * @version 1.3
 */
public class Menu extends JFrame {

    private final Color lightBlue = new Color(229, 244, 255);
    private final Color darkRed = new Color(124, 9, 15);
    private final Color turquoise = new Color(0, 127, 127);
    private final Font font = new Font("Dubai Medium", Font.PLAIN, 14);
    private final GridBagConstraints gbc = new GridBagConstraints();

    /**
     * constructor method
     */
    public Menu () {
        super("Menu | Tschau Sepp Premium");
        gui();
        setVisible(true);
    }

    /**
     * creates GUI
     */
    public void gui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setResizable(false);
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/resources/icons/icon1.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(lightBlue);
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(lightBlue);

        JLabel welcomeLabel = new JLabel("Welcome to Tschau Sepp Premium!");
        welcomeLabel.setForeground(turquoise);
        welcomeLabel.setFont(font.deriveFont(26f).deriveFont(Font.PLAIN));
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.add(welcomeLabel, c(0, 10, 0, 0, 0, 0));
        add(labelPanel, c(0, 25, 20, 20, 0, 0));

        JLabel creditLabel = new JLabel("by Marc Schmidt");
        creditLabel.setFont(font.deriveFont(15f));
        labelPanel.add(creditLabel, c(0, 1));

        JButton singleplayerButton = new JButton("SinglePlayer");
        singleplayerButton.setBackground(darkRed);
        singleplayerButton.setForeground(Color.WHITE);
        singleplayerButton.setPreferredSize(new Dimension(300, 50));
        singleplayerButton.setFont(font.deriveFont(20f));
        singleplayerButton.setFocusable(false);
        singleplayerButton.addMouseListener(new HoverListener());
        singleplayerButton.addActionListener(ae -> {
            new Singleplayer();
            dispose();
        });
        add(singleplayerButton, c(15, 5, 5, 5, 0, 1));

        JButton multiplayerButton = new JButton("MultiPlayer");
        multiplayerButton.setBackground(darkRed);
        multiplayerButton.setForeground(Color.WHITE);
        multiplayerButton.setPreferredSize(new Dimension(300, 50));
        multiplayerButton.setFont(font.deriveFont(20f));
        multiplayerButton.setFocusable(false);
        multiplayerButton.addMouseListener(new HoverListener());
        multiplayerButton.addActionListener(ae -> {
            new Multiplayer();
            dispose();
        });
        add(multiplayerButton, c(10, 10, 5, 5, 0, 2));
    }

    /**
     * sets GridBagConstraints
     *
     * @param x gridx
     * @param y gridy
     * @return GridBagConstraints
     */
    public GridBagConstraints c(int x, int y) {
        gbc.gridy = y;
        return gbc;
    }

    /**
     * sets GridBagConstraints
     *
     * @param top top of insets
     * @param bottom bottom of insets
     * @param left left of insets
     * @param right right of insets
     * @param x gridx
     * @param y gridy
     * @return GridBagConstraints
     */
    public GridBagConstraints c(int top, int bottom, int left, int right, int x, int y) {
        gbc.insets = new Insets(top, left, bottom, right);
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
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

}
