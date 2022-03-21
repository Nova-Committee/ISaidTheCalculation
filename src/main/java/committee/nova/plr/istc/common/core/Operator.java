package committee.nova.plr.istc.common.core;

import java.text.MessageFormat;

public class Operator {
    private final int type;

    public Operator(int type) {
        this.type = type;
    }

    public int getResult(int a, int b) {
        return switch (type) {
            case 0 -> a + b;
            case 1 -> a - b;
            default -> a * b;
        };
    }

    public String getTitle(int a, int b) {
        final String strB = b < 0 ? MessageFormat.format("({0})", b) : String.valueOf(b);
        return MessageFormat.format("{0} {1} {2} = ?", a, getOperatorString(), b);
    }

    public String getOperatorString() {
        return switch (type) {
            case 0 -> "+";
            case 1 -> "-";
            default -> "*";
        };
    }
}
