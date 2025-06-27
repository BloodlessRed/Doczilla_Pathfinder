import solution.model.GameState;
import solution.Solver;
import solution.model.Move;
import solution.model.Tube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiquidSortApp {
    public static void main(String[] args) {
        String[][] initialData = {
                {"4", "4", "10", "2"},
                {"8", "12", "8", "1"},
                {"9", "5", "7", "10"},
                {"5", "2", "3", "5"},
                {"7", "8", "11", "6"},
                {"2", "1", "12", "12"},
                {"11", "8", "7", "4"},
                {"1", "3", "11", "10"},
                {"9", "9", "7", "10"},
                {"11", "6", "2", "6"},
                {"3", "9", "6", "4"},
                {"1", "12", "3", "5"},
                {},
                {}
        };
        List<Tube> tubes = new ArrayList<>();
        fillTubes(findCapacity(initialData), initialData, tubes);
        Solver solver = new Solver();

        GameState gameState = new GameState(tubes);
        List<Move> solution = solver.solve(gameState);
        printResults(solution);

    }

    private static int findCapacity(String[][] data){
        int maxCp = 0;
        for (String[] i : data){
            maxCp = Math.max(i.length, maxCp);
        }
        return maxCp;
    }

    private static void fillTubes(int maxCp, String[][] data, List<Tube> tubes){
        for(int i = 0; i < data.length; i++){
            List<String> boxedLiquids = Arrays.asList(data[i]);
            tubes.add(new Tube(maxCp, boxedLiquids));
        }
    }

    private static void printResults (List<Move> solution){
        if (solution.isEmpty()){
            System.out.println("Решение не найдено!");
            return;
        }
        System.out.println("Конец пути");
        for (Move move : solution){
            System.out.println("( " + move.fromIndex() + " " + move.toIndex() + " )");
        }
        System.out.println("Начало пути");
    }
}
