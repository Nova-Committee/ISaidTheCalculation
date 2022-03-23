package committee.nova.plr.istc.common.core;

import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataCenter extends SavedData {
    public static final String NAME = "istc";
    private static DataCenter INSTANCE;
    private final Map<UUID, PlayerRecord> recordList = new HashMap<>();
    private final Map<UUID, PlayerScore> scoreList = new HashMap<>();
    private Question question = new Question(-99999, 0, "", 5000);

    public DataCenter() {
        super();
        this.setDirty();
    }

    public DataCenter(CompoundTag tag) {

        final ListTag scores = tag.getList("scores", Tag.TAG_COMPOUND);
        synchronized (scoreList) {
            scoreList.clear();
            final int scoreSize = scores.size();
            for (int i = 0; i < scoreSize; i++) {
                final PlayerScore s = new PlayerScore(scores.getCompound(i));
                scoreList.put(s.getPlayerUUID(), s);
            }
        }
        question = DataUtils.generateQuestion();
        this.setDirty();
    }

    public static DataCenter getInstance() {
        return INSTANCE;
    }

    public static void setInstance(DataCenter dc) {
        INSTANCE = dc;
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag tag) {
        final ListTag scores = new ListTag();
        synchronized (scoreList) {
            final Collection<PlayerScore> ss = scoreList.values();
            for (final PlayerScore score : ss) {
                final CompoundTag scoreTag = new CompoundTag();
                scoreTag.putString("name", score.getPlayerName());
                scoreTag.putUUID("uuid", score.getPlayerUUID());
                scoreTag.putLong("correct", score.getCorrectFreq());
                scoreTag.putLong("total", score.getTotalScore());
                scores.add(scoreTag);
            }
        }

        tag.put("scores", scores);
        this.setDirty();
        return tag;
    }

    public Map<UUID, PlayerRecord> getRecordList() {
        return recordList;
    }

    public Map<UUID, PlayerScore> getScoreList() {
        return scoreList;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question q) {
        question = q;
    }

    public void announceRecord(MinecraftServer server) {
        final int size = recordList.size();
        if (size == 0) return;
        DataUtils.broadcast(server, new TranslatableComponent("msg.istc.record.result"));
        final Collection<PlayerRecord> records = recordList.values();
        for (final PlayerRecord record : records) {
            DataUtils.broadcast(server, new TextComponent(MessageFormat.format(new TranslatableComponent("msg.istc.record.output").getString(), record.getPlayerName(), getFormattedTimeSpent(record.getTimeSpent()).getString())));
        }
    }

    public void clearRecord() {
        recordList.clear();
        this.setDirty();
    }

    public Component getFormattedTimeSpent(int time) {
        return new TextComponent(MessageFormat.format(new TranslatableComponent("unit.istc.second").getString(), time / 20));
    }
}
