package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;

import PKUCRProject.PKUCR.backend.Entity.BasicCourse;

@Mapper
public interface AllCoursesMapper {
    @Insert("insert into all_courses (courseID, courseName, category, teacher, credit, classroom, link) values (#{courseID}, #{courseName}, #{category}, #{teacher}, #{credit}, #{classroom}, #{link}})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(BasicCourse course);
    
    @Select("select * from all_courses where id = #{id}")
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "courseID", column = "courseID"),
            @Result(property = "courseName", column = "courseName"),
            @Result(property = "category", column = "category"),
            @Result(property = "teacher", column = "teacher"),
            @Result(property = "credit", column = "credit"),
            @Result(property = "classroom", column = "classroom"),
        }
    )
    BasicCourse selectById(Long id);

    @Select("select * from all_courses")
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "courseID", column = "courseID"),
            @Result(property = "courseName", column = "courseName"),
            @Result(property = "category", column = "category"),
            @Result(property = "teacher", column = "teacher"),
            @Result(property = "credit", column= "credit"),
        }
    )
    List<BasicCourse> selectAll();

    @Select("select * from all_courses where courseId = #{courseID}")
    @Results(
        {
            @Result(property = "id", column = "id"),
            @Result(property = "courseID", column = "courseID"),
            @Result(property = "courseName", column = "courseName"),
            @Result(property = "category", column = "category"),
            @Result(property = "teacher", column = "teacher"),
            @Result(property = "credit", column= "credit"),
        }
    )
    BasicCourse selectByCourseID(String courseID);
}