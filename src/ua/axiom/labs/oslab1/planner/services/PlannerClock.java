package ua.axiom.labs.oslab1.planner.services;

public interface PlannerClock {
    long getClockTime();

    void addClockTime(long time);

}
