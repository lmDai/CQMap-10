package android.zhixun.uiho.com.gissystem.app;


/**
 * Created by parcool on 2016/9/5.
 */

public class ServerHttpException extends RuntimeException {

    public ServerHttpException(String responseMessage) {
        super(handleError(responseMessage));
    }
    public ServerHttpException(String responseMessage,Throwable cause) {
        super(handleError(responseMessage),cause);
    }

    private static String handleError(String responseMessage){
        if(responseMessage==null||responseMessage.isEmpty()){
            return "Server out:null";
        }
        return responseMessage;
    }
//
//    @Override
//    public synchronized Throwable getCause() {
//        LogUtil.d("------>"+super.getCause().toString());
//        return super.getCause();
//    }
}
