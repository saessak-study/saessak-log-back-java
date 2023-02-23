package saessak.log.subscription.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saessak.log.post.Post;
import saessak.log.post.repository.PostRepository;
import saessak.log.subscription.Subscription;
import saessak.log.subscription.dto.SubscriptionDto;
import saessak.log.subscription.repository.SubscriptionRepository;
import saessak.log.user.User;
import saessak.log.user.repository.UserRepository;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubscriptionService {

    final UserRepository userRepository;
    final PostRepository postRepository;
    final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Long subscribe(Long fromUserId, Long toUserId) {
        Subscription subscription = new Subscription();
        User fromUser = userRepository.findById(fromUserId).orElseThrow(() -> new IllegalArgumentException());
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new IllegalArgumentException());
        subscription.setToUserId(toUser);
        subscription.setFromUserId(fromUser);
        subscription = subscriptionRepository.save(subscription);
        return subscription.getId();
    }

}
