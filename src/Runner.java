public class Runner {
    private int depthCutOff;
    private State currentState;
    public Runner(int depthCutOff) {
        this.depthCutOff = depthCutOff;
        currentState = new State();
    }
    public void run() {

        currentState.printBoard();

        currentState.createApplicableActions();

        System.out.println("size = " + currentState.getApplicableActions().size());

        for(Action a: currentState.getApplicableActions())
            System.out.println(a.getMove());

        while (!currentState.isTerminal()) {
            System.out.println("Current player = " + currentState.getPlayer());
            currentState.printBoard();
            if (currentState.getPlayer() == Player.AI) {
                currentState = getNextState(currentState, depthCutOff);
//                change player
                currentState.setPlayer(currentState.getPlayer().otherPlayer());
            }
            else {
                currentState.addTile();
                currentState.setPlayer(currentState.getPlayer().otherPlayer());
            }
        }
    }
    private State getNextState(State currentState, int depthCutOff) {
        return currentState.result(Minimax.AlphaBetaSearch(currentState, depthCutOff));
    }
}