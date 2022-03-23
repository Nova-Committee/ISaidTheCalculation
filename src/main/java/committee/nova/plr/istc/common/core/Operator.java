package committee.nova.plr.istc.common.core;

import java.text.MessageFormat;

public record Operator(int type) {

    public int getResult(int a, int b) {
        return switch (type) {
            case 0 -> a + b;
            case 1 -> a - b;
            default -> a * b;
        };
    }

    public String getTitle(int a, int b) {
        return MessageFormat.format("{0} {1} {2} = ?", formattedNumber(a), getOperatorString(), formattedNumber(b));
    }

    public String formattedNumber(int i) {
        return i < 0 ? MessageFormat.format("({0})", i) : String.valueOf(i);
    }

    public String getOperatorString() {
        return switch (type) {
            case 0 -> "+";
            case 1 -> "-";
            default -> "*";
        };
    }
}
