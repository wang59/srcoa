var synDataSource;
$(document).ready(function(){
	synDataSource = new $.synDataSource();
});
(function(){
	var _this;
	$.synDataSource = function(){
		_this = this;
		_this.settings = $.extend(true, {}, $.synDataSource.defaults);
		_this.syndataselecturl = "cgFormHeadController.do?dataSourceSelect";
		_this.init();
	};
	$.extend($.synDataSource,{
		defaults : {
			$SRCDATASOURCE : $('#srcDataSource'),
			$DESDATASOURCE : $('#desDataSource')
		},
		prototype : {
			init : function(){
				_this.bindEvent();
				_this.initSrcSelect(_this.settings.$SRCDATASOURCE);
				_this.initSrcSelect(_this.settings.$DESDATASOURCE);
			},
			bindEvent : function(){
				
			},
			initSrcSelect : function(jqdom){
				$.post(_this.syndataselecturl,function(data){
					data = $.parseJSON(data);
					var dom = "";
					for(var i = 0; i < data.length; i++){
						var obj = data[i];
						dom += '<option value="'+obj.key+'">'+obj.des+'</option>';
					}
					jqdom.append(dom);
				});
			}
		}
	});
})(jQuery);