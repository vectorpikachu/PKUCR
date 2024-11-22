package PKUCRProject.PKUCR.backend.Dao;

import java.util.List;

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

@Mapper
public interface TaskMapper {

    /* 当tasks不存在的时候创建tasks */    
    @Update({"<script>",
        "create table if not exists tasks (id bigint primary key auto_increment, user_id bigint not null, name varchar(255), date datetime, priority int, description text, foreign key (user_id) references users(id))",
        "</script>"
    })
    void createTable();

    @Insert("insert into tasks (user_id, name, date, priority, description) values (#{user_id}, #{name}, #{date}, #{priority}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(Task task);

    /* property对应Task对象的成员名 */
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "date"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "description", column = "description")
        }
    )
    @Select("select * from tasks where id = #{id}")
    Task selectById(@Param("id") Long id);

    @Select("select * from tasks where user_id = #{user_id}")
    List<Task> selectByUserID(@Param("user_id") Long user_id);

    @Update("update tasks set user_id = #{user_id}, name = #{name}, date = #{date}, priority = #{priority}, description = #{description} where id = #{id}")
    void update(Task task);

    @Delete("delete from tasks where id = #{id}")
    void delete(@Param("id") Long id);
}
