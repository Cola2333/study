package life.usc.study.mapper;

import life.usc.study.moel.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void createQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> getAllQuestions(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer countTotal();

    @Select("select count(1) from question")
    Integer countByAccountId(String accountId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
