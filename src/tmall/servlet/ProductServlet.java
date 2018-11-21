package tmall.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.xu_util.Page;
import tmall.xu_util.XuEncodeUtil;


@WebServlet("/ProductServlet")
public class ProductServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;
       

    public ProductServlet() {}


	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name=XuEncodeUtil.getNewString(request.getParameter("name"));
		String subTitle=XuEncodeUtil.getNewString(request.getParameter("subTitle"));
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		Product p =new Product();
		p.setCategory(categoryDAO.get(cid));
		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		productDAO.add(p);
		propertyValueDAO.init(p);
		return "@admin_Product_list?cid="+cid;
	}


	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		int cid = productDAO.get(id).getCategory().getId();
		productDAO.delete(id);
		return "@admin_Product_list?cid="+cid;//�����ͼƬ�Ļ�����ɾ����
	}


	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";
	}


	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name=XuEncodeUtil.getNewString(request.getParameter("name"));
		String subTitle=XuEncodeUtil.getNewString(request.getParameter("subTitle"));
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		Product p = productDAO.get(id);
		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		productDAO.update(p);
		return "@admin_Product_list?cid="+cid;//���������Լ�����ֵӦ�ò����ǰɣ������濼�ǣ�
	}


	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c=  categoryDAO.get(cid);
		List<Product>ps = productDAO.list(cid, page.getStart(), page.getCount());
		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+cid);
		request.setAttribute("c", c);
		request.setAttribute("ps", ps);
        request.setAttribute("page", page);
		return "admin/listProduct.jsp";
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(pid);
		List<PropertyValue> pvs = propertyValueDAO.list(pid);
		//propertyValueDAO.init(p);//�������һ����Ʒ����ʹ����������Ļ��������ݿ��в��Ҹ��ݲ�Ʒpid����propertyValue��ֵ���ᷢ���ǿյġ������������Ʒ��ȥ�������ԣ��ᷢ���ǿյģ�
		//����ʱ���ݿ���ȴ����������ֵ��Ӧ����������ٽ�ȥ��ҳ���������ֵ�ˡ����Ҿ���Ӧ���½���ʱ��ͳ�ʼ��,��
		request.setAttribute("p", p);
		request.setAttribute("pvs", pvs);
		return "admin/editProductValue.jsp";
	}
	
	//��������첽�ύ��ʽ���༭���޸�,�޸ĳɹ�����ɫ�߿��ʾ.���Ҳ���ÿ�α༭������һ�����ݿ�ɣ��༭�ɹ���Ӧ�÷���ajax�����ݣ����Ӧ����%��ͷ
	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = XuEncodeUtil.getNewString(request.getParameter("value"));//ajax��Ȼ����
		PropertyValue pv = propertyValueDAO.get(pvid);
        pv.setValue(value);
        propertyValueDAO.update(pv);
		return "%success";
	}

}
