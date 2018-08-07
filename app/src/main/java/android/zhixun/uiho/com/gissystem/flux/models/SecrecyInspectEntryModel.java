package android.zhixun.uiho.com.gissystem.flux.models;

import java.util.List;

public class SecrecyInspectEntryModel {

    public int secrecyInspectEntryId;
    public String title;
    public List<Remarks> remarks;

    public static class Remarks {
        public String remark;
        public String buttonName;
    }
}
