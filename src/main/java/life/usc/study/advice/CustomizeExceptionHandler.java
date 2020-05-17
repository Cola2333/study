package life.usc.study.advice;

import com.alibaba.fastjson.JSON;
import life.usc.study.dto.ResultDTO;
import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) { //先判断是json格式的请求抛出的异常
            ResultDTO resultDTO;//用来返回json 局部刷新
            if (e instanceof CustomizeException) { //是我们已知(已经定义过的)的异常
                resultDTO = ResultDTO.errorOf((CustomizeException) e); //保证是json格式
            }else { //未定义的异常
                //log.error("handle error", e);
                resultDTO =  ResultDTO.errorOf(CustormizeErrorCode.SYS_ERROR);
            }
            try { //手动写到前端 之所以这样是我们需要返回一个ModelView 然而Json格式的数据不能是ModelView
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else //非json格式异常
            //返回错误页面 页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            }else {
                log.error("handle error", e); //如果忘记写此句能会导致控制台无错误信息
                model.addAttribute("message", CustormizeErrorCode.SYS_ERROR.getMessage()); //如果忘记加getMessage可能会导致控制台无错误信息
            }
            return new ModelAndView("error");
    }
/**
 * 如果加了可能会导致控制台无错误信息
 * */
//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
