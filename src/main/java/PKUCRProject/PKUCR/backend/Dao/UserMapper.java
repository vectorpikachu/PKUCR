package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.User;

@Mapper
public interface UserMapper {

    @Insert("insert into users (email, password, permission, username) values (#{email}, #{password}, #{permission}, #{username})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(User user);

    @Select("select * from users where email = #{email}")
    User selectByEmail(String email);

    @Select("select * from users where email = #{email} and password = #{password}")
    User selectByEmailAndPassword(String email, String password);

    @Select("select * from users where id = #{id}")
    User selectById(Long id);

    @Update("update users set email = #{email}, password = #{password}, permission = #{permission}, username = #{username} where id = #{id}")
    void update(User user);

    @Update("update users set username = #{username} where id = #{id}")
    void updateUsername(Long id, String username);

    @Delete("delete from users where id = #{id}")
    void delete(Long id);
    
}
