package PKUCRProject.PKUCR.backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.CourseMapper;
import PKUCRProject.PKUCR.backend.Dao.CourseTimeMapper;
import PKUCRProject.PKUCR.backend.Entity.Course;

@Service
public class CourseService {
    
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTimeMapper courseTimeMapper;

    /** 把传递进来的 Course 对象插入数据库
     * @param course 是一个 Course 对象
     * @return Course 返回插入数据库后的 Course 对象
     */
    public Course insert(Course course) {
        courseMapper.insert(course);
        Course courseInDB = courseMapper.selectById(course.getId());
        for (var time : course.getTime()) {
            time.setCourseID(course.getId());
            courseTimeMapper.insert(time);
        }
        
        courseInDB.setTime(course.getTime());
        return courseInDB;
    }

    /**
     * 根据 id 查询数据库中的 Course 对象
     * @param id 是一个 Long 类型的 id
     * @return Course 返回查询到的 Course 对象
     */
    public Course selectById(Long id) {
        Course thisCourse = courseMapper.selectById(id);
        thisCourse.setTime(courseTimeMapper.selectByCourseID(id));
        return thisCourse;
    }

    public String update(Course course) {
        courseMapper.update(course);
        // 那就先删除 course_times 表中所有关于该课程的记录，然后再插入新的记录
        Long id = course.getId();
        courseTimeMapper.delete(id);
        for (var time : course.getTime()) {
            time.setCourseID(id);
            courseTimeMapper.insert(time);
        }
        return "update success with id = " + id;
    }

    /**
     * 根据 id 删除数据库中的 Course 对象
     * @param id 是一个 Long 类型的 id
     * @return String 返回删除操作的结果
     */
    public String delete(Long id) {
        // 考虑外键约束, 先删除 course_times 表中所有关于该课程的记录
        courseTimeMapper.delete(id);
        courseMapper.delete(id);
        return "delete course success by id = " + id;
    }

    public List<Course> selectByUserID(Long user_id) {
        List<Course> courses = courseMapper.selectByUserID(user_id);
        for (var course : courses) {
            course.setTime(courseTimeMapper.selectByCourseID(course.getId()));
        }
        return courses;
    }
}
