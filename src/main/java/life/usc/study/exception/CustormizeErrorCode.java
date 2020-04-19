package life.usc.study.exception;

public enum CustormizeErrorCode implements ICustomizeErrorcode {
    QUESTION_NOT_FOUND(2001, "您要找的问题不存在，换个问题试试吧~"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或者评论进行回复"),
    NO_LOGIN(2003, "当前操作需要先登录，请登录后重试"),
    SYS_ERROR(2004, "服务冒烟啦，稍后再试试吧"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"您回复的评论已不存在"),
    ;
    private String message;
    private  Integer code;

    CustormizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
