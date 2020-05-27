import javax.swing.*;
import java.awt.event.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-24
 * @project: M326
 */

public class CardListener implements ActionListener {

    NewGame g;
    int p;

    public CardListener(MultiplayerGame g, int p) {
        this.g = g;
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (g.p == p) {
            Card clicked = g.whatCard((JButton)ae.getSource());
            Card played = g.whatCard(g.discardPileButton);
            if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                System.out.println("Correct Card");
                g.discardPileButton.setIcon(g.img(clicked.getName(), 180, 270));
                g.players[p].removeCard(clicked);
                g.nextPlayer();
            } else {
                System.out.println("Error: Invalid Card");
            }
        } else {
            System.out.println("Error: Wrong Player");
        }
    }
}
