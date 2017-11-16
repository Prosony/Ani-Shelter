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
            testLog.sendToConsoleMessage("#INFO [AccountService ] [searchAccountByEmail] ID: "+key+", account.getEmail(): " + account.getEmail()+ " email: "+email);
            if (account.getEmail().equalsIgnoreCase(email)){
                return account;
            }
        }
        return null;
    }

}
