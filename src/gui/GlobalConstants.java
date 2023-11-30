package gui;

/**
 * The GlobalConstants class holds constant values used throughout the application.
 */
class GlobalConstants {
    public static final String FELIPE = "Felipe Akira Nozaki | 172885";
    public static final String JULIO = "Julio Morino Anastácio | 173434";
    public static final String LORENA = "Lorena Eduarda Santos Vieira | 178806";
    public static final String MIGUEL = "Miguel Miranda Melo Donanzam | 260851";
    public static final String PEDRO = "Pedro Canegusuco De Mendonça | 223238";

    public static final String FELIPE_IMAGE_PATH = "resources\\images\\Felipe.png";
    public static final String JULIO_IMAGE_PATH = "resources\\images\\Julio.jpg";
    public static final String LORENA_IMAGE_PATH = "resources\\images\\Lorena.jpeg";
    public static final String MIGUEL_IMAGE_PATH = "resources\\images\\Miguel.png";
    public static final String PEDRO_IMAGE_PATH = "resources\\images\\Pedro.jpg";

    public static final String SOFTWARE_NAME = "A5 - Online Chatting Software";
    public static final String VERSION = "Ver. 1.1.2";
    public static final String LAST_MODIFIED_DATE = "November 30th, 2023";

    /**
     * Returns the name and version of the software.
     *
     * @return The software name and version as a String
     */
    public static String getNameVersion() {
        return (SOFTWARE_NAME + " - " + VERSION);
    }

    /**
     * Returns the last modified date of the software.
     *
     * @return The last modified date as a String
     */
    public static String getLastModifiedDate() {
        return (LAST_MODIFIED_DATE);
    }
}
