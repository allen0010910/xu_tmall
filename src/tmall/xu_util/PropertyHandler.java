package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.CategoryDAO;

//���ݿ���е������͵ȼ۵�javabean�������Ʋ����ƣ���ô���ǿ���ͨ��ʹ���Զ����BasicRowProcessor������ӳ������
public class PropertyHandler extends BeanHandler<Property>{

	public PropertyHandler() {
		super(Property.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public Property handle(ResultSet rs) throws SQLException {
		Property p= super.handle(rs);//�õ�����ӳ��Ķ���
		Category category = new CategoryDAO().get(rs.getInt("cid"));//���ݽ�����е�cid��ȡĿ¼���еĶ���
		p.setCategory(category);//���ò�����ֱ��ӳ��Ķ���
		return p;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("name", "name");    
	      //columnsToFieldsMap.put("cid", "category");//�������Ҫ�Զ��崦��ģ��Ͳ��ܳ�����ӳ����
	      return columnsToFieldsMap;
    }
	
}
