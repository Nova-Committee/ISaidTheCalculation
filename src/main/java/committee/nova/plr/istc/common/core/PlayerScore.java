package committee.nova.plr.istc.common.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.MessageFormat;
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

    public PlayerScore(PlayerEntity player, long cor, long total) {
        this(player.getName().getString(), player.getUUID(), cor, total);
    }

    public PlayerScore(PlayerEntity player) {
        this(player, 0, 0);
    }

    public PlayerScore(CompoundNBT tag) {
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

    @Override
    public String toString() {
        return MessageFormat.format(new TranslationTextComponent("msg.istc.score.details").getString(), name, correctFreq, totalScore);
    }
}
