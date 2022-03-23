package committee.nova.plr.istc.common.tool;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;


public class PlayerHandler {
    public static void notifyServerPlayer(Player player, Component component) {
        if (!player.level.isClientSide) player.sendMessage(component, Util.NIL_UUID);
    }

}
