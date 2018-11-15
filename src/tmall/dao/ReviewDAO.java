package tmall.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import tmall.bean.Review;
import tmall.mydb.GetC3p0DB;
import tmall.xu_util.DateUtil;
import tmall.xu_util.ReviewHandler;
import tmall.xu_util.ReviewListHandler;

//��Ҫ����һ�£����ڵ�ת����
public class ReviewDAO {
	
    public int getTotal() {
        int total = 0;
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "SELECT count(*) FROM `Review`";
            QueryRunner queryRunner = new QueryRunner();
            total = ((Long)queryRunner.query(conn,sql,new ScalarHandler<>())).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return total;
    }

    public int getTotal(int pid) {
        int total = 0;
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "SELECT count(*) FROM `Review` WHERE `pid` = " + pid;
            QueryRunner queryRunner = new QueryRunner();
            total = ((Long)queryRunner.query(conn,sql,new ScalarHandler<>())).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return total;
    }
	//��ʱ����Ϊ����ȽϿ����û����ύ���۵�ʱ�򣬷���û���ύ�ɹ����ͻ��ε���ύ��ť�������ᵼ��ͬһ�����������ύ�˶�Ρ�
    //Ϊ����ҵ���ϱ�����������ķ������ڲ������ݿ�֮ǰ��ʹ��isExist���ж������Ƿ��Ѿ�������
    public boolean isExist(String content, int pid) {
        String sql = "SELECT * FROM `Review` WHERE `content` = ? AND `pid` = ?";
        try (Connection conn = GetC3p0DB.getConnection();) {
            QueryRunner qr = new QueryRunner();
            if(qr.query(conn,sql,new ReviewHandler(), content, pid)!=null)return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return false;
    }
    
    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }
    
    public List<Review> list(int pid, int start, int count) {
        List<Review> beans = new ArrayList<>();
        String sql = "SELECT * FROM `Review` WHERE `pid` = ? ORDER BY `id` DESC LIMIT ?, ? ";
        try (Connection conn = GetC3p0DB.getConnection();) {
            QueryRunner qr = new QueryRunner();
            beans = qr.query(conn ,sql ,new ReviewListHandler() ,pid ,start ,count);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return beans;
    }
	//=====================��������============================================
	public void add(Review bean) {
        String sql = "INSERT INTO `Review` VALUES(NULL, ?, ?, ?, ?)";
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
        	qr.update(conn,sql,bean.getContent(),bean.getUser().getId(),bean.getProduct().getId(),DateUtil.d2t(bean.getCreateDate()));
        	//���̰߳�ȫ
        	String id = ( qr.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler<>())).toString();               
        	bean.setId(Integer.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
    }
	
	public int update(Review bean) {
        String sql = "UPDATE `Review` SET `content` = ?, `uid` = ?, `pid` = ?, `createDate` = ? " +
                "WHERE id = ?";
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
        	return qr.update(conn , sql , bean.getContent() ,bean.getUser().getId(), bean.getProduct().getId(),
        			DateUtil.d2t(bean.getCreateDate()), bean.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return 0;
    }
	
	public int delete(int id) {
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "DELETE FROM `Review` WHERE `id` = " + id;
            QueryRunner qr = new QueryRunner();
            return qr.update(conn , sql );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return 0;
    }
	
	public Review get(int id) {
        Review bean = new Review();
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "SELECT * FROM `Review` WHERE `id` = " + id;
            QueryRunner qr = new QueryRunner();
            bean = qr.query(conn,sql,new ReviewHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return bean;
    }
}
