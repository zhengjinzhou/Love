package zhou.com.snail.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zhou.com.snail.bean.BaseInfo;


/**
 * 表单验证工具类
 * Created by Administrator on 2017/9/29.
 */

public class PersonalFormTools {

    private static PersonalFormTools install;

    public static PersonalFormTools getInstall() {
        if(install == null){
            install = new PersonalFormTools();
        }
        return install;
    }

    public PersonalFormTools() {
    }

    /**
     * 获取被选择的数据
     * @param data
     * @return
     */
    public List<String> getTureData(List<BaseInfo> data){

        List<String> entity = new ArrayList<>();
        int count = data.size();
        for(int i = 0; i < count; i++){
            BaseInfo info = data.get(i);
            if(info.isCheck()){
                entity.add(info.getName());
            }
        }

        return entity;
    }

    /**
     * 获取存在的图片
     * @param data
     * @return
     */
    public List<String> getTureImager(List<String> data){

        List<String> entity = new ArrayList<>();
        int count = data.size();
        for(int i = 0; i < count; i++){
            if(!data.get(i).equals("add")){
                entity.add(data.get(i));
            }
        }

        return entity;
    }

    public String getTureString(List<BaseInfo> data){
        int count = data.size();
        for(int i = 0; i < count; i++){
            BaseInfo info = data.get(i);
            if(info.isCheck()){
               return info.getName();
            }
        }
        return "";
    }

    public int getTureIndex(List<BaseInfo> data){
        int count = data.size();
        for(int i = 0; i < count; i++){
            BaseInfo info = data.get(i);
            if(info.isCheck()){
                return i;
            }
        }

        return 0;
    }

    /**
     *  判断集合里是否存在 name
     * @param name
     * @param data
     * @return
     */
    public boolean vefName(String name,List<BaseInfo> data){
        for(int i = 0; i < data.size(); i++){
            BaseInfo info = data.get(i);
            if(info.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean vefStringName(String name,List<String> data){
        for(int i = 0; i < data.size(); i++){
            String info = data.get(i);
            if(info.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean verf(List<BaseInfo> data){
        for(int i = 0; i < data.size(); i++){
            BaseInfo info = data.get(i);
            if(info.isCheck()) {
                return true;
            }
        }
        return false;
    }

    public boolean getRtueEmpt(List<BaseInfo> data){

        //List<String>

        return true;
    }

    /**
     * String 转成 list
     * @param text
     * @return
     */
    public List<String> getStringTolist(String text){
        List<String> array = new ArrayList<>();
        if(text.indexOf(",") != -1){
            String[] strings = text.split(",");
            array = Arrays.asList(strings);
        }else{
            array.add(text);
        }
        return array;
    }

}
