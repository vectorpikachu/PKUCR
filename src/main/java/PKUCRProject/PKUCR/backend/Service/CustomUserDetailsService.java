package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.UserMapper;
import PKUCRProject.PKUCR.backend.Entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserMapper userMapper;

    
    /** 
     * @param username = email of the user.
     * @return User
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库获得User信息
        // 我们这里的username = email !
        User userInDB = userMapper.selectByEmail(username);
        if (userInDB == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return userInDB;
    }

    public User registerUser(User user) {
        if (userMapper.selectByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User email already exists");
        }

        if (user.getPermission() == null) {
            user.setPermission(0);
        }

        System.err.println("Username: " + user.getUsernameReal());

        // 将用户信息保存到数据库
        userMapper.insert(user);
        userMapper.updateUsername(user.getId(), user.getUsernameReal());

        // 返回新创建的用户对象
        return userMapper.selectByEmail(user.getEmail());
    }

    public Long getUserID(String username) {
        return userMapper.selectByEmail(username).getId();
    }

    public Long getUserIDByName(String username) {
        return userMapper.selectByName(username).getId();
    }

    public User getUserById(Long userID) {
        return userMapper.selectById(userID);
    }

    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
}
