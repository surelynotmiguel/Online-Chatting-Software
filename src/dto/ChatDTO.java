package dto;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String username;
    public final String message;
    public final String dateTimeOfMessage;
    public File file;

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
}
