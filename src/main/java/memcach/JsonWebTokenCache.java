package memcach;
/**
 * @author Prosony
 * @since 0.2.7
 */
import model.account.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonWebTokenCache {

    private static JsonWebTokenCache instance = new JsonWebTokenCache();

    public static JsonWebTokenCache getInstance() {
        return instance;
    }

    private final Map<String, Account> mapOnlineAccount;

    private JsonWebTokenCache() {
        mapOnlineAccount = new ConcurrentHashMap<>();
    }

    public Account getAccountByJws(String compactJws) { return mapOnlineAccount.get(compactJws); }

    public void addJws(String compactJws, Account account) { mapOnlineAccount.put(compactJws, account); }

    public void deleteJws(String compactJws) { mapOnlineAccount.remove(compactJws); }

}
