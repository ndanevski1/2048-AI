public class Minimax {

    public static Action abPruning(State state, int depth) {
        int res = Integer.MIN_VALUE;
        Action resAction = null;
        state.createApplicableActions();
        System.out.print("Comparing between: ");
        for(Action a: state.getApplicableActions()) {
            State stateClone = state.stateClone();
            int val = minValue(stateClone, a, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
            System.out.print(val + " and ");
            if(val > res) {
                res = val;
                resAction = a;
            }
        }
        System.out.println();
        return resAction;
    }

    private static int maxValue(State previousState, Action toNewState, int alpha, int beta, int depth) {
        State newState = previousState.result(toNewState);
        newState.createApplicableActions();
        if (depth == 0)
            return newState.heuristicValue();

        int val = Integer.MIN_VALUE;
        for (Action a : newState.getApplicableActions()) {
            State stateClone = newState.stateClone();
            int valTemp = minValue(stateClone, a, alpha, beta, depth - 1);
            if (valTemp > val)
                val = valTemp;
            if (val >= beta)
                return val;
            alpha = Math.max(alpha, val);
        }
        return val;
    }

    private static int minValue(State previousState, Action toNewState, int alpha, int beta, int depth) {
        State newState = previousState.result(toNewState);
        newState.createApplicableActions();
        if (depth == 0)
            return newState.heuristicValue();

        int val = Integer.MAX_VALUE;
        for (Action a : newState.getApplicableActions()) {
            State stateClone = newState.stateClone();
            int valTemp = maxValue(stateClone, a, alpha, beta, depth - 1);
            if (valTemp < val)
                val = valTemp;
            if (val <= alpha)
                return val;
            beta = Math.min(beta, val);
        }
        return val;
    }
}
