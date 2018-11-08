package com.quarkdata.data.dal.rest.quarkshare;

import com.quarkdata.data.dal.api.UserInfo;
import com.quarkdata.data.util.PropertiesUtils;

public class IOTApiConstants {

    /**
     * data api base path
     */
    static String apiBasePath = PropertiesUtils.prop.get("iotApiBasePathInternal");

    /**
     * 登录
     */
    static String login = "/internal/login";

    /**
     * 登出
     */
    static String logout = "/internal/logout";

    /**
     * 获取验证码
     */
    static String get_validate_code = "/internal/common/get_validate_code";

    /**
     * 校验验证码
     */
    static String check_validate_code = "/internal/common/check_validate_code";

    /**
     * 公用邮件发送
     */
    static String email_sender = "/internal/email_sender/send";

    /**
     * 发送发送重置密码邮件
     */
    static String send_email = "/internal/reset/send_email";

    /**
     * 重置密码时获取用户名
     */
    static String get_username = "/internal/reset/get_username";

    /**
     * 重置密码
     */
    static String reset_password = "/internal/reset/reset_password";

    /**
     * 冻结连续登录失败用户
     */
    static String login_freeze = "/internal/user/login_freeze";

    /**
     * 用户api
     */
    static String list     =  "/internal/user/list";
    static String add      =  "/internal/user/add";
    static String update   =  "/internal/user/update";
    static String detail   =  "/internal/user/detail";
    static String delete   =  "/internal/user/delete";
    static String status   =  "/internal/user/freeze";
    static String resend_mail   =  "/internal/user/resend_mail";
    static String update_status   =  "/internal/user/update_status";
    static String reset_api_key = "/internal/user/reset_api_key";
    static String update_user_info   =  "/internal/user/update_user_info";
    static String trigger_send_mail   =  "/internal/triggerSendEmail";
    static String get_user_name = "/internal/user/get_username";
    static String reset_user_info   =  "/internal/user/reset_user_info";
    static String reset_user_password   =  "/internal/user/reset_password";
    
    static String get_users_by_ids ="/internal/user/get_users_by_ids";

    /**
     * 租户
     */
    static String get_tenant_by_secretid = "/internal/tenant/get_tenant_by_secretid";
}
