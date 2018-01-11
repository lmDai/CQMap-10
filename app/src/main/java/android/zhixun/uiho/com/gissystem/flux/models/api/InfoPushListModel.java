package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.io.Serializable;

/**
 * Created by simple on 2017/12/12.
 */

public class InfoPushListModel implements Serializable{

    public long createTime;//推送时间
    public long infoPushId;//消息ID
    public long pushTypeId;//推送类型
    public long isDelete;
    public String createUserName;//推送人
    public String pushTypeName;
    public String pushTxt;//内容
    public String createPerson;
    public String pushTitle;//标题
}
