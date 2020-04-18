package life.usc.study.exception;

public enum CustormizeErrorCode implements ICustomizeErrorcode {
    QUESTION_NOT_FOUND("您要找的问题不存在，换个问题试试吧~");
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustormizeErrorCode(String message) {
        this.message = message;
    }
}
