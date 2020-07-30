import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum Move {
    LEFT,
    RIGHT,
    UP,
    DOWN
}

enum Player {
    AI, NEW_TILE;

    public Player otherPlayer() {
        if (this == AI) {
            return NEW_TILE;
        } else {
            return AI;
        }
    }
}

public class State {
    private Player player;
    private int[][] board;
    private int boardSize = 4;
    private int score = 0;
    List<Action> applicableActions;

    public boolean isTerminal() {
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                if(board[i][j] == 0)
                    return false;
        return !Moves.moveDown(this.stateClone()) && !Moves.moveUp(this.stateClone()) &&
                !Moves.moveLeft(this.stateClone()) && !Moves.moveRight(this.stateClone());
    }

    public State() {
        this.player = Player.AI;
        this.board = createInitialBoard();
        this. applicableActions = new ArrayList<>();
    }

    public List<Action> deepCopyAppActions() {
        List<Action> res = new ArrayList<>();
        if(applicableActions != null)
            for(Action a : applicableActions)
                res.add(a.actionClone());
        return res;
    }

    public State stateClone() {
        State resState = new State();
        resState.setBoard(this.boardClone());
        resState.setPlayer(this.player);
        resState.setBoardSize(this.boardSize);
        resState.setScore(this.score);
        resState.setApplicableActions(this.deepCopyAppActions());
        return resState;
    }

    private int[][] createInitialBoard() {
        int[][] board = new int[this.boardSize][this.boardSize];
//        we generate two starting numbers; 2 with probability 0.8 and 4 with probability 0.2
        int[] possibleNumbers = {2, 2, 2, 2, 4};
        Random rand = new Random();

        int randomPossibleNumber = rand.nextInt(5);
        int randomIndex1 = rand.nextInt(4);
        int randomIndex2 = rand.nextInt(4);
        board[randomIndex1][randomIndex2] = possibleNumbers[randomPossibleNumber];
        randomPossibleNumber = rand.nextInt(5);
        randomIndex1 = rand.nextInt(4);
        randomIndex2 = rand.nextInt(4);
        board[randomIndex1][randomIndex2] = possibleNumbers[randomPossibleNumber];
        return board;
    }

    private int[][] boardClone() {
        int[][] to = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            System.arraycopy(board[i], 0, to[i], 0, boardSize);
        return to;
    }

    public void printBoard() {
        for(int i = 0; i < boardSize; i++) {
            System.out.println();
            for(int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + "\t\t");
            }
            System.out.println();
        }
    }


    public void createApplicableActions() {
        List<Action> applicableActions = new ArrayList<>();
        State stateClone = this.stateClone();
        if(Moves.moveUp(stateClone))
            applicableActions.add(new Action(Move.UP));
        stateClone = this.stateClone();
        if(Moves.moveDown(stateClone))
            applicableActions.add(new Action(Move.DOWN));
        stateClone = this.stateClone();
        if(Moves.moveLeft(stateClone))
            applicableActions.add(new Action(Move.LEFT));
        stateClone = this.stateClone();
        if(Moves.moveRight(stateClone))
            applicableActions.add(new Action(Move.RIGHT));

        this.applicableActions = applicableActions;
    }
//    returns the state we get into from the current state after applying action a
    public State result(Action a) {
        State resState = this;
        if(a != null)
            switch(a.getMove()) {
                case UP:
                    Moves.moveUp(resState);
                    break;
                case DOWN:
                    Moves.moveDown(resState);
                    break;
                case LEFT:
                    Moves.moveLeft(resState);
                    break;
                case RIGHT:
                    Moves.moveRight(resState);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + a.getMove());
            }
        return resState;
    }

    public int numberOfEmptyCells() {
        int res = 0;
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                if(this.board[i][j] == 0)
                    res++;
        return res;
    }

    public int heuristicValue() {
        return Heuristics.heuristicValue(this);
    }

    public void addTile() {
        List<Pair<Integer, Integer>> freeTiles = new ArrayList<>();
//        save the free coordinates in a list
        for(int i = 0; i < boardSize; i++)
            for(int j = 0; j < boardSize; j++)
                if (board[i][j] == 0)
                    freeTiles.add(new Pair(i, j));
        if (freeTiles.size() == 0) {
            System.exit(0);
//            is terminal
            return;
        }
        Random rand = new Random();
        int randomCoordinates = rand.nextInt(freeTiles.size());
        int[] possibleNumbers = {2, 2, 2, 2, 4};
        int randomPossibleNumber = rand.nextInt(5);
        board[freeTiles.get(randomCoordinates).getKey()][freeTiles.get(randomCoordinates).getValue()]
                = possibleNumbers[randomPossibleNumber];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Action> getApplicableActions() {
        return applicableActions;
    }

    public void setApplicableActions(List<Action> applicableActions) {
        this.applicableActions = applicableActions;
    }

}
class Action {
    private Move move;

    public Action(Move move) {
        this.move = move;
    }

    public Action actionClone() {
        return new Action(this.move);
    }

    public Move getMove() {
        return move;
    }
    public void setMove(Move move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return "Action{" +
                ", move=" + move +
                '}';
    }
}