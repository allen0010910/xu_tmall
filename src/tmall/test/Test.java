package tmall.test;

import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;

public class Test {

	public static void main(String[] args) {
//		Category cc = new Category();
//		cc.setName("tesstddxxxx");
//		CategoryDAO c = new CategoryDAO();
//		c.add(cc);
//		System.out.println(cc.getId());
		
//		PropertyDAO p = new PropertyDAO();
//		Property pp = p.get(83);//�õ�idΪ83�����ԡ�
//		System.out.println(pp.getCategory().getName());//�õ������Եķ��࣬�µ�����
		
//		ProductImageDAO pdao = new ProductImageDAO();
//		System.out.println(pdao.getTotal());
		
//		PropertyDAO p = new PropertyDAO();
//		List<Property> pp = p.list(83,0,5);
//		for(int i=0;i<pp.size();i++) {
//			System.out.println(pp.get(i).getCategory().getName());
//		}
		
//		PropertyValueDAO p = new PropertyValueDAO();
//		List<PropertyValue> pp = p.list(1,4);
//		for(int i=0;i<pp.size();i++) {
//			System.out.println(":"+pp.get(i).getProperty().getName()+":"+pp.get(i).getValue());
//		}
		
//		PropertyValueDAO p = new PropertyValueDAO();
//		System.out.println(p.get(113, 484).getValue());
//		OrderItemDAO o=new OrderItemDAO();
//		System.out.println(o.getSaleCount(91));
		//System.out.println(o.getTotal());
		Product p =new ProductDAO().get(981);
//		List<ProductImage>pisSingle = new ProductImageDAO().list(p, "type_single", 0, 1);
//		if (!pisSingle.isEmpty())
//			p.setFirstProductImage(pisSingle.get(0));
		//ProductImageDAO pdao = new ProductImageDAO();
		//ProductImage pp = pdao.get(10241);
//		int s = p.getFirstProductImage().getId();
//		System.out.println("s="+s);
	}

}
