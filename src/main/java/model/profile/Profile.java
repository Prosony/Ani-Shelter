package model.profile;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Simple generic for table Profile
 * @author Prosony
 * @since 0.0.1
 */
public class Profile {
    private UUID id;
    private LocalDate dateCreateAccount;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String birthday;
    private String path_avatar;
    private String about;

    public Profile(UUID id, LocalDate dateCreateAccount, String name, String surname, String email, String phone, String birthday, String about, String path_avatar){
        this.id = id;
        this.dateCreateAccount = dateCreateAccount;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.path_avatar = path_avatar;
        this.about = about;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id= id; }


    public LocalDate getDateCreateAccount() {
        return dateCreateAccount;
    }
    public void setDateCreateAccount(LocalDate dateCreateAccount) {
        this.dateCreateAccount = dateCreateAccount;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getPathAvatar() { return path_avatar; }
    public void setPathAvatar(String path_avatar) { this.path_avatar = path_avatar; }


    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " Name: " + getName() + " Surname: " + getSurname() + " Birthday: " + getBirthday() + " Path Avatar: " + getPathAvatar() + " data create account: "+getDateCreateAccount();
    }
}
