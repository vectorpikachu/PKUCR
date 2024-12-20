package PKUCRProject.PKUCR.backend.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.CourseTime;

@Mapper
public interface CourseTimeMapper {
    @Insert("insert into course_times (courseID, startDate, endDate, startTime, endTime, frequency) values (#{courseID}, #{startDate}, #{endDate}, #{startTime}, #{endTime}, #{frequency})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(CourseTime time);

    @Select("select * from course_times where courseId = #{courseID}")
    List<CourseTime> selectByCourseID(Long courseID);

    @Update("update course_times set startDate = #{startDate}, endDate = #{endDate}, startTime = #{startTime}, endTime = #{endTime}, frequency = #{frequency} where id = #{id}")
    void update(CourseTime time);

    @Delete("delete from course_times where courseId = #{id}")
    void delete(Long id);
}

