package committee.nova.plr.istc.common.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class PlayerRecord {
    private final String name;
    private final UUID uuid;
    private final int timeSpent;

    public PlayerRecord(Player player, int timeSpent) {
        this.name = player.getName().getString();
        this.uuid = player.getUUID();
        this.timeSpent = timeSpent;
    }

    public PlayerRecord(CompoundTag tag) {
        this.name = tag.getString("name");
        this.uuid = tag.getUUID("uuid");
        this.timeSpent = tag.getInt("time");
    }

    public String getPlayerName() {
        return name;
    }

    public UUID getPlayerUUID() {
        return uuid;
    }

    public int getTimeSpent() {
        return timeSpent;
    }
}
