package dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String username;
    private final String message;
    private final String dateTimeOfMessage;
    private File file;
    private byte[] fileContent;

    public ChatDTO(String username, String message, Date dateTimeOfMessage){
        this.username = username;
        this.message = message;
        SimpleDateFormat ft = new SimpleDateFormat ("E hh:mm a");
        this.dateTimeOfMessage = ft.format(dateTimeOfMessage);
        this.file = null;
    }
    
    public ChatDTO(String username, String message, Date dateTimeOfMessage, File file){
        this.username = username;
        this.message = message;
        SimpleDateFormat ft = new SimpleDateFormat ("E hh:mm a");
        this.dateTimeOfMessage = ft.format(dateTimeOfMessage);
        this.file = file;
        try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public String getUsername(){
        return this.username;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDateTimeOfMessage(){
        return this.dateTimeOfMessage;
    }
    
    public File getMessageFile() {
    	return this.file;
    }
    
    public byte[] getFileContent() {
    	return this.fileContent;
    }
}
