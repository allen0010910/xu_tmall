package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.dao.ProductDAO;
import tmall.dao.UserDAO;

public class ReviewHandler extends BeanHandler<Review>{
	public ReviewHandler() {
		super(Review.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
	}

	@Override
	public Review handle(ResultSet rs) throws SQLException {
		Review pv= super.handle(rs);//�õ�����ӳ��Ķ���
		Product product = new ProductDAO().get(rs.getInt("pid"));
        User user = new UserDAO().get(rs.getInt("uid"));
        Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
		pv.setProduct(product);//���ò�����ֱ��ӳ��Ķ���
		pv.setUser(user);
		pv.setCreateDate(createDate);
		return pv;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("content", "content");    
	      return columnsToFieldsMap;
    }

}
