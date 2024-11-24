package PKUCRProject.PKUCR.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import PKUCRProject.PKUCR.backend.Dao.AllCoursesMapper;
import PKUCRProject.PKUCR.backend.Entity.BasicCourse;
import java.util.List;


@Service
public class AllCourseService {
    
    @Autowired
    private AllCoursesMapper allCoursesMapper;

    public void insert(BasicCourse course) {
        allCoursesMapper.insert(course);
    }

    public BasicCourse selectById(Long id) {
        return allCoursesMapper.selectById(id);
    }

    public List<BasicCourse> selectAll() {
        return allCoursesMapper.selectAll();
    }

    public BasicCourse selectByCourseID(Long courseID) {
        return allCoursesMapper.selectByCourseID(courseID);
    }


}
