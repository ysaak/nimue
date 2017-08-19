package ysaak.nimue.core.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ysaak.common.exception.BusinessException;
import ysaak.common.exception.generic.DataValidationException;
import ysaak.nimue.core.exception.user.EmailAlreadyUsedException;
import ysaak.nimue.core.exception.user.LoginAlreadyUsedException;
import ysaak.nimue.core.exception.user.NoMatchingPasswordException;
import ysaak.nimue.core.model.User;
import ysaak.nimue.core.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private User getBasicUser() {
        User user = new User();
        user.setLogin("testUser1");
        user.setDisplayedName("Test User 1");
        user.setEmail("test@test.fr");
        user.setPassword("password");
        user.setPasswordConfirmation("password");
        user.setActive(true);
        return user;
    }

    @Test(expected = DataValidationException.class)
    public void testUserLoginWithMinimumSize() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setLogin(getRandomString(3));

        userService.create(testUser);
    }

    @Test(expected = DataValidationException.class)
    public void testUserLoginWithMaximumSize() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setLogin(getRandomString(51));

        userService.create(testUser);
    }

    @Test(expected = DataValidationException.class)
    public void testUserDisplayedNameWithMinimumSize() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setDisplayedName(getRandomString(3));

        userService.create(testUser);
    }

    @Test(expected = DataValidationException.class)
    public void testUserDisplayedNameWithMaximumSize() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setDisplayedName(getRandomString(251));

        userService.create(testUser);
    }

    @Test
    public void testUserEmailValidity() {
        String[] validAddresses = new String[] { "user@example.com", "USER@foo.COM", "A_US-ER@foo.bar.org",
                "first.last@foo.jp", "alice+bob@baz.cn" };

        User testUser = getBasicUser();

        for (String address : validAddresses) {
            testUser.setEmail(address);

            try {
                userService.create(testUser);
            }
            catch (BusinessException e) {
                Assert.fail(address + " should be valid");
            }
        }
    }

    @Test(expected = DataValidationException.class)
    public void testUserEmailInvalidity() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setDisplayedName(getRandomString(251));

        userService.create(testUser);
    }

    @Test(expected = DataValidationException.class)
    public void testUserPasswordsRequired() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setPassword(null);
        testUser.setPasswordConfirmation(null);
        userService.create(testUser);
    }

    @Test(expected = NoMatchingPasswordException.class)
    public void testUserPasswordsMatch() throws BusinessException {
        User testUser = getBasicUser();
        testUser.setPassword("password1");
        userService.create(testUser);
    }

    @Test
    public void testCreateUser() throws BusinessException {
        final User testUser = getBasicUser();

        final User storedTestUser = getBasicUser();
        storedTestUser.setId(1L);
        Mockito.when(userRepository.save(testUser)).thenReturn(storedTestUser);


        User newUser = userService.create(testUser);

        Assert.assertNotNull(newUser.getId());
        Assert.assertTrue(newUser.getId() > 0);
    }

    @Test(expected = LoginAlreadyUsedException.class)
    public void testLoginAlreadyUsed() throws BusinessException {
        final User testUser = getBasicUser();
        testUser.setId(1L);

        final User storedTestUser = getBasicUser();
        storedTestUser.setId(2L);
        Mockito.when(userRepository.findByLogin(storedTestUser.getLogin())).thenReturn(storedTestUser);

        userService.create(testUser);
    }

    @Test(expected = EmailAlreadyUsedException.class)
    public void testEmailAlreadyUsed() throws BusinessException {
        final User testUser = getBasicUser();
        testUser.setId(1L);

        final User storedTestUser = getBasicUser();
        storedTestUser.setId(2L);
        Mockito.when(userRepository.findByEmail(storedTestUser.getEmail())).thenReturn(storedTestUser);

        userService.create(testUser);
    }

    @Test
    public void testPasswordMatch_success() {
        final User testUser = getBasicUser();
        testUser.setId(1L);
        testUser.setPasswordDigest(userService.getPasswordEncoder().encode("password"));

        Assert.assertTrue(userService.checkPasswordMatches(testUser, "password"));
    }

    @Test
    public void testPasswordMatch_fail() {
        final User testUser = getBasicUser();
        testUser.setId(1L);
        testUser.setPasswordDigest(userService.getPasswordEncoder().encode(testUser.getPassword()));

        Assert.assertFalse(userService.checkPasswordMatches(testUser, ""));
    }

    @Test
    public void testFindByLogin_found() {
        final User testUser = getBasicUser();
        testUser.setId(1L);

        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenReturn(testUser);

        final User foundUser = userService.findByLogin(testUser.getLogin());
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(testUser, foundUser);
    }

    @Test
    public void testFindByLogin_notFound() {
        final User testUser = getBasicUser();
        testUser.setId(1L);

        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenReturn(null);

        final User foundUser = userService.findByLogin(testUser.getLogin());
        Assert.assertNull(foundUser);
    }

    @Test
    public void testListAll() {
        final User testUser = getBasicUser();
        testUser.setId(1L);
        final List<User> userList = Collections.singletonList(testUser);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        final List<User> foundUser = userService.listAll();
        Assert.assertNotNull(foundUser);
        Assert.assertEquals(userList, foundUser);
    }
}
