package PKUCRProject.PKUCR.backend.Service;

import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.CommentMapper;
import PKUCRProject.PKUCR.backend.Entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 对于评论的操作, 包括查找评论, 添加评论等.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    
    /**
     * 根据课程ID查找评论
     * @param courseID 课程ID
     * @return 评论列表
     */
    public List<Comment> selectByCourseID(String courseID){
        return commentMapper.selectByCourseID(courseID);
    }

    /**
     * 将评论插入数据库
     * @param comment 需要插入的评论
     */
    public void insertComment(Comment comment){
        commentMapper.insertComment(comment);
    }

    /**
     * 删除评论
     * @param id 评论ID
     */
    public void deleteComment(Long id){
        commentMapper.deleteComment(id);
    }

}
