package solution.model;

import solution.GameState;

public record SearchNode(GameState state, SearchNode parent, Move move) {
    public SearchNode(GameState state){
        this(state, null, null);
    }
}
