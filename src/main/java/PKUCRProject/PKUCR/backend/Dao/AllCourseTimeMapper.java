package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.CourseTime;

@Mapper
public interface AllCourseTimeMapper {
    @Insert("insert into all_course_times (courseID, startDate, endDate, startTime, endTime, frequency) values (#{courseID}, #{startDate}, #{endDate}, #{startTime}, #{endTime}, #{frequency})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(CourseTime time);

    @Select("select * from all_course_times where courseId = #{courseID}")
    CourseTime[] selectByCourseID(Long courseID);

    @Update("update all_course_times set startDate = #{startDate}, endDate = #{endDate}, startTime = #{startTime}, endTime = #{endTime}, frequency = #{frequency} where id = #{id}")
    void update(CourseTime time);

    @Delete("delete from all_course_times where courseID = #{id}")
    void delete(Long id);
}
