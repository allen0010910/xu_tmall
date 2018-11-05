package tmall.bean;

import java.util.List;

public class Category {
	private String name;
    private int id;
    //һ���������ж����Ʒ������һ�Զ�Ĺ�ϵ
    //����ҵ����û�����󣬼����ݷ�������ѯ��Ӧ�����ԣ���˲�������Ե�һ�Զ�Ĺ�ϵ
    private List<Product> products;
    //һ��������Ӧ���в�Ʒ����һ�в�Ʒ�����ж����Ʒ��¼
    //Ϊ��ʵ�ֽ����ϵ�������ܣ�ΪCategory�����������һ����������
    private List<List<Product>> productsByRow;
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }
 
    public List<Product> getProducts() {
        return products;
    }
 
    public void setProducts(List<Product> products) {
        this.products = products;
    }
 
    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }
 
    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
