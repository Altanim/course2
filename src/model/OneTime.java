package model;

import java.time.LocalDateTime;

public class OneTime implements Repeate{
    @Override
    public LocalDateTime nextTime(LocalDateTime currentDateTime) {
        return null;
    }

    @Override
    public String toString() {
        return "однократная";
    }
}
