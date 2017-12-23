package memcach;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSocketCache {

    private static MessageSocketCache instance = new MessageSocketCache();
    public static MessageSocketCache getInstance() {
        return instance;
    }

    private  Map<Session, UUID> mapIdAccountBySessionSocket =  new ConcurrentHashMap<>();

    public void addAccount(Session session, UUID idAccount){
        mapIdAccountBySessionSocket.put(session, idAccount);
    }
    public UUID getAccountBySessionSocket(Session session) {
        return mapIdAccountBySessionSocket.get(session);
    }
    public Set<Session> getAllSession(){
        return mapIdAccountBySessionSocket.keySet();
    }
    public void deleteAccount(Session session){
        mapIdAccountBySessionSocket.remove(session);
    }
    public void printAllOnline(){
        Set<Session> list = getAllSession();
        for (Session session : list){
            System.out.println("#INFO [MessageSocketCache] [printAllOnline] session"+ session +" idAccoount: "+mapIdAccountBySessionSocket.get(session));
        }

    }
}
