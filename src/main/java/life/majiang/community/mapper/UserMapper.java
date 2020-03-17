package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by yangwanjun on 2020/3/13
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (id, account_id, name, token, gmt_create, gmt_modified) values (#{id}, #{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@PathVariable("token") String token);
}
