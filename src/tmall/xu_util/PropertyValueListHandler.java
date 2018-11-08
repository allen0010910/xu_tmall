package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.dao.ProductDAO;
import tmall.dao.PropertyDAO;

public class PropertyValueListHandler extends BeanListHandler<PropertyValue>{
	//���캯��list�뵥����һ��
	public PropertyValueListHandler() {
		super(PropertyValue.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
	}

	@Override
	public List<PropertyValue> handle(ResultSet rs) throws SQLException {
		List<PropertyValue> p= super.handle(rs);//�õ�����ӳ��Ķ������Ѿ�������һ���ˡ���������ٴ�ʹ����Ҫ���¶���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ;index<p.size();index++) {
			Product product = new ProductDAO().get(rs.getInt("pid"));
            Property property = new PropertyDAO().get(rs.getInt("ptid"));
			p.get(index).setProduct(product);//���ò�����ֱ��ӳ��Ķ���
			p.get(index).setProperty(property);
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
