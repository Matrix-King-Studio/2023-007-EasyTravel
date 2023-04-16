package tour.base.enumerate;


public interface HttpStatusCodeService {
    /**
     * 获取状态码
     * @return 状态码
     */
    String getReCode();
    /**
     * 获取状态信息
     * @return 状态信息
     */
    String getReMsg();
}