package services.account;
/**
 * @author Prosony
 * @since 0.0.1
 */
import memcach.AccountCache;
import model.account.Account;
import test.TestLog;

import java.util.Set;
import java.util.UUID;

public class AccountService {
    private AccountCache instance = AccountCache.getInstance();

    private TestLog testLog = new TestLog();


    public Account searchAccountByEmail(String email){
        Set<UUID> collection = instance.getAllKeyMapAccount();
        Account account;
        for (UUID key : collection){
            account = instance.getAccountById(key);
            if (account.getEmail().equals(email)){
                testLog.sendToConsoleMessage("key: " + key);
                return account;
            }
        }
        return null;
    }

}
