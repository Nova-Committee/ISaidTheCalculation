package committee.nova.plr.istc.common.tool;

import committee.nova.plr.istc.common.core.*;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

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
            addScore(record);
        }
        broadcast(server,
                //todo
                new Component[]{});
        removeRecord(records);
        center.setQuestion(generateQuestion());
        DataCenter.getInstance().setDirty();
    }

    public static Question generateQuestion() {
        final Random random = new Random();
        final int difficulty = random.nextInt(1, 5);
        final Operator operator = new Operator(random.nextInt(1, 4));
        final int max = (int) Math.pow(10, difficulty);
        final int a = random.nextInt(-max, max + 1);
        final int b = random.nextInt(-max, max + 1);
        final int result = operator.getResult(a, b);
        return new Question(result, difficulty, operator.getTitle(a, b), 600);
    }

    public static void broadcast(MinecraftServer server, Component[] components) {
        if (server == null) return;
        for (final Component component : components) {
            server.getPlayerList().broadcastMessage(component, ChatType.CHAT, Util.NIL_UUID);
        }
    }

    public static void addRecord(Player player, int timeSpent) {
        final UUID uuid = player.getUUID();
        final DataCenter center = DataCenter.getInstance();
        final Map<UUID, PlayerRecord> records = center.getRecordList();
        if (records.size() >= 5) return;
        records.put(uuid, new PlayerRecord(player, timeSpent));
        if (records.size() == 5) center.getQuestion().setTimeLeft(1);
    }

    public static void removeRecord(Map<UUID, PlayerRecord> records) {
        records.clear();
        DataCenter.getInstance().setDirty();
    }

    public static void addScore(PlayerRecord record) {
        final UUID uuid = record.getPlayerUUID();
        final Map<UUID, PlayerScore> scores = DataCenter.getInstance().getScoreList();
        final PlayerScore score = scores.get(uuid);
        final long correct = score != null ? score.getCorrectFreq() + 1 : 1;
        final int scoreToAdd = calculateScore(record.getTimeSpent());
        final long total = score != null ? score.getTotalScore() + scoreToAdd : scoreToAdd;
        scores.put(uuid, new PlayerScore(record.getPlayerName(), uuid, correct, total));
        DataCenter.getInstance().setDirty();
    }

    public static int calculateScore(int timeSpent) {
        return Math.min((int) Math.pow(0.9, timeSpent / 600F) * 110, 100);
    }

}
