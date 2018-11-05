package tmall.mydb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBconfig {
	private static  String URL;
	private static  String DRIVER;
	private static  String USER;
	private static  String PASSWORD;
	
	static {
		init();
	}
	
	private static void init(){
		//�����������ʵ�־�̬�����ĳ�ʼ��
		//DBconfig.class �õ�����class DBconfig,����������ڶ�̬������,������߶�дЧ��.
		InputStream in = DBconfig.class.getClassLoader().getResourceAsStream("mydb/db.properties");
		//�������properties��Դ���룬�̳���hashtable�̰߳�ȫ����hashtableʵ��map��ֵ��
		Properties properties = new Properties();
		//�����õ�������Ϣ�Ϳ��Զ�ȡ����������
		try {
			properties.load(in);
			URL = properties.getProperty("URL");
			DRIVER = properties.getProperty("DRIVER");
			USER = properties.getProperty("USER");
			PASSWORD = properties.getProperty("PASSWORD");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static String getDRIVER() {
		return DRIVER;
	}

	public static void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
	}

	public static String getUSER() {
		return USER;
	}

	public static void setUSER(String uSER) {
		USER = uSER;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
}
