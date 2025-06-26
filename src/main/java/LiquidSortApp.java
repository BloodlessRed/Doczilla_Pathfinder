import solution.GameState;
import solution.Solver;
import solution.model.Move;
import solution.model.Tube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiquidSortApp {
    public static void main(String[] args) {
        int[][] initialData = {
                // Верхний ряд (слева направо)
                {4, 4, 10, 2},      // Пробирка 1
                {8, 12, 8, 1},      // Пробирка 2
                {9, 5, 7, 10},      // Пробирка 3
                {5, 2, 3, 5},       // Пробирка 4
                {7, 8, 11, 6},      // Пробирка 5
                {2, 1, 12, 12},     // Пробирка 6
                {11, 8, 7, 4},      // Пробирка 7

                // Нижний ряд (слева направо)
                {1, 3, 11, 10},     // Пробирка 8
                {9, 9, 7, 10},      // Пробирка 9
                {11, 6, 2, 6},      // Пробирка 10
                {3, 9, 6, 4},       // Пробирка 11
                {1, 12, 3, 5},      // Пробирка 12
                {},                 // Пробирка 13 (пустая)
                {}                  // Пробирка 14 (пустая)
        };
        int maxCp = 0;
        for (int[] i : initialData){
            maxCp = Math.max(i.length, maxCp);
        }
        List<Tube> tubes = new ArrayList<>();
        Solver solver = new Solver();

        for(int i = 0; i < initialData.length; i++){
            List<String> boxedLiquids = Arrays.stream(initialData[i])
                    .mapToObj(String::valueOf)
                    .toList();
            tubes.add(new Tube(maxCp, boxedLiquids));
        }
        GameState gameState = new GameState(tubes);
        List<Move> solution = solver.solve(gameState);

        for (Move move : solution){
            System.out.println("( " + move.fromIndex() + " " + move.toIndex() + " )");
        }
    }
}
