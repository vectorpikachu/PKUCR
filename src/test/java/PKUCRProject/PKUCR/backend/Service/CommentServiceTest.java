package PKUCRProject.PKUCR.backend.Service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import PKUCRProject.PKUCR.backend.Dao.CommentMapper;
import PKUCRProject.PKUCR.backend.Entity.Comment;
import PKUCRProject.PKUCR.backend.Service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);

        // 创建一个 Comment 对象
        comment = new Comment();
        comment.setID(1L);
        comment.setCourseID("CS101");
        comment.setUserID(1L);
        comment.setContent("Great course!");
        comment.setTime("Current time");
    }

    @Test
    public void testSelectByCourseID() {
        // 模拟 selectByCourseID 方法的返回值
        List<Comment> commentList = Arrays.asList(comment);
        when(commentMapper.selectByCourseID("CS101")).thenReturn(commentList);

        // 调用 selectByCourseID 方法
        List<Comment> result = commentService.selectByCourseID("CS101");

        // 验证返回的列表大小是否为1
        assertEquals(1, result.size());
        assertEquals(comment, result.get(0));

        // 验证 selectByCourseID 方法是否被调用
        verify(commentMapper, times(1)).selectByCourseID("CS101");
    }

    @Test
    public void testCommentInsert() {
        // 调用 insertComment 方法
        commentService.insertComment(comment);

        // 验证 insertComment 方法是否被调用
        verify(commentMapper, times(1)).insertComment(comment);
    }
}
