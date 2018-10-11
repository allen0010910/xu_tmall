package mydb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//�������������Լ���Ҫȥʵ�ֺ�������
public class QuestionAction {
	private QuestionDAOImp questionDAOI;
	public QuestionAction(){
		questionDAOI = new QuestionDAOImp();
	}
	
	public void add(Question question){
		if(question!=null){
			questionDAOI.Insert(question);
		}
	}
	//���������Ҫ�Լ������ˣ�����DAO�ı�׼������ʽ����ʵ���Լ��Ĳ�ѯ�����������ѯ���������ݣ���������ѯ�������Բ�д
	public List<Question> queryAll(){
		List<Map<String, Object>> Myparams = new ArrayList<Map<String,Object>>();
		List<Question> questionList = questionDAOI.query(Myparams,"");
		return questionList;
		//System.out.println(questionList);
	}
	//���ڷ�ҳ��ѯ��
	public List<Question> queryAll(int index,int size){
		List<Map<String, Object>> Myparams = new ArrayList<Map<String,Object>>();
		//��ʼ�������
		StringBuffer s = new StringBuffer();
		s.append("limit ");
		s.append(index);
		s.append(",");
		s.append(size);
		List<Question> questionList = questionDAOI.query(Myparams,s.toString());
		return questionList;
	}
	//���ڲ�ѯͳ�����ݣ���������Ҫ���Ը��Ĳ�ѯ����
	public int count(){
		return questionDAOI.countOfquestion();
	}
}
