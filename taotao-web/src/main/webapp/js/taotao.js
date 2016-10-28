var TT = TAOTAO = {
	checkLogin : function(){
		var _token = $.cookie("TT_TOKEN");//这里$.cookie不是jquery提供的，而是第三方插件jquery.cookie提供的
		if(!_token){
			return ;
		}
		$.ajax({
			url : "http://sso.taotao.com/service/user/" + _token,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				var html =data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
				$("#loginbar").html(html);
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});