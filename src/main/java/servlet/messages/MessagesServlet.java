package servlet.messages;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.JsonWebTokenCache;
import model.account.Account;
import test.TestLog;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/messages-socket/{token}")
public class MessagesServlet {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session peer, @PathParam("token") String token){
        testLog.sendToConsoleMessage("token : "+token);
//       , HandshakeRequest request, HandshakeResponse response
//        testLog.sendToConsoleMessage("#INFO [SOCKET] peer: "+ peer);
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getQueryString: "+request.getQueryString());
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getHeaders: "+request.getHeaders());
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getParameterMap: "+request.getParameterMap());
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getHttpSession: "+request.getHttpSession());
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getRequestURI: "+request.getRequestURI());
//        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getUserPrincipal: "+request.getUserPrincipal());
//        System.out.println("Open Connection ... incomingToken: "+incomingToken+" outcomingToken: "+outcomingToken);
        peers.add(peer);
    }


    @OnClose
    public void onClose(Session peer){
        System.out.println("Connection with user: "+peer+" is closed!");
        peers.remove(peer);
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }

    @OnMessage
    public String onMessage(String message, Session session){
        testLog.sendToConsoleMessage("#INFO [SOCKET] message from the client: " + message);
        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject)jsonParser.parse(message);
        String token = object.get("outcoming_token").getAsString();
        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) {
                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
            }
        }
        return message;
    }



}




//@ServerEndpoint("/messages-socket")
//public class MessagesServlet {
//
//    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
//    private TestLog testLog = TestLog.getInstance();
//
//    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
//
//    @OnOpen
//    public void onOpen(Session peer, @PathParam("token") String token){
//        testLog.sendToConsoleMessage("token : "+token);
////       , HandshakeRequest request, HandshakeResponse response
////        testLog.sendToConsoleMessage("#INFO [SOCKET] peer: "+ peer);
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getQueryString: "+request.getQueryString());
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getHeaders: "+request.getHeaders());
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getParameterMap: "+request.getParameterMap());
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getHttpSession: "+request.getHttpSession());
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getRequestURI: "+request.getRequestURI());
////        testLog.sendToConsoleMessage("#INFO [SOCKET] request.getUserPrincipal: "+request.getUserPrincipal());
////        System.out.println("Open Connection ... incomingToken: "+incomingToken+" outcomingToken: "+outcomingToken);
//        peers.add(peer);
//    }
//
//
//    @OnClose
//    public void onClose(Session peer){
//        System.out.println("Connection with user: "+peer+" is closed!");
//        peers.remove(peer);
//    }
//
//    @OnError
//    public void onError(Throwable e){
//        e.printStackTrace();
//    }
//
//    @OnMessage
//    public String onMessage(String message, Session session){
//        testLog.sendToConsoleMessage("#INFO [SOCKET] message from the client: " + message);
//        JsonParser jsonParser = new JsonParser();
//        JsonObject object = (JsonObject)jsonParser.parse(message);
//        String token = object.get("outcoming_token").getAsString();
//        if (token != null && !token.isEmpty() && !token.equals("null")) {
//            Account account = tokenCache.getAccountByJws(token);
//            if (account != null) {
//                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
//            }
//        }
//        return message;
//    }
//
//}
//
//
