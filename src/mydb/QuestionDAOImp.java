package mydb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class QuestionDAOImp implements QuestionDAO {
	private Connection conn;
	public QuestionDAOImp(){
		conn = GetDBConn.getConnection();
	}

	@Override
	public int Insert(Question question) {
		try {
			if(conn.isClosed())conn=GetDBConn.getConnection();
			System.out.println("reget-conn-Insert");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//�����ֵ��ȷ����ĳЩ���ܲ���Ϊ�գ����оͲ������������û��
		if(question==null)return 0;
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into exam_questions (title,optionA,optionB,optionC,optionD,answer) values(?,?,?,?,?,?)";
		Connection conn = GetDBConn.getConnection();
		//��֪��Ϊɶ��mysql����char�ڲ����ʱ�򣬲�����char��ֻ����string
		try {
			queryRunner.execute(conn,sql, question.getTitle(),question.getOptionA()
					            ,question.getOptionB(),question.getOptionC(),question.getOptionD(),question.getAnswer());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("insert-success");
			try {
				DbUtils.close(conn);
				System.out.println("insert-close");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
		//��֪��Ϊɶ�������string,
		//�����жϸ���ֵ�Ƿ�Ϊ��,
		//����ϸ���۵Ļ�����https://www.bilibili.com/video/av28515960?p=2
//		StringBuffer s = new StringBuffer();
//		s.append("insert into table (");
//		if(!ObjisEmpty(question.getTitle()))s.append(",title");
//		s.append("");
//		//������������һ�����š������ҵ���һ�����ŵ�λ�ã�����ɾ�����Ϳ�����
//		int i=s.indexOf(",");s.delete(i, i+1);
	}

	@Override
	public int Delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Update(Question question) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Question SelectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> selectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> query(List<Map<String, Object>> params, String special) {
		try {
			if(conn.isClosed())conn=GetDBConn.getConnection();
			System.out.println("reget-conn-query");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		List<Question> result = null;
		StringBuilder sql=new StringBuilder();
		//1=1��Ϊ�˸�andһ���á���Ȼwhereû�ط��ţ�������ϲ����ڣ�ֱ��ִ���������Ҳ����
		sql.append("select * from exam_questions where 1=1 ");
		if(params!=null&&params.size()>0){
			for (int i = 0; i < params.size(); i++) {
				Map<String, Object> map=params.get(i);
				sql.append(" and  "+map.get("name")+" "+map.get("rela")+" "+map.get("value")+" ");
			}
		}
		sql.append(special);//Ϊ�˷�ҳ��ѯ����Ҫ��sql����β����limit 0,3�������ַ���ѡ���ҳ������
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = GetDBConn.getConnection();
		try {
			//�����ʹ��ubtiles����ѯ���ص�������Ҫresult.next()��Ȼ��һ������һ�����ݵĸ�ֵ������ӵ�list
			result = queryRunner.query(conn,sql.toString(), new BeanListHandler<Question>(Question.class));
			//System.out.println(result.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("query-success");
			try {
				DbUtils.close(conn);
				System.out.println("query-close");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public int countOfquestion() {
		try {
			if(conn.isClosed())conn=GetDBConn.getConnection();
			System.out.println("reget-conn-query");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int result = 0;
		StringBuilder sql=new StringBuilder();
		sql.append("select count(*) from exam_questions");//������ʵ���Խ�count(*)�����ַ������Զ���ѡ�����ݣ����Ǹ�����ѡ��һ��
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = GetDBConn.getConnection();
		try {
			//qr.query()����object���� ����ת�� ScalarHandler��Long���� Ȼ�� ��תΪ int����,����ԭ������<>�����˿���ȥ��һЩ��̾��
			result = ((Long)queryRunner.query(conn,sql.toString(),new ScalarHandler<>())).intValue();//ScalarHandler����ĳһ��ֵ����ͳ�ƺ�����ֵ
		    //result = 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("query-success");
			try {
				DbUtils.close(conn);
				System.out.println("query-close");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//���ߣ��ж��Ƿ�Ϊ�գ���ֵҲ�е�ͨ
//	private boolean ObjisEmpty(Object obj){
//		if (obj == null) return true;
//		if ((obj instanceof List))return ((List) obj).size() == 0;
//		if ((obj instanceof String))return ((String) obj).trim().equals("");
//		return false;
//	}

}
