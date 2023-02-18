package saessak.log.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saessak.log.user.User;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinDto {

    private Long id;
    private String profileId;
    private String password;
    private String name;
    private String email;

    @Builder
    public UserJoinDto(Long id, String profileId, String password, String name, String email) {
        this.id = id;
        this.profileId = profileId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .profileId(profileId)
                .password(password)
                .email(email)
                .name(name).build();

    }
}
