package com.codeclan.example.myapplication.modelsTests.cardsTests;

import com.codeclan.example.myapplication.models.cards.Card;
import com.codeclan.example.myapplication.models.cards.Deck;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 28/01/2018.
 */

public class DeckTest {


    Deck deck;


    @Before
    public void before(){

        deck = new Deck();

    }


    @Test
    public void canGetCardsInDeck(){
        assertEquals(16, deck.getCards().size());
    }

    @Test
    public void canRemoveCardFromDeck(){
        Card card = deck.removeTopCard();
        assertEquals(15, deck.getCards().size());
    }

    @Test
    public void canResetDeck(){
        deck.removeTopCard();
        deck.removeTopCard();
        assertEquals(14, deck.getCards().size());

        deck.resetDeck();
        deck.removeTopCard();
        assertEquals(15, deck.getCards().size());
    }
}
