package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

import PKUCRProject.PKUCR.backend.Entity.Course;

@Mapper
public interface CourseMapper {

    /* 当courses不存在的时候创建courses */    
    @Update({"<script>",
        "create table if not exists courses (id bigint primary key auto_increment, user_id bigint not null, name varchar(255), date date, priority int, description text, foreign key (user_id) references users(id))",
        "</script>"
    })
    void createTable();

    @Insert("insert into courses (user_id, courseID, courseName, teacher, credit) values (#{user_id}, #{courseID}, #{courseName}, #{teacher}, #{credit})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(Course course);

    /* property对应Course对象的成员名 */
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "courseID", column = "courseID"),
            @Result(property = "courseName", column = "courseName"),
            @Result(property = "teacher", column = "teacher"),
            @Result(property = "credit", column = "credit"),
        }
    )
    @Select("select * from courses where id = #{id}")
    Course selectById(@Param("id") Long id);

    @Update("update courses set user_id = #{user_id}, courseID = #{courseID}, courseName = #{courseName}, teacher = #{teacher}, credit = #{credit} where id = #{id}")
    void update(Course course);

    @Delete("delete from courses where id = #{id}")
    void delete(@Param("id") Long id);

    @Select("select * from courses where user_id = #{user_id}")
    List<Course> selectByUserID(@Param("user_id") Long user_id);
}