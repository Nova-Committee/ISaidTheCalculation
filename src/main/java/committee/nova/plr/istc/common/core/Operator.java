package committee.nova.plr.istc.common.core;

import java.text.MessageFormat;

public class Operator {

    int type;

    public Operator(int type) {
        this.type = type;
    }

    public int getResult(int a, int b) {
        switch (type) {
            case 0:
                return a + b;
            case 1:
                return a - b;
            default:
                return a * b;
        }

    }

    public String getTitle(int a, int b) {
        return MessageFormat.format("{0} {1} {2} = ?", formattedNumber(a), getOperatorString(), formattedNumber(b));
    }

    public String formattedNumber(int i) {
        return i < 0 ? MessageFormat.format("({0})", i) : String.valueOf(i);
    }

    public String getOperatorString() {
        switch (type) {
            case 0:
                return "+";
            case 1:
                return "-";
            default:
                return "*";
        }
    }

    public int getType() {
        return type;
    }

}
