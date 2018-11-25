package tmall.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
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

    
}
