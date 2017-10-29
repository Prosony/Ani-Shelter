package model.profile;

import java.util.UUID;

/**
 * Simple generic for table Profile
 * @author Prosony
 * @since 0.0.1
 */
public class Profile {
    private UUID id;
    private String name;
    private String surname;
    private String phone;
    private String birthday;
    private String path_avatar;
    private String about;

    public Profile(UUID id, String name, String surname, String phone, String birthday, String about, String path_avatar){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthday = birthday;
        this.path_avatar = path_avatar;
        this.about = about;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id= id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

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
        return "ID: " + getId() + " Name: " + getName() + " Surname: " + getSurname() + " Birthday: " + getBirthday() + " Path Avatar: " + getPathAvatar();
    }
}
