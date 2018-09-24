package project.model.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.dao.repository.UserRepository;
import project.model.domain.User;
import project.model.util.OrderPriceGenerator;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findUserByLogin(String userLogin) {
        return userRepository.findByLogin(userLogin);
    }

    public Long getUserDiscount(String userLogin) {
        return OrderPriceGenerator.getDiscountBasedOnMoneySpent(findUserByLogin(userLogin).get().getMoneySpent());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}