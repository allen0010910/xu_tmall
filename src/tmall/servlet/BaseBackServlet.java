package tmall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
            // ��ȡ��ҳ��Ϣ�����û������ֵ���ͻ��������ʼ����0��5,���㱨�쳣Ҳ����
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
            // �������䣬���ö�Ӧ�ķ�����֮�����÷��䣬��ΪĿǰ��list��add�ȷ��������麯������service�����ڻ�����У�����ֻ�Ǽ̳��ˣ�����ֱ�ӵ��������Լ���list�ȷ�����
            // ������÷���Ҳ������������ÿ���඼��Ҫ�Լ�дservice������Ҳ������дdoget��dopost,����˵�����ʡ�˴�����
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
            response.sendRedirect(redirect.substring(1));//��ַ�ض������ڿͻ�����ת
        else if (redirect.startsWith("%"))
        {
        	//request.setCharacterEncoding("utf-8");  response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(redirect.substring(1));
        }
        else
            request.getRequestDispatcher(redirect).forward(request, response);//�ַ����������ת,redirect�����Ǹ���ַ����ΪservletҲ����Ϊֱ�ӵ�jsp
    }
    //�����������Ӧ�÷���ÿһ��Ҫ������ϴ���add�����µģ����Ƕ�Ҫ�ã��ɴ��ڸ�����д����
    InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream is = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // �����ϴ��ļ��Ĵ�С����Ϊ10M�������ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼�У������Ҫ������ʱĿ¼��������
            factory.setSizeThreshold(1024 * 10240);
            @SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
            if(items == null || items.size()<=0)return null;
            // ����������
            for (FileItem item : items) {
                //FileItem item = (FileItem) item1;
            	//�����ڱ��е��ֶ�,��Ϊ�����ָ�����Զ����Ƶ���ʽ��multipart/form-data���ύ���ݣ���ô�Ͳ���ͨ��������ֶλ�ȡ��File�ֶ�
                if (!item.isFormField()) {
                    // item.getInputStream() ��ȡ�ϴ��ļ���������
                    is = item.getInputStream();
                } else {
                	//request.getParameter("heroName")�в�ͨ
                    String paramName = item.getFieldName();//�Ǳ��ֶεĻ���������map��
                    String paramValue = item.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
