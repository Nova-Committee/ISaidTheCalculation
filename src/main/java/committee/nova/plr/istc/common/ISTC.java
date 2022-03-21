package committee.nova.plr.istc.common;

import committee.nova.plr.istc.common.core.DataCenter;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(ISTC.MODID)
public class ISTC {
    public static final String MODID = "istc";

    public ISTC() {
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        final ServerLevel world = event.getServer().overworld();
        if (!world.isClientSide) {
            final DataCenter dataCenter = world.getDataStorage().computeIfAbsent(DataCenter::new, DataCenter::new, DataCenter.NAME);
            DataCenter.setInstance(dataCenter);
        }
    }
}
