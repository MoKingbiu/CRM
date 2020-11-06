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

5.在js中，用EL表达式取值，需在双引号里。例如--"${user.name}"，
  在jsp其他区域中取值，则不用。

6.$.ajax({
  			url:"",
  			data:{

  			},
  			type:"",
  			dataType:"json",
  			success:function(data){

  			}
  		});

7.模态窗口中的回车敲击事件，默认清空全部数据。要手动关闭。return false.

8.  //全选
    $("#qx").click(function(){
        $("input[name=xz]").prop("checked",this.checked)
    })

    //动态查询的元素绑定事件方法，有效外壳.on()
    $("#searchAcList").on("click",$("input[name=xz]"),function(){
        $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
    });

9.?key=value&key=value(不用走后台，但数据不能涉及隐私，不能过于庞大)
    EL取值用${param.key}，不能${key}。
    只有域对象才可以，直接挂参数不在域中。

10.若key为不定变量，则json.key不可取值。必须使用json[key].

11.RunException异常不需要throws,try-catch.因为此异常希望出现异常是程序终止。

12.多表联查时，非必填项可能为空，需用外连接（left/right join），保护主表。