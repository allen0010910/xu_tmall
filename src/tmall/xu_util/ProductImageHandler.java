package tmall.xu_util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductDAO;

public class ProductImageHandler extends BeanHandler<ProductImage>{
	public ProductImageHandler() {
		super(ProductImage.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public ProductImage handle(ResultSet rs) throws SQLException {
		ProductImage pi= super.handle(rs);//�õ�����ӳ��Ķ���
		Product product = new ProductDAO().get(rs.getInt("pid"));//���ݽ�����е�cid��ȡĿ¼���еĶ���
		pi.setProduct(product);//���ò�����ֱ��ӳ��Ķ���
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
