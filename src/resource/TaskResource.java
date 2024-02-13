package resource;

import bean.ApiResponse;
import bean.TaskBean;
import db.DataBase;

import java.time.LocalDate;

public class TaskResource implements BaseCRUDResource<TaskBean> {
    @Override
    public ApiResponse create() {
        return null;
    }

    @Override
    public ApiResponse view() {
        return null;
    }

    @Override
    public ApiResponse update(Integer taskId) {
        boolean isCompleted = DataBase.completeTask(taskId);
        return isCompleted ? new ApiResponse(200, "Task is completed", true) :
                new ApiResponse(400, "Error occurred", false);
    }


    public ApiResponse update(Integer taskId, String name, String description, LocalDate dueDate) {
        boolean isChanged = DataBase.updateTask(taskId, name, description, dueDate);
        return isChanged ? new ApiResponse(200, "Successfully changed", true) :
                new ApiResponse(400, "Error occurred", false);
    }

    @Override
    public ApiResponse delete(Integer taskId) {
        boolean isDeleted = DataBase.deleteTask(taskId);
        return isDeleted ? new ApiResponse(200, "Successfully deleted", true) :
                new ApiResponse(400, "Error occurred", false);
    }

    public ApiResponse addTask(TaskBean task) {
        boolean isAdded = DataBase.addTask(task);
        return isAdded ? new ApiResponse(200, "Successfully created", false) :
                new ApiResponse(400, "Error occurred", true);
    }

}
