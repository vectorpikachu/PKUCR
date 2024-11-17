package main.java.PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.Task;
import PKUCRProject.PKUCR.backend.Entity.User;

@Mapper
public class ResourceMapper {

    @Insert("insert into users (username, password, permission) values (#{username}, #{password}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Select("select * from users where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from users where id = #{id}")
    User selectById(int id);

    @Update("update users set username = #{username}, password = #{password}, permission = #{permission} where id = #{id}")
    void update(User user);

    @Delete("delete from users where id = #{id}")
    void delete(int id);
    
}
