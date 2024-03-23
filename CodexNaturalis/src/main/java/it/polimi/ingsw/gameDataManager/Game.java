package it.polimi.ingsw.gameDataManager;

import java.util.Map;

public class Game {
    private Map<Player, Boolean> players;
    private TableOfDecks tableOfDecks;
    private PointTable pointTable;
    private Player winner;
    private Boolean started;
    private String id;
    private Turn turn;
}
