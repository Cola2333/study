package life.usc.study.mapper;

import life.usc.study.moel.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}