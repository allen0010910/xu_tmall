package tmall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.User;

/**
 * Servlet Filter implementation class ForeAuthFilter
 */
@WebFilter("/*")
public class ForeAuthFilter implements Filter {

    public ForeAuthFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	//�û��ڹ��ﳵҳ��ͣ���ܳ�ʱ�䵼��session���ڣ�
	//��������ԭ�򡣵���sessionû����Ϣ������Ҫÿ��ҳ������жϡ�����ͨ�������������������Ҫ��½��ҳ�棬�����������ж�
	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String contextPath = request.getServletContext().getContextPath();
        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"
        };

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if (uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
            String method = StringUtils.substringAfterLast(uri, "/fore");
            //����÷����������ڸ������С�˵����Ҫ��½
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                User user = (User) request.getSession().getAttribute("user");
                if (null == user) {
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
