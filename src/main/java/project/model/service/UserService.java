package project.model.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.model.dao.repository.UserRepository;
import project.model.domain.User;
import project.model.dto.UserDto;
import project.model.exception.EntityAlreadyExists;
import project.model.util.OrderPriceGenerator;
import project.model.util.Utils;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = EntityAlreadyExists.class)
    public void registerUser(UserDto userDto) throws EntityAlreadyExists {
        try {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExists(Utils.getNameOfUniqueColumn(e), Utils.getValueFromNotUniqueException(e,
                    userDto));
        }
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