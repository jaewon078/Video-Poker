/*****************************************
 * Deck class for Video Poker game
 * Jaewon Lee
 * My Deck Class
 ****************************************/

public class Deck {

    private Card[] cards;
    private int top; // the index of the top of the deck
    private int suit; // might have to put this inside Deck()
    private int rank;
    private int randomNumber;
    private int randomNumber2;
    private final int DECK_SIZE = 52;
    private final int SUIT_SIZE = 13;

    // add more instance variables if needed

    public Deck() {
        // make a 52 card deck here
        cards = new Card[DECK_SIZE];
        suit = 1;
        rank = 1;

        // I use a for loop to iterate through the card objects. Once rank is greater
        // than 13, it moves to another suit and resets rank to 1 until all 52 card
        // objects are defined.
        for (int i = 0; i < DECK_SIZE; i++) {
            cards[i] = new Card(suit, rank);
            rank++;
            if (rank > SUIT_SIZE) {
                suit++;
                rank = 1;
            }
        }

    }

    public void shuffle() {
        // shuffle the deck here

        // Generates two random cards that will swap places 500 times
        for (int i = 0; i < 500; i++) {
            randomNumber = (int) (Math.random() * (DECK_SIZE));
            randomNumber2 = (int) (Math.random() * (DECK_SIZE));

            // Executes the actual swapping of card positions
            Card temp = cards[randomNumber];
            cards[randomNumber] = cards[randomNumber2];
            cards[randomNumber2] = temp;
        }

    }

    public Card deal() {
        // deal the top card in the deck
        return cards[top++];
    }

    // add more methods here if needed

}