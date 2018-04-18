//登录js部分
//判断如果用户勾选了记住我的登录状态就在初始化的时候给账号密码框赋值
if($("#LonginUser").val()!=''){
	$("#loginUser").val($("#LonginUser").val());
	$('#loginUser').blur();//触发获取焦点事件
}
if($("#LonginPass").val()!=''){
	$("#loginPass").val($("#LonginPass").val());
	$('#loginPass').blur();
	$('#password').focus(); 
}
if($("#loginUser").val()=='' || $("#loginUser").val()=="手机号／邮箱／账户名" || $("#loginPass").val()!='' ){
	$('#loginSubmit').attr("disabled",true); 
}
//判断是否都输入取消登录按钮的控制
function keyup(){
	if($("#loginUser").val()!='' && $("#loginUser").val()!="手机号／邮箱／账户名"){
		if($("#loginPass").val()!='' ){
			confirm();
		}else{
			cancel();
		}
	}else{
		cancel();
	}
}
//后台处理完判断账号密码不正确之后的提示
var msg = "$!msg";
if(msg != ""){
	if(msg=="用户名不正确"){
		$("#logninUsers_").empty();
		$("#logninUsers_").text(msg);
		$('#loginSubmit').addClass('button_yes'); 
		$('#loginSubmit').attr("disabled",false); 
	}
	if(msg=="密码不正确"){
		$("#loginPass_").empty();
		$("#loginPass_").text(msg);
		$('#loginSubmit').addClass('button_yes'); 
		$('#loginSubmit').attr("disabled",false); 
	}
}
var validator = $("#val_acclogin").validate({
    errorPlacement: function(error, element) {
        $('.err_tip').empty();
        error.appendTo($('.err_tip'));
    },
    onfocusout:function(element) { $(element).valid(); },
    submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form
        form.submit();   //提交表单
    },
    messages:{
        loginUser:{
            required:"用户名不能为空"
        },
        loginPass:{
            required: "密码不能为空"
        }
    }
});
$("#loginSubmit").click(function() {
	if(validator.form()){ //若验证通过，则调用修改方法
     	$("#val_acclogin").submit();
     }
});
//---------------------------------登录js   end--------------------------------
