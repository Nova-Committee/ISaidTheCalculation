package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import committee.nova.plr.istc.common.core.DataCenter;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AdminCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("complete").executes(AdminCommand::complete);
    }

    public static int complete(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        DataCenter.getInstance().getQuestion().quickFinish();
        return 0;
    }
}
