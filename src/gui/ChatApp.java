package gui;
import java.awt.HeadlessException;

public class ChatApp {
    public static void main(String[] args) {
    	try {
    		ChatFrame frame = new ChatFrame();
    		frame.start();
    	} catch(HeadlessException e) {
    		printErrorAndFinish("Program terminated by a HeadlessException in the main() method", e);
    	} catch (Exception e) {
    		printErrorAndFinish("Program terminated by a Generic Exception in the main() method", e);
    	}
    }
    
    private static void printErrorAndFinish(String message, Exception e) {
    	System.out.println("Error message:\t" + message);
    	System.out.println("Exception text:\t" + e.getMessage());
    	System.exit(1);
    }

}
