package committee.nova.plr.istc.common;

import committee.nova.plr.istc.common.config.CommonConfig;
import committee.nova.plr.istc.common.core.DataCenter;
import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

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

    public void onServerStarted(FMLServerStartedEvent event) {
        final ServerWorld world = event.getServer().overworld();
        if (!world.isClientSide) {
            final DataCenter dataCenter = world.getDataStorage().computeIfAbsent(DataCenter::new, DataCenter.NAME);
            DataCenter.setInstance(dataCenter);
        }
    }

    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        final MinecraftServer server = event.getEntity().getServer();
        if (server == null) return;
        final List<ServerPlayerEntity> list = server.getPlayerList().getPlayers();
        if (list.size() <= 1) DataUtils.start(server, DataCenter.getInstance());
    }
}
