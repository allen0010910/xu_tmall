## 总结：反射 ##

**后台反射**：  
后台执行增删改查。面向bean。提供一个增删改查的servlet基类，所有子类均继承service方法，在该方法中处理请求。  

- 不使用反射，所有子类自行重写service方法，根据过滤器传的数据判断调用哪个操作函数。
- 使用反射后，在基类的service方法中（子类继承后，this.getClass拿的是子类的类对象）利用反射来调用子类的函数，完成请求的处理。主要是减少了很多代码量。


**前台反射**：  
前台执行的操作不止增删改查。面向用户。可以只有一个servlet而言，其实使用的方式都一样。主要是过滤器的不同。

## 总结：数据库存储的数据与bean中数据##

由于bean是实际在后台进行处理的对象，而数据库中存储的只是普通的数据类型（blob暂时不算），中间有一个过渡。

- 例如，orderItem与order是多对一的关系，一个订单有多个订单项。数据库中，orderItem存的是pid、uid、oid，order_存的是uid。在执行DAO操作的时候，肯定是需要根据这些id来生成对应对象，这样在bean层就建立了联系。
- 重点是，订单里面没有直接存储订单项的数据，只在‘一’对‘多’中，‘多’的这一层存储了‘一’的数据。因此，在从数据库取出‘一’的这个数据，也就是订单的时候，需要进行一个fill操作，填充‘一’对应的‘多’的数据——订单项。

## 总结：乱码问题 ##

- 解决乱码问题的首要前提是，EncodingFilter（自定义的编码过滤器）在web.xml中注册的排序一定要在其他filter之前。
- 如果还是解决不了，试试这种方式，写一个工具类。new String(name.gebyte("iso-8859-1"),"utf-8");这种方式：先以iso-8859-1编码，再以utf-8解码，为什么要用iso-8859-1，因为servlet中get请求默认在servlet中是iso-8859-1。