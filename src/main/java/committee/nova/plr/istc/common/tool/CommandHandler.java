package committee.nova.plr.istc.common.tool;

public class CommandHandler {
    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return -1;
        }
    }
}
