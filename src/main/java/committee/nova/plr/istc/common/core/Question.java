package committee.nova.plr.istc.common.core;

public class Question {
    private final int answer;
    private final int difficulty;
    private final String str;
    private int timeLeft;

    public Question(int answer, int difficulty, String str, int timeLeft) {
        this.answer = answer;
        this.difficulty = difficulty;
        this.str = str;
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

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void consumeTime() {
        timeLeft--;
    }
}
