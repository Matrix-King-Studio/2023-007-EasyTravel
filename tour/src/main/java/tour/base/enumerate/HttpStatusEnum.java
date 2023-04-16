package tour.base.enumerate;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum HttpStatusEnum implements HttpStatusCodeService {
    /**
     * 200 成功状态码
     */
    RE_CODE_SUCCESS("200", "成功"),
    /**
     * 100 网络异常状态码
     */
    RE_CODE_DATABASE_ERROR("100", "网络连接异常"),
    /**
     * 101 咨讯、活动等 topic 选择错误 或者选择的不在意料之中错误码
     */
    RE_CODE_TOPIC_ERROR("101", "所选主题不存在"),
    /**
     * 102 参数错误状态码
     */
    RE_CODE_PARAM_ERROR("102", "参数错误"),
    /**
     * 103 查询对象为空状态码
     */
    RE_CODE_NO_HAVE_OBJECT("103", "查询对象不存在"),
    /**
     * 104 文件过大
     */
    RE_CODE_FILE_TO_BIG("104", "文件过大"),
    /**
     * 105 创建token失败
     */
    RE_CODE_JWT_CREATE("105", "创建token失败"),
    /**
     * 1001 账号不存在状态码 需退出登录
     */
    RE_CODE_NO_HAVE_ACCOUNT("901", "账号不存在"),
    /**
     * 1002 登录过期状态码 需退出登录
     */
    RE_CODE_LOGIN_EXPIRED("902", "登录已过期,请重新登录!"),
    /**
     * 1003 请携带正确的token
     */
    RE_CODE_NO_HAVE_TOKEN("903", "请携带正确的token"),
    /**
     * 1004 账号密码错误状态码
     */
    RE_CODE_PASSWORD_ERROR("904", "账号密码错误");

    private String code;

    private String message;

    @Override
    public String getReCode() {
        return code;
    }

    @Override
    public String getReMsg() {
        return message;
    }
}
