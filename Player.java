
/*****************************************
 * Player class for Video Poker game
 * Jaewon Lee
 * My Player Class
 ****************************************/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Player {

    private ArrayList<Card> hand; // the player's cards
    private double bankroll;
    private double bet;
    private double amountWon;
    private Scanner input;
    private final int INITIAL_BANKROLL = 50;

    // you may choose to use more instance variables

    public Player() {
        // create a player here
        input = new Scanner(System.in);
        hand = new ArrayList<Card>();
        bankroll = INITIAL_BANKROLL;
    }

    public void addCard(Card c) {
        // add the card c to the player's hand
        hand.add(c);
    }

    public void removeCard(Card c) {
        // remove the card c from the player's hand
        hand.remove(c);
    }

    public void bets(double amt) {
        // player makes a bet
        bet = amt;

        // In case the player tries to bet more than they have OR more than 5 credits OR
        // invalid number (less than 1)
        while (bet > bankroll || bet > 5 || bet < 1) {
            System.out.println("You're betting an invalid amount of tokens. Input a valid number of tokens to bet:");
            bet = input.nextInt();
        }

        // Deducts the bet from the player's bankroll
        bankroll -= bet;

    }

    public void winnings(double odds) {
        // adjust bankroll if player wins
        amountWon = odds * bet;

        System.out.println("You just won: " + amountWon);

        bankroll += amountWon;
    }

    public double getBankroll() {
        // return current balance of bankroll
        return bankroll;
    }

    // you may wish to use more methods here

    public ArrayList<Card> getPlayerHand() {
        return hand;
    }

    // Sorts hand using Collections.sort
    public void sortHand() {
        Collections.sort(hand);
    }
}
