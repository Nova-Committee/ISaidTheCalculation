package committee.nova.plr.istc.common.core;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.text.MessageFormat;

public class Question {
    private final int answer;
    private final int difficulty;
    private final String str;
    private final int totalTime;
    private int timeLeft;

    public Question(int answer, int difficulty, String str, int timeLeft) {
        this.answer = answer;
        this.difficulty = difficulty;
        this.str = str;
        this.totalTime = timeLeft;
        this.timeLeft = timeLeft;
    }

    public int getAnswer() {
        return answer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getStr() {
        return str;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void quickFinish() {
        this.timeLeft = 1;
    }

    public void consumeTime() {
        timeLeft--;
    }

    public String getQuestionTitle() {
        return MessageFormat.format(new TranslatableComponent("msg.istc.question.title").getString(), this.getStr());
    }

    @Override
    public String toString() {
        return MessageFormat.format(new TranslatableComponent("msg.istc.question.details").getString(), str, difficulty, getFormattedTimeLeft().getString());
    }

    public Component getFormattedTimeLeft() {
        return new TextComponent(MessageFormat.format(new TranslatableComponent("unit.istc.second").getString(), timeLeft / 20));
    }
}
