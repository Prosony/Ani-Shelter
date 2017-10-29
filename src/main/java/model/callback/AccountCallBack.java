package model.callback;

import java.util.UUID;

/**
 * Simple service for content posts
 * @author Prosony
 * @since 0.2.7.2
 */
public class AccountCallBack {
    private UUID id;
    private String token;

    public AccountCallBack(UUID idAccount, String token){
        this.id = idAccount;
        this.token = token;
    }

    public UUID getIdAccount() {
        return id;
    }

    public void setIdAccount(UUID idAccount) {
        this.id = idAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
