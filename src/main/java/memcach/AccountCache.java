package memcach;

/**
 * @author Prosony
 * @since 0.0.1
 */

import model.account.Account;
import model.profile.Profile;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AccountCache { //TODO rewrite this shit

    private static AccountCache instance = new AccountCache();
    public static AccountCache getInstance(){
        return instance;
    }
    private final Map<UUID, Account> mapAccount = new ConcurrentHashMap<>();
    private final Map<UUID, Profile> mapIdProfileWithProfile =  new ConcurrentHashMap<>();

    private AccountCache(){

    }

    public void addAccount(Account account, Profile profile) {
        mapAccount.put(account.getId(), account);
        mapIdProfileWithProfile.put(account.getId(), profile);
    }
    public Account getAccountById(UUID idAccount) { return mapAccount.get(idAccount); }

    public Set<UUID> getAllKeyMapAccount(){ return mapAccount.keySet(); }

    public Profile getProfileById(UUID idAccount) { return mapIdProfileWithProfile.get(idAccount); }

}
