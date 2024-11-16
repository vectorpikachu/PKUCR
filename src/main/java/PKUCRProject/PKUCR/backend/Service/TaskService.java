package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.TaskMapper;
import PKUCRProject.PKUCR.backend.Entity.Task;

/* 标注为一个服务类，来自Spring框架 */
@Service
public class TaskService {
    
    /* Autowired会自动注入依赖，不太理解 */
    @Autowired
    private TaskMapper taskMapper;

    public Task insert(Task task) {
        taskMapper.insert(task);
        Task taskInDB = taskMapper.selectById(task.getId());
        return taskInDB;
    }

    public Task selectById(int id) {
        return taskMapper.selectById(id);
        // return "selectById" + id;
    }

    public String update(Task task) {
        taskMapper.update(task);
        return "update success";
    }

    public String delete(int id) {
        taskMapper.delete(id);
        return "delete task success by id = " + id;
    }

}
