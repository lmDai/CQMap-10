package android.zhixun.uiho.com.gissystem.flux.custom;

import java.io.Serializable;

/**
 * Created by simple on 2017/12/12.
 */

public enum PushTypeId implements Serializable{

    //成果申请消息
    RESULT_DECLAR_MESSAGE(5),
    //预审消息
    PREPARE_MESSAGE(6),
    //成果目录更新消息
    RESULT_FOLDER_UPDATE_MESSAGE(7),
    //保密检查消息
    SECRECY_INSPECT_MESSAGE(8)
    //
    ;

    public int pushTyId;

    PushTypeId(int pushTypeId) {
        this.pushTyId = pushTypeId;
    }

}
