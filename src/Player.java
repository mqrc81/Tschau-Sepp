import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Player {

    private List<Card> cards = new ArrayList<>();

    public Player() {
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    //FIXME
    public void removeCard(Card c) {
        cards.remove(c);
    }

    public int handSize() {
        return cards.size();
    }

}