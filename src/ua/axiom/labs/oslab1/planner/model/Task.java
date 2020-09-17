package ua.axiom.labs.oslab1.planner.model;

import ua.axiom.labs.oslab1.planner.services.PlannerClock;

public class Task {
    public enum TType{INTERACTIVE, BACKGROUND}

    private TType type;
    private long creationTime;
    private long startTime = -1;
    private long remainingTime;
    private final long complexityTime;

    public Task(PlannerClock clock, long complexityTime) {
        this.creationTime = clock.getClockTime();
        this.complexityTime = complexityTime;
    }

    public TType getType() {
        return type;
    }

    public void removeTime(long timePassed) {
        this.remainingTime -= timePassed;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setType(TType type) {
        this.type = type;
    }

    public void setTime(long time) {
        this.remainingTime = time;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        if(this.startTime == -1) {
            this.startTime = startTime;
        }
    }

    public long getComplexity() {
        return this.complexityTime;
    }
}
