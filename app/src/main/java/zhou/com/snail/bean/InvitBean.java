package zhou.com.snail.bean;

/**
 * Created by zhou on 2017/12/28.
 */

public class InvitBean {

    /**
     * error : -1
     * inviteCode : qboq
     * msg : 生成邀请码成功
     */

    private String error;
    private String inviteCode;
    private String msg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
