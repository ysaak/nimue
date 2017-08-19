package ysaak.nimue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ysaak.nimue.core.model.User;
import ysaak.nimue.core.service.UserService;

@SpringBootApplication
@ComponentScan({"ysaak.common", "ysaak.nimue"})
public class NimueApp implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(NimueApp.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User u = new User();
        u.setActive(true);
        u.setDisplayedName("Test admin user");
        u.setEmail("test@test.fr");
        u.setLogin("admin");
        u.setPassword("password2");
        u.setPasswordConfirmation("password2");
        userService.create(u);
    }
}
