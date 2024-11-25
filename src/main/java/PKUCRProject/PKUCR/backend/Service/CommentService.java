package PKUCRProject.PKUCR.backend.Service;

import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.CommentMapper;
import PKUCRProject.PKUCR.backend.Entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    
    public List<Comment> selectByCourseID(String courseID){
        return commentMapper.selectByCourseID(courseID);
    }

    public void insertComment(Comment comment){
        commentMapper.insertComment(comment);
    }

}
