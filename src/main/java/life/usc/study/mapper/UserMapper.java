package life.usc.study.mapper;

import life.usc.study.moel.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USER(account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) VALUES(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id=#{creator} limit 1")
    User getUserById(@Param("creator") String creator);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},bio=#{bio},avatar_url=#{avatarUrl} where account_id=${accountId}")
    void update(User user);
}
