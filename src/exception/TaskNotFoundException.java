package exception;

public class TaskNotFoundException extends Exception{
    private int id;
    public TaskNotFoundException(int id){
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Задача " + id + " не найдена!";
    }
}
