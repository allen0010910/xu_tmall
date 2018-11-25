package tmall.servlet;

import java.lang.reflect.Method;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.xu_util.Page;

/**
 * Servlet implementation class BaseForeServlet
 */
@WebServlet("/BaseForeServlet")
public class BaseForeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected CategoryDAO categoryDAO = new CategoryDAO();
    OrderDAO orderDAO = new OrderDAO();
    OrderItemDAO orderItemDAO = new OrderItemDAO();
    ProductDAO productDAO = new ProductDAO();
    ProductImageDAO productImageDAO = new ProductImageDAO();
    PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    ReviewDAO reviewDAO = new ReviewDAO();
    UserDAO userDAO = new UserDAO();
       
    
    public BaseForeServlet() {
    	PropertyDAO propertyDAO = new PropertyDAO();
    }
    
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            int start = 0;
            int count = 10;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception ignored) {
            }

            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception ignored) {
            }

            Page page = new Page(start, count);
            String method = (String) request.getAttribute("method");
            System.out.println(page);
            System.out.println(method);
            //��Ϊ�������ڵ������service���������thisָ���������������������õ�������������
            Method m = this.getClass().getMethod(
                    method, HttpServletRequest.class,
                    HttpServletResponse.class, Page.class
            );
            String redirect = m.invoke(this, request, response, page).toString();//��ǰ̨������Ӧ��Ӧ�����ת����̨����
            BaseBackServlet.redirectStartWithCase(request, response, redirect);//���ݵ�ַ����̨������Ӧ��ת
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    

}
