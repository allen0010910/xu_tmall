package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.CategoryDAO;

public class PropertyListHandler extends BeanListHandler<Property>{
	public PropertyListHandler() {
		super(Property.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public List<Property> handle(ResultSet rs) throws SQLException {
		List<Property> p= super.handle(rs);//�õ�����ӳ��Ķ������Ѿ�������һ���ˡ���������ٴ�ʹ����Ҫ���¶���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ;index<p.size();index++) {
			Category category = new CategoryDAO().get(rs.getInt("cid"));//���ݽ�����е�cid��ȡĿ¼���еĶ���
			p.get(index).setCategory(category);//���ò�����ֱ��ӳ��Ķ���
			rs.next();//�α�����
		}
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
