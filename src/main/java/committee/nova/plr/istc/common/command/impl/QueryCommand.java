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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class QueryCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> registerQ() {
        return Commands.literal("question").executes(QueryCommand::queryQuestion);
    }

    public static ArgumentBuilder<CommandSourceStack, ?> registerS() {
        return Commands.literal("score").executes(QueryCommand::queryScore);
    }

    public static ArgumentBuilder<CommandSourceStack, ?> registerA() {
        return Commands.literal("answer").executes(QueryCommand::queryAnswer);
    }

    public static int queryQuestion(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        PlayerHandler.notifyServerPlayer(player, new TextComponent(DataCenter.getInstance().getQuestion().toString()));
        return 0;
    }

    public static int queryScore(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        final PlayerScore score = DataUtils.queryScore(player);
        PlayerHandler.notifyServerPlayer(player, score != null ? new TextComponent(score.toString()) : new TranslatableComponent("msg.istc.score.unknown"));
        return 0;
    }

    public static int queryAnswer(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        final boolean permitted = player.hasPermissions(4);
        final int answer = DataCenter.getInstance().getQuestion().getAnswer();
        PlayerHandler.notifyServerPlayer(player, new TextComponent(permitted ? String.valueOf(answer) : new TranslatableComponent("msg.istc.answer.idk").getString()));
        return 0;
    }
}
