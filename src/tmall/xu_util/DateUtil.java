package tmall.xu_util;

//��Ҫ������java.util.Date����java.sql.Timestamp ��Ļ���ת����
//MySQL�е����ڸ�ʽ�ﱣ��ʱ����Ϣ������ʹ��datetime���͵��ֶΣ�
//��jdbcҪ��ȡdatetime�����ֶε���Ϣ����Ҫ����java.sql.Timestamp����ȡ������ֻ�ᱣ��������Ϣ������ʧʱ����Ϣ��
public class DateUtil {
	public static java.sql.Timestamp d2t(java.util.Date d) {
        if (null == d)
            return null;
        return new java.sql.Timestamp(d.getTime());
    }
 
    public static java.util.Date t2d(java.sql.Timestamp t) {
        if (null == t)
            return null;
        return new java.util.Date(t.getTime());
    }
}
