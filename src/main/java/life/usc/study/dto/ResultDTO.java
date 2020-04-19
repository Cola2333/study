package life.usc.study.dto;

import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    /** 还是封装成一个resultDTO 为什么不可以把上面的方法改成直接封装一个errorCode呢 */
    public static ResultDTO errorOf(CustormizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }
}
