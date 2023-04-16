package tour.po;

import lombok.Data;


@Data
public class CommentPO {
    private Long CommentId;
    private Long articleId;
    private String type;
}
