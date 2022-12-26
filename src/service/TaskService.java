package service;

import exception.TaskNotFoundException;
import model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskService {
private static final Map<Integer, Task> TASK_MAP = new HashMap<>();
private TaskService(){

}
public static void add(Task task){
    TASK_MAP.put(task.getId(), task);
}
public static Collection<Task> getDailyTask(LocalDate day){
Collection<Task> taskByDay = new ArrayList<>();
Collection<Task> allTasks = TASK_MAP.values();
for (Task task : allTasks){
    LocalDateTime currentDateTime = task.getDateTime();
    if (currentDateTime.toLocalDate().equals(day)){
        taskByDay.add(task);
        continue;

    }
    LocalDateTime nextDateTime = currentDateTime;
    do{
        nextDateTime = task.getRepeatability().nextTime(currentDateTime);
        if (nextDateTime == null){
            break;
        }
        if (nextDateTime.toLocalDate().equals(day)){
            taskByDay.add(task);
            break;
        }
    } while (nextDateTime.toLocalDate().isBefore(day));
}
    return taskByDay;
}
    public static void removeId (int id) throws TaskNotFoundException {
       if (TASK_MAP.remove(id) == null) {
           throw new TaskNotFoundException(id);
       }
    }
}
