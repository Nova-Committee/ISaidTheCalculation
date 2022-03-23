package committee.nova.plr.istc.common.command.impl;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.core.Question;
import committee.nova.plr.istc.common.tool.DataUtils;
import committee.nova.plr.istc.common.tool.PlayerHandler;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

import java.text.MessageFormat;


public class AnswerCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.argument("result", IntegerArgumentType.integer()).executes(AnswerCommand::answerExecution);
    }

    public static int answerExecution(CommandContext<CommandSourceStack> context) throws CommandRuntimeException, CommandSyntaxException {
        final Player player = context.getSource().getPlayerOrException();
        final int answer = context.getArgument("result", Integer.class);
        final Question q = DataCenter.getInstance().getQuestion();
        if (answer == q.getAnswer()) {
            final int total = q.getTotalTime();
            final boolean b = DataUtils.addRecord(player, total - q.getTimeLeft(), total);
            PlayerHandler.notifyServerPlayer(player, new TranslatableComponent(MessageFormat.format("msg.istc.answer.{0}", b ? "success" : "late")));
        } else {
            PlayerHandler.notifyServerPlayer(player, new TranslatableComponent("msg.istc.answer.incorrect"));
        }
        return 0;
    }
}
