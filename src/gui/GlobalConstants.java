package gui;

class GlobalConstants {
	public static final String FELIPE = "Felipe Akira Nozaki | 172885";
	public static final String JULIO = "Julio Morino Anastácio | 173434";
	public static final String LORENA = "Lorena Eduarda Santos Vieira | 178806";
	public static final String MIGUEL = "Miguel Miranda Melo Donanzam | 260851";
	public static final String PEDRO = "Pedro Canegusuco De Mendonça | 223238";

	public static final String FELIPE_IMAGE_PATH = "..\\Online-Chatting-Software\\images\\Felipe.png";
    public static final String JULIO_IMAGE_PATH = "..\\Online-Chatting-Software\\images\\Julio.png";
    public static final String LORENA_IMAGE_PATH = "..\\Online-Chatting-Software\\images\\Lorena.jpeg";
    public static final String MIGUEL_IMAGE_PATH = "..\\Online-Chatting-Software\\images\\Miguel.png";
    public static final String PEDRO_IMAGE_PATH = "..\\Online-Chatting-Software\\images\\Pedro.png";
	
	public static final String SOFTWARE_NAME = "A5 - Online Chatting Software";
	public static final String VERSION = "Ver. 1.0.1";
	public static final String LAST_MODIFIED_DATE = "November 28th, 2023";
	
	public static String getNameVersion() {
		return (SOFTWARE_NAME + " - " + VERSION);
	}
	
	public static String getAuthorsWithImages() {
	    return ("<img src='" + FELIPE_IMAGE_PATH + "' width='50' height='50'>&nbsp;&nbsp;" + FELIPE + "<br>" +
	            "<img src='" + JULIO_IMAGE_PATH + "' width='50' height='50'>&nbsp;&nbsp;" + JULIO + "<br>" +
	            "<img src='" + LORENA_IMAGE_PATH + "' width='50' height='50'>&nbsp;&nbsp;" + LORENA + "<br>" +
	            "<img src='" + MIGUEL_IMAGE_PATH + "' width='50' height='50'>&nbsp;&nbsp;" + MIGUEL + "<br>" +
	            "<img src='" + PEDRO_IMAGE_PATH + "' width='50' height='50'>&nbsp;&nbsp;" + PEDRO + "<br><br>");
	}
	
	public static String getLastModifiedDate() {
		return (LAST_MODIFIED_DATE);
	}
}
