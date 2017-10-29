package memcach;
/**
 * @author Prosony
 * @since 0.0.1
 */
import model.favorites.Favorites;
import java.util.*;

public class FavoritesCache {

    private static FavoritesCache instance = new FavoritesCache();
    public static FavoritesCache getInstance(){
        return instance;
    }


    private final Map<UUID, ArrayList<Favorites>> mapIdAccountWithArrayListFavorites;

    public FavoritesCache(){
        mapIdAccountWithArrayListFavorites = new HashMap<>();
    }

    /**
     * Add to map new list Favorites by account id
     * @param idAccount
     * @param list
     */
    public void addListFavorites(UUID idAccount, ArrayList<Favorites> list){
        mapIdAccountWithArrayListFavorites.put(idAccount, list);
    }

    public void deleteListFavorites(UUID idAccount){
        mapIdAccountWithArrayListFavorites.remove(idAccount);
    }
    /**
     * Return ArrayList<Favorites> by id account
     * @param idAccount
     * @return Set<Favorites>
     */
    public ArrayList<Favorites> getListFavoritesByIdAccount(UUID idAccount){
        return mapIdAccountWithArrayListFavorites.get(idAccount);
    }

}
