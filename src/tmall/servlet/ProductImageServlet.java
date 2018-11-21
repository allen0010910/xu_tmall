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

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.xu_util.ImageUtil;
import tmall.xu_util.Page;


@WebServlet("/ProductImageServlet")
public class ProductImageServlet  extends BaseBackServlet{
	private static final long serialVersionUID = 1L;

    public ProductImageServlet() {
        super();
    }

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String type = params.get("type");
		int pid = Integer.parseInt(params.get("pid"));
		ProductImage pi = new ProductImage();
		Product p = productDAO.get(pid);
		
		pi.setProduct(p);
		pi.setType(type);
		productImageDAO.add(pi);
		
		String imageFolder,imageFolder_small=null,imageFolder_middle=null;
		
		switch(type) {
		    case "type_single" :
		    	imageFolder= request.getSession().getServletContext().getRealPath("img/productSingle");
		    	imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
	            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		    	break;
		    case "type_detail" :
		    	imageFolder= request.getSession().getServletContext().getRealPath("img/productDetail");
		    	break;
		    default:
		    	imageFolder=null; 
		    	break;
		}
		
        File file = new File(imageFolder, pi.getId() + ".jpg");//������Ҫ�����ļ���Ŀ¼�Ĵ������ļ��Ĳ��Һ��ļ���ɾ��
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
                    if(imageFolder_small!=null && imageFolder_middle!=null && imageFolder!=null) {
                    	File small = new File(imageFolder_small,pi.getId()+".jpg");
                    	File middle = new File(imageFolder_middle,pi.getId()+".jpg");
                    	ImageUtil.resizeImage(file, 56, 56, small);
                        ImageUtil.resizeImage(file, 217, 190, middle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return "@admin_ProductImage_list?pid="+pid;//��Ϊ�ض�����Ҫ���´���Ʒ��id
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage pi = productImageDAO.get(id);
		productImageDAO.delete(id);
		//Ȼ����Ҫɾ����Ӧ��ͼƬ�ļ�,�����ɾ�������һ���������������ҳ��ʾ�Ͳ����ˡ��ļ���ɾ��������File��
		switch(pi.getType()) {
		    case "type_single" :
		    	String imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
	            String imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
	            String imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
	            File f_single = new File(imageFolder_single, pi.getId() + ".jpg");
	            f_single.delete();
	            File f_small = new File(imageFolder_small, pi.getId() + ".jpg");
	            f_small.delete();
	            File f_middle = new File(imageFolder_middle, pi.getId() + ".jpg");
	            f_middle.delete();
		    	break;
		    case "type_detail" :
		    	String imageFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
	            File f_detail = new File(imageFolder_detail, pi.getId() + ".jpg");
	            f_detail.delete();
		    	break;
		    default:
		    	break;
		}
		return "@admin_ProductImage_list?pid="+pi.getProduct().getId();
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
		int id = Integer.parseInt(request.getParameter("pid"));
		Product p =productDAO.get(id);
		List<ProductImage>pisSingle = productImageDAO.list(p, "type_single", page.getStart(), page.getCount());
		List<ProductImage>pisDetail = productImageDAO.list(p, "type_detail", page.getStart(), page.getCount());
		request.setAttribute("pisSingle", pisSingle);
		request.setAttribute("pisDetail", pisDetail);
		request.setAttribute("p", p);
		return "admin/listProductImage.jsp";
	}

}
