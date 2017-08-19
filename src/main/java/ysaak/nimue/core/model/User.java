package ysaak.nimue.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 4, max=50)
    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Size(min=8, max=250)
    private transient String password;
    private transient String passwordConfirmation;

    @Size(min=8, max=250)
    @Column(name = "password", nullable = false)
    private String passwordDigest;

    @NotNull
    @Size(min = 4, max=250)
    @Column(nullable = false, length = 250)
    private String displayedName;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active &&
                Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordConfirmation, user.passwordConfirmation) &&
                Objects.equals(passwordDigest, user.passwordDigest) &&
                Objects.equals(displayedName, user.displayedName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, passwordConfirmation, passwordDigest, displayedName, email, active);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("login", login)
                .append("password", password)
                .append("passwordConfirmation", passwordConfirmation)
                .append("passwordDigest", passwordDigest)
                .append("displayedName", displayedName)
                .append("email", email)
                .append("active", active)
                .toString();
    }
}
