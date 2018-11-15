package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.dao.CategoryDAO;

public class ProductHandler extends BeanHandler<Product>{
	public ProductHandler() {
		super(Product.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
	}
	
	@Override
	public Product handle(ResultSet rs) throws SQLException {
		Product bean= super.handle(rs);//�õ�����ӳ��Ķ���
		Category category = new CategoryDAO().get(rs.getInt("cid"));
		Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
		bean.setCategory(category);
		bean.setCreateDate(createDate);
		return bean;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("name", "name");
	      columnsToFieldsMap.put("subTitle", "subTitle");    
	      columnsToFieldsMap.put("orignalPrice", "orignalPrice");    
	      columnsToFieldsMap.put("promotePrice", "promotePrice");    
	      columnsToFieldsMap.put("stock", "stock");    
	      return columnsToFieldsMap;
    }
	
}
