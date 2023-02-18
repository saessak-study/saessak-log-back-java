package saessak.log.jwt.dto;

import saessak.log.user.User;

public class TokenToUserDto {

    private String profileId;

    private String password;

    private String name;

    private String email;

    public TokenToUserDto(User user) {
        this.profileId = user.getProfileId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public User toEntity() {
        return User.of(profileId, password, name, email);
    }
}
