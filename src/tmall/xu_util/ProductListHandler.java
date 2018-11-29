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

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductImageDAO;


public class ProductListHandler extends BeanListHandler<Product>{
	public ProductListHandler() {
		super(Product.class, new BasicRowProcessor(new BeanProcessor(mapColumnsToFields())));
		
	}

	@Override
	public List<Product> handle(ResultSet rs) throws SQLException {
		List<Product> bean= super.handle(rs);//�õ�����ӳ��Ķ���
		rs.first();//���¶���rs���αꡣ�ٴζ�ȡʹ��
		for(int index = 0 ; index<bean.size(); index++) {
			Category category = new CategoryDAO().get(rs.getInt("cid"));
			Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
			bean.get(index).setCategory(category);
			bean.get(index).setCreateDate(createDate);
			if(bean.get(index).getFirstProductImage()==null) {
				List<ProductImage>pisSingle = new ProductImageDAO().list(bean.get(index), "type_single", 0, 1);
				if (!pisSingle.isEmpty())
					bean.get(index).setFirstProductImage(pisSingle.get(0));
			}
			rs.next();//�α�����
		}
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
	      //columnsToFieldsMap.put("cid", "category");//�������Ҫ�Զ��崦��ģ��Ͳ��ܳ�����ӳ����
	      return columnsToFieldsMap;
    }
	
}
