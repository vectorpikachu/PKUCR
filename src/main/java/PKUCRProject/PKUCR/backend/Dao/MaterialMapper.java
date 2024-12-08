package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import PKUCRProject.PKUCR.backend.Entity.Material;

import java.util.List;

@Mapper
public interface MaterialMapper {
    
    @Select("select * from materials where course_id = #{courseID}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userID", column = "user_id"),
        @Result(property = "courseID", column = "course_id"),
        @Result(property = "filename", column = "filename"),
        @Result(property = "filedir", column = "filedir"),
        @Result(property = "url", column = "url"),
        @Result(property = "time", column = "time")
    })
    List<Material> selectByCourseID(String courseID);

    /**
     * 根据资料ID在数据库中查找对应资料
     * @param id
     * @return 资料类
     */
    @Select("select * from materials where id = #{id}")
    Material selectByID(Long id);


    @Insert("insert into materials (user_id, course_id, filename, filedir, url, time) values (#{userID}, #{courseID}, #{filename}, #{filedir}, #{url}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMaterial(Material material);
}
