package com.yibogame.util;

import android.text.TextUtils;

/**
 * Created by parcool on 2016/4/22.
 */
public class ValidateUtil {
    private static ValidateUtil ourInstance = new ValidateUtil();

    public static ValidateUtil getInstance() {
        return ourInstance;
    }

    private ValidateUtil() {
    }

    /**
     * 验证手机格式
     */
    public boolean isMobileNum(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public String validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return "密码不能为空";
        }
        if (password.length() < 6) {
            return "密码长度不能小于6位";
        }
        if (password.length() > 20) {
            return "密码过长，为方便记忆请设置稍短一点的密码";
        }
        return "";
    }


    public String validatePasswordRepeat(String oriPassword, String confirmPassword) {
        this.validatePassword(confirmPassword);
        if (!confirmPassword.equals(oriPassword)) {
            return "两次密码不一致";
        }
        return "";
    }

    public boolean validateEmail(String email) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        return email.matches(EMAIL_PATTERN);
    }


    public boolean validateVerifyCode(String verifyCode) {
        if (verifyCode.length() < 4) {
            return false;
        }
        if (!TextUtils.isDigitsOnly(verifyCode)) {
            return false;
        }
        return true;
    }
}
