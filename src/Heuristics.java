public class Heuristics {

    public static int calculateClusteringScore(State state) {
        int boardSize = state.getBoardSize();
        int[][] board = state.getBoard();

        int clusteringScore = 0;
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if(board[i][j] == 0)
                    continue;
                int sumForCell = 0;
                int numOfNeighbors = 0;
                for(int k = 0; k < 4; k++) {
                    int newI = i + dx[k];
                    int newJ = j + dy[k];
                    if(newI >= 0 && newI < boardSize && newJ >= 0 && newJ < boardSize)
                        if(board[newI][newJ] != 0) {
                            sumForCell += Math.abs(board[newI][newJ] - board[i][j]);
                            numOfNeighbors++;
                        }
                }
                if(numOfNeighbors != 0)
                    clusteringScore += sumForCell; // /numOfNeighbors;
            }
        }
        return clusteringScore;
    }

    public static int calculateCornerScore(State state) {
        int boardSize = state.getBoardSize();
        int[][] board = state.getBoard();

        int finalScore = 0;
        int[][] bottomLeft = {
                {5, 1, 0, 0},
                {10, 5, 1, 0},
                {30, 10, 5, 1},
                {50, 30, 10, 5},
        };
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                finalScore += board[i][j] * bottomLeft[i][j];
        return finalScore;
    }
    public static int side(State state) {
        int x = 10;
        return x;
    }
    public static int heuristicValue(State state) {
//        TODO: implement
        int actualScore = state.getScore();
        int numberOfEmptyCells = state.numberOfEmptyCells();
        int clusteringScore = calculateClusteringScore(state);
        int cornerScore = calculateCornerScore(state);
        int score = (int) (cornerScore+Math.log(actualScore)*numberOfEmptyCells - clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
//        int clusteringScore = calculateClusteringScore(state);
//        int emptyCells = state.numberOfEmptyCells();
//        int heuristicScore = cornerScore - clusteringScore;
//        return heuristicScore;
    }

}
