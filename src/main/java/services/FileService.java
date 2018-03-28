package services;
/**
 * @author Prosony
 * @since 0.0.1
 */
import com.google.gson.JsonArray;
import test.TestLog;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {

    private TestLog log = TestLog.getInstance();
    private static final int DEFAULT_BUFFER_SIZE = 102_400; //100kb

    public byte[] getFileByPath(String path){

        if (path != null && !path.isEmpty() && !path.equalsIgnoreCase("null")) {
            File file = new File(path);
            if (file.exists()) {
                log.sendToConsoleMessage("#TEST [class FileService] method[getFileByPath] file name: "+file.getName()+", file exist?: "+file.exists()+", path: "+file.getPath());
                BufferedInputStream input = null;
                byte[] bytes;
                try {
                    bytes = loadFile(file);
                    return bytes;
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    close(input);
//                }
            }
        }
        return null;
    }
    private byte[] loadFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            log.sendToConsoleMessage("#TEST [class FileServlet] method[loadFile] file is too large");
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        inputStream.close();
        return bytes;
    }
    private void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();// Do your thing with the exception. Print it, log it or mail it.
            }
        }
    }

    public JsonArray saveArrayFileOnFS(UUID idAccount, UUID uuid, JsonArray objectJsonImageBase64){

        JsonArray resultArray = new JsonArray();

        boolean isCreated;
        try {
            isCreated = createFolder(idAccount, uuid);
            if (isCreated){
                String path = "E:/file/"+idAccount+"/"+uuid;
                for(int index = 0; index < objectJsonImageBase64.size(); index++){

                    resultArray.add(path+"/"+UUID.randomUUID()+".txt");

                    File newTextFile = new File(resultArray.get(index).getAsString());
                    FileWriter fw = new FileWriter(newTextFile);
                    fw.write(objectJsonImageBase64.get(index).getAsString());
                    fw.close();
                }
                log.sendToConsoleMessage("resultArray: "+resultArray);
                return resultArray;
            }else{
                log.sendToConsoleMessage("#TEST [class AddPostAdServlet] [saveFileOnFS] [ERROR]: Something wrong with path [E:/file/"+idAccount+"/"+uuid+"]");
            }
        } catch (IOException iox) {
            iox.printStackTrace();
        }
        return null;
    }

    private boolean createFolder(UUID idAccount, UUID idPostAd){
        boolean isFirst = false;
        boolean isSecond = false;
        String pathFirst = "E:/file/"+idAccount;
        String pathSecond = "E:/file/"+idAccount+"/"+idPostAd;

        if ( !Files.exists(Paths.get(pathFirst)) ) {
            File folder = new File(pathFirst);
            isFirst = folder.mkdir();
        }else{
            isFirst = true;
        }
        if ( !Files.exists(Paths.get(pathSecond)) ) {
            File folder = new File(pathSecond);
            isSecond = folder.mkdir();
        }else{
            isSecond = true;
        }
        System.out.println("#INFO [AddPostAdServlet] [createFolder] isFirst: "+isFirst+" isSecond: "+isSecond);
        return (isFirst && isSecond);
    }
}
