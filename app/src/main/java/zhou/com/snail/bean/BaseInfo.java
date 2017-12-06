package zhou.com.snail.bean;

/**
 * Created by zhou on 2017/10/25.
 */

public class BaseInfo {

    private boolean isCheck;
    private String name;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseInfo(boolean isCheck, String name) {
        this.isCheck = isCheck;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "isCheck=" + isCheck +
                ", name='" + name + '\'' +
                '}';
    }
}