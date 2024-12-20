package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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

    @Insert("insert into comments (user_id, course_id, content, time) values (#{userID}, #{courseID}, #{content}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertComment(Comment comment);

    @Delete("delete from comments where id = #{id}")
    void deleteComment(Long id);

}
