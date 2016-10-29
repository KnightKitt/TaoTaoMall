# TaoTaoMall
淘淘商城学习与实践记录
Try to write some comments in English:
This project is a record about learning and practing on TaoTaoMall, which comms from ChuanZhiBoKe.

## 2016-10-26 day07学习
1、单点登录开发环境搭建
2、静态资源加载问题解决方案

## 2016-10-27 day07学习
实现登录功能，几个要点：
1、细节：登录用户的信息查询验证时，采用的方案是根据用户的用户名查询User对象，然后比较密码是否相同，
		而不是使用用户名和密码一起查询User对象是否存在，这样做可以提高数据库的查询效率，
		因为查询条件越多，查询速度越慢，对于用户量大的系统有一定的影响。
2、生成token，模拟sessionId，并将token保存到redis，同时设置到Cookie中。在此过程中有以下几个要点：
1）将token保存到Redis中时，最好加上一个统一的前缀，方便日后系统做信息统计使用，不然的话token在Redis中无规则保存，不便于统计。
2）区分Redis在系统中的作用是“缓存服务器”还是“内存数据库”，如果是缓存服务器，则其使用出现异常时不应该影响正常业务的执行，
	如果是内存数据库，比如这里保存用户的登录信息，则其使用时如果出现异常，这是正常业务的一部分，就必须进行处理。
3、通过使用Jackson的注解@JsonIgnore，指明json序列化时忽略的字段，不将密码保存到Redis中。
4、登录成功后，token没有写入Cookie的问题
	这是由于使用 nginx 代理时，其转发给Tomcat的请求域名是127.0.0.1，而需要将cookie写入到的是真实的请求域名taotao.com中，
	这样违反了浏览的安全的原则，导致写入失败。（跨域写cookie的问题）
	请求过程：用户请求 --> nginx --> tomcat，这时tomcat根本不知道真正的请求来源。
	解决方案：在nginx的配置文件中添加Host的代理头信息，这样tomcat就可以知道真正的请求来源。
	
## 2016-10-28 day07学习
实现显示当前登录人信息
1、jquery.cookie插件使用：获取cookie中的token信息
2、跨域请求：前台页面www.taotao.com请求单点登录系统sso.taotao.com，获取用户信息，注意SpringMVC中配置自定义的JSON转换器，支持JSONP的输出
3、易遗漏的操作：刷新Redis中用户信息的生存时间，非常重要！
## 2016-10-29 day08学习
一个简化了的订单系统学习：
1、了解开发平台（开发者平台）
2、订单系统接口实现的学习：
1）MyBatis多表查询时配置文件（xxxMapper.xml）的写法，以及多表查询的延迟加载；
2）在MySQL JDBC配置的URL中设置连接参数允许一个statement执行多条sql语句（allowMultiQueries=true）
3、下单按钮功能的实现
4、清理git库，删除之前提交的多余内容，使用的Git命令如下：
1）$ git rm --cached -r taotao-manage/*/target/
2）$ git rm --cached -r itcast-usermanage
3）$ git rm --cached -r taotao-common/target/
