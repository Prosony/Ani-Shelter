package model.favorites;
/**
 * Simple generic for table Favorites (Bookmarks)
 * @author Prosony
 * @since 0.0.1
 */
import test.TestLog;

import java.util.UUID;

public class Favorites {


    private UUID idThisFavorites;
    private UUID idAccount;
    private UUID idFavoritesAd;

    public Favorites(){}

    public Favorites(UUID idAccount, UUID idFavoritesAd){
        this.idThisFavorites = UUID.randomUUID();
        this.idAccount = idAccount;
        this.idFavoritesAd = idFavoritesAd;
        TestLog.getInstance().sendToConsoleMessage("#TEST [class Favorites] Account "+idAccount+" add to Favorites ad: "+idFavoritesAd);
    }

    public UUID getId() { return idThisFavorites; }
    public void setId(UUID idThisFavorites) { this.idThisFavorites = idThisFavorites; }

    public UUID getIdAccount() { return idAccount; }
    public void setIdAccount(UUID idAccount) { this.idAccount = idAccount; }

    public UUID getIdFavoritesAd() { return idFavoritesAd; }
    public void setIdFavoritesAd(UUID idFavoritesAd) { this.idFavoritesAd= idFavoritesAd; }

    @Override
    public String toString() {
        return "ID: " + getId() +" idAccount: " + getIdAccount() + " idFavoritesAd: " + getIdFavoritesAd();
    }
}
