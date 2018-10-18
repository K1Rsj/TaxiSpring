package project.model.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.model.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    @Modifying
    @Query("UPDATE User user SET user.moneySpent = :moneySpent WHERE user.id = :userId")
    void updateMoneySpent(@Param("userId") Integer userId, @Param("moneySpent") Long moneySpent);
}
