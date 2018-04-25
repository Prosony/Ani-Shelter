package services;
/**
 * @author Prosony
 * @since 0.0.1
 */
import com.google.gson.JsonArray;
import junit.framework.Assert;
import test.CheckProperties;
import test.TestLog;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class FileService {

    private TestLog log = TestLog.getInstance();
    private static final int DEFAULT_BUFFER_SIZE = 102_400; //100kb
    private String pathFile;

    public FileService(){
        pathFile = CheckProperties.getInstance().getPathFile();
    }

    public String getFileByPath(String path){

        if (path != null && !path.isEmpty() && !path.equalsIgnoreCase("null")) {
            File file = new File(path);
            if (file.exists()) {
                log.sendToConsoleMessage("#TEST [FileService][getFileByPath] file name: "+file.getName()+", file exist?: "+file.exists()+", path: "+file.getPath());
                BufferedInputStream input = null;
                byte[] bytes;

                    bytes = loadFile(file, path);
                    if (bytes != null){
                        log.sendToConsoleMessage("#SUCCESS [FileService][getFileByPath]");
                        return new String(bytes);
                    }else{

                    }



//                try {
//                    input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
        return null;
    }

    private byte[] loadFile(File file, String path){
        try {
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                log.sendToConsoleMessage("#INFO [class FileServlet][loadFile] file is too large");
            }
            byte[] buffer = new byte[(int)length]; // Use this for reading the data.

            FileInputStream inputStream = new FileInputStream(file);

            int total = 0;
            int nRead = 0;
            while((nRead = inputStream.read(buffer)) != -1) {
//                System.out.println(new String(buffer));
                total += nRead;
            }
            inputStream.close();
            System.out.println("Read " + total + " bytes");
            return buffer;
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + path + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + path + "'");
        }
        return null;
    }

    private byte[] loadFile(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                log.sendToConsoleMessage("#INFO [class FileServlet][loadFile] file is too large");
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
        }
        return null;
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
                StringBuilder builder = new StringBuilder();
//                builder.append("E:/file/");
                builder.append(pathFile);
                builder.append(idAccount);
                builder.append("/");
                builder.append(uuid);
                String path = builder.toString();
                System.out.println("");
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
                log.sendToConsoleMessage("#INFO [class AddPostAdServlet][saveFileOnFS] [ERROR]: Something wrong with path");
            }
        } catch (IOException iox) {
            iox.printStackTrace();
        }
        return null;
    }

    private boolean createFolder(UUID idAccount, UUID idPostAd){
        boolean isFirst = false;
        boolean isSecond = false;
        String pathFirst = pathFile+idAccount;
        String pathSecond = pathFile+idAccount+"/"+idPostAd;

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
        System.out.println("#INFO [AddPostAdServlet][createFolder] isFirst: "+isFirst+" isSecond: "+isSecond);
        return (isFirst && isSecond);
    }
}
