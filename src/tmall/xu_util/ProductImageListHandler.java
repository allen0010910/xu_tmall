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
import tmall.bean.ProductImage;
import tmall.dao.ProductDAO;

public class ProductImageListHandler extends BeanListHandler<ProductImage> {
	public ProductImageListHandler() {
		super(ProductImage.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public List<ProductImage> handle(ResultSet rs) throws SQLException {
		List<ProductImage> pi= super.handle(rs);//�õ�����ӳ��Ķ���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ; index<pi.size(); index++) {
			Product product = new ProductDAO().get(rs.getInt("pid"));//���ݽ�����е�pid��ȡĿ¼���еĶ���
			pi.get(index).setProduct(product);//���ò�����ֱ��ӳ��Ķ���
			rs.next();//�α�����
		}
		return pi;
	}
	//���ݸú����е���ӳ��
	public static Map<String, String> mapColumnsToFields() {
	      Map<String, String> columnsToFieldsMap = new HashMap<>();
	      columnsToFieldsMap.put("id", "id");
	      columnsToFieldsMap.put("type", "type");    
	      //columnsToFieldsMap.put("pid", "category");//�������Ҫ�Զ��崦��ģ��Ͳ��ܳ�����ӳ����
	      return columnsToFieldsMap;
	}
}
