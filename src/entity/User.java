package entity;

public class User {
    private String uId;
    private String uPassword;
    private String uStatus;

    public User(String uId, String uPassword, String uStatus) {
        this.uId = uId;
        this.uPassword = uPassword;
        this.uStatus = uStatus;
    }

    public String getuId() {
        return uId;
    }

    public String getuPassword() {
        return uPassword;
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }
}
