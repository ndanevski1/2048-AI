public class Runner {
    private int depthCutOff;
    private State currentState;
    public Runner(int depthCutOff) {
        this.depthCutOff = depthCutOff;
        currentState = new State();
    }
    public void run() {

//        currentState.printBoard();

        currentState.createApplicableActions();

//        for(Action a: currentState.getApplicableActions())
//            System.out.println(a.getMove());

        while (!currentState.isTerminal()) {
            System.out.println("\n\n\n\n");

            if (currentState.getPlayer() == Player.AI) {
                currentState.printBoard();
                System.out.println("Current score = " + currentState.getScore());

                currentState = getNextState(currentState, depthCutOff);
                currentState.createApplicableActions();
//                change player
                currentState.setPlayer(currentState.getPlayer().otherPlayer());
            }
            else {
                currentState.addTile();
                currentState.setPlayer(currentState.getPlayer().otherPlayer());

//                currentState.printBoard();
            }
        }
        currentState.printBoard();
    }
    private State getNextState(State currentState, int depthCutOff) {
        return currentState.result(Minimax.AlphaBetaSearch(currentState, depthCutOff));
    }
}