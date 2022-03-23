package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.core.PlayerScore;
import committee.nova.plr.istc.common.tool.DataUtils;
import committee.nova.plr.istc.common.tool.PlayerHandler;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class QueryCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> registerQ() {
        return Commands.literal("question").executes(QueryCommand::question);
    }

    public static ArgumentBuilder<CommandSourceStack, ?> registerS() {
        return Commands.literal("score").executes(QueryCommand::score);
    }

    public static int question(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        PlayerHandler.notifyServerPlayer(player, new TextComponent(DataCenter.getInstance().getQuestion().toString()));
        return 0;
    }

    public static int score(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        final PlayerScore score = DataUtils.queryScore(player);
        if (score == null) return 0;
        PlayerHandler.notifyServerPlayer(player, new TextComponent(score.toString()));
        return 0;
    }
}
