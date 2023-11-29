package dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The ChatDTO class represents a data transfer object for chat-related information.
 * It implements the Serializable interface for object serialization.
 */
public class ChatDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String message;
    private final String dateTimeOfMessage;
    private File file;
    private byte[] fileContent;

    /**
     * Constructs a ChatDTO object with username, message, and date/time of the message.
     *
     * @param username         The username associated with the message
     * @param message          The content of the message
     * @param dateTimeOfMessage The date and time when the message was sent
     */
    public ChatDTO(String username, String message, Date dateTimeOfMessage) {
        this.username = username;
        this.message = message;
        SimpleDateFormat ft = new SimpleDateFormat("E hh:mm a");
        this.dateTimeOfMessage = ft.format(dateTimeOfMessage);
        this.file = null;
    }

    /**
     * Constructs a ChatDTO object with username, message, date/time, and a file attachment.
     *
     * @param username         The username associated with the message
     * @param message          The content of the message
     * @param dateTimeOfMessage The date and time when the message was sent
     * @param file             The file attached to the message
     */
    public ChatDTO(String username, String message, Date dateTimeOfMessage, File file) {
        this.username = username;
        this.message = message;
        SimpleDateFormat ft = new SimpleDateFormat("E hh:mm a");
        this.dateTimeOfMessage = ft.format(dateTimeOfMessage);
        this.file = file;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the username associated with the message.
     *
     * @return The username as a String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content as a String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the date and time of the message.
     *
     * @return The date and time as a formatted String
     */
    public String getDateTimeOfMessage() {
        return this.dateTimeOfMessage;
    }

    /**
     * Gets the file attached to the message.
     *
     * @return The attached file as a File object
     */
    public File getMessageFile() {
        return this.file;
    }

    /**
     * Gets the content of the attached file as bytes.
     *
     * @return The file content as a byte array
     */
    public byte[] getFileContent() {
        return this.fileContent;
    }
}
