package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import java.util.List;

import PKUCRProject.PKUCR.backend.Entity.BasicCourse;

@Mapper
public interface AllCoursesMapper {
    @Insert("insert into all_courses (courseID, courseName, category, teacher, credit, classroom, time, week) values (#{courseID}, #{courseName}, #{category}, #{teacher}, #{credit}, #{classroom}, #{time}, #{week})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insert(BasicCourse course);
    
    @Select("select * from all_courses where id = #{id}")
    BasicCourse selectById(Long id);

    @Select("select * from all_courses")
    List<BasicCourse> selectAll();

    @Select("select * from all_courses where courseId = #{courseID}")
    BasicCourse selectByCourseID(String courseID);
}