package test;

/**
 *
 * @author Prosony
 * @since 0.2.4
 */
public class TestLog {

    private static TestLog instance = new TestLog();
    public static TestLog getInstance(){
        return instance;
    }

    private CheckProperties checkProperties = CheckProperties.getInstance();
    private boolean isTest = checkProperties.getProperties();

    public void sendToConsoleMessage(String message){
            if (isTest){
                System.out.println(message);
            }
    }
}
