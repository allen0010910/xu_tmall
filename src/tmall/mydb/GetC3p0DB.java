package tmall.mydb;

import java.sql.Connection;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GetC3p0DB {
	//c3p0.xml���ֱ���Ϊ�Ǹ�������Ҫ����src�ļ�����
	private static ComboPooledDataSource dataSource = null;
	// ���ݿ����ӳ�Ӧֻ����ʼ��һ��.
    static {
        dataSource = new ComboPooledDataSource("tmall");
    	//dataSource = new ComboPooledDataSource();//ʹ��Ĭ�ϵ�
    }
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
	
    
}
