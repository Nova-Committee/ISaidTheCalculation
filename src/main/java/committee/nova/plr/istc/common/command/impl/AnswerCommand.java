package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.core.Question;
import committee.nova.plr.istc.common.tool.DataUtils;
import committee.nova.plr.istc.common.tool.PlayerHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.MessageFormat;


public class AnswerCommand {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.argument("result", IntegerArgumentType.integer()).executes(AnswerCommand::answerExecution);
    }

    public static int answerExecution(CommandContext<CommandSource> context) throws CommandException, CommandSyntaxException {
        final PlayerEntity player = context.getSource().getPlayerOrException();
        final int answer = context.getArgument("result", Integer.class);
        final Question q = DataCenter.getInstance().getQuestion();
        if (answer == q.getAnswer()) {
            final int total = q.getTotalTime();
            final boolean b = DataUtils.addRecord(player, total - q.getTimeLeft(), total);
            PlayerHandler.notifyServerPlayer(player, new TranslationTextComponent(MessageFormat.format("msg.istc.answer.{0}", b ? "success" : "late")));
        } else {
            PlayerHandler.notifyServerPlayer(player, new TranslationTextComponent("msg.istc.answer.incorrect"));
        }
        return 0;
    }
}
