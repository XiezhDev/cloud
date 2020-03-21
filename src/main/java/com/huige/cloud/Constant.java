package com.huige.cloud;

import org.springframework.context.ApplicationContext;

/**
 *@Author xiezh
 *@Description 
 *@Date 2018/4/2 9:40
 */
public class Constant {
    private static ApplicationContext BF = null;

    // 8小时
    public static int cookieTime = 8 * 60 * 60;
    // 7天
    public static int saveCookieTime = 7 * 24 * 60 * 60;
    // 用户回话属性名
    public static final String USERSESSION = "userSession";
    // 保存失败
    public static final int Fail = -1;
    // 用户已存在
    public static final int DUPLICATE = -2;
    // 保存
    public static final int SAVE = 1;
    // 更新
    public static final int UPDATE = 2;

    // 知识种类
    // 个人知识库知识
    public static final int PRIVATE_KNOWLEDGE = 1;
    // 收藏知识
    public static final int FAVORITE_KNOWLEDGE = 2;
    // 分享知识
    public static final int SHARED_KNOWLEDGE = 3;

   static {
        try {
            if (BF == null) {
                BF = SpringContext.getApplicationContext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
