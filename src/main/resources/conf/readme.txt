1.监听器，过滤器，拦截器由服务器管理。
  无法直接访问spring管理的bean。
  需要WebApplicationContextUtils来获取业务对象。

2.监听器执行有顺序，
  需先执行spring容器创建监听器，
  然后在执行自定义的容器创建监听器，
  以避免应用bean对象为null。

3.动态查询的元素绑定事件方法，有效外壳.on()。

4.动态元素绑定事件，使用直接触发onclick.
  onclick方法参数必须处于字符串中，
  若使用生成id法,则需复选框联合使用。

5.在jsp中，从Session中获取值，需在双引号里。例如--"${user.name}"

