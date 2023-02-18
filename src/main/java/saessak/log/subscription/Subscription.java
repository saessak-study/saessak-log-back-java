package saessak.log.subscription;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saessak.log.user.User;


import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Subscription {

    @Id
    @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUserId;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUserId;
}
