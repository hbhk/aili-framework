(function($){  
    //备份jquery的ajax方法  
    var _ajax=$.ajax;  
    //重写jquery的ajax方法  
    $.ajax=function(opt){
        //备份opt中error和success方法  
        var fn = {  
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            exception:function(data, textStatus){},  
            success:function(data, textStatus){}  
        };
        if(opt.error){  
            fn.error=opt.error;
        }  
        if(opt.success){ 
            fn.success=opt.success;  
        }  
        if(opt.exception){  
            fn.exception=opt.exception;
        }  
          
        //扩展增强处理  
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){  
                //错误方法增强处理  
            	if(opt.error){ 
            		fn.error(XMLHttpRequest, textStatus, errorThrown);
            	}else{
            		 alert(errorThrown);
            	}
            },  
            success:function(data, textStatus){
            	if(data.success){
            		 //成功回调方法增强处理  
                    fn.success(data, textStatus);
            	}else{
            		//业务异常调方法增强处理  
                    fn.exception(data, textStatus);  
            	}
            } 
        });  
        _ajax(_opt);  
    };
    /**
     * Toast效果，主要是用于在不打断程序正常执行的情况下显示提示数据
     * @param config
     * @return
     */
    var Toast = function(config){
    	this.context = config.context==null?$('body'):config.context;//上下文
    	this.message = config.message;//显示内容
    	this.time = config.time==null?3000:config.time;//持续时间
    	this.left = config.left;//距容器左边的距离
    	this.top = config.top;//距容器上方的距离
    	this.init();
    }
    var msgEntity;
    Toast.prototype = {
    	//初始化显示的位置内容等
    	init : function(){
    		$("#toastMessage").remove();
    		//设置消息体
    		var msgDIV = new Array();
    		msgDIV.push('<div id="toastMessage">');
    		msgDIV.push('<span>'+this.message+'</span>');
    		msgDIV.push('</div>');
    		msgEntity = $(msgDIV.join('')).appendTo(this.context);
    		//设置消息样式
    		var left = this.left == null ? this.context.width()/2-msgEntity.find('span').width()/2 : this.left;
    		var top = this.top == null ? '20px' : this.top;
    		msgEntity.css({position:'absolute',top:top,'z-index':'99',left:left,'background-color':'black',color:'white','font-size':'18px',padding:'10px',margin:'10px'});
    		msgEntity.hide();
    	},
    	//显示动画
    	show :function(){
    		msgEntity.fadeIn(this.time/2);
    		msgEntity.fadeOut(this.time/2);
    	}
    }

    $.toast=function(msg){
    	new Toast({context:$('body'),message:msg}).show();  
    }
})(jQuery);


