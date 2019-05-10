package service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.model.dao.repository.UserRepository;
import project.model.domain.User;
import project.model.dto.UserDto;
import project.model.exception.EntityAlreadyExists;
import project.model.service.UserService;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {ApplicationConfig.class})
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setFirstName("Vasya");
        user.setEmail("ololo@gmail.com");
        user.setPassword("qwerty");
        user.setLogin("ara");
        user.setMoneySpent(100500L);
    }

    @Test
    public void registerUserTest() throws EntityAlreadyExists {
        UserDto userDto = new UserDto();
        when(passwordEncoder.encode(anyString())).thenReturn("ololo");
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        userService.registerUser(new UserDto());

        verify(userRepository).save(userArgumentCaptor.capture());
        verify(passwordEncoder).encode(anyString());
        verify(modelMapper).map(userDto, User.class);
        User savedUser = userArgumentCaptor.getValue();

        assertThat(savedUser, is(user));
        assertThat(user.getPassword(), is("ololo"));
    }

    @Test(expected = EntityAlreadyExists.class)
    public void registerUserThrowsEntityAlreadyExists() throws EntityAlreadyExists {
        when(passwordEncoder.encode(anyString())).thenReturn("ololo");
        when(modelMapper.map(new UserDto(), User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(new DataIntegrityViolationException("duplicate"));

        userService.registerUser(new UserDto());
    }

    @Test
    public void findUserByLoginTest() {
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        User expected = User.builder().login("ara").password("qwerty").firstName("Vasya").email("ololo@gmail.com")
                .moneySpent(100500L).build();

        Optional<User> actual = userService.findUserByLogin("123414");

        verify(userRepository).findByLogin("123414");
        assertThat(actual.get(), is(expected));
    }

    @Test
    public void getUserDiscountTest() {
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
        Long expectedDiscount = 5L;

        Long actual = userService.getUserDiscount("123414");

        assertThat(actual, is(expectedDiscount));
    }

    @Test
    public void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> actual = userService.getAllUsers();

        assertThat(actual, is(Collections.emptyList()));
    }

    @Test
    public void deleteUserByIdTest() {
        doNothing().when(userRepository).deleteById(anyInt());

        userService.deleteUserById(100);

        verify(userRepository).deleteById(100);
    }

    @Test
    public void getUserByIdTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        User expected = User.builder().login("ara").password("qwerty").firstName("Vasya").email("ololo@gmail.com")
                .moneySpent(100500L).build();

        Optional<User> actualUser = userService.getUserById(100);

        assertThat(actualUser.get(), is(expected));
    }
}
