package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;

public class AdminCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("complete").executes(AdminCommand::complete).then(Commands.literal("start").executes(AdminCommand::start));
    }

    public static int complete(CommandContext<CommandSource> context) throws CommandException {
        DataCenter.getInstance().getQuestion().quickFinish();
        return 0;
    }

    public static int start(CommandContext<CommandSource> context) throws CommandException {
        final MinecraftServer server = context.getSource().getServer();
        DataUtils.start(server, DataCenter.getInstance());
        return 0;
    }
}
