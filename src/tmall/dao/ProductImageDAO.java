package tmall.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.mydb.GetC3p0DB;
import tmall.xu_util.ProductImageHandler;
import tmall.xu_util.ProductImageListHandler;

//��ƷͼƬ�����ǰ���productImage.id.png�ķ�ʽ����ģ������ͽ�����ļ��������⣬�������ά���ļ�����Ϣ�����Ҳ����ظ��� 
public class ProductImageDAO {
	//����ͨ���������bean� �ڽ϶ೡ��bean�����Զ����ɣ��������bean����Զ����ɵ�ʱ�򣬾ͱ������ˡ�bean����������ˬ
	public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
    
    public int getTotal() {
        int total = 0;
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "SELECT count(*) FROM `ProductImage`";
            QueryRunner queryRunner = new QueryRunner();
            total = ((Long)queryRunner.query(conn,sql,new ScalarHandler<>())).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return total;
    }
    
    public List<ProductImage> list(Product p, String type) {
        return list(p, type, 0, Short.MAX_VALUE);
    }

    public List<ProductImage> list(Product p, String type, int start, int count) {
        List<ProductImage> beans = new ArrayList<ProductImage>();
        String sql = "SELECT * FROM `ProductImage` WHERE `pid` = ? AND `type` = ? ORDER BY `id` " +
                "DESC LIMIT ?, ? ";
        
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
            beans = qr.query(conn,sql,new ProductImageListHandler(),p.getId(),type,start,count);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return beans;
    }
    
	//==================================��������(ûд����,��Ϊҵ���ϲ���Ҫ)=========================================================
	public void add(ProductImage bean) {
        String sql = "INSERT INTO `ProductImage` VALUES(NULL, ?, ?)";
        try (Connection conn = GetC3p0DB.getConnection();) {
        	QueryRunner qr = new QueryRunner();
        	qr.update(conn , sql , bean.getProduct().getId(), bean.getType());
        	//���̰߳�ȫ
        	String id = ( qr.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler<>())).toString();               
        	bean.setId(Integer.valueOf(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
    }
	
	public int delete(int id) {
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "DELETE FROM `ProductImage` WHERE `id` = " + id;
            QueryRunner qr = new QueryRunner();
            return qr.update(conn , sql );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return 0;
    }
	
	public ProductImage get(int id) {
        ProductImage bean = new ProductImage();
        try (Connection conn = GetC3p0DB.getConnection();) {
            String sql = "SELECT * FROM `ProductImage` WHERE `id` = " + id;
            QueryRunner qr = new QueryRunner();
            //����ѯ�õ��Ľ�������ľ������Բ�һ��ʱӦ����ô���
            bean = qr.query(conn,sql, new ProductImageHandler());//ʹ���Զ�������Դ����ߡ�
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			e1.printStackTrace();
		}
        return bean;
    }
}
