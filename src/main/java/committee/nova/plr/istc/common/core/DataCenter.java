package committee.nova.plr.istc.common.core;

import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataCenter extends SavedData {
    public static final String NAME = "ISTCDataCenter";
    private static DataCenter INSTANCE;
    private final Map<UUID, PlayerRecord> recordList = new HashMap<>();
    private final Map<UUID, PlayerScore> scoreList = new HashMap<>();
    private Question question = new Question(-99999, 0, "", 5000);

    public DataCenter() {
        super();
    }

    public DataCenter(CompoundTag tag) {
        this();
        final ListTag tempRecord = tag.getList("temp_record", Tag.TAG_COMPOUND);
        synchronized (recordList) {
            recordList.clear();
            final int recordSize = tempRecord.size();
            for (int i = 0; i < recordSize; i++) {
                final PlayerRecord p = new PlayerRecord(tempRecord.getCompound(i));
                recordList.put(p.getPlayerUUID(), p);
            }
        }

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
        final ListTag tempRecord = new ListTag();
        synchronized (recordList) {
            final Collection<PlayerRecord> rs = recordList.values();
            for (final PlayerRecord record : rs) {
                final CompoundTag recordTag = new CompoundTag();
                recordTag.putString("name", record.getPlayerName());
                recordTag.putUUID("uuid", record.getPlayerUUID());
                recordTag.putInt("time", record.getTimeSpent());
                tempRecord.add(recordTag);
            }
        }
        tag.put("temp_record", tempRecord);
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

}
