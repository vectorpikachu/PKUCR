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

import PKUCRProject.PKUCR.backend.Entity.Course;

@Mapper
public interface CourseMapper {

    //TODO: 初始建表的方法

    @Insert("insert into courses (courseID, courseName, teacher, time, location, credit) values (#{courseID}, #{courseName}, #{teacher}, #{time}, #{location}, #{credit})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Course course);

    /* property对应Course对象的成员名 */
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "courseID", column = "courseID"),
            @Result(property = "courseName", column = "courseName"),
            @Result(property = "teacher", column = "teacher"),
            @Result(property = "time", column = "time"),
            @Result(property = "location", column = "location"),
            @Result(property = "credit", column = "credit"),
        }
    )
    @Select("select * from courses where id = #{id}")
    Course selectById(@Param("id") int id);

    @Update("update courses set courseID = #{courseID}, courseName = #{courseName}, teacher = #{teacher}, time = #{time}, location = #{location}, credit = #{credit} where id = #{id}")
    void update(Course course);

    @Delete("delete from courses where id = #{id}")
    void delete(@Param("id") int id);

}
