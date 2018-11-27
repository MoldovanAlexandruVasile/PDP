import java.util.concurrent.ExecutionException;

class Main {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        Integer vertexes = randomNumber(1, 10);
        Integer graph[][] = new Integer[vertexes][vertexes];
        graph = makeMinusOnes(graph, vertexes);
        graph = generateGraph(graph, vertexes);
        printGraph(graph, vertexes);
        HamiltonianCycle hamiltonian1 = new HamiltonianCycle(vertexes, graph);
        hamiltonian1.hamCycle();
    }

    private static void printGraph(Integer[][] graph, Integer vertexes) {
        for (Integer i = 0; i < vertexes; i++) {
            for (Integer j = 0; j < vertexes; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static Integer[][] makeMinusOnes(Integer[][] graph, Integer vertexes) {
        for (Integer i = 0; i < vertexes; i++) {
            for (Integer j = 0; j < vertexes; j++) {
                graph[i][j] = -1;
            }
        }
        return graph;
    }

    public static Integer[][] generateGraph(Integer[][] graph, Integer vertexes) {
        for (Integer i = 0; i < vertexes; i++) {
            for (Integer j = 0; j < vertexes; j++) {
                Integer value = randomNumber(0, 2);
                graph[i][j] = value;
            }
        }
        return graph;
    }

    public static Integer randomNumber(Integer low, Integer up) {
        Integer lowBound = low;
        Integer highBound = up;
        Integer range = highBound - lowBound + lowBound;
        Integer rand = (int) (Math.random() * range) + lowBound;
        return rand;
    }
}
