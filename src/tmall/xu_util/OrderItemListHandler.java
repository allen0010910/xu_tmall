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

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.dao.OrderDAO;
import tmall.dao.ProductDAO;
import tmall.dao.UserDAO;


public class OrderItemListHandler extends BeanListHandler<OrderItem>{
	public OrderItemListHandler() {
		super(OrderItem.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public List<OrderItem> handle(ResultSet rs) throws SQLException {
		List<OrderItem> bean= super.handle(rs);//�õ�����ӳ��Ķ���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ; index<bean.size(); index++) {
			Product product = new ProductDAO().get(rs.getInt("pid"));
			User user = new UserDAO().get(rs.getInt("uid"));
			int oid = rs.getInt("oid");
			if (-1 != oid) {
	            Order order = new OrderDAO().get(oid);
	            bean.get(index).setOrder(order);
	        }
			bean.get(index).setProduct(product);
			bean.get(index).setUser(user);
			rs.next();//�α�����
		}
		return bean;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("number", "number");
	      return columnsToFieldsMap;
    }
}
