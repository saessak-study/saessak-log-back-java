package saessak.log.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class CommentViewDto {

    private String profileId;
    private String comment;
    private LocalDateTime createdDate;

    public CommentViewDto(
            String profileId,
            String comment,
            LocalDateTime createdDate
    ) {
        this.profileId = profileId;
        this.comment = comment;
        this.createdDate = createdDate;
    }

}
