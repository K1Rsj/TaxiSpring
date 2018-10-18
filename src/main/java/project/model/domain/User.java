package project.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_users", nullable = false)
    private Integer id;

    @Column(name = "login", nullable = false, length = 45, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmationPassword;

    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 45)
    private String secondName;

    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;

    @Column(name = "money_spent", insertable = false, nullable = false)
    private Long moneySpent;

    @Column(name = "role", nullable = false, insertable = false, length = 45)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    /**
     * User roles
     */
    public enum Role {
        USER, ADMIN
    }
}
