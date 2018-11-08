package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.dao.ProductDAO;
import tmall.dao.PropertyDAO;

public class PropertyValueHandler extends BeanHandler<PropertyValue>{
	public PropertyValueHandler() {
		super(PropertyValue.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
	}

	@Override
	public PropertyValue handle(ResultSet rs) throws SQLException {
		PropertyValue pv= super.handle(rs);//�õ�����ӳ��Ķ���
		Product product = new ProductDAO().get(rs.getInt("pid"));
        Property property = new PropertyDAO().get(rs.getInt("ptid"));
		pv.setProduct(product);//���ò�����ֱ��ӳ��Ķ���
		pv.setProperty(property);
		return pv;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("value", "value");    
	      return columnsToFieldsMap;
    }
	
}
