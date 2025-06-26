package solution;

import solution.model.Move;
import solution.model.Tube;

import java.util.*;

public record GameState(List<Tube> tubes) {
    private static Set<String> uniqueColors;
    public GameState(){
        this(new ArrayList<>());
    }

    public boolean isSolved(){
        int counter = 0;
        for (Tube tube : tubes){
            if (tube.isEmpty()){
                continue;
            }
            if (tube.isFull() && tube.isHomogeneous()){
                counter++;
            }
        }
        return counter == getUniqueColors().size();
    }

    public Set<String> getUniqueColors(){
        if (uniqueColors != null){
            return uniqueColors;
        }
        Set<String> uniques = new HashSet<>();
        for (Tube tube : this.tubes){
            if (!tube.isEmpty()){
                for (String liquid : tube.getColors()){
                    if (!liquid.isEmpty()){
                        uniques.add(liquid);
                    }
                }
            }
        }
        uniqueColors = uniques;
        return uniqueColors;
    }

    public int calculateHeuristics(){
        Map<String, Tube> homeTubes = assignHomeTubes();

        int h_score = 0;
        for (Tube currentTube: tubes){
            for (String color: currentTube.getLiquids()){
                Tube homeTubeForColor = homeTubes.get(color);
                if (!currentTube.equals(homeTubeForColor)){
                    h_score++;
                }
            }
        }
        return h_score;
    }

    private Map<String, Tube> assignHomeTubes(){
        Map<String, Tube> homeTubes = new HashMap<>();
        for (String color : getUniqueColors()){
            int highscore = -1;
            Tube bestTube = null;
            for (Tube tube : tubes){
                int score = 0;
                int sameColorsCount = (int) tube.getLiquids().stream()
                        .filter(x -> x.equals(color))
                        .count();
                int emptySlots = tube.getCapacity() - tube.getCurrentVolume();

                score = sameColorsCount * 10 + emptySlots;

                if (score > highscore){
                    highscore = score;
                    bestTube = tube;
                }
            }
            homeTubes.put(color, bestTube);
        }
        return homeTubes;
    }

    public List<Move> generatePossibleMoves(Move previousMove){
        List<Move> moves = new ArrayList<>();
        for (int fromId = 0; fromId < tubes.size(); fromId++) {
            Tube currentTube = tubes.get(fromId);
            if (currentTube.isEmpty()){
                continue;
            }
            for (int toId = 0; toId < tubes.size(); toId++) {
                Tube otherTube = tubes.get(toId);
                int topBlockSize = currentTube.getTopColorBlockSize();
                if (currentTube.equals(otherTube)
                        || currentTube.isHomogeneous()
                        || otherTube.isHomogeneous()
                        || otherTube.isFull()
                        || (!otherTube.isEmpty() && !currentTube.getTopColor().equals(otherTube.getTopColor()))
                        || (otherTube.isEmpty() && currentTube.getCurrentVolume() == topBlockSize)
                        || (previousMove != null && previousMove.toIndex() == fromId && previousMove.fromIndex() == toId)){
                    continue;
                }
                if (otherTube.isEmpty()){
                    boolean hasEmptyTubeWithLowerIndex = false;
                    for (int i = 0; i < toId; i++){
                        if (tubes.get(i).isEmpty()){
                            hasEmptyTubeWithLowerIndex = true;
                            break;
                        }
                    }
                    if (hasEmptyTubeWithLowerIndex){
                        continue;
                    }
                }
                if (otherTube.isEmpty()
                        || Objects.equals(currentTube.getTopColor(), otherTube.getTopColor())){
                    moves.add(new Move(fromId, toId));
                }
            }
        }
        return moves;
    }

    public GameState applyMove(Move move){
        List<Tube> copiedTubes = new ArrayList<>(tubes);
        Tube from = copiedTubes.get(move.fromIndex());
        Tube to = copiedTubes.get(move.toIndex());

        Tube copiedFrom = new Tube(from);
        Tube copiedTo = new Tube(to);

        int topBlockSize = copiedFrom.getTopColorBlockSize();
        int freeSpace = copiedTo.getFreeSpace();
        int amountToMove = Math.min(topBlockSize, freeSpace);

        for (int i = 0; i < amountToMove; i++ ){
            String removedColor = copiedFrom.removeLiquid();
            copiedTo.addLiquid(removedColor);
        }

        copiedTubes.set(move.fromIndex(), copiedFrom);
        copiedTubes.set(move.toIndex(), copiedTo);
        return new GameState(copiedTubes);
    }
}
