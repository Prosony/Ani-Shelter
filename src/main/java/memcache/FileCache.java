package memcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FileCache {

    private static FileCache instance = new FileCache();
    public static FileCache getInstance(){
        return instance;
    }


    private final Map<String, String> mapBase64ByPath;

    public FileCache(){
        mapBase64ByPath = new ConcurrentHashMap<>();
    }

    /**
     * Add to map new file
     * @param path
     * @param base64
     */
    public void addListFavorites(String path, String base64){
        mapBase64ByPath.put(path, base64);
    }

    public void deleteFileFromCache(String path){
        mapBase64ByPath.remove(path);
    }

    /**
     * Return String base64 file
     * @param path
     * @return string with Base64
     */
    public String getFileByPath(String path){
        return mapBase64ByPath.get(path);
    }

}
