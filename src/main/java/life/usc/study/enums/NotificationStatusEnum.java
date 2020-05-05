package life.usc.study.enums;

public enum NotificationStatusEnum {
    UNREDE(0),
    READE(1)
    ;
    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
