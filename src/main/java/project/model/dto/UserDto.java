package project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.domain.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String login;
    private String password;
    private String confirmationPassword;
    private String email;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private User.Role role;
}
