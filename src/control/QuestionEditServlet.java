package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mydb.Question;
import mydb.QuestionAction;

public class QuestionEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuestionAction questionA = new QuestionAction();
		String title = request.getParameter("title");//����ַ������������ַ���ո񣬼Ӻţ��������
		//Ϊ+���Ĵ���getQueryString�õ�title=7+5,Ȼ����ȥ���������õ�7+5.�Ӻ�����û�н����������������
		//������Ŀ��ȡ���ݿ��������Ϣ,����˳����һ�����������
		List<Question> questionList = questionA.getAllTitle(title);
		//���õ���������Ϣ���͵�jsp
		request.setAttribute("question", questionList.get(0));
		request.getRequestDispatcher("/exammanager/examEdit.jsp").forward(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
