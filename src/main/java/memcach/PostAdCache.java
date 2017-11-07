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
        PostAd postAd; PostAd postAd2; PostAd postAd3; PostAd postAd4; PostAd postAd5; PostAd postAd6;

        String textCheep = "Здравствуйте! Меня зовут Чип. Я милый рыжий мальчик, которому еще нет и года. В феврале 2017 года я попал в муниципальный приют Бирюлево вместе со своими сестричками. Одна из них, к моему счастью, обрела дом!  Несмотря на все то горе, что пришлось пережить, я остался дружелюбным псом. Итак, что говорят люди о моем характере.  Я настоящий джентльмен, чересчур умен для своего юного возраста. Обладаю охранными  качествами, но без агрессии, потому как такого добряка поискать еще надо. Умею ходить на поводке и отзываться на команду «рядом, Чип». Скажу честно, ростом я невелик – около 40 см, что чуть меньше среднего размера. Но это для меня не проблема, ведь такой ласковый и гладкошерстный красавец идеально подойдет для проживания как в квартире, так и в собственном доме с участком. Очень надеюсь, что все страшное останется позади и я совсем скоро обрету хозяина, который станет мне лучшим другом, и благодаря которому я позабуду потерю моей мамы и моих сестер.";
        String textAmper = "Если повсюду ампер это измерение силы электрического тока, то наш Ампер - это сам заряд! Заряд энергии, заряд любви и ласки, заряд позитивного настроения и желания жить! Мальчишка совсем новичок в приюте, привыкнуть еще не успел, а вот сердца покорил уже многим. Ласковый, активный и позитивный пес, который реально всех вокруг заряжает своим вечно потрясаюшим настроением. Совсем молодой Ампер только достиг возраста одного года, отлично ходит на поводке, послушный и безобидный ребенок! Сейчас у Ампера именно тот возраст, когда лучше всего начать воспитание собаки. Щенячья гиперактивность уже прошла, а понимание настроения человека пришло! Остальное за Вами. Присмотритесь к этому блондину, он сможет стать Вам не просто другом, но и вашим защитником, напарником и обеспечит Вашу жизнь яркими красками! Хотите жить ярко? Тогда Ампер - собака для Вас!";
        String textBo = "В приюте мне выбрали довольно обидное прозвище. Хотя я слышу, что волонтеры называют мое имя очень ласково, я все равно его немножечко стыжусь. А зовут меня Боявка, потому что я небольшая трусишка… Мне очень интересно, кто же это там заходит к нам в клетку и зачем, но потом становится так страшно, что я спешу скорее спрятаться. Я же не знаю, что хотят сделать приходящие к нам люди – а вдруг что-то плохое? Но остальные коты рассказывают, что это все неправда. Что люди приходят, чтобы забрать нас к себе домой: отмыть, высушить, накормить, а потом уложить к себе на плечико и ласково погладить. И мне хочется поверить, но в душе я очень-очень боюсь обознаться. Я мечтаю выбраться из приюта. Но мой характер не позволяет нашим гостям забрать меня отсюда. Но может, к нам придет мои будущие мама и папа, и захотят, чтоб я больше никогда-никогда не боялась. И когда я буду жить с ними дома, они выберут мне другое, мое настоящее имя.";
        String textFour = "В нашем приюте очень много кошек, которые сидят здесь уже по несколько лет. Только представьте, как этот добряк с выразительными зелеными глазами уже соскучился по домашнему уюту! Ему, конечно, нравится дружить с другими котами, но еще больше он хочет найти себе дом с добрейшим хозяином. Нил кастрирован, приучен к туалету, здоров – казалось бы, что еще нужно для людей? Но каждый раз они проходят мимо его клетки. Взрослый кот знает цену родному дому. Он не капризен и готов ждать вас с работы хоть целый день напролет. Лишь бы ему было кому вечером промурчать, какой хороший сегодня был день";
        String textFive = "Очень ласковые и добрые малыши в поиске неравнодушных людей, которые подарят им заботу, любовь и домашний уют.";
        String textSix = "Вашему вниманию лохмато-пушистый и мягкий, солнечно-рыжий Жужа. Также Жужон обладатель шикарного хвоста! Он не крупный (не выше колена), около 5 лет, при этом спортивный, активный и безусловно позитивный. Игривый как щенок - на прогулке бегать, прыгать и играть - Жужа первый! И зовет всех с собой. Но если нет компании, то это не беда – он будет бегать вокруг Вас с не меньшим удовольствием.\n" +
                "\n" +
                "Приходишь в приют – все собаки приветственно лают, а Жужа прыгает выше всех, что бы первым увидеть и поприветствовать на свой лад. Если попросить описать его в 2 словах, то это будет, пожалуй, «фонтан энергии». Поэтому ему особенно тяжело сидеть в маленьком вольере.\n" +
                "\n" +
                "Жужа любит людей и совершенно не агрессивен по отношению к ним. С собаками более избирателен, он не навязывается сам и не любит навязчивости в свой адрес. Так что отдается в семью без животных.";
        for (UUID id: idAll) {
            Account account = accountCache.getAccountById(id);
            if (account.getEmail().equals("111")){

                ArrayList<String> listTagCheep = new ArrayList<>();
                listTagCheep.add("Dog"); listTagCheep.add("Vaccinations"); listTagCheep.add("no breed");
                ArrayList<String> listCheep = new ArrayList<>();
                listCheep.add("E:\\file\\images\\cheep\\ch1.jpg"); listCheep.add("E:\\file\\images\\cheep\\ch2.jpg"); listCheep.add("E:\\file\\images\\cheep\\ch3.jpg");
                listCheep.add("E:\\file\\images\\cheep\\ch4.jpg");

                ArrayList<String> listTagAmper = new ArrayList<>();
                listTagAmper.add("Dog"); listTagAmper.add("Vaccinations"); listTagAmper.add("no breed");

                ArrayList<String> listAmper = new ArrayList<>();
                listAmper.add("E:\\file\\images\\amper\\amper1.jpg"); listAmper.add("E:\\file\\images\\amper\\amper2.jpg"); listAmper.add("E:\\file\\images\\amper\\amper3.jpg");
                listAmper.add("E:\\file\\images\\amper\\amper4.jpg"); listAmper.add("E:\\file\\images\\amper\\amper5.jpg"); listAmper.add("E:\\file\\images\\amper\\amper6.jpg");
                listAmper.add("E:\\file\\images\\amper\\amper7.jpg");

                ArrayList<String> listTagBo = new ArrayList<>();
                listTagBo.add("Cat"); listTagBo.add("Vaccinations"); listTagBo.add("no breed");
                ArrayList<String> listBo = new ArrayList<>();
                    listBo.add("E:\\file\\images\\bo\\bo1.jpg"); listBo.add("E:\\file\\images\\bo\\bo2.jpg"); listBo.add("E:\\file\\images\\bo\\bo3.jpg");
                    listBo.add("E:\\file\\images\\bo\\bo4.jpg"); listBo.add("E:\\file\\images\\bo\\bo5.jpg");

                ArrayList<String> listTagVan = new ArrayList<>();
                listTagVan.add("Cat"); listTagVan.add("Vaccinations"); listTagVan.add("no breed");
                ArrayList<String> listVan = new ArrayList<>();
                listVan.add("E:\\file\\images\\van\\van1.jpg"); listVan.add("E:\\file\\images\\van\\van2.jpg"); listVan.add("E:\\file\\images\\van\\van3.jpg");
                listVan.add("E:\\file\\images\\van\\van4.jpg"); listVan.add("E:\\file\\images\\van\\van5.jpg");

                ArrayList<String> listTagZu = new ArrayList<>();
                listTagZu.add("Dog"); listTagZu.add("Vaccinations"); listTagZu.add("no breed");
                ArrayList<String> listZu= new ArrayList<>(); listZu.add("E:\\file\\images\\zu\\zu1.jpg"); listZu.add("E:\\file\\images\\zu\\zu2.jpg"); listZu.add("E:\\file\\images\\zu\\zu3.jpg");
                listZu.add("E:\\file\\images\\zu\\zu4.jpg"); listZu.add("E:\\file\\images\\zu\\zu5.jpg");

                postAd = new PostAd(account.getId(), "ЧИП", textCheep, listCheep.get(2), listCheep.get(3), listCheep, listTagCheep);
                postAd2 = new PostAd(account.getId(), "Ампер -вихрь позитивной энергии и неиссякаемой любви!", textAmper, listAmper.get(0), listAmper.get(1), listAmper, listTagAmper);
                postAd3 = new PostAd(account.getId(), "Маленькая трусишка. Живет в приюте и мечтает уехать домой!", textBo, listBo.get(0), listBo.get(1), listBo, listTagBo);
                postAd5 = new PostAd(account.getId(), "ВАНЕЧКА", textFive, listVan.get(2), listVan.get(1), listVan, listTagVan);
                postAd6 = new PostAd(account.getId(), "Энергичный пес, нуждающийся в любви и внимании!", textSix, listZu.get(0), listZu.get(2), listZu, listTagZu);

                addPostAd(postAd.getId(),postAd);
                addPostAd(postAd2.getId(),postAd2);
                addPostAd(postAd3.getId(),postAd3);
                addPostAd(postAd5.getId(),postAd5);
                addPostAd(postAd6.getId(),postAd6);

                ArrayList<PostAd>  listPost = new ArrayList<>();

                listPost.add(postAd);
                listPost.add(postAd2);
                listPost.add(postAd3);
//                listPost.add(postAd4);
                listPost.add(postAd5);
                listPost.add(postAd6);

                addListPostAd(account.getId(),listPost);
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
