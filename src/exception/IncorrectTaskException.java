package exception;

public class IncorrectTaskException extends Exception{
    private final String parameter;

    public IncorrectTaskException(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getMessage() {
        return parameter + " задан неверно!";
    }
}
