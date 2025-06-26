package solution.model;

public record SearchNode(GameState state,
                         SearchNode parent,
                         Move move,
                         int move_count_score,
                         int h_score) implements Comparable<SearchNode> {
    public SearchNode(GameState state){
        this(state, null, null, 0, state.calculateHeuristics());
    }

    public int getFinalScore(){
        return move_count_score + h_score;
    }
    @Override
    public int compareTo(SearchNode o) {
        return Integer.compare(getFinalScore(), o.getFinalScore());
    }
}
