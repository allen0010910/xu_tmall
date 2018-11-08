package tmall.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import tmall.bean.User;
import tmall.mydb.GetC3p0DB;

public class UserDAO {
	
	public boolean isExist(String name) {
        User user = get(name);
        return user!=null;
    }
	
	public int getTotal() {
        int total = 0;
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "select count(*) from User";
            QueryRunner queryRunner = new QueryRunner();
            total = ((Long)queryRunner.query(conn,sql,new ScalarHandler<>())).intValue();//ScalarHandler����ĳһ��ֵ����ͳ�ƺ�����ֵ
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
	//��ҳ
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }
    
    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<User>();
  
        String sql = "select * from User order by id desc limit ?,? ";
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
            beans = qr.query(conn,sql,new BeanListHandler<User>(User.class) ,start, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }
    
	//==================================��������=========================================================
	public void add(User bean) {
        String sql = "insert into user values(null ,? ,?)";
        try (Connection conn = GetC3p0DB.getConnection(); ) {
        	QueryRunner qr = new QueryRunner();
        	qr.update(conn , sql , bean.getName(), bean.getPassword());
        	//���̰߳�ȫ
        	String id = ( qr.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler<>())).toString();               
        	bean.setId(Integer.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public int update(User bean) {
        String sql = "update user set name= ? , password = ? where id = ? ";
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
        	return qr.update(conn , sql , bean.getName() ,bean.getPassword(), bean.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
	
	public int delete(int id) {
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "delete from User where id = " + id;
            QueryRunner qr = new QueryRunner();
            return qr.update(conn , sql );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
	
	public User get(int id) {
        User bean = null;
        List<Map<String, Object>> Myparams = new ArrayList<Map<String,Object>>();
		Map<String,Object> Myparam = new HashMap<String,Object>();
		Myparam.put("name", "id");
		Myparam.put("rela", "=");
		Myparam.put("value", id);//id�����֣�����Ҫ����
		Myparams.add(Myparam);
		List<User> temp = query(Myparams);
		if(temp.size()>0)bean = temp.get(0);
//        try (Connection conn = GetC3p0DB.getConnection();) {
//            String sql = "select * from User where id = " + id;
//            QueryRunner qr = new QueryRunner();
//            bean = qr.query(conn,sql, new BeanListHandler<User>(User.class)).get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return bean;
    }
	//ע���ʱ����Ҫ�ж�ĳ���û��Ƿ��Ѿ����ڣ��˺������Ƿ���ȷ�Ȳ���
	public User get(String name) {
        User bean = null;
        List<Map<String, Object>> Myparams = new ArrayList<Map<String,Object>>();
		Map<String,Object> Myparam = new HashMap<String,Object>();
		Myparam.put("name", "name");
		Myparam.put("rela", "=");
		Myparam.put("value", "'"+name+"'");//name���ַ�������Ҫ����
		Myparams.add(Myparam);
		List<User> temp = query(Myparams);
		if(temp.size()>0)bean = temp.get(0);
        return bean;
    }
	
	public User get(String name, String password) {
        User bean = null;
        List<Map<String, Object>> Myparams = new ArrayList<Map<String,Object>>();
		Map<String,Object> Myparam = new HashMap<String,Object>();
		Myparam.put("name", "name");
		Myparam.put("rela", "=");
		Myparam.put("value", "'"+name+"'");//name���ַ�������Ҫ����
		Map<String,Object> Myparam2 = new HashMap<String,Object>();
		Myparam2.put("name", "password");
		Myparam2.put("rela", "=");
		Myparam2.put("value", "'"+password+"'");
		Myparams.add(Myparam);
		Myparams.add(Myparam2);
		List<User> temp = query(Myparams);
		if(temp.size()>0)bean = temp.get(0);
        return bean;
    }
	
	//���ڲ�ѯ,�����дaction�㣬��ֱ������������
	private List<User> query(List<Map<String, Object>> params) {
		List<User> result = null;
		StringBuilder sql=new StringBuilder();
		//1=1��Ϊ�˸�andһ���á���Ȼwhereû�ط��ţ�������ϲ����ڣ�ֱ��ִ���������Ҳ����
		sql.append("select * from user where 1=1 ");
		if(params!=null&&params.size()>0){
			for (int i = 0; i < params.size(); i++) {
				Map<String, Object> map=params.get(i);
				sql.append(" and  "+map.get("name")+" "+map.get("rela")+" "+map.get("value")+" ");
			}
		}
		System.out.println(sql.toString());
		//sql.append(special);//Ϊ�˷�ҳ��ѯ����Ҫ��sql����β����limit 0,3�������ַ���ѡ���ҳ������
		try (Connection conn = GetC3p0DB.getConnection();) {
            QueryRunner qr = new QueryRunner();
            result = qr.query(conn,sql.toString(), new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
}
