package memcach;
/**
 * Simple service for ad post
 * @author Prosony
 * @since 0.0.1
 */
import model.account.Account;
import model.ad.PostAd;

import java.util.*;

public class PostAdCache {

    private static final PostAdCache instance = new PostAdCache();

    public static PostAdCache getInstance() {
        return instance;
    }

    private final Map<UUID, PostAd> mapPostAd;
    private final Map<UUID, ArrayList<PostAd>> mapListPostAd;

    public PostAdCache(){
        mapPostAd = new HashMap<>();
        mapListPostAd = new HashMap<>();
        AccountCache accountCache = AccountCache.getInstance();
        Set<UUID> idAll = accountCache.getAllKeyMapAccount();
        PostAd postAd;
        PostAd postAd2;
        PostAd postAd3;
        PostAd postAd4;
        PostAd postAd5;
        PostAd postAd6;
        String textFirst = "Лира ищет уютный дом и понимающих хозяев. У кошечки бархатная черно-белая шубка, желтые глаза и трогательный розовый носик. Лира спокойная и ненавязчивая кошечка. Она попала в приют с улицы, и пока робка и осторожна в общении с людьми. Но мы уверены, в домашней обстановке Лира станет ласковой и нежной. Мы ищем для кошечки особенного человека, который с пониманием отнесется к тому, что Лире может понадобится время для адаптации в новом доме. ";
        String textSecond = "Наблюдательная Марпл хочет любить и быть любимой. Совершенно очевидно, что в недавнем времени Марпл была домашней. Мы не знаем, что именно случилось, но кошку нашли в подъезде, ютившейся в небольшой коробке. У нее была сломана челюсть и клык. Сейчас Марпл уже пришла в себя после операции и готова открыть новую главу домашней жизни. Марпл обладает спокойным и ласковым нравом, но она немного стесняется незнакомых людей. Мы уверены, что Марпл сможет стать вашим лучшим другом - приезжайте в приют знакомиться!";

        for (UUID id: idAll) {
            Account account = accountCache.getAccountById(id);
            if (account.getEmail().equals("111")){
                postAd = new PostAd(account.getId(), "Лира ищет уютный дом и понимающих хозяев", textFirst, "E:\\file\\images\\cat-one.jpg", "E:\\file\\images\\cat-two.jpg");
                postAd2 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");
                postAd3 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");
                postAd4 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");
                postAd5 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");
                postAd6 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");

                addPostAd(postAd.getId(),postAd);
                addPostAd(postAd2.getId(),postAd2);
                addPostAd(postAd3.getId(),postAd3);
                addPostAd(postAd4.getId(),postAd4);
                addPostAd(postAd5.getId(),postAd5);
                addPostAd(postAd6.getId(),postAd6);

                ArrayList<PostAd>  list = new ArrayList<>();

                list.add(postAd);
                list.add(postAd2);
                list.add(postAd3);
                list.add(postAd4);
                list.add(postAd5);
                list.add(postAd6);

                addListPostAd(account.getId(),list);
            }
        }
    }
    /** simple generic post ad*/
    public void addPostAd(UUID idPostAd, PostAd content){
        mapPostAd.put(idPostAd, content);
    }

    public PostAd getPostAdByIdPostAd(UUID idPostAd){
        return mapPostAd.get(idPostAd);
    }

    public Set<UUID> getAllIdPostAd(){
        return mapPostAd.keySet();
    }

    public void deletePostContentByIdPostAd(UUID idPostAd){
        mapPostAd.remove(idPostAd);
    }

    /** ArrayList post ad*/
    public void addListPostAd(UUID idAccount, ArrayList<PostAd> content){
        mapListPostAd.put(idAccount, content);
    }

    public ArrayList<PostAd> getListPostAdByIdAccount(UUID idAccount){
        return mapListPostAd.get(idAccount);
    }

    public void deleteListPostAdByIdAccount(UUID idAccount){
        mapListPostAd.remove(idAccount);
    }

}
