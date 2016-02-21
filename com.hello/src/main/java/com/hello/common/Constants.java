package com.hello.common;

/**
 *  常量类
 * @author Administrator
 *
 */
public class Constants {
	/**系统类型**/
	public static final String SYS_TYPE = "SYS_TYPE";
	
	/**失败**/
	public static final int STATUS_FAILURE = 1;
	
	/**成功**/
	public static final int STATUS_SUCCESS= 2;
	
	/**存用户session**/
	public static final String USER_SESSION = "USER_SESSION";
	
	/**状态：有效**/
	public static final int VALID_TYPE_EFFECT = 1;
	
	/**状态：无效**/
	public static final int VALID_TYPE_INVALID = 2;
	
	/**内网上传**/
	public static final int OPERATE_TYPE_INNER = 1;
	
	/**外网上传**/
	public static final int OPERATE_TYPE_OUTER = 2;
	
	/**权限分配**/
	public static final int OPERATE_TYPE_AUTH = 3;
	
	/**登录**/
	public static final int OPERATE_TYPE_LOGIN = 4;
	
	/**菜单级别：1-配置菜单**/
	public static final int MENU_LEVEL_FIRST = 1;
	
	/**菜单级别：2-菜单目录**/
	public static final int MENU_LEVEL_SECOND = 2;
	
	/**菜单级别：8-功能菜单**/
	public static final int MENU_LEVEL_FUNCTION = 8;
	
	/**菜单级别：9-功能按钮**/
	public static final int MENU_LEVEL_BUTTON = 9;
	
	/**session  存放用户菜单列表**/
	public static final String MENU_LIST = "menu_list";
	
	/**session 存放用户功能列表**/
	public static final String FUNCTION_LIST = "function_list";
	
	public static final String UPLOAD_FILE_PATH;//上传文件路径
	/**
	 * 注册激活邮件
	 */
	public static final String REGEMAILCCONTENT="这是一封来自工作室的激活邮件,点击一下连接激活邮件:";
	static{
		new FusysConfig();
		UPLOAD_FILE_PATH = FusysConfig.getUploadFilePath();
	}
	
	/**
	 * 房子照片缓存文件夹
	 */
	public static final String HOURSEUPLOADCACHE="hourseUploadCache";
	
	/**
	 * 房子照片真正文件夹
	 */
	public static final String HOURSEIMAGE="hourseImage";
	
	
	/**
	 * 头像照片缓存文件夹
	 */
	public static final String HEADERCACHE="headerCache";
	
	/**
	 * 头像照片缓存文件夹
	 */
	public static final String HEADERIMAGE="headerImage";
}