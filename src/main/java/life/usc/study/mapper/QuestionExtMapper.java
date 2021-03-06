package life.usc.study.mapper;

import life.usc.study.dto.QuestionDTO;
import life.usc.study.dto.QuestionQueryDTO;
import life.usc.study.model.Question;
import life.usc.study.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incViewCount(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question record);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}