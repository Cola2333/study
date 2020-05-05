package life.usc.study.service;

import life.usc.study.dto.PaginationDTO;
import life.usc.study.dto.QuestionDTO;
import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.mapper.QuestionExtMapper;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.mapper.UserMapper;
import life.usc.study.model.Question;
import life.usc.study.model.QuestionExample;
import life.usc.study.model.User;
import life.usc.study.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    /*
    * 首页分页显示
    * */
    public PaginationDTO list(Integer pageNum, Integer size) {
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        //Integer totalCount = questionMapper.countTotal();
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

        Integer offset = (pageNum - 1) * size;// limit子句中的第一个参数

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc"); // 倒序排列
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        //List<Question> questions = questionMapper.getAllQuestions(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();// 用于如果不要pagination的话 可以用这个显示所有question
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        /*
        * 得到了所需的全部属性 一起赋值
        * */
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(questionDTOS, pageNum, totalPage);
        return paginationDTO;
    }

    /*
    * 我发布的问题分页显示
    * */
    public PaginationDTO list(Integer pageNum, Integer size, String accountId) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(accountId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        //Integer totalCount = questionMapper.countByAccountId(accountId);
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

        Integer offset = (pageNum - 1) * size;// limit子句中的第一个参数

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(accountId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        //List<Question> questions = questionMapper.getAllQuestions(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();// 用于如果不要pagination的话 可以用这个显示所有question
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        /*
         * 得到了所需的全部属性 一起赋值
         * */
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(questionDTOS, pageNum, totalPage);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) { //可能页面从地址上直接写了一个不存在的问题id
            throw new CustomizeException(CustormizeErrorCode.QUESTION_NOT_FOUND);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }
        else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustormizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        questionExtMapper.incViewCount(question);
    }

    /*
    * 查找相关问题 其实不需要使用questionDTO 用question就可以吧
    * */
    public List<QuestionDTO> getRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
