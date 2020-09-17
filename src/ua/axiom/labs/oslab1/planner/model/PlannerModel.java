package ua.axiom.labs.oslab1.planner.model;

import ua.axiom.labs.oslab1.planner.controller.TaskListener;
import ua.axiom.labs.oslab1.planner.services.PlannerClock;

public class PlannerModel implements TaskListener, PlannerClock {

    private long timeForBackgroundProcess = 0;
    private long timeForInteractiveProcess = 0;
    private long timePassedWaitingForTask = 0;

    private long globalTime = 0;

    private boolean isRunning = true;

    @Override
    public void notifyOnHewTask(Task task) {
    }

    @Override
    public void notifyNoMoreTasks() {
        this.isRunning = false;
    }

    /**
     * Adds @param mcSecs microseconds to passed time variable
     */
    public void addBackgroundPassedTime(long mcSecs) {
        timeForBackgroundProcess += mcSecs;
    }

    public void addInteractivePassedTime(long mcSecs) {
        timeForInteractiveProcess += mcSecs;
    }

    public void addWaitTime(long mcSecs) {
        timePassedWaitingForTask += mcSecs;
    }

    public long getPassedTime() {
        return timeForBackgroundProcess;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public synchronized long getClockTime() {
        return globalTime;
    }

    @Override
    public synchronized void addClockTime(long time) {
        this.globalTime += time;
    }
}