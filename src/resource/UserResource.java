package resource;

import bean.ApiResponse;
import bean.TaskBean;
import bean.UserBean;
import db.DataBase;

import java.util.List;

public class UserResource implements BaseCRUDResource<UserBean> {
    @Override
    public ApiResponse create() {
        return null;
    }

    @Override
    public ApiResponse view() {
        return null;
    }

    @Override
    public ApiResponse update(Integer userId) {
        return null;
    }

    @Override
    public ApiResponse delete(Integer taskId) {
        return null;
    }

    public ApiResponse getUser(UserBean user) {
        UserBean userBean = DataBase.getUser(user.getUsername(), user.getPassword());
        return userBean == null ? new ApiResponse(400, "User not found", null) :
                new ApiResponse(200, "Successfully Logged in", userBean);

    }

    public ApiResponse add(UserBean user) {
        UserBean userBean = DataBase.addUser(user);
        return userBean == null ? new ApiResponse(400, "User exists", null) :
                new ApiResponse(200, "Successfully created!", userBean);
    }

    public ApiResponse viewTasks(Integer id) {
        List<TaskBean> tasks = DataBase.getTasks(id);
        return tasks.isEmpty() ? new ApiResponse(400, "Tasks not found", null) :
                new ApiResponse(200, "Task list", tasks);
    }

    public ApiResponse update(Integer id, String name, String lName, String username, String password) {
        UserBean user = DataBase.updateUserInfo(id, name, lName, username, password);
        return user == null ? new ApiResponse(400, "Error occurred", null) :
                new ApiResponse(200, "Successfully updated", user);
    }
}
