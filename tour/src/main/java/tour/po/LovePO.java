package tour.po;

import lombok.Data;

@Data
public class LovePO {
    /**
     * 评论 id
     */
    private Long id;
    /**
     * 评论发布者的 id
     */
    private Long userId;
    /**
     * 点赞状态(0: 未点赞 | 1: 已点赞)
     */
    private Integer status;
}
