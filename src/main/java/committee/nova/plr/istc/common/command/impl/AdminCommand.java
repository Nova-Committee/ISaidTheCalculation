package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;

public class AdminCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> registerC() {
        return Commands.literal("complete").executes(AdminCommand::complete);
    }

    public static ArgumentBuilder<CommandSourceStack, ?> registerS() {
        return Commands.literal("start").executes(AdminCommand::start);
    }

    public static int complete(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        DataCenter.getInstance().getQuestion().quickFinish();
        return 0;
    }

    public static int start(CommandContext<CommandSourceStack> context) throws CommandRuntimeException {
        final MinecraftServer server = context.getSource().getServer();
        DataUtils.start(server, DataCenter.getInstance());
        return 0;
    }
}
