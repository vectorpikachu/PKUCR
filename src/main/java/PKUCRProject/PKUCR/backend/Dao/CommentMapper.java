package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import PKUCRProject.PKUCR.backend.Entity.Comment;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select * from comments where course_id = #{course_id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userID", column = "user_id"),
        @Result(property = "courseID", column = "course_id"),
        @Result(property = "content", column = "content"),
        @Result(property = "time", column = "time")
    })
    List<Comment> selectByCourseID(String course_id);
}
