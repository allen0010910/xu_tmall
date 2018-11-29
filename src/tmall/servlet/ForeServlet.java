package tmall.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.OrderItem;
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
    	//String keyword = XuEncodeUtil.getNewString(request.getParameter("keyword"));
    	String keyword =request.getParameter("keyword");//���ύ��ʽ��Ϊget�ˡ�û�취����
    	List<Product> ps = productDAO.search(keyword, 0, 20);
    	//productDAO.setSaleAndReviewNumber(ps);
        request.setAttribute("ps", ps);
    	return "searchResult.jsp";//Ϊɶ�������productsBySearch.jsp����Ϊ�����������棬��ַ���Է��ʲ���������searchResult.jsp����Ϊֱ��������
    }
    
    //
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page){
    	//����д�Ľ�����һ��ǿ��ת������û��
    	if(request.getSession().getAttribute("user")==null)return "%fail";//ʧ�ܺ�ִ��$("#loginModal").modal('show');���modal������Ҫ�ͽ�jsp������
    	else return "%success";
    }
    
    //���ﳵ�����ɶ����Ӧ�õ�����ﳵ֮��͸������ϽǵĹ��ﳵ�������п���
    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int pid= Integer.parseInt(request.getParameter("pid"));
    	int num= Integer.parseInt(request.getParameter("num"));
    	User user = (User)request.getSession().getAttribute("user");
    	
    	//����Ѿ����������Ʒ��Ӧ��OrderItem�����һ�û�����ɶ����������ڹ��ﳵ�С� ��ô��Ӧ���ڶ�Ӧ��OrderItem�����ϣ�����������
    	boolean found = false;
    	List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
    	for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == pid) {
                oi.setNumber(oi.getNumber() + num);
                orderItemDAO.update(oi);//������
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
        	oi.setUser(user);
        	oi.setProduct(productDAO.get(pid));
            orderItemDAO.add(oi);
        }
    	return "%success";
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
    
    public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int cid= Integer.parseInt(request.getParameter("cid"));
    	String sort = request.getParameter("sort");
    	Category c= categoryDAO.get(cid);
    	List<Product>ps= productDAO.list(cid);//���ݿⲻ���ܴ���Щ��Ϣ��ֻ��ÿ���ó�����ʱ���������
    	productDAO.fill(Collections.singletonList(c));//????���÷����µĲ�Ʒ���а�
    	//productDAO.setSaleAndReviewNumber(c.getProducts());//�����в�Ʒ�������Ը�ֵ,�����ǰ����ÿһ����Ҫ��һ���������Ȼ����
    	//�Բ�Ʒ��������
    	if(sort!=null) {
    		switch(sort) {
	    	case "all":
	    		ps.sort((p1,p2)->p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount());
	    		break;
	    	case "review":
	    	    ps.sort((p1,p2)->p2.getReviewCount() - p1.getReviewCount());
	    		break;
	    	case "date":
	    		ps.sort((p1,p2)->p2.getCreateDate().compareTo(p1.getCreateDate()));
	    		break;
	    	case "saleCount":
	    		ps.sort((p1,p2)->p2.getSaleCount() - p1.getSaleCount());
	    		break;
	    	case "price":
	    		ps.sort((p1,p2)->(int)(p1.getPromotePrice() - p2.getPromotePrice()));
	    		break;
	    	default:
	    		break;
    	}
    	}
    	c.setProducts(ps);
    	request.setAttribute("c", c);
    	return "category.jsp";
    }
    
    //�ڲ�Ʒҳ������Ѿ���¼��������򣬻��ύ���ݵ�����ˣ����ɶ����������ת������ҳ�档�Ҿ����������Ӧ�øĳ�buyNow
    public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int pid= Integer.parseInt(request.getParameter("pid"));
    	User user = (User)request.getSession().getAttribute("user");
    	int number =Integer.parseInt(request.getParameter("num"));
    	OrderItem oi = new OrderItem();
    	oi.setUser(user);
    	oi.setProduct(productDAO.get(pid));
    	//����Ѿ����������Ʒ��Ӧ��OrderItem�����һ�û�����ɶ����������ڹ��ﳵ�С� ��ô��Ӧ���ڶ�Ӧ��OrderItem�����ϣ�������������Ȼ�ˣ���������ҿ϶�������
    	oi.setNumber(number);
    	orderItemDAO.add(oi);
    	int oiid = oi.getId();
    	return "@forebuy?oiid=" + oiid;//�ҵĻ�ֱ��дreturn "buy.jsp"������buy.jsp���յĲ�����ois�������ж������˿϶�ʱ�����˴ӹ��ﳵ������������2����������ۺϣ��ڽ���ҳ��
    }
    
    //�ڽ���ҳ����ʾ��ѡ�еĶ����ȱois��total
    public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
    	String[] oiids = request.getParameterValues("oiid");//�ǻ����checkbox�ࣨ������ͬ����ֵ�ж����������
    	List<OrderItem>ois=new ArrayList<>();
    	float total =0.0f;
    	for(int i=0; i<oiids.length;i++) {
    		int id = Integer.parseInt(oiids[i]);
    		OrderItem oi = orderItemDAO.get(id);
    		total += oi.getProduct().getPromotePrice()*oi.getNumber();
    		//����firstImageֻ��listHandler�д������������handler�д���ͱ����ݿ����ӹ���Ĵ���(ԭ��δ֪)���������ֻ���Լ�������ÿ����Ʒ��firstImage
//    		List<ProductImage>pisSingle = new ProductImageDAO().list(oi.getProduct(), "type_single", 0, 1);
//    		if (!pisSingle.isEmpty())
//    			oi.getProduct().setFirstProductImage(pisSingle.get(0));
    		productDAO.setFirstProductImage(oi.getProduct());
    		//���
    		ois.add(oi);
    	}
    	//oisΪʲôҪ����session�������request�У�.���ﳵ����Ҫ�ã����� request ��鲻����
    	request.getSession().setAttribute("ois", ois);
    	request.setAttribute("total", total);
    	return "buy.jsp";
    }
    
    //ȱois
    public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
    	User user = (User)request.getSession().getAttribute("user");
//    	if(user==null)return "login.jsp";//�����Ǹ��������󣬾Ͳ���Ҫ��ÿһ��ȱsession��ҳ�����仰�ˡ�
    	List<OrderItem>ois=orderItemDAO.listByUser(user.getId());
    	for(OrderItem oi:ois) {
    		productDAO.setFirstProductImage(oi.getProduct());
    	}
    	request.setAttribute("ois", ois);
    	return "cart.jsp";
    }
    
    public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int oiid = Integer.parseInt(request.getParameter("oiid"));
    	if(orderItemDAO.delete(oiid)<=0)return "%fail";
    	else return "%success";
    }
    
    public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
    	
    	return "";
    }
    
    public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int oiid = Integer.parseInt(request.getParameter("oiid"));//�Ҿ�������õ�
    	int num = Integer.parseInt(request.getParameter("num"));
    	OrderItem oi = orderItemDAO.get(oiid);
    	oi.setNumber(num);
    	orderItemDAO.update(oi);
    	return "%success";
    }

    
}
