package db;

import bean.TaskBean;
import bean.UserBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    protected static List<UserBean> users = new ArrayList<>();
    protected static List<TaskBean> tasks = new ArrayList<>();
    public static UserBean session = null;


    public static UserBean getUser(String username, String password) {
        for (UserBean user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return user;
        }
        return null;
    }

    public static UserBean addUser(UserBean user) {
        for (UserBean bean : users) {
            if (bean.getUsername().equals(user.getUsername())) return null;
        }
        user.setId(users.size());
        users.add(user);
        return user;
    }

    public static Boolean addTask(TaskBean task) {
        task.setId(tasks.size());
        return tasks.add(task);
    }

    public static List<TaskBean> getTasks(Integer userId) {
        List<TaskBean> lists = new ArrayList<>();
        for (TaskBean task : tasks) {
            if (task.getCreatorId().equals(userId)) lists.add(task);
        }
        return lists;
    }

    public static boolean updateTask(Integer taskId, String name, String description, LocalDate dueDate) {
        for (TaskBean task : tasks) {
            if (task.getId().equals(taskId)) {
                task.setName(name);
                task.setDescription(description);
                task.setDescription(String.valueOf(dueDate));
                return true;
            }
        }
        return false;
    }

    public static boolean updateTask(boolean completed) {
        for (TaskBean task : tasks) {
//            if()
        }
        return false;
    }

    public static boolean completeTask(Integer taskId) {
        for (TaskBean task : tasks) {
            if (task.getId().equals(taskId)) {
                task.setCompleted(true);
                return true;
            }
        }
        return false;
    }

    public static UserBean updateUserInfo(Integer userId, String fName, String lName, String username, String password) {
        for (UserBean user : users) {
            if (user.getId().equals(userId)) {
                if (!fName.isBlank()) user.setFirstName(fName);
                if (!lName.isBlank()) user.setLastName(lName);
                if (!username.isBlank()) user.setUsername(username);
                if (!password.isBlank()) user.setPassword(password);
                return user;
            }
        }
        return null;
    }

    public static boolean deleteTask(Integer taskId) {
        TaskBean task = new TaskBean();
        for (TaskBean taskBean : tasks) {
            if (taskBean.getId().equals(taskId)) task = taskBean;
        }
        return tasks.remove(task);
    }
}
