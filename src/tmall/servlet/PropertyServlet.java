package tmall.servlet;


import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.xu_util.Page;
import tmall.xu_util.XuEncodeUtil;

@WebServlet("/PropertyServlet")
public class PropertyServlet extends BaseBackServlet{
	private static final long serialVersionUID = 1L;
	
    public PropertyServlet() {
        super(); 
    }

@Override
public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
	//ǰ�˴���name,cid.���ﲻһ�����ڣ�֮ǰ����list��ʱ�����ҳ�����д�һ��cid��������ûֱ�Ӵ����Ҿ��ÿ���request��һ��������֮���ض��򡣲���
	int cid = Integer.parseInt(request.getParameter("cid"));
	//String name=XuEncodeUtil.getNewString(request.getParameter("name"));
	String name=request.getParameter("name");
	Category c = categoryDAO.get(cid);
	Property p = new Property();
	p.setCategory(c);
	p.setName(name);
	propertyDAO.add(p);
	return "@admin_Property_list?cid="+cid;//����֮���ض������ֱ����������дcid
}

@Override
public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
	int id = Integer.parseInt(request.getParameter("id"));
	int cid = propertyDAO.get(id).getCategory().getId();//�õ���ǰɾ�������Եķ���cid�����ڴ���ɾ��֮����ض���
	propertyDAO.delete(id);
	return "@admin_Property_list?cid="+cid;
}

@Override
public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
	int id = Integer.parseInt(request.getParameter("id"));
	Property p =propertyDAO.get(id);
	request.setAttribute("p", p);
	return "admin/editProperty.jsp";
}

@Override
public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
	String name=request.getParameter("name");
	int cid = Integer.parseInt(request.getParameter("cid"));
	int id = Integer.parseInt(request.getParameter("id"));
	Property p =propertyDAO.get(id);
	p.setName(name);
	propertyDAO.update(p);
	return "@admin_Property_list?cid="+cid;
}

@Override
public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
	int cid = Integer.parseInt(request.getParameter("cid"));
	int total = propertyDAO.getTotal(cid);
	Category c = categoryDAO.get(cid);
	List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
	page.setTotal(total);
	page.setParam("&cid="+cid);
	request.setAttribute("c", c);
    request.setAttribute("ps", ps);
    request.setAttribute("page", page);
    return "admin/listProperty.jsp";//��������ת.Ҫֱ���������ʣ�������ps��page�����Ķ��󣬲�Ȼҳ��û����Ϣ
}

}
