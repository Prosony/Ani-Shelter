package model.account;
/**
 * Simple generic for json token jsw
 * @author Prosony
 * @since 0.2.5
 */
public class TokenJWS {

    private String token;

    public TokenJWS(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
