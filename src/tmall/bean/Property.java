package tmall.bean;

public class Property {
	private String name;
    private Category category;//���ϵ�����洢id��ֱ�Ӵ洢��Ӧ���󡣷����ȡ
    private int id;
 
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
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
