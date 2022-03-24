package committee.nova.plr.istc.common;

import committee.nova.plr.istc.common.config.CommonConfig;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

@Mod(ISTC.MODID)
public class ISTC {
    public static final String MODID = "istc";

    public ISTC() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJoinWorld);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onServerStarted(ServerStartedEvent event) {
        final ServerLevel world = event.getServer().overworld();
        if (!world.isClientSide) {
            final DataCenter dataCenter = world.getDataStorage().computeIfAbsent(DataCenter::new, DataCenter::new, DataCenter.NAME);
            DataCenter.setInstance(dataCenter);
        }
    }

    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!CommonConfig.AUTO_REFRESH.get()) return;
        if (!(event.getEntity() instanceof Player)) return;
        final MinecraftServer server = event.getEntity().getServer();
        if (server == null) return;
        final List<ServerPlayer> list = server.getPlayerList().getPlayers();
        if (list.size() <= 1) DataUtils.start(server, DataCenter.getInstance());
    }
}
