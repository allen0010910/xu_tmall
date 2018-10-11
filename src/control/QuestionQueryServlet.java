package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mydb.Question;
import mydb.QuestionAction;

public class QuestionQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��һ���������ֲ�����
		QuestionAction questionA = new QuestionAction();
		List<Question> questionList = null;
		int pageIndex = 0;//��������з�ҳ����,��Ҫ��������������ҳ���������㵱ǰҳ��Ӧ����ʼ��
		int startLine = 0;//��ǰҳ��Ӧ��ʼ�е��к�
		int totalCount = 0;//������
		int totalPage = 0;//��ҳ��
		//�ڶ�����ʹ��JDBC���������ݿ��������ȡ���ڴ���
		//�����������õ���������ת��Ϊ�������ʽ���档
		//������ҳ��Ϊ�ջ��߿��ַ���ʱ,pageIndexΪ��ʱ��pageIndex=1�����򣬽�pageIndexתΪint����
		pageIndex = request.getParameter("pageIndex")==null || "".equals(request.getParameter("pageIndex"))?1:Integer.valueOf(request.getParameter("pageIndex"));
		startLine = (pageIndex - 1)*3;//ÿҳ3��
		questionList = questionA.queryAll(startLine,3);
		totalCount = questionA.count();
		totalPage = totalCount%3==0?totalCount/3:totalCount/3+1;//�������ǡ��3�ı�����ҳ����1
		//���Ĳ������õ����������ͨ������ת���������ָ����jsp.
		request.setAttribute("questionList", questionList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("pageIndex", pageIndex);//��ť��������������˵õ�pageIndex��ֵ��ͨ����servlet������ȷ���´����������
		//getRequestDispatcher�Ƿ������ڲ���ת����ַ����Ϣ���䣬ֻ����ת��webӦ���ڵ���ҳ��
		//ͨ��forward��������󣬷���֮����jspʵ���������
		request.getRequestDispatcher("exammanager/exams.jsp").forward(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
