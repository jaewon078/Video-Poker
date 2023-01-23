/*****************************************
 * Card class for Video Poker game
 * Jaewon Lee
 * My Card Class
 ****************************************/

public class Card implements Comparable<Card> {

    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank
    private String suitName;
    private String rankName;
    private String cardName;
    private int result;

    public Card(int s, int r) {
        // make a card with suit s and value v
        this.suit = s;
        this.rank = r;
    }

    public int compareTo(Card c) {
        // use this method to compare cards so they
        // may be easily sorted
        // I use Collections.sort (found in Player.sortHand())

        if (this.rank > c.rank) {
            result = 1;
        }

        if (this.rank < c.rank) {
            result = -1;
        }

        if (this.rank == c.rank) {
            if (this.suit > c.suit) {
                result = 1;
            } else if (this.suit < c.suit) {
                result = -1;
            } else {
                result = 0;
            }
        }

        return result;

    }

    public String toString() {
        // use this method to easily print a Card object
        switch (suit) {
            case 1:
                suitName = "Clubs";
                break;
            case 2:
                suitName = "Diamonds";
                break;
            case 3:
                suitName = "Hearts";
                break;
            case 4:
                suitName = "Spades";
                break;
        }

        switch (rank) {
            case 1:
                rankName = "Ace";
                break;
            case 2:
                rankName = "Two";
                break;
            case 3:
                rankName = "Three";
                break;
            case 4:
                rankName = "Four";
                break;
            case 5:
                rankName = "Five";
                break;
            case 6:
                rankName = "Six";
                break;
            case 7:
                rankName = "Seven";
                break;
            case 8:
                rankName = "Eight";
                break;
            case 9:
                rankName = "Nine";
                break;
            case 10:
                rankName = "Ten";
                break;
            case 11:
                rankName = "Jack";
                break;
            case 12:
                rankName = "Queen";
                break;
            case 13:
                rankName = "King";
                break;
        }

        cardName = rankName + " of " + suitName;
        return cardName;
    }
    // add some more methods here if needed

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

}
