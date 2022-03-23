package committee.nova.plr.istc.common.command.init;

import com.mojang.brigadier.CommandDispatcher;
import committee.nova.plr.istc.common.command.impl.AdminCommand;
import committee.nova.plr.istc.common.command.impl.AnswerCommand;
import committee.nova.plr.istc.common.command.impl.QueryCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandInit {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("answer").then(AnswerCommand.register()));
        dispatcher.register(Commands.literal("istc").then(Commands.literal("query").requires(e -> e.hasPermission(0)).then(QueryCommand.registerQ())));
        dispatcher.register(Commands.literal("istc").then(Commands.literal("query").requires(e -> e.hasPermission(0)).then(QueryCommand.registerS())));
        dispatcher.register(Commands.literal("istc").then(Commands.literal("query").requires(e -> e.hasPermission(0)).then(QueryCommand.registerA())));
        dispatcher.register(Commands.literal("istc").then(Commands.literal("admin").requires(e -> e.hasPermission(2)).then(AdminCommand.register())));
    }
}
