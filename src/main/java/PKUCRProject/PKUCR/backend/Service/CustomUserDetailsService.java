package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
            throw new RuntimeException("Username already exists");
        }

        if (user.getPermission() == null) {
            user.setPermission(0);
        }

        // 将用户信息保存到数据库
        userMapper.insert(user);

        // 返回新创建的用户对象
        return userMapper.selectByEmail(user.getEmail());
    }

    public Long getUserID(String username) {
        return userMapper.selectByEmail(username).getId();
    }

    public User getUserById(Long userID) {
        return userMapper.selectById(userID);
    }

    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
}
