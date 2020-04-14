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

        Integer totalCount = questionMapper.countTotal();
        Integer totalPage;

        /*
        * 计算totalPage
        * */
        if (totalCount % size == 0) {
            totalPage = totalCount / size; //totalCount是总问题数 size是每页展示的问题数
        }else {
            totalPage = totalCount / size + 1;
        }

        /*
         * 防止页面数超过总页面数 重新给pageNum赋值
         **/
        if (pageNum > totalPage) {
            pageNum = totalPage;
        }else if (pageNum < 1){
            pageNum = 1;
        }

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

        /*
        * 得到了所需的全部属性 一起赋值
        * */
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(questionDTOS, pageNum, totalPage);
        return paginationDTO;
    }
}
