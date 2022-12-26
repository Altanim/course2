package model;

import exception.IncorrectTaskException;
import util.Constant;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String taskDescription;
    private TaskType type;
    private LocalDateTime dateTime;
    private final int id;
    private static int idGenerator = 1;
    private Repeate repeatability;

    public Task(String title, String taskDescription, TaskType type, LocalDateTime dateTime, Repeate repeatability) throws IncorrectTaskException {
        setTitle(title);
        setTaskDescription(taskDescription);
        setType(type);
        setDateTime(dateTime);
        setRepeatability(repeatability);
        id = idGenerator++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IncorrectTaskException {
        if (title == null || title.isEmpty() || title.isBlank()) {
            throw new IncorrectTaskException("заголовок задачи");
        }
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) throws IncorrectTaskException {
        if (taskDescription == null || taskDescription.isEmpty() || taskDescription.isBlank()) {
            throw new IncorrectTaskException("описание задачи");
        }
        this.taskDescription = taskDescription;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        if (type == null) {
            type = type.personal;
        }
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) throws IncorrectTaskException {
        if (dateTime == null) {
            throw new IncorrectTaskException("дата и время");
        }
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }


    public Repeate getRepeatability() {
        return repeatability;
    }

    public void setRepeatability(Repeate repeatability) {
        if (repeatability == null) {
            repeatability = new OneTime();
        }
        this.repeatability = repeatability;
    }

    @Override
    public String toString() {
        return "id: " + id
                + "\nНазвание: " + title
                + "\nОписание: " + taskDescription
                + "\nТип задачи: " + type
                + "\nДата и время: " + dateTime.format(Constant.DATE_TIME_FORMATTER)
                + "\nПовторяемость: " + repeatability;
    }
}
