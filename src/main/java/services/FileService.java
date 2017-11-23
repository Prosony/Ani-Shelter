package services;
/**
 * @author Prosony
 * @since 0.0.1
 */
import test.TestLog;
import java.io.*;

public class FileService {

    private TestLog testLog = TestLog.getInstance();
    private static final int DEFAULT_BUFFER_SIZE = 102_400; //100kb

    public byte[] getFileByPath(String path){

        if (path != null && !path.isEmpty() && !path.equalsIgnoreCase("null")) {
            File file = new File(path);
            if (file.exists()) {
                testLog.sendToConsoleMessage("#TEST [class FileService] method[getFileByPath] file name: "+file.getName()+", file exist?: "+file.exists()+", path: "+file.getPath());
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
            testLog.sendToConsoleMessage("#TEST [class FileServlet] method[loadFile] file is too large");
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
}
