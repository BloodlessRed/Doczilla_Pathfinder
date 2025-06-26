package solution;

import solution.model.Move;
import solution.model.SearchNode;

import java.util.*;

public class Solver {
    private Queue<SearchNode> queue;
    private Set<GameState> visitedState;

    public Solver(){
        this.queue = new LinkedList<>();
        this.visitedState = new HashSet<>();
    }

    public List<Move> solve(GameState initialState){
        if (queue.size() == 0 && visitedState.size() == 0){
            queue.add(new SearchNode(initialState));
            visitedState.add(initialState);
        }
        while (!queue.isEmpty()){
            SearchNode currentNode = queue.poll();
            GameState currentState = currentNode.state();
            boolean isSolved = currentState.isSolved();
            if (isSolved){
                return reconstructPath(currentNode);
            }
            List<Move> possibleMoves = currentState.generatePossibleMoves(currentNode.move());
            for (Move move : possibleMoves){
                GameState nextState = currentState.applyMove(move);
                if(visitedState.contains(nextState)){
                    continue;
                }
                visitedState.add(nextState);
                SearchNode newNode = new SearchNode(nextState, currentNode, move);
                queue.add(newNode);
            }

        }
        return new ArrayList<>();
    }

    public List<Move> reconstructPath(SearchNode finalNode){
        List<Move> path = new ArrayList<>();
        SearchNode currentNode = finalNode;
        while (currentNode.parent() != null){
            path.add(currentNode.move());
            currentNode = currentNode.parent();
        }
        return path;
    }
}
