package model;

import java.time.LocalDateTime;

public class Year implements Repeate{
    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return currentDateTime.plusYears(1);
    }

    @Override
    public String toString() {
        return "ежегодовая";
    }
}
