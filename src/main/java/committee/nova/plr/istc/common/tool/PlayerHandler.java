package committee.nova.plr.istc.common.tool;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;


public class PlayerHandler {
    public static void notifyServerPlayer(PlayerEntity player, ITextComponent component) {
        if (!player.level.isClientSide) player.sendMessage(component, Util.NIL_UUID);
    }

}
