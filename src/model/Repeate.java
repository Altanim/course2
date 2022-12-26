package model;

import java.time.LocalDateTime;

public interface Repeate {
    LocalDateTime nextTime(LocalDateTime currentDateTime);
}
