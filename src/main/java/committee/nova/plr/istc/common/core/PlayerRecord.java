package committee.nova.plr.istc.common.core;

import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class PlayerRecord {
    private final String name;
    private final UUID uuid;
    private final int timeSpent;
    private final int timeGiven;

    public PlayerRecord(Player player, int timeSpent, int timeGiven) {
        this.name = player.getName().getString();
        this.uuid = player.getUUID();
        this.timeSpent = timeSpent;
        this.timeGiven = timeGiven;
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

    public int getTimeGiven() {
        return timeGiven;
    }
}
