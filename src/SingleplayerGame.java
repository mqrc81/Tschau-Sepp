import java.util.List;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class SingleplayerGame extends NewGame {

    public SingleplayerGame() {
        setTitle("Singleplayer | Tschau Sepp Premium");
    }

    public void updateHand(List<Card> cards, int p) {
        handPanel[p].removeAll();

        //TODO

        handPanel[p].revalidate();
        handPanel[p].repaint();
    }

}
