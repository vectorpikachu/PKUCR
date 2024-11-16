package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.CourseMapper;
import PKUCRProject.PKUCR.backend.Dao.TaskMapper;
import PKUCRProject.PKUCR.backend.Dao.UserMapper;
import PKUCRProject.PKUCR.backend.Entity.User;

@Service
public class UserService {
 
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private CourseMapper courseMapper;

    public User login(User user) {
        User userInDB = userMapper.selectByEmail(user.getEmail());
        if (userInDB == null || !userInDB.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Login failed");
        }
        return userInDB;
    }

    public void updateToken(User user) {
        userMapper.updateToken(user);
    }

    public void createTaskTable(String tableName) {
        taskMapper.createTable(tableName);
    }

    public void createCourseTable(String tableName) {
        courseMapper.createTable(tableName);
    }

    public String insert(User user) {
        /* 注册之前不要同名 */
        if (userMapper.selectByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }
        userMapper.insert(user);
        return "register success";
    }

    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    public String update(User user) {
        userMapper.update(user);
        return "update success";
    }

    public String delete(int id) {
        userMapper.delete(id);
        return "delete user success by id = " + id;
    }

}
