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
        String textThree = "В приюте мне выбрали довольно обидное прозвище. Хотя я слышу, что волонтеры называют мое имя очень ласково, я все равно его немножечко стыжусь. А зовут меня Боявка, потому что я небольшая трусишка… Мне очень интересно, кто же это там заходит к нам в клетку и зачем, но потом становится так страшно, что я спешу скорее спрятаться. Я же не знаю, что хотят сделать приходящие к нам люди – а вдруг что-то плохое? Но остальные коты рассказывают, что это все неправда. Что люди приходят, чтобы забрать нас к себе домой: отмыть, высушить, накормить, а потом уложить к себе на плечико и ласково погладить. И мне хочется поверить, но в душе я очень-очень боюсь обознаться. Я мечтаю выбраться из приюта. Но мой характер не позволяет нашим гостям забрать меня отсюда. Но может, к нам придет мои будущие мама и папа, и захотят, чтоб я больше никогда-никогда не боялась. И когда я буду жить с ними дома, они выберут мне другое, мое настоящее имя.";
        String textFour = "В нашем приюте очень много кошек, которые сидят здесь уже по несколько лет. Только представьте, как этот добряк с выразительными зелеными глазами уже соскучился по домашнему уюту! Ему, конечно, нравится дружить с другими котами, но еще больше он хочет найти себе дом с добрейшим хозяином. Нил кастрирован, приучен к туалету, здоров – казалось бы, что еще нужно для людей? Но каждый раз они проходят мимо его клетки. Взрослый кот знает цену родному дому. Он не капризен и готов ждать вас с работы хоть целый день напролет. Лишь бы ему было кому вечером промурчать, какой хороший сегодня был день";
        String textFive = "Очень ласковые и добрые малыши в поиске неравнодушных людей, которые подарят им заботу, любовь и домашний уют.На передержке";
        String textSix = "Если повсюду ампер это измерение силы электрического тока, то наш Ампер - это сам заряд! Заряд энергии, заряд любви и ласки, заряд позитивного настроения и желания жить! Мальчишка совсем новичок в приюте, привыкнуть еще не успел, а вот сердца покорил уже многим. Ласковый, активный и позитивный пес, который реально всех вокруг заряжает своим вечно потрясаюшим настроением. Совсем молодой Ампер только достиг возраста одного года, отлично ходит на поводке, послушный и безобидный ребенок!";

        for (UUID id: idAll) {
            Account account = accountCache.getAccountById(id);
            if (account.getEmail().equals("111")){
                postAd = new PostAd(account.getId(), "Лира ищет уютный дом и понимающих хозяев", textFirst, "E:\\file\\images\\cat-one.jpg", "E:\\file\\images\\cat-two.jpg");
                postAd2 = new PostAd(account.getId(), "Наблюдательная Марпл хочет любить и быть любимой", textSecond, "E:\\file\\images\\mkl.jpg", "E:\\file\\images\\mkl2.jpg");
                postAd3 = new PostAd(account.getId(), "Маленькая трусишка. Живет в приюте и мечтает уехать домой!", textThree, "E:\\file\\images\\bo1.jpg", "E:\\file\\images\\bo2.jpg");
                postAd4 = new PostAd(account.getId(), "Добряк с выразительными зелеными глазами", textFour, "E:\\file\\images\\neel1.jpg", "E:\\file\\images\\neel2.jpg");
                postAd5 = new PostAd(account.getId(), "УМКА И ВАНЕЧКА", textFive, "E:\\file\\images\\umka1.jpg", "E:\\file\\images\\umka2.jpg");
                postAd6 = new PostAd(account.getId(), "Ампер -вихрь позитивной энергии и неиссякаемой любви!", textSix, "E:\\file\\images\\amper1.jpg", "E:\\file\\images\\amper2.jpg");

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
