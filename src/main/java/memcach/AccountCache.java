package memcach;

/**
 * @author Prosony
 * @since 0.0.1
 */

import model.account.Account;
import model.profile.Profile;
import test.TestLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AccountCache {

    private static AccountCache instance = new AccountCache();
    public static AccountCache getInstance(){
        return instance;
    }
    private final Map<UUID, Account> mapAccount;
    private final Map<UUID, Profile> mapIdProfileWithProfile;


    public AccountCache(){
        String putin = "Без шуток блэдь Советский и российский государственный и политический деятель, действующий президент Российской Федерации";
        String jackie = "Актёр, каскадёр, кинорежиссёр, продюсер, сценарист, постановщик трюков и боевых сцен, певец, филантроп, мастер боевых искусств. Посол доброй воли ЮНИСЕФ. Кавалер ордена Британской империи";
        String sanya = "Просто батька, вот так блдь";
        String bonus = "российский актёр озвучивания, театра и кино, телеведущий. Творческий псевдоним — «Бонус»";

        mapAccount = new HashMap<>();
        mapIdProfileWithProfile = new HashMap<>();
        Account account1 = new Account("111", "111");
        Account account2 = new Account("222", "222");
        Account account3 = new Account("333", "333");
        Account account4 = new Account("444", "444");
        addAccount(account1, new Profile( account1.getId(),"Vladimir", "Putin", "8(999)-868-1243", "17.10.1952",putin ,"E:\\file\\images\\Putin.jpg"));
        addAccount(account2, new Profile( account2.getId(),"Jackie", "Chan", "8(999)-868-1243", "07.04.1954", jackie,"E:\\file\\images\\Jackie.jpg"));
        addAccount(account3, new Profile( account3.getId(),"Sanya", "Batya", "8(999)-868-1243", "30.08.1954", sanya,"E:\\file\\images\\batya.jpg"));
        addAccount(account4, new Profile( account4.getId(),"Бори́с", "Репету́р ", "8(999)-868-1243", "29.07.1958", bonus,"E:\\file\\images\\example.jpg"));

        TestLog.getInstance().sendToConsoleMessage("#TEST [class AccountCache] added three account to cash");

    }

    public void addAccount(Account account, Profile profile) {
        mapAccount.put(account.getId(), account);
        mapIdProfileWithProfile.put(account.getId(), profile);
    }
    public Account getAccountById(UUID idAccount) { return mapAccount.get(idAccount); }

    public Set<UUID> getAllKeyMapAccount(){ return mapAccount.keySet(); }

    public Profile getProfileById(UUID idAccount) { return mapIdProfileWithProfile.get(idAccount); }

}
