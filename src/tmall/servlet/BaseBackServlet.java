package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
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

public abstract class BaseBackServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);

    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);
    
    CategoryDAO categoryDAO = new CategoryDAO();
    OrderDAO orderDAO = new OrderDAO();
    OrderItemDAO orderItemDAO = new OrderItemDAO();
    ProductDAO productDAO = new ProductDAO();
    ProductImageDAO productImageDAO = new ProductImageDAO();
    PropertyDAO propertyDAO = new PropertyDAO();
    PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    UserDAO userDAO = new UserDAO();
    
    //��ʱ��Ҳ��ֱ����дservice()�������������ṩ��Ӧ�ķ��񣬾Ͳ������ֵ�����get����post�ˡ�
    //����servlet�̳����service������,
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            // ��ȡ��ҳ��Ϣ�����û������ֵ���ͻ��������ʼ����0��5
            int start = 0;
            int count = 5;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception ignored) {
            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception ignored) {
            }
            Page page = new Page(start, count);
            // �������䣬���ö�Ӧ�ķ���
            String method = (String) request.getAttribute("method");//list
            //����1���ײ㷽���Ķ��󣬲���2��"product x"���������ݵĲ����������ж��
            Method m = this.getClass().getMethod(method, HttpServletRequest.class,
                    HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();//�õ�servlet����󷵻ص��ַ�����

            // ���ݷ����ķ���ֵ��������Ӧ�Ŀͻ�����ת���������ת�����߽���������ַ��� */
            redirectStartWithCase(request, response, redirect);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    static void redirectStartWithCase(HttpServletRequest request, HttpServletResponse response, String redirect) throws IOException, ServletException {
        if (redirect.startsWith("@"))
            response.sendRedirect(redirect.substring(1));
        else if (redirect.startsWith("%"))
            response.getWriter().print(redirect.substring(1));
        else
            request.getRequestDispatcher(redirect).forward(request, response);
    }
}
