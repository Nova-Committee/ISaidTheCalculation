package committee.nova.plr.istc.common.core;

import committee.nova.plr.istc.common.tool.DataUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataCenter extends WorldSavedData {
    public static final String NAME = "istc";
    private static DataCenter INSTANCE;
    private final Map<UUID, PlayerRecord> recordList = new HashMap<>();
    private final Map<UUID, PlayerScore> scoreList = new HashMap<>();
    private Question question = new Question(-99999, 0, "", 5000);

    public DataCenter() {
        super(NAME);
        this.setDirty();
    }

    public DataCenter(CompoundNBT nbt) {
        super(NAME);
        this.load(nbt);
        this.setDirty();
    }

    @Override
    public void load(CompoundNBT tag) {
        final ListNBT scores = tag.getList("scores", 7);
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
    public CompoundNBT save(@Nonnull CompoundNBT tag) {
        final ListNBT scores = new ListNBT();
        synchronized (scoreList) {
            final Collection<PlayerScore> ss = scoreList.values();
            for (final PlayerScore score : ss) {
                final CompoundNBT scoreTag = new CompoundNBT();
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
        DataUtils.broadcast(server, new TranslationTextComponent("msg.istc.record.result"));
        final Collection<PlayerRecord> records = recordList.values();
        for (final PlayerRecord record : records) {
            DataUtils.broadcast(server, new StringTextComponent(MessageFormat.format(new TranslationTextComponent("msg.istc.record.output").getString(), record.getPlayerName(), getFormattedTimeSpent(record.getTimeSpent()).getString())));
        }
    }

    public void clearRecord() {
        recordList.clear();
        this.setDirty();
    }

    public ITextComponent getFormattedTimeSpent(int time) {
        return new StringTextComponent(MessageFormat.format(new TranslationTextComponent("unit.istc.second").getString(), time / 20));
    }
}
