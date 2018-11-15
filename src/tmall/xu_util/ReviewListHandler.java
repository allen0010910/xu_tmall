package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.dao.ProductDAO;
import tmall.dao.UserDAO;

public class ReviewListHandler extends BeanListHandler<Review>{
	public ReviewListHandler() {
		super(Review.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
	}
	
	@Override
	public List<Review> handle(ResultSet rs) throws SQLException {
		List<Review> p= super.handle(rs);//�õ�����ӳ��Ķ������Ѿ�������һ���ˡ���������ٴ�ʹ����Ҫ���¶���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ;index<p.size();index++) {
			Product product = new ProductDAO().get(rs.getInt("pid"));
	        User user = new UserDAO().get(rs.getInt("uid"));
	        Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
			p.get(index).setProduct(product);//���ò�����ֱ��ӳ��Ķ���
			p.get(index).setUser(user);
			p.get(index).setCreateDate(createDate);
			rs.next();//�α�����
		}
		return p;
	}
	//���ݸú����е���ӳ�䣬��һ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("value", "value");    
	      //columnsToFieldsMap.put("cid", "category");//�������Ҫ�Զ��崦��ģ��Ͳ��ܳ�����ӳ����
	      return columnsToFieldsMap;
    }
}
