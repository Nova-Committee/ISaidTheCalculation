package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.core.PlayerScore;
import committee.nova.plr.istc.common.tool.DataUtils;
import committee.nova.plr.istc.common.tool.PlayerHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class QueryCommand {
    public static ArgumentBuilder<CommandSource, ?> registerQ() {
        return Commands.literal("question").executes(QueryCommand::queryQuestion);
    }

    public static ArgumentBuilder<CommandSource, ?> registerS() {
        return Commands.literal("score").executes(QueryCommand::queryScore);
    }

    public static ArgumentBuilder<CommandSource, ?> registerA() {
        return Commands.literal("answer").executes(QueryCommand::queryAnswer);
    }

    public static int queryQuestion(CommandContext<CommandSource> context) throws CommandException, CommandSyntaxException {
        final PlayerEntity player = context.getSource().getPlayerOrException();
        PlayerHandler.notifyServerPlayer(player, new StringTextComponent(DataCenter.getInstance().getQuestion().toString()));
        return 0;
    }

    public static int queryScore(CommandContext<CommandSource> context) throws CommandException, CommandSyntaxException {
        final PlayerEntity player = context.getSource().getPlayerOrException();
        final PlayerScore score = DataUtils.queryScore(player);
        PlayerHandler.notifyServerPlayer(player, score != null ? new StringTextComponent(score.toString()) : new TranslationTextComponent("msg.istc.score.unknown"));
        return 0;
    }

    public static int queryAnswer(CommandContext<CommandSource> context) throws CommandException, CommandSyntaxException {
        final PlayerEntity player = context.getSource().getPlayerOrException();
        final boolean permitted = player.hasPermissions(4);
        final int answer = DataCenter.getInstance().getQuestion().getAnswer();
        PlayerHandler.notifyServerPlayer(player, new StringTextComponent(permitted ? String.valueOf(answer) : new TranslationTextComponent("msg.istc.answer.idk").getString()));
        return 0;
    }
}
