package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import PKUCRProject.PKUCR.backend.Entity.Material;
import java.util.List;

@Mapper
public interface MaterialMapper {
    
    @Select("select * from materials where course_id = #{courseID}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userID", column = "user_id"),
        @Result(property = "courseID", column = "course_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "url", column = "url"),
        @Result(property = "time", column = "time")
    })
    List<Material> selectByCourseID(String courseID);
}
