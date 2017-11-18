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

public class AccountCache {

    private static AccountCache instance = new AccountCache();
    public static AccountCache getInstance(){
        return instance;
    }
    private final Map<UUID, Account> mapAccount = new ConcurrentHashMap<>();
    private final Map<UUID, Profile> mapIdProfileWithProfile =  new ConcurrentHashMap<>();


    private AccountCache(){
        String sanya = "Просто батька, вот так блдь";
        String bonus = "российский актёр озвучивания, театра и кино, телеведущий. Творческий псевдоним — «Бонус»";

        Account account3 = new Account(UUID.randomUUID(), "333@bk.ru", "333");
        Account account4 = new Account(UUID.randomUUID(), "444@bk.ru", "444");
        addAccount(account3, new Profile( account3.getId(), LocalDate.now(),"Sanya", "Batya", "333@bk.ru", "8(999)-868-1243", "30.08.1954", sanya,"E:\\file\\images\\batya.jpg"));
        addAccount(account4, new Profile( account4.getId(), LocalDate.now(),"Бори́с", "Репету́р ", "444@bk.ru", "8(999)-868-1243", "29.07.1958", bonus,"E:\\file\\images\\example.jpg"));
    }

    public void addAccount(Account account, Profile profile) {
        mapAccount.put(account.getId(), account);
        mapIdProfileWithProfile.put(account.getId(), profile);
    }
    public Account getAccountById(UUID idAccount) { return mapAccount.get(idAccount); }

    public Set<UUID> getAllKeyMapAccount(){ return mapAccount.keySet(); }

    public Profile getProfileById(UUID idAccount) { return mapIdProfileWithProfile.get(idAccount); }

}
