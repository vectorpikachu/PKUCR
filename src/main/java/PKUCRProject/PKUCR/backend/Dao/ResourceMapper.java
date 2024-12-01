package PKUCRProject.PKUCR.backend.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import PKUCRProject.PKUCR.backend.Entity.User;
import PKUCRProject.PKUCR.backend.Entity.Resource;

@Mapper
public interface ResourceMapper {

    @Insert("INSERT INTO materials (resourceID, courseID, userID, fileName, uploadTime, savePath) " +
            "VALUES (#{materialID}, #{courseID}, #{userID}, #{fileName}, #{uploadTime}, #{savePath})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long insertResource(Resource resource);

    @Select("SELECT * FROM resources WHERE courseID = #{courseID}")
    List<Resource> selectResourcesByCourseID(String courseID);

    @Select("SELECT * FROM resources WHERE resourceID = #{resourceID}")
    List<Resource> selectResourcesByMaterialID(String resourceID);

    //@Delete("delete from users where id = #{id}")
    //void delete(int id);
}
