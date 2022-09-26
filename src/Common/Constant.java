package Common;

public final class Constant {
	// Define general information to access test application
	public static String BASE_URL = "http://inspections.oceantech.vn/session/signin";
	// User name and password to access website
	public static String BASE_EMAIL = "admin";
	public static String BASE_PASSWORD = "admin";

	// Define timeout
	public static final double WAIT_INTERVAL = 0.5; 
	public static final int WAIT_WINDOW = 10;
	public static final double WAIT_CLOSE_WINDOW = 2.5;
	public static final double WAIT_CLOSE_WINDOW_MAX = 3.5;
	public static final int WAIT_ELEMENT_EXIST = 10;
	public static final int WAIT_ELEMENT_NOT_EXIST = 2;
	public static final int WAIT_NETWORK = 10;
	public static final double WAIT_REFRESH_SCREEN = 2.0;
	public static final int WAIT_CLICKABLE = 20;
	public static final double WAIT_LOAD_SCREEN = 10.0;

	// Define threshold
	public static final double SIMILITY_THRESHOLD = 0.99;

	// Define browser
	public static final String IE_BROWSER = "IE";
	public static final String EDGE_BROWSER = "Edge";
	public static final String CHROME_BROWSER = "Chrome";
	public static final String FIREFOX_BROWSER = "FireFox";
	public static final String MSEDGE_BROWSER = "MSEdge";

	// Path to storage
	public static String IMAGE_PATH = "./images/";
	public static String DATA_PATH = "./data/";

}
