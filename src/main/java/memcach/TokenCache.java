package memcach;
/**
 * @author Prosony
 * @since 0.2.7
 */
import model.account.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCache {

    private static TokenCache instance = new TokenCache();

    public static TokenCache getInstance() {
        return instance;
    }

    private final Map<String, Account> mapOnlineAccount;

    private TokenCache() {
        mapOnlineAccount = new ConcurrentHashMap<>();
    }

    public Account getAccountByToken(String compactJws) { return mapOnlineAccount.get(compactJws); }

    public void addJws(String compactJws, Account account) { mapOnlineAccount.put(compactJws, account); }

    public void deleteJws(String compactJws) { mapOnlineAccount.remove(compactJws); }

}
