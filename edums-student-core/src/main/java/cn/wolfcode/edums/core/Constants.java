package cn.wolfcode.edums.core;

public interface Constants {

    /** Admin 用户名 */
    String ADMIN = "admin";
    /** 权限表达式：所有权限 */
    String PERMISSION_EXP_ALL = "*:*";
    /** 当前用户 */
    String CURRENT_USER = "CURRENT_USER";
    /** 客户端信息 */
    String USER_AGENT = "USER-AGENT";
    /** 客户端信息 */
    String USER_IP = "USER_IP";
    /** 请求报文体 */
    String REQUEST_BODY = "edums.requestBody";
    /** 客户端主题 */
    String WEBTHEME = "webTheme";
    /** 客户端语言 */
    String USERLANGUAGE = "userLanguage";
    /** 缓存命名空间 */
    String CACHE_NAMESPACE = "EDUMS:";
    /** 上次请求地址 */
    String PREREQUEST = CACHE_NAMESPACE + "PREREQUEST";
    /** 上次请求时间 */
    String PREREQUEST_TIME = CACHE_NAMESPACE + "PREREQUEST_TIME";

    /** 系统默认日期格式 */
    String DEFAULT_DATE_PARTTERN = "yyyy-MM-dd";
    /** 系统默认时间格式 */
    String DEFAULT_DATE_TIME_PARTTERN = "yyyy-MM-dd HH:mm:ss";
    /** 系统默认时区 */
    String DEFAULT_TIMEZONE = "GMT+8";

    /** 前端页面路径 */
    String FRONT_PAGE_PATH = "front_page_path";
    /** 前端 js 路径 */
    String FRONT_JS_PATH = "front_js_path";
    /** html 后缀 */
    String HTML_SUFFIX = ".html";
    /** js 后缀 */
    String JS_SUFFIX = ".js";
    /** 前端 list 页面名称 */
    String LIST_PAGE_NAME = "list";
    /** 前端 form 页面名称 */
    String FORM_PAGE_NAME = "form";
    /** 前端 list 页面模板路径 */
    String TEMPLATE_LIST_PAGE = "/templates/list.html";
    /** 前端 form 页面模板路径 */
    String TEMPLATE_FORM_PAGE = "/templates/form.html";
    /** 前端 js 模板路径 */
    String TEMPLATE_JS = "/templates/form.js";

    /** String.format 占位符 */
    String STRING_PLACEHOLDER = "%s";
    /** 本系统权限状态，用于区分旧 crm 权限数据 */
    Integer EDUMS_PERMISSION_STATUS = 1001;
    /** 本系统菜单类型，用于区分旧 crm 权限数据 */
    String EDUMS_SYSTEMMENU_TYPE = "EDUMS";

    /** 符号(*) */
    String ASTERISK_FOR_SYMBOL = "*";

    /**
     * 默认登陆密码
     */
    String DEFAULT_LOGIN_PASSWORD = "1";

    interface Times {
        /**一秒 */
        long SECOND = 1000;
        /**一分钟 */
        long MINUTE = SECOND * 60;
        /**一小时 */
        long HOUR = MINUTE * 60;
        /**一天 */
        long DAY = HOUR * 24;
        /**一周 */
        long WEEK = DAY * 7;
        /**一年 */
        long YEAR = DAY * 365;
    }
}
