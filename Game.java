
/*****************************************
 * Game class for Video Poker game
 * Jaewon Lee
 * My Game Class
 ****************************************/

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    // My many instance variables...
    private Player p;
    private Deck cards;
    private int betAmount;
    private int exchangeChoiceInt;
    private int exchangeChoiceTemp;
    private int exchangeChoiceDigit;
    private int exchangeChoiceLength;
    private String handScore;
    private int odds;
    private int numOfPairs;
    private int firstSuit;
    private int nextSuit;
    private int currentRank;
    private int nextRank;
    private int lastCard;
    private char suitChar;
    private int suit;
    private int rank;
    ArrayList<Card> playerHand;
    private final int HAND_SIZE = 5;

    // you'll probably need some more here

    public Game(String[] testHand) {
        // This constructor is to help test your code.
        // use the contents of testHand to
        // make a hand for the player
        // use the following encoding for cards
        // c = clubs
        // d = diamonds
        // h = hearts
        // s = spades
        // 1-13 correspond to ace-king
        // example: s1 = ace of spades
        // example: testhand = {s1, s13, s12, s11, s10} = royal flush

        // Instantiates the objects used to access Player and Deck classes
        p = new Player();
        cards = new Deck();

        // An enhanced for loop that iterates though each "card" element of testHand.
        // Gets each card's suit and rank.
        for (String card : testHand) {
            suitChar = card.charAt(0);
            switch (suitChar) {
                case 'c':
                    suit = 1;
                    break;
                case 'd':
                    suit = 2;
                    break;
                case 'h':
                    suit = 3;
                    break;
                case 's':
                    suit = 4;
                    break;
            }
            // This fancy/scary looking code removes everything that isn't a digit and
            // replaces it with "" (aka nothing). In the end, all that is left is a clean
            // integer for us to assign as the rank.
            rank = Integer.parseInt(card.replaceAll("[\\D]", ""));

            Card newTestCard = new Card(suit, rank);
            p.addCard(newTestCard);

        }

    }

    public Game() {
        // This no-argument constructor is to actually play a normal game

        // Instantiates the objects used to access Player and Deck classes
        p = new Player();
        cards = new Deck();

        // Introduction
        System.out.println("Hello!");
        System.out.println("Welcome to:");
        System.out.println("");
        System.out.println("###############");
        System.out.println("##VIDEO POKER##");
        System.out.println("###############");
        System.out.println("");

    }

    public void play() {
        // this method should play the game

        // Instantiates Scanner
        Scanner input = new Scanner(System.in);

        // STAGE ONE: Betting
        System.out.println("You have " + p.getBankroll() + " token(s)");
        System.out.println("Input an amount of tokens (1-5) to bet:");
        betAmount = input.nextInt();
        p.bets(betAmount);
        System.out.println("You now have: " + p.getBankroll());

        // STAGE TWO: Shuffling + Dealing

        // A reference variable to p.getPlayerHand(). Makes the code much more readable.
        playerHand = p.getPlayerHand();
        cards.shuffle();

        // An if statement is included in case the user wants to test something. Thus,
        // if playerHand has something inside of it at this beginning stage of the game,
        // the program will skip the shuffle and deal stage because the player already
        // manually inputted their desired cards.

        if (playerHand.size() == 0) {
            // Deals 5 cards to the player's hand
            for (int i = 0; i < HAND_SIZE; i++) {
                p.addCard(cards.deal());
            }
        }

        System.out.println("Your initial hand:");
        System.out.println(playerHand);

        // STAGE THREE: Exchanging Cards (all at once approach)
        System.out.println(
                "Which cards would you like to exchange? Input 1-5 all at once. For example, if you would like to exchange cards 1, 3, 5, input 135. If you choose not to exchange, press 9.");

        // The cards that the player wants to swap
        exchangeChoiceInt = input.nextInt();

        // This variable is used to help me parse through each digit (as I use a modulo
        // below)
        exchangeChoiceTemp = exchangeChoiceInt;

        // Determines the length of the integer so I can loop for as many digits are
        // entered
        exchangeChoiceLength = String.valueOf(exchangeChoiceInt).length();

        // My approach to exchanging cards
        for (int i = 0; i < exchangeChoiceLength; i++) {

            // Iterates through the integer and grabs its value
            exchangeChoiceDigit = exchangeChoiceTemp % 10;
            exchangeChoiceTemp = exchangeChoiceTemp / 10;

            // Substitutes the desired card with a new card from the deck
            if (exchangeChoiceDigit == 1 || exchangeChoiceDigit == 2 || exchangeChoiceDigit == 3
                    || exchangeChoiceDigit == 4 || exchangeChoiceDigit == 5) {
                p.getPlayerHand().set(exchangeChoiceDigit - 1, cards.deal());

            } else if (exchangeChoiceDigit == 9) {
                System.out.println("You have chosen to not exchange any cards.");

            } else {
                System.out.println("Invalid entry detected. Input a valid integer.");
                i = -1; // Resets the loop counter (since i will revert to 0 once i++ is implemented)

                // Resets all of the variables so they can input a valid exchange choice
                exchangeChoiceInt = input.nextInt();
                exchangeChoiceTemp = exchangeChoiceInt;
                exchangeChoiceLength = String.valueOf(exchangeChoiceInt).length();
                continue;
            }
        }

        // STAGE FOUR: Reveal Cards
        System.out.println("Your cards:");
        System.out.println(playerHand);

        // STAGE FIVE: Score Hand + Award Winnings
        p.sortHand();
        handScore = checkHand(playerHand);
        odds = getOdds(handScore);

        System.out.println("Your hand: " + handScore);
        p.winnings(odds);
        System.out.println("Your current bankroll: " + p.getBankroll());

        // Closes the scanner
        input.close();
    }

    public String checkHand(ArrayList<Card> hand) {
        // this method should take an ArrayList of cards
        // as input and then determine what evaluates to and
        // return that as a String

        if (isRoyalFlush(hand)) {
            return "Royal Flush!";
        } else if (isStraightFlush(hand)) {
            return "Straight Flush!";
        } else if (isFourOfAKind(hand)) {
            return "Four of a Kind!";
        } else if (isFullHouse(hand)) {
            return "Full House!";
        } else if (isFlush(hand)) {
            return "Flush!";
        } else if (isStraight(hand)) {
            return "Straight!";
        } else if (isThreeOfAKind(hand)) {
            return "Three of a Kind!";
        } else if (isTwoPairs(hand)) {
            return "Two Pairs!";
        } else if (isOnePair(hand)) {
            return "One Pair!";
        } else {
            return "No Pair!";
        }

    }

    // you will likely want many more methods here
    // per discussion in class

    // Finds the number of pairs within the player's hand
    private int pairCount(ArrayList<Card> hand) {
        numOfPairs = 0;

        // A nested for loop. The way pairCount works is by taking the first digit and
        // seeing if it matches with any succeeding digit in the list, then moving onto
        // the second. Examples are provided below.
        for (int i = 0; i < HAND_SIZE; i++) {
            for (int j = i + 1; j < HAND_SIZE; j++) {
                if (hand.get(i).getRank() == hand.get(j).getRank()) {
                    numOfPairs++;
                }
            }
        }
        return numOfPairs;
    }

    // A Royal Flush is simply a Flush (same suit), a Straight (consecutive ranks),
    // and must have 10, jack, queen, king, and ace. Since my isStraight method
    // accounts for ace being equal to "1," all that is left for me to do is set the
    // lastCard as 13 because that ensures that the hand is specifically 10, jack,
    // queen, king, and ace.
    private boolean isRoyalFlush(ArrayList<Card> hand) {
        lastCard = hand.get(4).getRank();
        if (isFlush(hand) && isStraight(hand) && lastCard == 13) {
            return true;
        }
        return false;
    }

    // A Straight Flush is simply just a Straight and a Flush.
    private boolean isStraightFlush(ArrayList<Card> hand) {
        if (isStraight(hand) && isFlush(hand)) {
            return true;
        }
        return false;
    }

    private boolean isFourOfAKind(ArrayList<Card> hand) {
        // pairCount(hand) is equal to 6 because:
        // Ex: 4 4 4 4 1
        // The way pairCount works is by... (see above explanation)
        // In this case, we would get 3 matches when the first digit is evaluated, then
        // 2 matches for the second, 1 match for the third. This equals 6.
        if (pairCount(hand) == 6) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFullHouse(ArrayList<Card> hand) {
        // pairCount(hand) is equal to 4 because:
        // Ex: 4 4 4 5 5
        // The way pairCount works is by... (see above explanation)
        // In this case, we would get 2 matches when the first digit is evaulated, then
        // 1 match when the second digit is evaluated, then 0 for the third, then 1 for
        // the fourth
        if (pairCount(hand) == 4) {
            return true;
        }
        return false;
    }

    // Works by taking the first suit of the hand and comparing it to the rest. If
    // any mismatch is found, the method returns false. Else, returns true.
    private boolean isFlush(ArrayList<Card> hand) {
        firstSuit = hand.get(0).getSuit();
        for (int i = 1; i < HAND_SIZE; i++) {
            nextSuit = hand.get(i).getSuit();
            if (firstSuit != nextSuit) {
                return false;
            }
        }
        return true;
    }

    private boolean isStraight(ArrayList<Card> hand) {
        // Works by taking the first rank and comparing it to the rest of the ranks. If
        // there's a mismatch, returns false. Else, returns true.
        currentRank = hand.get(0).getRank();
        for (int i = 1; i < HAND_SIZE; i++) {
            nextRank = hand.get(i).getRank();

            // Special Ace Case: If an Ace precedes a Ten, the below if statement bypasses
            // the comparison and moves to another for loop. check if the rest of the ranks
            // are in order. Explanation for how this for loop works mirrors the explanation
            // provided in the next comment.

            if (currentRank == 1 && nextRank == 10) {
                currentRank = hand.get(1).getRank();
                for (int j = 2; j < HAND_SIZE; j++) {
                    nextRank = hand.get(j).getRank();
                    if (currentRank != nextRank - (j - 1)) {
                        return false;
                    }
                }
                break;
            }

            // Here, I use "i" to see if nextRank suceeds currentRank sequentially.
            // Ex: 1 2 3 4 5
            // Since curentRank will always = 1, I check if nextRank is = 2 by obtaining its
            // rank value, then minus it by its index (1). Since 1 = (2-1), it moves onto
            // the next nextRank. The next nextRank will then be 3, and I will check if it
            // is sequential to 1 by subtracting 3's index (2) from it. As 1 = (3-1) it
            // moves onto the next nextRank where the process continues until it either
            // returns true/false.
            if (currentRank != nextRank - i) {
                return false;
            }
        }
        // Special Ace Case Continued: the below code will bypass the comparison between
        // the Ace and the King and check if the rest of the ranks are in order. Works
        // in the same way as the above for loop.

        return true;
    }

    private boolean isThreeOfAKind(ArrayList<Card> hand) {
        if (pairCount(hand) == 3) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTwoPairs(ArrayList<Card> hand) {
        if (pairCount(hand) == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOnePair(ArrayList<Card> hand) {
        if (pairCount(hand) == 1) {
            return true;
        } else {
            return false;
        }
    }

    // Returns odds based on what the handScore was
    private int getOdds(String handScore) {
        this.handScore = handScore;
        if (handScore.equals("Royal Flush!")) {
            return 250;
        } else if (handScore.equals("Straight Flush!")) {
            return 50;
        } else if (handScore.equals("Four of a Kind!")) {
            return 25;
        } else if (handScore.equals("Full House!")) {
            return 6;
        } else if (handScore.equals("Flush!")) {
            return 5;
        } else if (handScore.equals("Straight!")) {
            return 4;
        } else if (handScore.equals("Three of a Kind!")) {
            return 3;
        } else if (handScore.equals("Two Pairs!")) {
            return 2;
        } else if (handScore.equals("One Pair!")) {
            return 1;
        } else {
            return 0;
        }
    }

}
