package PKUCRProject.PKUCR.backend.Service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import PKUCRProject.PKUCR.backend.Dao.CourseMapper;
import PKUCRProject.PKUCR.backend.Entity.Course;
import PKUCRProject.PKUCR.backend.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);

        // 创建一个 Course 对象
        course = new Course();
        course.setId(1L);
        course.setCourseID("CS101");
        course.setCourseName("Introduction to Computer Science");
        course.setTeacher("teacher123");
    }

    @Test
    public void testInsert() {
        // 模拟 insert 方法
        when(courseMapper.selectById(course.getId())).thenReturn(course);

        // 调用 insert 方法
        Course result = courseService.insert(course);

        // 验证 courseMapper.selectById 是否被调用
        verify(courseMapper, times(1)).selectById(course.getId());

        // 验证返回的 Course 对象是否与插入的对象相同
        assertEquals(course, result);
    }

    @Test
    public void testSelectById() {
        // 模拟 selectById 方法
        when(courseMapper.selectById(1L)).thenReturn(course);

        // 调用 selectById 方法
        Course result = courseService.selectById(1L);

        // 验证返回的课程是否正确
        assertEquals(course, result);

        // 验证 selectById 是否被调用
        verify(courseMapper, times(1)).selectById(1L);
    }

    @Test
    public void testUpdate() {
        // 调用 update 方法
        String result = courseService.update(course);

        // 验证 update 返回值
        assertEquals("update success", result);

        // 验证 update 方法是否被调用
        verify(courseMapper, times(1)).update(course);
    }

    @Test
    public void testDelete() {
        // 调用 delete 方法
        String result = courseService.delete(1L);

        // 验证删除返回值
        assertEquals("delete course success by id = 1", result);

        // 验证 delete 方法是否被调用
        verify(courseMapper, times(1)).delete(1L);
    }

    @Test
    public void testSelectByUserID() {
        // 创建课程列表并模拟 selectByUserID 方法的返回值
        List<Course> courses = Arrays.asList(course);
        when(courseMapper.selectByUserID(123L)).thenReturn(courses);

        // 调用 selectByUserID 方法
        List<Course> result = courseService.selectByUserID(123L);

        // 验证返回的课程列表
        assertEquals(1, result.size());
        assertEquals(course, result.get(0));

        // 验证 selectByUserID 方法是否被调用
        verify(courseMapper, times(1)).selectByUserID(123L);
    }
}
