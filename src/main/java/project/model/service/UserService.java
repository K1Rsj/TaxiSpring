package project.model.service;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.dao.repository.UserRepository;
import project.model.domain.User;
import project.model.util.OrderPriceGenerator;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws ConstraintViolationException {
        try {
            userRepository.save(user);
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getMessage(), e.getSQLException(), e.getConstraintName());
        }
    }

    public User findUserByLogin(String userLogin) {
        return userRepository.findByLogin(userLogin);
    }

    public Integer getUserDiscount(String userLogin) {
        return OrderPriceGenerator.getDiscountBasedOnMoneySpent(findUserByLogin(userLogin).getMoneySpent());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }
}