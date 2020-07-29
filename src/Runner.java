public class Runner {
    private int depthCutOff;
    private State currentState;
    public Runner(int depthCutOff) {
        this.depthCutOff = depthCutOff;
        currentState = new State();
    }
    public void run() {
        currentState.createApplicableActions();
        while (!currentState.isTerminal()) {
            System.out.println("Current score = " + currentState.getScore() + ". Current player = " + currentState.getPlayer());
            currentState.printBoard();
            System.out.println();
            if (currentState.getPlayer() == Player.AI) {
                currentState = getNextState(currentState, depthCutOff);
                currentState.createApplicableActions();
            }
            else
                currentState.addTile();
            currentState.setPlayer(currentState.getPlayer().otherPlayer());
        }
        System.out.println("Final position of the board:");
        currentState.printBoard();
        System.out.println("Final score = " + currentState.getScore());
    }
    private State getNextState(State currentState, int depthCutOff) {
        Action nextAction = Minimax.abPruning(currentState, depthCutOff);
        return currentState.result(nextAction);
    }
}