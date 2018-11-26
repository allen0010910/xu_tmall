package tmall.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.xu_util.Page;
import tmall.xu_util.XuEncodeUtil;

/**
 * Servlet implementation class ForeServlet
 */
@WebServlet("/ForeServlet")
public class ForeServlet extends BaseForeServlet {
	private static final long serialVersionUID = 1L;
       
    //categoryMenu.jsp��Ҫcs���Ҿ�����Ҫ��ȱ��Ϣ��cs�����ˣ��������cs��������Ϣ����
    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
    	List<Category> cs = new CategoryDAO().list();
        new ProductDAO().fill(cs);
        new ProductDAO().fillByRow(cs);
        request.setAttribute("cs", cs);
    	return "home.jsp";
    }
    
    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
    	String name = XuEncodeUtil.getNewString(request.getParameter("name"));
        String password = request.getParameter("password");
        name = HtmlUtils.htmlEscape(name);//java��̨��ǰ������������ַ�����ת��
        if(userDAO.isExist(name)) {
        	request.setAttribute("msg", "�û����Ѿ���ʹ��,����ʹ��");
        	return "register.jsp";
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDAO.add(user);
    	return "@registerSuccess.jsp";//�϶�Ҫ�ض��򰡣���һ��mse��ô��
    }
    
    public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
    	String name = XuEncodeUtil.getNewString(request.getParameter("name"));
    	String password = request.getParameter("password");
    	name = HtmlUtils.htmlEscape(name);//java��̨��ǰ������������ַ�����ת��
    	User user = userDAO.get(name, password);
    	if(user == null) {
    		request.setAttribute("msg", "�������򲻴��ڸ��û�");
    		return "login.jsp";
    	}
    	//getSession()��
    	//����Session�д�ȡ��¼��Ϣʱ��һ�㽨�飺HttpSession session =request.getSession();
    	//����Session�л�ȡ��¼��Ϣʱ��һ�㽨�飺HttpSession session =request.getSession(false);
    	request.getSession().setAttribute("user", user);//�������û��ˣ���˽�����һ��ҳ���ʱ��ǰ̨���������õ�����û���
    	return "@forehome";//�������home.jsp��ԭ����Ҫ�ض�����
    }
    
    public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
        request.getSession().removeAttribute("user");
        return "@forehome";
    }
    //product,review,provertyvalue.���ڻ�������û��
    public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int pid= Integer.parseInt(request.getParameter("pid"));
    	Product p = productDAO.get(pid);
    	List<Review> reviews = reviewDAO.list(pid);
    	List<PropertyValue> pvs = propertyValueDAO.list(pid);
    	//��ȡ����productû�г�ʼ��ͼƬ�����һ��firstͼƬ����û�����е�ͼƬ
    	List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
        List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
    	request.setAttribute("p", p);
    	request.setAttribute("reviews", reviews);
    	request.setAttribute("pvs", pvs);
    	return "product.jsp";
    }
    
    public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
    	return "productsBySearch.jsp";
    }
    
    //
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page){
    	//����д�Ľ�����һ��ǿ��ת������û��
    	if(request.getSession().getAttribute("user")==null)return "%fail";//ʧ�ܺ�ִ��$("#loginModal").modal('show');���modal������Ҫ�ͽ�jsp������
    	else return "%success";
    }
    
    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
    	return "";
    }
    
    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	name = HtmlUtils.htmlEscape(name);
    	User user = userDAO.get(name, password);
    	if(user == null) {
    		return "%fail";
    	}
    	request.getSession().setAttribute("user", user);
    	return "%success";
    }

    
}
