package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.AllCourseTimeMapper;
import PKUCRProject.PKUCR.backend.Dao.AllCoursesMapper;
import PKUCRProject.PKUCR.backend.Entity.BasicCourse;
import java.util.List;


/**
 * 对于所有课程的操作, 包括添加评论, 上传资料等.
 */
@Service
public class AllCourseService {
    
    @Autowired
    private AllCoursesMapper allCoursesMapper;

    @Autowired
    private AllCourseTimeMapper allCourseTimeMapper;

    /**
     * 插入一门课程
     * @param course 需要插入的课程
     */
    public void insert(BasicCourse course) {
        allCoursesMapper.insert(course);
        for (var time : course.getTime()) {
            allCourseTimeMapper.insert(time);
        }
    }

    /**
     * 根据课程ID查找课程, 根据resource需要的信息
     * 这里其实不需要返回时间
     * @param id 课程ID
     * @return 课程
     */
    public BasicCourse selectById(Long id) {
        return allCoursesMapper.selectById(id);
    }

    /**
     * 查找所有课程
     * @return 所有课程
     */
    public List<BasicCourse> selectAll() {
        return allCoursesMapper.selectAll();
    }

    /**
     * 根据课程ID查找课程
     * @param courseID 课程ID
     * @return 课程
     */
    public BasicCourse selectByCourseID(String courseID) {
        return allCoursesMapper.selectByCourseID(courseID);
    }


}
