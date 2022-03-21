package committee.nova.plr.istc.common.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class PlayerScore {
    private final String name;
    private final UUID uuid;
    private long correctFreq;
    private long totalScore;

    public PlayerScore(String name, UUID uuid, long cor, long total) {
        this.name = name;
        this.uuid = uuid;
        this.correctFreq = cor;
        this.totalScore = total;
    }

    public PlayerScore(Player player, long cor, long total) {
        this(player.getName().getString(), player.getUUID(), cor, total);
    }

    public PlayerScore(Player player) {
        this(player, 0, 0);
    }

    public PlayerScore(CompoundTag tag) {
        this.name = tag.getString("name");
        this.uuid = tag.getUUID("uuid");
        this.correctFreq = tag.getLong("correct");
        this.totalScore = tag.getLong("total");
    }

    public void addCorrect() {
        correctFreq++;
    }

    public void addScore(int i) {
        totalScore += i;
    }

    public String getPlayerName() {
        return name;
    }

    public UUID getPlayerUUID() {
        return uuid;
    }

    public long getCorrectFreq() {
        return correctFreq;
    }

    public long getTotalScore() {
        return totalScore;
    }
}
