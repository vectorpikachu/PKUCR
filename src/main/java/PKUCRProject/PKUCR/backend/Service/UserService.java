package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.UserMapper;
import PKUCRProject.PKUCR.backend.Entity.User;

@Service
public class UserService {
 
    @Autowired
    private UserMapper userMapper;

    public String insert(User user) {
        userMapper.insert(user);
        return "insert success with id = " + user.getId();
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
