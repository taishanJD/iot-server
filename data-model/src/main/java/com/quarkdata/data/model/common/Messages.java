package com.quarkdata.data.model.common;

public class Messages {

    public static int SUCCESS_CODE = 0;
    public static String SUCCESS_MSG = "success";

    public static int MISSING_INPUT_CODE = 10000;
    public static String MISSING_INPUT_MSG = "missing required input params";

    public static final int ACCOUNT_LOGIN_ERROR_CODE = 10001;
    public static final String ACCOUNT_LOGIN_ERROR_MSG = "用户名或密码错误";
    public static final int ACCOUNT_INACTIVE_CODE = 10002;
    public static final String ACCOUNT_INACTIVE_MSG = "账号未激活";
    public static final int ACCOUNT_LOCKED_CODE = 10003;
    public static final String ACCOUNT_LOCKED_MSG = "账号被冻结，请联系管理员";
    public static final int ACCOUNT_STATUS_ERROR_CODE = 10004;
    public static final String ACCOUNT_STATUS_ERROR_MSG = "账号状态异常";
    public static final int ACCOUNT_NOT_FOUND_CODE = 10005;
    public static final String ACCOUNT_NOT_FOUND_MSG = "账号不存在";
    public static final int TEANT_INFO_ERROR_CODE = 10006;
    public static final String TEANT_INFO_ERROR_MSG = "查询租户信息失败";

    public static final int VERIFICATION_GET_ERROR_CODE = 11001;
    public static final String VERIFICATION_GET_ERROR_MSG = "获取验证码失败";
    public static final int VERIFICATION_CODE_ERROR_CODE = 11002;
    public static final String VERIFICATION_CODE_ERROR_MSG = "验证码错误";

    public static final int AES_DECODE_ERROR_CODE = 12001;
    public static final String AES_DECODE_ERROR_MSG = "邮件链接已失效";

    public static final int UPDATE_PASSWORD_ERROR_CODE = 13001;
    public static final String UPDATE_PASSWORD_ERROR_MSG = "修改密码失败";

    public static final int EMAIL_RECEIVER_ERROR_CODE = 14001;
    public static final String EMAIL_RECEIVER_ERROR_MSG = "邮件收件人不能为空";
    public static final int EMAIL_ACCOUNT_ERROR_CODE = 14002;
    public static final String EMAIL_ACCOUNT_ERROR_MSG = "邮件收件人邮箱格式错误";
    public static final int EMAIL_CONTENT_ERROR_CODE = 14003;
    public static final String EMAIL_CONTENT_ERROR_MSG = "邮件内容参数错误";
    public static final int EMAIL_FORMAT_ERROR_CODE = 14004;
    public static final String EMAIL_FORMAT_ERROR_MSG = "邮件格式参数错误";


    public static final int API_AUTHENTICATION_FAILED_CODE = 40001;
    public static final String API_AUTHENTICATION_FAILED_MSG = "token不存在，请重新登录";


    public static final int TOKEN_OUT_OF_TIME_CODE = 80000;
    public static final String TOKEN_OUT_OF_TIME_MSG = "身份验证过期,请重新登录";

    public static int API_ERROR_CODE = 90000;
    public static String API_ERROR_MSG = "接口繁忙，请稍后重试";

    public static final int API_AUTHEXCEPTION_CODE = 90001;
    public static final String API_AUTHEXCEPTION_MSG = "API权限校验失败";

    public static int AUTH_SECRET_ID_NOT_EXIST_CODE = 90002;
    public static String AUTH_SECRET_ID_NOT_EXIST_MSG = "用户secretId不存在";

    public static int AUTH_SECRET_KEY_NOT_EXIST_CODE = 90003;
    public static String AUTH_SECRET_KEY_NOT_EXIST_MSG = "用户secretKey不存在";

    public static final Integer JWT_ERRCODE_EXPIRE_CODE = 71000;
    public static final String JWT_ERRCODE_EXPIRE_MSG = "token已过期，请重新登录";
    public static final Integer JWT_ERRCODE_SIGN_CODE = 71001;
    public static final String JWT_ERRCODE_SIGN_MSG = "token签名无效";
    public static final Integer JWT_ERRCODE_FAIL_CODE = 71002;
    public static final String JWT_ERRCODE_FAIL_MSG = "token验证失败";
    public static final Integer JWT_ERRCODE_TOKEN_INVALID_CODE = 71003;
    public static final String JWT_ERRCODE_TOKEN_INVALID_MSG = "token无效";

    public static final Integer API_ADD_PROJECT_CODE = 20001;
    public static final String API_ADD_PROJECT_MSG = "创建项目失败";

    public static final Integer PROJECT_NAME_REPEAT_CODE = 20002;
    public static final String PROJECT_NAME_REPEAT_MSG = "项目名称重复";

    public static final Integer PROJECT_KEY_REPEAT_CODE = 20003;
    public static final String PROJECT_KEY_REPEAT_MSG = "项目标识重复";

    public static final Integer UPDATE_PROJECT_CODE = 20004;
    public static final String UPDATE_PROJECT_MSG = "编辑项目失败";

    public static final Integer EDIT_DATASOURCE_CODE = 30000;
    public static final String EDIT_DATASOURCE_MSG = "编辑数据源失败";

    public static final Integer DELETE_DATASOURCE_CODE = 30001;
    public static final String DELETE_DATASOURCE_MSG = "删除数据源失败";

    public static final Integer CONNECT_DATASOURCE_ERR_CODE = 30002;
    public static final String CONNECT_DATASOURCE_ERR_MSG = "数据源连接失败";

    public static final Integer DATASOURCE_NOT_FOUND_CODE = 30003;
    public static final String DATASOURCE_NOT_FOUND_MSG = "数据源不存在";
    
    public static final Integer DATASOURCE_NAME_REPEAT = 30004;
    public static final String DATASOURCE_NAME_REPEAT_MSG = "数据源名称重复";

    public static final Integer EDIT_TAG_FAILED_CODE = 20005;
    public static final String EDIT_TAG_FAILED_MSG = "编辑标签失败";

    public static final Integer GET_PROJECT_DETAIL_CODE = 20006;
    public static final String GET_PROJECT_DETAIL_MSG = "获取项目详情失败";

    public static final Integer GET_PROJECT_LIST_CODE = 20007;
    public static final String GET_PROJECT_LIST_MSG = "获取项目列表失败";

    public static final Integer ADD_COMMENT_CODE = 20008;
    public static final String ADD_COMMENT_MSG = "添加评论失败";

    public static final Integer GET_COMMENT_LIST_CODE = 20009;
    public static final String GET_COMMENT_LIST_MSG = "获取评论列表失败";

    public static final Integer GET_TIMELINE_LIST_CODE = 20010;
    public static final String GET_TIMELINE_LIST_MSG = "获取时间轴列表失败";

    public static final Integer DELETE_PROJECT_FAILED_CODE = 20011;
    public static final String DELETE_PROJECT_FAILED_MSG = "删除项目失败";

    public static final Integer GET_TAG_LIST_FAILED_CODE = 20013;
    public static final String GET_TAG_LIST_FAILED_MSG = "获取标签列表失败";

    public static final Integer PROJECT_NOT_EXIST_CODE = 20014;
    public static final String PROJECT_NOT_EXIST_MSG = "项目不存在";

    public static final Integer UPLOAD_PROJECT_IMAGE_FAILED_CODE = 20015;
    public static final String UPLOAD_PROJECT_IMAGE_FAILED_MSG = "上传项目图片失败";

    public static final Integer ADD_TAG_FAILED_CODE = 20016;
    public static final String ADD_TAG_FAILED_MSG = "添加标签失败";

    public static final Integer DELETE_TAG_FAILED_CODE = 20017;
    public static final String DELETE_TAG_FAILED_MSG = "删除标签失败";

    public static final Integer TAG_ALREADY_EXIST_CODE = 20018;
    public static final String TAG_ALREADY_EXIST_MSG = "标签已存在";

    public static final Integer DELETE_WORKFLOW_FAILED_CODE = 20019;
    public static final String DELETE_WORKFLOW_FAILED_MSG = "删除工作流失败";

    public static final Integer GET_USER_NAME_CODE = 20020;
    public static final String GET_USER_NAME_MSG = "获取用户名称列表失败";

    public static final Integer DATASOURCE_DATASET_EXIST_CODE = 30004;
    public static final String DATASOURCE_DATASET_EXIST_MSG = "存在关联数据集";

    public static final Integer ADD_DATASET_FAILED_CODE = 50001;
    public static final String ADD_DATASET_FAILED_MSG = "添加数据集失败";

    public static final Integer GET_DATASET_LIST_FAILED_CODE = 50002;
    public static final String GET_DATASET_LIST_FAILED_MSG = "获取数据集列表失败";

    public static final Integer GET_DATASET_DATA_COUNT_FAILED_CODE = 50003;
    public static final String GET_DATASET_DATA_COUNT_FAILED_MSG = "获取数据集数据数量失败";

    public static final Integer GET_DATASET_DETAIL_FAILED_CODE = 50004;
    public static final String GET_DATASET_DETAIL_FAILED_MSG = "获取数据集详情失败";

    public static final Integer DELETE_DATASET_FAILED_CODE = 50005;
    public static final String DELETE_DATASET_FAILED_MSG = "删除数据集失败";

    public static final Integer GET_TABLE_NAMES_FAILED_CODE = 50006;
    public static final String GET_TABLE_NAMES_FAILED_MSG = "获取数据表列表失败";

    public static final Integer DATA_SET_TEST_CONN_FAILED_CODE = 50007;
    public static final String DATA_SET_TEST_CONN_FAILED_MSG = "连接数据集失败";

    public static final Integer TABLE_NOT_EXIST_CODE = 50008;
    public static final String TABLE_NOT_EXIST_MSG = "数据表不存在";

    public static final Integer TABLE_TEST_FAILED_CODE = 50009;
    public static final String TABLE_TEST_FAILED_MSG = "数据表测试失败";

    public static final Integer GET_SOURCE_TABLE_STRUCTURE_FAILED_CODE = 50010;
    public static final String GET_SOURCE_TABLE_STRUCTURE_FAILED_MSG = "获取源表结构失败";

    public static final Integer DATASET_PREVIEW_FAILED_CODE = 50011;
    public static final String DATASET_PREVIEW_FAILED_MSG = "数据集预览失败";

    public static final Integer DATASET_COLUMN_NOT_EXIST_CODE = 50012;
    public static final String DATASET_COLUMN_NOT_EXIST_MSG = "数据集字段不存在";
    
    public static final Integer WORKFLOW_NAME_ALREADY_EXIST_CODE = 51000;
    public static final String WORKFLOW_NAME_ALREADY_EXIST_MSG = "工作流名称已经存在";

    public static final Integer GET_INPUT_LIST_FAILED_CODE = 50013;
    public static final String GET_INPUT_LIST_FAILED_FAILED_MSG = "获取输入数据集列表失败";

    public static final Integer GET_OUTPUT_LIST_FAILED_CODE = 50014;
    public static final String GET_OUTPUT_LIST_FAILED_FAILED_MSG = "获取输出数据集列表失败";

    public static final Integer DATASET_NOT_WRITED_CODE = 50015;
    public static final String DATASET_NOT_WRITED_MSG = "数据集不可写";

    public static final Integer DATASET_CLEAR_FAILED_CODE = 50016;
    public static final String DATASET_CLEAR_FAILED_MSG = "数据集清除失败";

    public static final Integer DROP_TABLE_FAILED_CODE = 50020;
    public static final String DROP_TABLE_FAILED_MSG = "删除数据表失败";

    public static final Integer CREATE_TABLE_FAILED_CODE = 50021;
    public static final String CREATE_TABLE_FAILED_MSG = "新建数据表失败";

    public static final Integer INSERT_DATA_FAILED_CODE = 50022;
    public static final String INSERT_DATA_FAILED_MSG = "插入数据失败";

    public static final Integer GET_SQL_COLUMNS_FAILED_CODE = 50023;
    public static final String GET_SQL_COLUMNS_FAILED_MSG = "获取临时表字段失败";

    public static final Integer DATA_NULL_CODE = 50024;
    public static final String DATA_NULL_MSG = "该数据集无数据";

    public static final Integer GET_TABLE_STRUCTURE_FAILED_CODE = 51001;
    public static final String  GET_TABLE_STRUCTURE_FAILED_MSG = "获取数据集表结构失败";

    public static final Integer SAVE_CODE_PROCESS_FAILED_CODE = 51002;
    public static final String  SAVE_CODE_PROCESS_FAILED_MSG = "SQL处理保存失败";

    public static final Integer QUERY_FAILED_CODE = 60001;

    public static final Integer DIFF_DATASOURCE_CODE = 60002;
    public static final String DIFF_DATASOURCE_MSG = "输入数据集来自不同数据源";

    public static final Integer CANNOT_EDIT_TABLE_CODE = 60003;
    public static final String CANNOT_EDIT_TABLE__MSG = "不可进行增删改操作";

    public static final Integer TABLE_NOT_IN_DATASET_LIST_CODE = 60004;
    public static final String TABLE_NOT_IN_DATASET_LIST__MSG = "SQL中使用的表未添加到输入数据集列表";

    public static final Integer LACK_OF_SEMICOLONS_CODE = 60005;
    public static final String LACK_OF_SEMICOLONS_MSG = "缺少分号";

    public static final Integer OPERATION_LOG_NOT_GET = 70001;
    public static final String OPERATION_LOG_NOT_GET__MSG = "获得操作日志失败";


}
