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

    public String insert(int id, String name, String date, int priority, String description) {
        taskMapper.insert(id, name, date, priority, description);
        return "insert success";
    }

    public String selectById(int id) {
        return taskMapper.selectById(id).toString();
        // return "selectById" + id;
    }

    public String update(int id, String name, String date, int priority, String description) {
        taskMapper.update(new Task(id, name, date, priority, description));
        return "update success";
    }

    public String delete(int id) {
        taskMapper.delete(id);
        return "delete success";
    }

}
