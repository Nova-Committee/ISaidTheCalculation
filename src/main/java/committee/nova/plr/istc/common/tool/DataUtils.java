package committee.nova.plr.istc.common.tool;

import committee.nova.plr.istc.common.config.CommonConfig;
import committee.nova.plr.istc.common.core.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DataUtils {
    public static void complete(MinecraftServer server, DataCenter center) {
        final Map<UUID, PlayerRecord> records = center.getRecordList();
        if (server == null) return;
        final Collection<PlayerRecord> recordC = records.values();
        for (final PlayerRecord record : recordC) {
            addScore(server, record);
        }
        center.announceRecord(server);
        start(server, center);
    }

    public static void start(MinecraftServer server, DataCenter center) {
        center.clearRecord();
        final Question q = generateQuestion();
        center.setQuestion(q);
        broadcast(server, new StringTextComponent(q.getQuestionTitle()));
        DataCenter.getInstance().setDirty();
    }


    public static Question generateQuestion() {
        final Random random = new Random();
        final int range = RandomUtils.nextInt(1, 5);
        final Operator operator = new Operator(RandomUtils.nextInt(1, 4));
        final int max = (int) Math.pow(10, range);
        final int a = RandomUtils.nextInt(-max, max + 1);
        final int b = RandomUtils.nextInt(-max, max + 1);
        final int result = operator.getResult(a, b);
        final int difficulty = range * (operator.getType() == 2 ? 2 : 1);
        return new Question(result, difficulty, operator.getTitle(a, b), CommonConfig.TIME_FOR_A_QUESTION.get());
    }


    public static void broadcast(MinecraftServer server, ITextComponent component) {
        if (server == null) return;
        server.getPlayerList().broadcastMessage(component, ChatType.CHAT, Util.NIL_UUID);
    }

    public static boolean addRecord(PlayerEntity player, int timeSpent, int timeGiven) {
        final UUID uuid = player.getUUID();
        final DataCenter center = DataCenter.getInstance();
        final Map<UUID, PlayerRecord> records = center.getRecordList();
        if (records.size() >= 5) return false;
        if (!records.containsKey(uuid)) {
            records.put(uuid, new PlayerRecord(player, timeSpent, timeGiven));
            if (records.size() == 5) center.getQuestion().quickFinish();
        } else {
            return false;
        }
        return true;
    }

    public static void addScore(MinecraftServer server, PlayerRecord record) {
        final UUID uuid = record.getPlayerUUID();
        final Map<UUID, PlayerScore> scores = DataCenter.getInstance().getScoreList();
        final PlayerScore score = scores.get(uuid);
        final long correct = score != null ? score.getCorrectFreq() + 1 : 1;
        final int scoreToAdd = calculateScore(record.getTimeSpent(), record.getTimeGiven());
        final ServerPlayerEntity player = server.getPlayerList().getPlayer(uuid);
        if (player != null) player.giveExperiencePoints(scoreToAdd / 10);
        final long total = score != null ? score.getTotalScore() + scoreToAdd : scoreToAdd;
        scores.put(uuid, new PlayerScore(record.getPlayerName(), uuid, correct, total));
        DataCenter.getInstance().setDirty();
    }

    @Nullable
    public static PlayerScore queryScore(PlayerEntity player) {
        final UUID uuid = player.getUUID();
        return DataCenter.getInstance().getScoreList().get(uuid);
    }


    public static int calculateScore(int timeSpent, int timeGiven) {
        final int difficulty = DataCenter.getInstance().getQuestion().getDifficulty();
        return Math.min((int) (Math.pow(0.9, timeSpent / (timeGiven * 1F)) * 110), 100) * difficulty;
    }

}
