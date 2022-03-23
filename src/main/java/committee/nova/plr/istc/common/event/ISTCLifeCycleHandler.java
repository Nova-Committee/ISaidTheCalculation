package committee.nova.plr.istc.common.event;

import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.core.Question;
import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class ISTCLifeCycleHandler {
    @SubscribeEvent
    public static void onWorldTickEvent(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.world.dimension() != World.OVERWORLD) return;
        final DataCenter center = DataCenter.getInstance();
        final Question question = center.getQuestion();
        if (question.getTimeLeft() > 0) {
            question.consumeTime();
        } else {
            DataUtils.complete(event.world.getServer(), center);
        }
    }
}
