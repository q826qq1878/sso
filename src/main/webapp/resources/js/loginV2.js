        function agin(){
            $(".input").each(function(){
                var error_left = $(this).siblings(".input_tips").width()+10;
                $(this).siblings(".error_tips0").css('left', error_left);
            });            
        };
        agin();
        function account_register(obj){
        	document.getElementById('pageName').innerHTML=regName;
        	$("#phone").removeClass("error1");
        	$("#yzm_code").removeClass("error1");
        	$("#yzmcode").removeClass("error1");
        	$("#_fir_pwd").removeClass("error1");
        	$("#_two_pwd").removeClass("error1");
        	
        	$(".display_a").text("");
        	$(".display_b").text("");
        	$(".display_c").text("");
        	$(".display_d").text("");
        	$(".display_e").text("");
        	
        	$("#phone").val('手机号');
        	$("#yzm_code").val('验证码');
        	$("#yzmcode").val('短信验证码');
        	$("#_fir_pwd").val('设置密码');
        	$("#_two_pwd").val('确认密码');
        	$("#fir_pwd").val('');
        	$("#two_pwd").val('');
        	$("#fir_pwd").css("display","none");
        	$("#two_pwd").css("display","none");
        	$("#_two_pwd").css("display","block");
        	$("#_fir_pwd").css("display","block");
        	$("#phone_zhuce").removeClass("button_yes");
    		$("#phone_zhuce").attr("disabled",true); 
    		$("input:checkbox").removeAttr("checked");
        	$(".input_tips").css("visibility","hidden").animate({top:'10px'});
            $(".content_login").hide();
            $(".content_register").show();
           
            agin();
            //$(".title_big").text("注册Iambuyer")
        };
        function account_login(obj){
        	document.getElementById('pageName').innerHTML=loginName;
        	$("#loginUser").removeClass("error1");
        	$("#password").removeClass("error1");
        	$("#logninUsers_").text("");
        	$("#loginPass_").text("");
        	$("#loginPass").val("");
        	$("#loginPass").css("display","none");
        	$("#password").css("display","block");
        	$("#loginPass").removeClass("error1");
        	$("#loginUser").val("手机号／邮箱／账户名");
        	$("#password").val("请输入密码");
        	$("#loginSubmit").removeClass("button_yes");
    		$("#loginSubmit").attr("disabled",true); 
    		$("input:checkbox").removeAttr("checked");
        	$(".input_tips").css("visibility","hidden").animate({top:'10px'});
            $(".content_register").hide();
            $(".content_login").show();
            agin();
           // $(".title_big").text("登录Iambuyer")
        };
        function account_password(obj){
        	document.getElementById('pageName').innerHTML="找回密码";
        	$("#phone_").removeClass("error1");
        	$("#yzm_code_").removeClass("error1");
        	$("#phoneYZM").removeClass("error1");
        	$("#pass1").removeClass("error1");
        	$("#pass2").removeClass("error1");
            $("#setPassword").text('');
            $("#yesPassword").text('');
        	$("#findPhone").text("");
        	$("#findYzm").text("");
        	$("#smsYZM").text("");
        	
        	$("#phone_").val("手机号");
        	$("#yzm_code_").val("验证码");
        	$("#phoneYZM").val("短信验证码");
        	$("#pass1_").val("设置密码");
        	$("#pass2_").val("确认密码");
        	$("#findPassword").removeClass("button_yes");
    		$("#findPassword").attr("disabled",true); 
        	$(".input_tips").css("visibility","hidden").animate({top:'10px'});
            $(".content_login").hide();
            $(".content_password").show();
            agin();
            //$(".title_big").text("找回密码")
        };
        function back_login(obj){
        	document.getElementById('pageName').innerHTML=loginName;
        	phoneTrue = false;
        	passwordTrue = false;
        	$("#loginUser").removeClass("error1");
        	$("#password").removeClass("error1");
        	$("#logninUsers_").text("");
        	$("#loginPass_").text("");
        	$("#loginPass").val("");
        	$("#loginPass").css("display","none");
        	$("#password").css("display","block");
        	$("#loginUser").val("手机号／邮箱／账户名");
        	$("#password").val("密码");
        	$("#loginSubmit").removeClass("button_yes");
    		$("#loginSubmit").attr("disabled",true); 
    		$("input:checkbox").removeAttr("checked");
        	$(".input_tips").css("visibility","hidden").animate({top:'10px'});
            $(".content_password").hide();
            $(".content_login").show();
            agin();
           // $(".title_big").text("登录Iambuyer")
        };
        function back_register(obj){
        	document.getElementById('pageName').innerHTML=regName;
        	$("#phone").removeClass("error1");
        	$("#yzm_code").removeClass("error1");
        	$("#yzmcode").removeClass("error1");
        	$("#_fir_pwd").removeClass("error1");
        	$("#_two_pwd").removeClass("error1");
        	
        	$(".display_a").text("");
        	$(".display_b").text("");
        	$(".display_c").text("");
        	$(".display_d").text("");
        	$(".display_e").text("");
        	
        	$("#phone").val('手机号');
        	$("#yzm_code").val('验证码');
        	$("#yzmcode").val('短信验证码');
        	$("#_fir_pwd").val('设置密码');
        	$("#_two_pwd").val('确认密码');
        	$("#fir_pwd").val('');
        	$("#two_pwd").val('');
        	$("#fir_pwd").css("display","none");
        	$("#two_pwd").css("display","none");
        	$("#_two_pwd").css("display","block");
        	$("#_fir_pwd").css("display","block");
        	$("#phone_zhuce").removeClass("button_yes");
    		$("#phone_zhuce").attr("disabled",true); 
    		$("input:checkbox").removeAttr("checked");
        	$(".input_tips").css("visibility","hidden").animate({top:'10px'});
            $(".content_password").hide();
            $(".content_register").show();
           
            agin();
           // $(".title_big").text("注册Iambuyer")
        };
        function set_passwprd(obj){
            $("#findPsw1").hide();
            $("#findPsw2").show();
            agin();
            $(".flowitem").addClass("addcolor")
        };
        function get_passwprd(obj){
            $("#findPsw2").hide();
            $("#findPsw1").show()
        };
        function fg_txt(obj){
           $(".get_yzm").text("重新发送(60)").css("color","#9d9d9d");
        };     
        //给class名为input的绑定事件
        $('.input').bind({ 
            //获得焦点事件
            focus:function(){   
                //文字提示显示
                $(this).parent(".input_item").children(".input_tips").css("visibility","visible").animate({top:'-4px'});
                //判断输入框是否有showPwd的class名 有则是密码
                if($(this).hasClass("showPwd")){
                    var text_value = $(this).val();
                    if (text_value == "请输入密码" || "设置密码" || "确认密码") {
                        $(this).hide().siblings(".input_password").show().focus();
                    }                    
                }
                //输入框内文字提示判断
                if (this.value == this.defaultValue){ 
                    this.value=""; 
                } 
            }, 
            //取消焦点事件
            blur:function(){    
                //文字提示
                var i_length=$(this).val().length;
                if (i_length==0) {
                    $(this).parent(".input_item").children(".input_tips").css("visibility","visible").animate({top:'-4px'});//文字提示隐藏
                }else{
                    $(this).parent(".input_item").children(".input_tips").css("visibility","visible").animate({top:'-4px'});//文字提示显示
                };
                //判断输入框是否有input_password的class名 有则是密码
                if($(this).hasClass("input_password")){
                    var text_value = $(this).val();
                    if (text_value == "") {
                        $(this).hide().siblings(".showPwd").show().blur();
                    }                
                }
                //输入框内文字提示判断
                if (this.value == ""){ 
                    this.value = this.defaultValue; 
                }; 
            } 
        }); 

