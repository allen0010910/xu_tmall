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
import tmall.bean.User;
import tmall.dao.UserDAO;

public class OrderListHandler extends BeanListHandler<Order>{
	public OrderListHandler() {
		super(Order.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public List<Order> handle(ResultSet rs) throws SQLException {
		List<Order> bean= super.handle(rs);//�õ�����ӳ��Ķ���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ; index<bean.size(); index++) {
			Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
	        Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
	        Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
	        Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
	        User user = new UserDAO().get(rs.getInt("uid"));
	        bean.get(index).setCreateDate(createDate);
	        bean.get(index).setPayDate(payDate);
	        bean.get(index).setDeliveryDate(deliveryDate);
	        bean.get(index).setConfirmDate(confirmDate);
	        bean.get(index).setUser(user);
			rs.next();//�α�����
		}
		return bean;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("orderCode", "orderCode");
	      columnsToFieldsMap.put("address", "address");    
	      columnsToFieldsMap.put("post", "post");    
	      columnsToFieldsMap.put("receiver", "receiver");    
	      columnsToFieldsMap.put("mobile", "mobile");    
	      columnsToFieldsMap.put("userMessage", "userMessage");    
	      columnsToFieldsMap.put("status", "status");  
	      columnsToFieldsMap.put("id", "id");    
	      //columnsToFieldsMap.put("cid", "category");//�������Ҫ�Զ��崦��ģ��Ͳ��ܳ�����ӳ����
	      return columnsToFieldsMap;
    }
}
