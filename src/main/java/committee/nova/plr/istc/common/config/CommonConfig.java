package committee.nova.plr.istc.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.IntValue TIME_FOR_A_QUESTION = BUILDER.comment(
            "Configuration of ISTC",
            "How much time should be given to a question?",
            "Default is 600 ticks(30 seconds)"
    ).defineInRange("time_given", 600, 200, Integer.MAX_VALUE);

    public static final ForgeConfigSpec COMMON_CONFIG = BUILDER.build();
}
