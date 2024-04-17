package it.polimi.ingsw;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.Angle;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card testCard;

    @BeforeEach
    public void setUp() {
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        testCard = new InitialCard(true, frontItems, backItems, backResource);
    }

    @Test
