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

    public CardListener(NewGame g, int p) {
        this.g = g;
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (g.p == p) {
            Card clicked = whatCard((JButton)ae.getSource());
            Card played = whatCard(g.cardPlayedButton);
            if (clicked.getSymbol() == played.getSymbol() || clicked.getNumber() == played.getNumber()) {
                System.out.println("Correct Card");
                g.players[p].removeCard(clicked);
                g.nextPlayer();
            } else {
                System.out.println("Wrong Card");
            }
        } else {
            System.out.println("Wrong Player");
        }
    }

    public Card whatCard(JButton b) {
        String desc = ((ImageIcon)b.getIcon()).getDescription();
        String[] values = desc.split("_", 2);
        return new Card(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }
}
