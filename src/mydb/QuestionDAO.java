/**
 * 
 */
package mydb;

import java.util.List;
import java.util.Map;

/**
 * ���ڴ���question�����ݿ��Ĳ����ӿ�
 *
 */
public interface QuestionDAO {
	int Insert(Question question);
	int Delete(int id);
	//����һ��������Question����ÿһ������ֵ�����Ϊnull�򲻸��¡���ֵ�����
	int Update(Question question);
	Question SelectById(int id);
	List<Question>selectByName(String name);
	List<Question>query(List<Map<String, Object>> params, String special);//���հ��ѯ,�Լ�����һ�������ѯ
	int countOfquestion();
}
