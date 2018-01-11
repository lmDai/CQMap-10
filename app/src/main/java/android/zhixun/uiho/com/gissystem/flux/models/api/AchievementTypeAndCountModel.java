package android.zhixun.uiho.com.gissystem.flux.models.api;

/**
 * Created by tanyi on 2017/3/23.
 */

public class AchievementTypeAndCountModel {

    /**
     * categoryName : 三角点
     * num : 2
     */

    private String categoryName;
    private int num;
    private int categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
