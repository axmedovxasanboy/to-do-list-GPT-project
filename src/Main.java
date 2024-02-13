import bean.ApiResponse;
import bean.TaskBean;
import bean.UserBean;
import db.DataBase;
import resource.TaskResource;
import resource.UserResource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerNum = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to To-Do List Manager project");
        while (true) {
            System.out.println();
            if (DataBase.session == null) {
                System.out.print("""
                        0. Exit
                        1. Log In
                        2. Register
                        >>>\s""");
            } else {
                System.out.print("""
                        0. Exit
                        1. Create Task
                        2. View Tasks
                        3. Edit Task
                        4. Delete Task
                        5. My Profile
                        9. Log out
                        >>>\s""");
            }
            String choice = scannerStr.nextLine();
            if (DataBase.session == null) {
                switch (choice) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> logIn();
                    case "2" -> register();
                }
            } else {
                switch (choice) {
                    case "0" -> {
                        return;
                    }
                    case "1" -> createTask();
                    case "2" -> viewTasks();
                    case "3" -> editTask();
                    case "4" -> deleteTask();
                    case "5" -> userInfo();
                    case "9" -> logOut();
                }
            }
        }
    }

    private static void deleteTask() {
        viewTasks();
        System.out.println("-1. Back to menu (for any inserting)");
        System.out.print("Id of task which you want to delete: ");
        Integer taskId = idChecker(scannerNum.nextLine());
        if (taskId == -1) return;
        TaskResource resource = new TaskResource();
        ApiResponse response = resource.delete(taskId);
        System.out.println(response.getMessage());

    }

    private static void userInfo() {
        System.out.println(DataBase.session);
        System.out.print("""
                1. Edit profile
                Or press Enter to continue...
                >>>\s""");

        String choice = scannerStr.nextLine();
        if (choice.equals("1")) editInfo();
    }

    private static void editInfo() {
        System.out.println("-1. Back to menu (for any inserting)");
        System.out.print("Insert new first name (optional): ");
        String fName = scannerNum.nextLine();
        if (fName.equals("-1")) return;
        System.out.print("Insert new last name (optional): ");
        String lName = scannerNum.nextLine();
        if (lName.equals("-1")) return;
        System.out.print("Insert new username (optional): ");
        String username = scannerNum.nextLine();
        if (username.equals("-1")) return;
        System.out.print("Insert new password (optional): ");
        String password = scannerNum.nextLine();
        if (password.equals("-1")) return;
        UserResource resource = new UserResource();
        ApiResponse response = resource.update(DataBase.session.getId(), fName, lName, username, password);
        System.out.println(response.getMessage());
        if (response.getData() != null) {
            System.out.println("Your new information:");
            System.out.println(response.getData());
        }

    }

    private static void editTask() {
        UserResource res = new UserResource();
        ApiResponse resp = res.viewTasks(DataBase.session.getId());
        if (resp.getData() != null) {
            viewTasks();
            System.out.println("-1. Back to menu (for any inserting)");
            System.out.print("Insert task id: ");
            String id = scannerNum.nextLine();
            if (id.equals("-1")) return;
            Integer taskId = idChecker(id);
            if (taskId == -1) return;
            TaskResource resource = new TaskResource();
            System.out.print("Is task completed\n1-yes\n2-no\n>>> ");
            String helper = scannerStr.nextLine();
            if (helper.equals("-1")) return;
            boolean completed = helper.equals("1");

            if (!completed) {
                System.out.print("Insert new name: ");
                String name = scannerNum.nextLine();
                if (name.equals("-1")) return;
                System.out.print("Insert new description: ");
                String description = scannerNum.nextLine();
                if (description.equals("-1")) return;
                System.out.print("Insert new due date (dd-MM-yyyy): ");
                String date = scannerNum.nextLine();
                if (date.equals("-1")) return;
                if (dateChecker(date)) {
                    ApiResponse response = resource.update(taskId, name, description, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    System.out.println(response.getMessage());
                } else {
                    System.out.println("Invalid date inserted");
                }
            } else {
                ApiResponse response = resource.update(taskId);
                System.out.println(response.getMessage());

            }
        } else {
            System.out.println(resp.getMessage());
        }
    }

    private static void viewTasks() {
        UserResource resource = new UserResource();
        ApiResponse response = resource.viewTasks(DataBase.session.getId());

        System.out.println("********************");
        System.out.println(response.getMessage());
        List<TaskBean> list = (List<TaskBean>) response.getData();
        if (list != null) list.forEach(System.out::println);
        System.out.println("********************");

    }

    private static void createTask() {
        TaskBean task;
        boolean isDescriptionEmpty = true;
        System.out.println("-1. Back to menu (for any inserting)");
        System.out.print("What would you like to do?\n>>> ");
        String taskName = scannerStr.nextLine();
        if (taskName.equals("-1")) return;
        System.out.print("Description (optional): ");
        String description = scannerStr.nextLine();
        if (description.equals("-1")) return;
        if (!description.isBlank()) isDescriptionEmpty = false;
        System.out.print("Insert due date (dd-MM-yyyy): ");
        String date = scannerStr.nextLine();
        if (date.equals("-1")) return;
        if (dateChecker(date)) {
            LocalDate dueDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (isDescriptionEmpty) {
                task = new TaskBean(taskName, DataBase.session.getId(), dueDate);
            } else {
                task = new TaskBean(taskName, description, DataBase.session.getId(), dueDate);
            }
        } else {
            System.out.println("Invalid date inserted");
            return;
        }
        TaskResource resource = new TaskResource();
        ApiResponse response = resource.addTask(task);
        System.out.println(response.getMessage());

    }

    private static boolean dateChecker(String date) {
        boolean matches = date.matches("\\d{2}-\\d{2}-\\d{4}");
        if (matches) {
            String[] split = date.split("-");
            int month = Integer.parseInt(split[1]);
            switch (month) {
                case 1, 3, 5, 7, 8, 10, 12 -> {
                    if (Integer.parseInt(split[0]) > 31) {
                        return false;
                    }
                }
                case 2 -> {
                    if (Integer.parseInt(split[2]) % 4 == 0 && Integer.parseInt(split[2]) % 100 != 0) {
                        if (Integer.parseInt(split[0]) > 29) {
                            return false;
                        }
                    } else {
                        if (Integer.parseInt(split[0]) > 28) {
                            return false;
                        }
                    }
                }
                case 4, 6, 9, 11 -> {
                    if (Integer.parseInt(split[0]) > 30) {
                        return false;
                    }
                }
                default -> {
                    return false;
                }
            }
            LocalDate now = LocalDate.now();
            LocalDate dueDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return dueDate.isAfter(now);
        }
        return false;
    }

    private static Integer idChecker(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                System.out.println("Invalid id inserted");
                return -1;
            }
        }
        return Integer.parseInt(id);
    }

    private static void logOut() {
        DataBase.session = null;
    }

    private static void register() {
        UserResource resource = new UserResource();

        System.out.println("-1. Back to menu (for any inserting)");
        System.out.print("Enter your first name: ");
        String firstName = scannerStr.nextLine();
        if (firstName.equals("-1")) return;
        System.out.print("Enter your last name: ");
        String lastName = scannerStr.nextLine();
        if (lastName.equals("-1")) return;
        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        if (username.equals("-1")) return;
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();
        if (password.equals("-1")) return;

        UserBean user = new UserBean(firstName, lastName, username, password);

        ApiResponse response = resource.add(user);

        System.out.println(response.getMessage());
        if (response.getCode().equals(400)) register();

        else if (response.getCode().equals(200)) DataBase.session = (UserBean) response.getData();

    }

    private static void logIn() {
        System.out.println("-1. Back to menu (for any inserting)");
        System.out.print("Insert username: ");
        String username = scannerStr.nextLine();
        if (username.equals("-1")) return;
        System.out.print("Insert password: ");
        String password = scannerStr.nextLine();
        if (password.equals("-1")) return;
        UserBean user = new UserBean(username, password);
        UserResource resource = new UserResource();
        ApiResponse response = resource.getUser(user);
        System.out.println(response.getMessage());
        DataBase.session = (UserBean) response.getData();
    }

}