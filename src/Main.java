import exception.IncorrectTaskException;
import exception.TaskNotFoundException;
import model.*;
import service.TaskService;
import util.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");

    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");
                label:
                while (true) {
                    printMenu();
                    System.out.print("Выберите пункт меню: ");
                    if (scanner.hasNextInt()) {
                        int menu = scanner.nextInt();
                        switch (menu) {
                            case 1:
                                addTask(scanner);
                                break;
                            case 2:
                                removeTask(scanner);
                                break;
                            case 3:
                                printTaskByDay(scanner);
                                break;
                            case 0:
                                break label;
                        }
                    } else {
                        scanner.next();
                        System.out.println("Выберите пункт меню из списка!");
                    }
                }
            }

    private static void printTaskByDay(Scanner scanner) {
            do {
                System.out.print("Введите дату в формате 22.12.2022: ");
                if (scanner.hasNext(DATE_PATTERN)) {
                    LocalDate day = parseDate(scanner.next(DATE_PATTERN));
                    if (day == null){
                        System.out.println("Некорректный формат даты!");
                        continue;
                    }
                    Collection<Task> taskByDay = TaskService.getDailyTask(day);
                    if (taskByDay.isEmpty()){
                        System.out.println("Задач на " + day.format(Constant.DATE_FORMATTER) + " нет!");
                    } else {
                        System.out.println("Задачи на " + day.format(Constant.DATE_FORMATTER) + ": ");
                        for (Task task:taskByDay){
                            System.out.println(task);
                        }
                    }

                    break;
                } else {
                    scanner.next();
                }
            } while (true);
    }

    private static void removeTask(Scanner scanner) {
        try {
            do {
                System.out.print("Введите id задачи:");
                if (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    TaskService.removeId(id);
                    System.out.println("\nЗадача id0" + id + " удалена!\n");
                    break;
                } else {
                    scanner.next();
                }
            } while (true);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addTask(Scanner scanner) {
        try {
            System.out.print("Введите название задачи: ");
            String title = scanner.next();
            System.out.print("Введите описание задачи: ");
            String description = scanner.next();
            TaskType type = inputType(scanner);
            LocalDateTime dateTime = inputDateTime(scanner);
            Repeate repeate = inputRepeat(scanner);
            Task task = new Task(title,description,type,dateTime,repeate);
            TaskService.add(task);
            System.out.println("Задача " + task + " добавлена.");
        } catch (IncorrectTaskException e) {
            System.out.println(e.getMessage());
        }


        }
        private static LocalDateTime inputDateTime(Scanner scanner){
            LocalDateTime dateTime;
            do {
                System.out.print("Введите дату и время задачи в формате \"dd.mm.yyyy hh:mm\" ");
                if (scanner.hasNext(DATE_TIME_PATTERN)){
                   dateTime = parseDateTime(scanner.next(DATE_TIME_PATTERN));
                   if (dateTime == null){
                       System.out.println("Некорректный формат даты и времени");
                       continue;
                   }
                    break;
                } else {
                    scanner.next();
                }
            } while (true);
            return dateTime;
        }
        private static LocalDateTime parseDateTime (String dateTime){
        try {
            return LocalDateTime.parse(dateTime, Constant.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e){
            return null;
        }
        }
    private static LocalDate parseDate (String date){
        try {
            return LocalDate.parse(date, Constant.DATE_FORMATTER);
        } catch (DateTimeParseException e){
            return null;
        }
    }
        private static TaskType inputType(Scanner scanner){
            TaskType type;
            do {
                System.out.print("Введите тип задачи:\n1. Личная задача.\n2. Рабочая задача.\nВвод:");
                if (scanner.hasNextInt()){
                    int num = scanner.nextInt();
                    if (num != 1 && num != 2){
                        System.out.println("ВВедите 1 или 2!");
                        continue;
                    }
                    if (num == 1){
                        type = TaskType.personal;
                    } else {
                        type = TaskType.work;
                    }
                    break;
                } else {
                    scanner.next();
                }
            } while (true);
            return type;
        }
    private static Repeate inputRepeat(Scanner scanner){
        Repeate repeate;
        do {
            System.out.print("Как часто задача будет повторяться? :\n1. Однократно.\n2. Каждый день.\n" +
                    "3. Каждую неделю.\n4. Каждый месяц.\n5. Каждый год.\nВвод: ");
            if (scanner.hasNextInt()){
                int num = scanner.nextInt();
                if (num < 1 || num > 5){
                    System.out.println("ВВедите цифру от 1 до 5!");
                    continue;
                }
                switch (num){
                    default:
                    case 1:
                        repeate = new OneTime();
                        break;
                    case 2:
                        repeate = new Daily();
                        break;
                    case 3:
                        repeate = new Weekly();
                        break;
                    case 4:
                        repeate = new Montly();
                        break;
                    case 5:
                        repeate = new Year();
                        break;
                }
                break;
            } else {
                scanner.next();
            }
        } while (true);
        return repeate;
    }

        private static void printMenu() {
            System.out.println(
                    """
                            1. Добавить задачу
                            2. Удалить задачу
                            3. Получить задачу на указанный день
                            0. Выход
                            """
            );
        }

    }
