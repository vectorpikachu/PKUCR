package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.Task;

@Mapper
public interface TaskMapper {

    @Insert("insert into tasks (id, name, date, priority, description) values (#{id}, #{name}, #{date}, #{priority}, #{description})")
    int insert(
        @Param("id") int id,
        @Param("name") String name,
        @Param("date") String date,
        @Param("priority") int priority,
        @Param("description") String description
    );

    /* property对应Task对象的成员名 */
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "date"),
            @Result(property = "priority", column = "priority"),
            @Result(property = "description", column = "description")
        }
    )
    @Select("select * from tasks where id = #{id}")
    Task selectById(@Param("id") int id);

    @Update("update tasks set name = #{name}, date = #{date}, priority = #{priority}, description = #{description} where id = #{id}")
    void update(Task task);

    @Delete("delete from tasks where id = #{id}")
    void delete(@Param("id") int id);
}