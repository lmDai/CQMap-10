package android.zhixun.uiho.com.gissystem.app;

/**
 * Created by parcool on 2016/8/1.
 */

public class Config {
    public static final int PAGE_COUNT = 7;
    public static boolean isDebugModel = true;
    public static final int DEFAULT_INTERVAL = 4 * 1000;

    /***
     * 启动页开始倒计时的初始时间
     */
    public static final int START_PAGE_INIT_COUNT_DOWN_TIME = 0;

    public static final int SELECT_IMAGE_LIMIT_MAX = 5;
    public static final int CROP_WIDTH = 1200, CROP_HEIGHT = 1600;
    public static final boolean CROP_TOOL_DEFAULT = true;//true:takePhoto自带 false：第三方(是否是系统自带?)
    public static final boolean ASPECT = true;//true:宽/高   false:宽*高

    public static final String LOG_DIR = "log", CACHE_DIR = "cache", UPDATE_DIR = "update", MAP_DIR = "map";

    public static final String[] CQ_PREFECTURE = {"全部", "渝中区", "渝北区", "江北区", "沙坪坝区", "九龙坡区", "大渡口区", "南岸区", "巴南区", "北碚区",
            "涪陵区", "万州区", "綦江区", "大足区", "黔江区", "长寿区", "江津区", "合川区", "永川区", "南川区", "璧山区", "铜梁区", "潼南区", "荣昌区",
            "梁平县", "城口县", "丰都县", "垫江县", "武隆县", "忠县", "开州区", "云阳县", "奉节县", "巫山县", "巫溪县", "石柱县", "秀山县", "酉阳县", "彭水县"};
    public static final String[] INDUSTRY_CATEGORY = {"全部", "党政机关", "事业单位", "私营企业", "涉外企业", "国(境)外组织机构", "其他"};

    public static final String[] CENSORSHIP_REGISTER = {"重庆市规划局", "事业单位", "私营企业", "涉外企业", "国(境)外组织机构", "其他"};

    public static final String[] RESULT_TYPE = {"重力点", "水准点", "三角点", "CNSS成果", "矢量地图数据", "数字高程模型", "分辐正射影像", "数字栅格地图", "航空影像", "卫星影像", "模拟地形图"};

    public static final String[] BJ_CODE = {"H201601111234", "H201601111235", "H201601111235", "H201601111229", "H201601111230", "H201601111240", "H201601111241", "H201601111250", "H201601111260", "H201601111289"};//报价编号
    public static final String[] ACHIEVEMENT_TIME = {"2015-6-9", "2015-7-8", "2015-8-12", "2015-9-20", "2015-10-15", "2016-1-12", "2016-3-4", "2016-5-12", "2016-7-7", "2016-8-9"};
    public static final String[] XIN_TU_HAO = {"H50C1001", "H50C1002", "H50C1003", "H50C1004", "H50C1005"};//新图号
    public static final String[] PRODUCT_TIME = {"2009-12", "2008", "2012-11", "2015-10", "2007-09"};//生产时间
    public static final String[] DADI_JZ = {"2000国家大地标准系列", "1999国家大地标准系列", "1998国家大地标准系列", "1997国家大地标准系列", "1996国家大地标准系列"};//大地基准
    public static final String[] DATA_FORMAT = {"COVERAHE", "SHP", "GDB", "GDB", "COVERAHE"};//数据格式

    public static final String[] CALL = {"重庆50", "重庆51", "重庆52", "重庆53", "重庆54"};//点名
    public static final String[] DIT = {"060", "061", "062", "063", "064", "065"};//点号
    public static final String[] RANK = {"一等", "一等", "一等", "一等", "一等"};//等级
    public static final String[] HEIGHT_DATUM = {"1985国家高程基准", "1985国家高程基准", "1985国家高程基准", "1985国家高程基准", "1985国家高程基准"};//高程基准
    //模拟矢量地图详情内容
    public static final String[] VECTOR_MAP_DETAIL_CONTENT = {"重庆市", "H50C001001", "H-50[01]", "5000", "矢量地图数据", "2009-12", "2009", "未匹配项目", "秘密", "2000国家大地标准系列", "1985国家高程基准", "地理坐标系", "COVERAHE", "重庆市地理信息中心", "www.cqcc.cn", "25万数据（BL）2009版", "2009", "无内容", "40657"};

    //模拟矢量地图详情属性
    public static final String[] VECTOR_MAP_DETAIL = {"成果单元名称", "新图号", "旧图号", "比例尺", "成果类型", "生产时间", "版本", "所属项目", "密级", "大地基准", "高程基准", "投影", "数据格式", "分发单位", "链接地址", "数据名称", "版本年代", "整体现势性", "编号"};

    //模拟水准点详情属性
    public static final String[] BENCHMARK_DETAIL = {"路线名称", "路线编号", "点名", "点号", "新图号", "旧图号", "网名", "等级", "成果类型", "生产时间", "所属项目", "密级", "大地基准", "高程基准", "分发单位", "链接地址"};

    //模拟水准点详情属性内容
    public static final String[] BENCHMARK_DETAIL_CONTENT = {"重庆－忠县", "G50", "重庆50", "060", "FHSC001001", "FH-50-B", "一等重庆－忠县水准网", "一等", "水准点", "2009/2016", "无匹配项目", "机密", "2000国家大地标准系列", "1985国家高程基准", "重庆市地理信息中心", "www.cqcc.cn"};

    public static final String
            ARCGIS_OBJECTID = "OBJECTID",
            ARCGIS_ADDR = "ADDR",
            ARCGIS_NAME = "NAME",
            ARCGIS_UNITID = "UNITID",
            ARCGIS_SHAPE = "SHAPE",
            FRUIT_ID = "FRUITID";

}
