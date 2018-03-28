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
    public boolean checkData(String email ,String password, String name, String surname, String phone, String birthday, String pathToAvatar, String about, String dateCreateAccount){
        boolean allRight = true;

        if (email == null || email.isEmpty()) allRight = false;
        if (password == null || password.isEmpty()) allRight = false;
        if (name == null || name.isEmpty()) allRight = false;
        if (surname == null || surname.isEmpty()) allRight = false;
        if (phone == null || phone.isEmpty()) allRight = false;
        if (birthday == null || birthday.isEmpty()) allRight = false;
        if (pathToAvatar == null || pathToAvatar.isEmpty()) allRight = false;
        if (about == null || about.isEmpty()) allRight = false;
        if (dateCreateAccount == null || dateCreateAccount.isEmpty()) allRight = false;
        return allRight;
    }
}
