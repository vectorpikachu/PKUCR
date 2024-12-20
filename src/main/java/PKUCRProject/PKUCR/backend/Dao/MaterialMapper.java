package PKUCRProject.PKUCR.backend.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import PKUCRProject.PKUCR.backend.Entity.Material;

import java.util.List;

@Mapper
public interface MaterialMapper {
    
    /**
     * 根据课程ID查找数据库中资料
     * @param courseID
     * @return 资料列表
     */
    @Select("select * from materials where courseId = #{courseID}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userID", column = "userId"),
        @Result(property = "courseID", column = "courseId"),
        @Result(property = "filename", column = "filename"),
        @Result(property = "filedir", column = "filedir"),
        @Result(property = "url", column = "url"),
        @Result(property = "time", column = "time")
    })
    List<Material> selectByCourseID(String courseID);

    /**
     * 根据资料ID在数据库中查找对应资料
     * @param id
     * @return 资料条目
     */
    @Select("select * from materials where id = #{id}")
    Material selectByID(Long id);

    /**
     * 根据 ID 删除资料
     * @param id 资料的 ID
     */
    @Delete("delete from materials where id = #{id}")
    void deleteMaterial(Long id);

    /**
     * 将资料信息插入数据库
     * @param material
     */
    @Insert("insert into materials (userID, courseID, filename, filedir, url, time) values (#{userID}, #{courseID}, #{filename}, #{filedir}, #{url}, #{time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMaterial(Material material);
}
