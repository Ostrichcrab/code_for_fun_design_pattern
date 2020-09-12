package interpreter.warning;

import java.util.Map;

public interface Expression {
    boolean interpret(Map<String, Long> stats);
}

