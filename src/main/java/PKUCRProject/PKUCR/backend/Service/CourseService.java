package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKUCRProject.PKUCR.backend.Dao.CourseMapper;
import PKUCRProject.PKUCR.backend.Entity.Course;

@Service
public class CourseService {
    
    @Autowired
    private CourseMapper courseMapper;

    public Course insert(Course course) {
        courseMapper.insert(course);
        Course courseInDB = courseMapper.selectById(course.getId());
        return courseInDB;
    }

    public Course selectById(Long id) {
        return courseMapper.selectById(id);
        // return "selectById" + id;
    }

    public String update(Course course) {
        courseMapper.update(course);
        return "update success";
    }

    public String delete(Long id) {
        courseMapper.delete(id);
        return "delete course success by id = " + id;
    }
}
