public class Moves {
    private static boolean move(State state, int start, int yIncrease, int xIncrease) {
        int boardSize = state.getBoardSize();
        int[][] board = state.getBoard();
        int score = state.getScore();
        boolean moved = false;
        boolean merged[][] = new boolean[boardSize][boardSize];

        for (int i = 0; i < boardSize * boardSize; i++) {
            int j = Math.abs(start - i);
            int row = j / boardSize;
            int col = j % boardSize;

            if (board[row][col] == 0)
                continue;

            int nextRow = row + yIncrease;
            int nextCol = col + xIncrease;

            while (nextRow >= 0 && nextRow < boardSize && nextCol >= 0 && nextCol < boardSize) {
                int next = board[nextRow][nextCol];
                int curr = board[row][col];
//                if the next cell if free, shift that way
                if (next == 0) {
                    board[nextRow][nextCol] = curr;
                    board[row][col] = 0;
                    row = nextRow;
                    col = nextCol;
                    nextRow += yIncrease;
                    nextCol += xIncrease;
                    moved = true;

                } else if (next == curr) {
                    if(merged[row][col] || merged[nextRow][nextCol])
                        break;
                    next *= 2;
                    curr = 0;
                    score += next;

                    board[nextRow][nextCol] = next;
                    board[row][col] = curr;
                    merged[nextRow][nextCol] = true;
                    merged[row][col] = true;
                    moved = true;
                    break;
                } else
                    break;
            }
        }
        state.setScore(score);
        return moved;
    }

    static boolean moveUp(State state) {
        return move(state, 0, -1, 0);
    }

    static boolean moveDown(State state) {
        return move(state, state.getBoardSize() * state.getBoardSize() - 1, 1, 0);
    }

    static boolean moveLeft(State state) {
        return move(state, 0, 0, -1);
    }

    static boolean moveRight(State state) {
        return move(state, state.getBoardSize() * state.getBoardSize() - 1, 0, 1);
    }

}
