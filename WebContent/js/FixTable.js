(function($){  
    $.fn.fixTable = function(options){ 
		var defaults = {  
			fixColumn: 0,  
			width:0,
			height:0  
		};    
	    var opts = $.extend(defaults, options); 
	    	// 将resultTable第一列的多选框ID替换
	    	$(this).find('input[id^=kanList_chkSelectRecord]').each(function(i){
	    		$(this).attr('id',$(this).attr('id').replaceAll('kanList_chkSelectRecord','clone_kanList_chkSelectRecord'));
	    	});
			var _this = $(this);
			var _clone = _this.clone();
			var _columnClone = _this.clone();
			var _columnDataClone = _this.clone();
			_this.wrap(function() {
               return $("<div id='_fixTableMain'></div>");
            });
			$("#_fixTableMain").css({
				"width":"auto",
				"height":"auto",
				"overflow":"scroll",
				"position":"relative"
			});
			$("#_fixTableMain").wrap(function() {
               return $("<div id='_fixTableBody'></div>");
            });
			$("#_fixTableBody").css({
				//"background-color":"yellow",
				"width":"auto",
				"height":"auto",
				"overflow":"hidden",
				"position":"relative"
			});
			$("#_fixTableBody").append("<div id='_fixTableHeader'></div>");
			$(_clone).height($(_clone).find("thead").outerHeight());
			$("#_fixTableHeader").append(_clone);
			$("#_fixTableHeader").css({
				//"background-color":"gray",
				"overflow":"hidden",
				"width":defaults["width"]-17,
				"height":_clone.find("thead").find("tr").height()+1,
				"position":"absolute",
				"z-index":"4",
				"top":"0"
			});
			
			$("#_fixTableBody").append("<div id='_fixTableColumn'></div>");
			
			var _fixColumnNum = defaults["fixColumn"];
			var _fixColumnWidth = 0;
			$($(_this).find("thead").find("tr").find("th")).each(function(index, element) {
               	if((index+1)<=_fixColumnNum){
					_fixColumnWidth += $(this).outerWidth(true);
				}
            });
			
			$("#_fixTableColumn").css({
				"overflow":"hidden",
				"width":_fixColumnWidth+1,
				"height":defaults["height"]-17,
				"position":"absolute",
				"z-index":"8",
				"top":"0",
				"left":"0"
			});
			
			
			$("#_fixTableColumn").append("<div id='_fixTableColumnHeader'></div>");
			$("#_fixTableColumnHeader").css({
				//"background-color":"#abc123",
				"width":_fixColumnWidth+1,
				"height":_this.find("thead").outerHeight(),
				"overflow":"hidden",
				"position":"absolute",
				"z-index":"7"
			});
			$("#_fixTableColumnHeader").append(_columnClone);
			
			$("#_fixTableColumn").append("<div id='_fixTableColumnBody'></div>");
			$("#_fixTableColumnBody").css({
				//"background-color":"#acd542",
				"width":_fixColumnWidth+1,
				"height":_this.outerHeight()-_this.find("tfoot").outerHeight(),
				"overflow":"hidden",
				"position":"absolute",
				"z-index":"6",
				"top":"0"
			});
			$("#_fixTableColumnBody").append(_columnDataClone);
			
			
			
			$("#_fixTableBody").append("<div id='_fixTableFoot'></div>");
			$(_clone).height($(_clone).find("tfoot").height());
			$("#_fixTableFoot").append(_clone);
			$("#_fixTableFoot").css({
				//"background-color":"gray",
				"overflow":"hidden",
				"width":defaults["width"]-17,
				"height":_clone.find("tfoot").outerHeight(),
				"position":"absolute",
				"z-index":"4",
				"top":"0"
			});
			
			// 将resultTable第一列的多选框ID还原
			$("#_fixTableColumnBody table input[id^=clone_kanList_chkSelectRecord]").each(function(){
				var id = $(this).attr('id').replaceAll('clone_kanList_chkSelectRecord','kanList_chkSelectRecord');
				$(this).attr('id',id);	
			});
			
			$("#_fixTableMain").scroll(function(e) {
                $("#_fixTableHeader").scrollLeft($(this).scrollLeft());
				$("#_fixTableColumnBody").scrollTop($(this).scrollTop());
            });
		};
    
})(jQuery); 