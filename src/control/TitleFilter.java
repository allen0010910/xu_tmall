package control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import mydb.QuestionAction;

/**
 * Servlet Filter implementation class TitleFilter
 */
@WebFilter("/QuestionAddServlet")
public class TitleFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TitleFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		QuestionAction questionA = new QuestionAction();
		//���ȶ���Ŀ��һ���жϣ�����ajax������,���ﲻ����json�������ˣ�ֻ�����ַ���
		if(isAjaxRequest(request)){
			//ajax�ж��Ƿ�����Ŀ����
			if(questionA.getAllTitle(request.getParameter("title")).size()>0)
			{
				response.getWriter().write(request.getParameter("title")+":�Ѵ��ڸ���Ŀ���������ٴ����");
			}
		}
		else chain.doFilter(request, response);//�������ajax����ֱ�����
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	//�ж��Ƿ�Ϊajax����
	private boolean isAjaxRequest(ServletRequest request) {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    	String head = httpServletRequest.getHeader("X-Requested-With");
    	return (head != null && "XMLHttpRequest".equalsIgnoreCase(head));
    }


}
