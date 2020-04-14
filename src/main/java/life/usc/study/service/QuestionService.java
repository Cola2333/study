package life.usc.study.service;

import life.usc.study.dto.PaginationDTO;
import life.usc.study.dto.QuestionDTO;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.mapper.UserMapper;
import life.usc.study.moel.Question;
import life.usc.study.moel.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer pageNum, Integer size) {

        // limit子句中的第一个参数
        Integer offset = (pageNum - 1) * size;

        List<Question> questions = questionMapper.getAllQuestions(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countTotal();
        paginationDTO.setPagination(questionDTOS, pageNum, totalCount, size);
        return paginationDTO;
    }
}
