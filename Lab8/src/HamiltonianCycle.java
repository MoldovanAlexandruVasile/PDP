import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class HamiltonianCycle {
    private Integer V;
    private Integer path[];
    private Integer graph[][];
    ArrayList<FutureTask> futureTasks = new ArrayList<>();


    HamiltonianCycle(Integer v, Integer[][] graph) {
        V = v;
        this.graph = graph;
    }

    private Boolean isSafe(Integer v, Integer graph[][], Integer path[], Integer pos) {
        if (graph[path[pos - 1]][v] == 0)
            return false;
        for (Integer i = 0; i < pos; i++)
            if (path[i] == v)
                return false;
        return true;
    }

    private Boolean hamCycleUtil(Integer graph[][], Integer path[], Integer pos) throws ExecutionException, InterruptedException {
        if (pos == V) {
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }
        for (Integer v = 1; v < V; v++) {
            if (isSafe(v, graph, path, pos)) {
                path[pos] = v;
                FutureTask<Boolean> futureTask = new FutureTask<>(() -> hamCycleUtil(graph, path, pos + 1));
                futureTask.run();
                Boolean result = futureTask.get();
                if (result)
                    return true;
                path[pos] = -1;
            }
        }
        return false;
    }

    Integer hamCycle() throws ExecutionException, InterruptedException {
        path = new Integer[V];
        for (Integer i = 0; i < V; i++)
            path[i] = -1;

        path[0] = 0;
        if (!hamCycleUtil(graph, path, 1)) {
            System.out.println("\n\tSolution does not exist");
            return 0;
        }
        printSolution(path);
        return 1;
    }

    private void printSolution(Integer path[]) {
        System.out.println("\n\tSolution Exists:");
        System.out.print("\t");
        for (Integer i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");
        System.out.println(" " + path[0] + " ");
    }
}