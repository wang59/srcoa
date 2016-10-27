var transferDataSource;
$(document).ready(function(){
	transferDataSource = new $.transferDataSource();
});
(function(){
	var _this;
	$.transferDataSource = function(){
		_this = this;
		_this.settings = $.extend(true, {}, $.transferDataSource.defaults);
		_this.datasourceUrl = "transferDataSourceController.do?dataSourceSelect";
		_this.datasourceShowTableUrl = "transferDataSourceController.do?showTables";
		_this.init();
	};
	$.extend($.transferDataSource,{
		defaults : {
			$SRCSELECT : $('select[name="srcSelect"]'),//旧数据源
			$DESSELECT : $('select[name="desSelect"]'),//新数据源
			$SRCTABLESSELECT : $('select[name="srcTablesSelect"]'),//旧数据源表单列表
			$DESTABLESSELECT : $('select[name="desTablesSelect"]')//新数据源表单列表
		},
		prototype : {
			init : function(){
				_this.bindEvent();
				_this.initSrcSelect();
			},
			bindEvent : function(){
				_this.settings.$SRCSELECT.bind('change',function(){
					var val = _this.settings.$SRCSELECT.val();
					_this.showTables(_this.settings.$SRCTABLESSELECT,val);
				});
				_this.settings.$DESSELECT.bind('change',function(){
					var val = _this.settings.$DESSELECT.val();
					_this.showTables(_this.settings.$DESTABLESSELECT,val);
				});
			},
			initSrcSelect : function(){
				$.post(_this.datasourceUrl,function(data){
					data = $.parseJSON(data);
					var dom = "";
					for(var i = 0; i < data.length; i++){
						dom += '<option value="'+data[i].key+'">'+data[i].des+'</option>';
					}
					_this.settings.$SRCSELECT.append(dom);
					_this.settings.$DESSELECT.append(dom);
				});
			},
			showTables : function(jqdom, key){
				jqdom.empty();
				$.post(_this.datasourceShowTableUrl + "&key=" + key,function(data){
					data = $.parseJSON(data);
					var dom = "";
					for(var i = 0; i < data.length; i++){
						var obj = data[i];
						$.each(obj,function(k,v){
							dom += '<option value="'+v+'">'+v+'</option>'
						})
					}
					jqdom.append(dom);
				});
			}
		}
	});
})(jQuery);