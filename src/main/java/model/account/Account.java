package model.account;
/**
 * Simple generic for table account
 * @author Prosony
 * @since 0.0.1
 */
import java.util.UUID;

public class Account {
    private UUID id;
    private String email;
    private String password;

    public Account(){
    }

    public Account(UUID id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "ID: " + getId()  + " Email: " + getEmail() + " Password: "  + getPassword();
    }
}

