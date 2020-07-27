public class Minimax {

    public static Action AlphaBetaSearch(State state, int depthCutoff) {

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int v = maxValue(state, depthCutoff, alpha, beta);

        System.out.println("v = " + v);

        Action returnAction = null;
        for(Action a: state.applicableActions())
            if(a.getValue() == v)
                returnAction = a;
        return returnAction;
    }

    private static int maxValue(State state, int depth, int alpha, int beta) {
        if (state.isTerminal())
            return state.utility();
        else if (depth == 0)
            return state.heuristicValue();
        int v = Integer.MIN_VALUE;
        for (Action a: state.applicableActions()) {
            v = Math.max(v, minValue(state.result(a), depth - 1, alpha, beta));
            if (v >= beta)
                return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    private static int minValue(State state, int depth, int alpha, int beta) {
        if (state.isTerminal())
            return state.utility();
        else if (depth == 0)
            return state.heuristicValue();
        int v = Integer.MAX_VALUE;
        for (Action a: state.applicableActions()) {
            v = Math.min(v, maxValue(state.result(a), depth - 1, alpha, beta));
            if (v <= alpha)
                return v;
            beta = Math.min(beta, v);
        }
        return v;
    }
}
