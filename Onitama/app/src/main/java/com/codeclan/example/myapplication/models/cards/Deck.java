package com.codeclan.example.myapplication.models.cards;

import com.codeclan.example.myapplication.R;
import com.codeclan.example.myapplication.constants.CardName;
import com.codeclan.example.myapplication.constants.FactionColour;
import com.codeclan.example.myapplication.models.Coordinate;
import com.codeclan.example.myapplication.models.moves.Down;
import com.codeclan.example.myapplication.models.moves.Left;
import com.codeclan.example.myapplication.models.moves.Right;
import com.codeclan.example.myapplication.models.moves.Up;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by user on 28/01/2018.
 */

public class Deck implements Serializable {


    private Down    down;
    private Up      up;
    private Left    left;
    private Right   right;
    private Card    card;

    private ArrayList<Card> cards;


    public Deck(){

        this.up      = new Up();
        this.down    = new Down();
        this.left    = new Left();
        this.right   = new Right();

        this.cards = new ArrayList<>();

        createDeck();
        shuffleCards();

    }


    public Collection getCards() {
        ArrayList<Card> copyCards = new ArrayList<>(this.cards);
        return copyCards;
    }

    public Card removeTopCard() {
        return cards.remove(0);
    }

    public void resetDeck() {
        cards.clear();
        createDeck();
    }


    private void createDeck() {
        createFrog();
        createCobra();
        createRooster();
        createMantis();
        createHorse();
        createElephant();
        createBoar();
        createDragon();
        createTiger();
        createCrab();
        createOx();
        createEel();
        createCrane();
        createGoose();
        createRabbit();
        createMonkey();
    }

    private void createMonkey() {
        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(left, down));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));
        ArrayList<Coordinate> move4 = new ArrayList<>(Arrays.asList(right, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);
        moveset.put(4, move4);

        card = new Card(CardName.MONKEY, FactionColour.BLUE,
                        R.drawable.monkey_blue_view, R.drawable.monkey_red_view, moveset);

        cards.add(card);
    }

    private void createRabbit() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right, right));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(right, up));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.RABBIT, FactionColour.BLUE,
                        R.drawable.rabbit_blue_view, R.drawable.rabbit_red_view, moveset);

        cards.add(card);
    }

    private void createGoose() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));
        ArrayList<Coordinate> move4 = new ArrayList<>(Arrays.asList(right, down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);
        moveset.put(4, move4);

        card = new Card(CardName.GOOSE, FactionColour.BLUE,
                        R.drawable.goose_blue_view, R.drawable.goose_red_view, moveset);

        cards.add(card);
    }

    private void createCrane() {
        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(left, down));
        ArrayList<Coordinate> move3 = new ArrayList<Coordinate>(Arrays.asList(up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.CRANE, FactionColour.BLUE,
                        R.drawable.crane_blue_view, R.drawable.crane_red_view, moveset);

        cards.add(card);
    }

    private void createEel() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(left, down));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.EEL, FactionColour.BLUE,
                        R.drawable.eel_blue_view, R.drawable.eel_red_view, moveset);

        cards.add(card);
    }

    private void createOx() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(down));
        ArrayList<Coordinate> move3 = new ArrayList<Coordinate>(Arrays.asList(up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.OX, FactionColour.BLUE,
                        R.drawable.ox_blue_view, R.drawable.ox_red_view, moveset);

        cards.add(card);
    }

    private void createCrab() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right, right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left, left));
        ArrayList<Coordinate> move3 = new ArrayList<Coordinate>(Arrays.asList(up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.CRAB, FactionColour.BLUE,
                        R.drawable.crab_blue_view, R.drawable.crab_red_view, moveset);

        cards.add(card);
    }

    private void createTiger() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(up, up));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);

        card = new Card(CardName.TIGER, FactionColour.BLUE,
                        R.drawable.tiger_blue_view, R.drawable.tiger_red_view, moveset);

        cards.add(card);
    }

    private void createDragon() {
        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, right, up));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(left, left, up));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move4 = new ArrayList<>(Arrays.asList(left, down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);
        moveset.put(4, move4);

        card = new Card(CardName.DRAGON, FactionColour.RED,
                        R.drawable.dragon_blue_view, R.drawable.dragon_red_view, moveset);

        cards.add(card);
    }

    private void createBoar() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<Coordinate>(Arrays.asList(up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.BOAR, FactionColour.RED,
                        R.drawable.boar_blue_view, R.drawable.boar_red_view, moveset);

        cards.add(card);
    }

    private void createElephant() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(right, up));
        ArrayList<Coordinate> move4 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);
        moveset.put(4, move4);

        card = new Card(CardName.ELEPHANT, FactionColour.RED,
                        R.drawable.elephant_blue_view, R.drawable.elephant_red_view, moveset);

        cards.add(card);
    }

    private void createHorse() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(up));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<Coordinate>(Arrays.asList(down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.HORSE, FactionColour.RED,
                        R.drawable.horse_blue_view, R.drawable.horse_red_view, moveset);

        cards.add(card);
    }

    private void createMantis() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(down));
        ArrayList<Coordinate> move2 = new ArrayList<>(Arrays.asList(right, up));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.MANTIS, FactionColour.RED,
                        R.drawable.mantis_blue_view, R.drawable.mantis_red_view,moveset);

        cards.add(card);
    }

    private void createRooster() {
        ArrayList<Coordinate> move1 = new ArrayList<Coordinate>(Arrays.asList(right));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(right, up));
        ArrayList<Coordinate> move4 = new ArrayList<>(Arrays.asList(left, down));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);
        moveset.put(4, move4);

        card = new Card(CardName.ROOSTER, FactionColour.RED,
                        R.drawable.rooster_blue_view, R.drawable.rooster_red_view, moveset);

        cards.add(card);
    }

    private void createCobra() {
        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(right, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.COBRA, FactionColour.RED,
                        R.drawable.cobra_blue_view, R.drawable.cobra_red_view, moveset);

        cards.add(card);
    }

    private void createFrog() {
        ArrayList<Coordinate> move1 = new ArrayList<>(Arrays.asList(right, down));
        ArrayList<Coordinate> move2 = new ArrayList<Coordinate>(Arrays.asList(left, left));
        ArrayList<Coordinate> move3 = new ArrayList<>(Arrays.asList(left, up));

        HashMap<Integer, ArrayList<Coordinate>> moveset = new HashMap<>();
        moveset.put(1, move1);
        moveset.put(2, move2);
        moveset.put(3, move3);

        card = new Card(CardName.FROG, FactionColour.RED,
                        R.drawable.frog_blue_view, R.drawable.frog_red_view, moveset);

        cards.add(card);
    }

    private void shuffleCards(){
        Collections.shuffle(cards);
    }

}
