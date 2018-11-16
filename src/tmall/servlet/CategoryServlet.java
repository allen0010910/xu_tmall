package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.xu_util.ImageUtil;
import tmall.xu_util.Page;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;


    public CategoryServlet() {
    }

    //���ӷ��࣬������ֻ��һ�����ָ�id
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);//�ڻ����request�Ĳ��Ǳ��ֶε��ļ����д�������ļ������������������ֶα���
		String name = params.get("name");
		Category c = new Category();
        c.setName(name);
        categoryDAO.add(c);//�����˷���
        
        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));//getRealPath��λ��webRootĿ¼
        File file = new File(imageFolder, c.getId() + ".jpg");//������Ҫ�����ļ���Ŀ¼�Ĵ������ļ��Ĳ��Һ��ļ���ɾ��
        try {
            if (null != is && 0 != is.available()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    // ͨ�����´��룬���ļ�����Ϊjpg��ʽ,.������Ϊ�ǲ���tomcat�������Ӧ��JavaĿ¼�²�û�б���֮���id.jpgͼƬ��������tomcat��Ӧ�µ�·��
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return "@admin_Category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);
        request.setAttribute("thecs", cs);
        request.setAttribute("page", page);//������request���ܴ�����
        return "admin/listCategory.jsp";
	}

}
