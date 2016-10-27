$(function(){
	$("from").Validform({//允许为空
		datatype: {
			"empty": /^\s*$/,
			"nhtml":/^<(?:.|\s)*?>/g
		}
	
	
	});
});