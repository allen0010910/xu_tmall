package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import mydb.Question;
import mydb.QuestionAction;

public class QuestionAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��һ���������ֲ�����������������д�ֲ�������������һ��ʼ��д�ã�
		String title,optionA,optionB,optionC,optionD,answer;
		QuestionAction questionA = new QuestionAction();
		Question question = new Question();
		//�ڶ�������ȡ��������͵��������
		title = request.getParameter("title");
		optionA = request.getParameter("optionA");
		optionB = request.getParameter("optionB");
		optionC = request.getParameter("optionC");
		optionD = request.getParameter("optionD");
		answer = request.getParameter("answer");
		//������������ȡ���������ݣ����浽���ݿ�
		question.setTitle(title);
		question.setOptionA(optionA);
		question.setOptionB(optionB);
		question.setOptionC(optionC);
		question.setOptionD(optionD);
		question.setAnswer(answer);
		questionA.add(question);
		//���������ı��ύ���ݺ󣬾�Ӧ����ת��servletҳ���У���ҳ���е����ݣ�����servlet��response��д��
		//�����QuestionQueryServlet·����ʵ����Ϊ����ģ�����web.xml����Ϊ����ģ���һ����Ҫ��servlet������ͬ
		request.getRequestDispatcher("QuestionQueryServlet").forward(request, response);;
		//response.getOutputStream().write("������ӳɹ�".getBytes());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
	}

}
