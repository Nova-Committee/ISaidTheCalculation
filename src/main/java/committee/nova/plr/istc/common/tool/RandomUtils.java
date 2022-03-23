package committee.nova.plr.istc.common.tool;

import java.util.Random;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/23 20:20
 * Version: 1.0
 */
public class RandomUtils {
    static Random rand = new Random();

    public static int nextInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
}
