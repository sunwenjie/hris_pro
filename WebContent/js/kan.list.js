function kanList_init(tableWrapperId, cacheId) {
	if(tableWrapperId == null || tableWrapperId.trim() == ''){
		tableWrapperId = '#tableWrapper';
	}else{
		tableWrapperId = '#' + tableWrapperId;
	}
	
	$(tableWrapperId + ' #kanList_chkSelectAll').click(
		function() {
			var valueToSet = false;

			if ($(this).attr('checked') == 'checked') {
				valueToSet = true;
			}

			$(tableWrapperId + ' input[id^="kanList_chkSelectRecord_"]').attr('checked', valueToSet);
			fillSelectedIds(tableWrapperId, cacheId);
		}
	);

	$(tableWrapperId + ' input[id^="kanList_chkSelectRecord_"]').click(
		function() {
			var selectorCheckboxes = $(tableWrapperId + ' input[id^="kanList_chkSelectRecord_"]');
			var isAllChecked = (selectorCheckboxes.size() == selectorCheckboxes.filter(':checked').size());
			$(tableWrapperId + ' #kanList_chkSelectAll').attr('checked', isAllChecked);
			fillSelectedIds(tableWrapperId, cacheId);
		}
	);
};

function fillSelectedIds(tableWrapperId, cacheId) {
	if(cacheId == null || cacheId.trim() == ''){
		cacheId = '#selectedIds';
	}else{
		cacheId = '#' + cacheId;
	}
	
	var selectedIdArray = $(cacheId).val().split(",");
	var selectedIdString = "";
	var selectedNumber = 0;

	$(tableWrapperId + ' input[id^="kanList_chkSelectRecord_"]').each(function(i) {
		var exist = false;
		var i = 0;

		for (i = 0; i < selectedIdArray.length; i++) {
			if (selectedIdArray[i] == $(this).val()) {
				if (this.checked) {
					exist = true;
				} else {
					selectedIdArray[i] = "";
				}
			}
		}

		if (exist == false && this.checked) {
			selectedIdArray[i] = $(this).val();
		}
	});

	for ( var i = 0; i < selectedIdArray.length; i++) {
		if (selectedIdArray[i] != "") {
			if (selectedNumber == 0) {
				selectedIdString = selectedIdString + selectedIdArray[i];
			} else {
				selectedIdString = selectedIdString + "," + selectedIdArray[i];
			}
			selectedNumber = selectedNumber + 1;
		}
	}

	$(cacheId).val(selectedIdString);
};

function kanCheckbox_init(tableWrapperId, cacheId) {
	if(tableWrapperId == null || tableWrapperId.trim() == ''){
		tableWrapperId = '#tableWrapper';
	}else{
		tableWrapperId = '#' + tableWrapperId;
	}
	
	if(cacheId == null || cacheId.trim() == ''){
		cacheId = '#selectedIds';
	}else{
		cacheId = '#' + cacheId;
	}
	
	if( $(cacheId).val() != null && $(cacheId).val() != ''){
		var selectedIdArray = $(cacheId).val().split(",");
		var checkboxRecords = $(tableWrapperId + ' input[id^="kanList_chkSelectRecord_"]');
		
		for ( var j = 0; j < selectedIdArray.length; j++) {
			checkboxRecords.each(function(i) {
				if($(this).val() == selectedIdArray[j]){
					$(this).attr('checked', true);
				}
			});
		}
		
		var isAllChecked = (checkboxRecords.size() == checkboxRecords.filter(':checked').size());
		$(tableWrapperId + ' #kanList_chkSelectAll').attr('checked', isAllChecked);
	}
};

function kanlist_dbclick(event,classForm){
	if(classForm==null){
		classForm="list_form";
	}
	 var selectedIds = $('.'+classForm+' input[id="selectedIds"]').val();
	 var checkboxValue=$(event).children("TD").children("input[id^='kanList_chkSelectRecord']").val();
	 if($(event).children("TD").children("input[id^='kanList_chkSelectRecord']").attr("checked")){
			$(event).children("TD").children("input[id^='kanList_chkSelectRecord']").removeAttr("checked");
		 	var selectedIdsValue=new Array();
		 	var num=0;
		 	if(selectedIds.indexOf(",")>=0){
				var arr=selectedIds.split(",");
				for(var i=0;i<arr.length;i++){
					if(arr[i]!=checkboxValue){
						selectedIdsValue[num]=arr[i];
						num++;
					}
				}
				$('.'+classForm+' input[id="selectedIds"]').val(selectedIdsValue.join(","));
			}else{
				$('.'+classForm+' input[id="selectedIds"]').val("");	
			}
	 }else{
			$(event).children("TD").children("input[id^='kanList_chkSelectRecord']").attr("checked",'true');
			if(selectedIds == null || selectedIds.trim() == ''){
				$('.'+classForm+' input[id="selectedIds"]').val(checkboxValue);
			}else{
				$('.'+classForm+' input[id="selectedIds"]').val(selectedIds+","+checkboxValue);
			}
	 }
}

function kanlist_dbclickDailogBox(event,classForm){
	 var selectedIds =  $('.'+classForm+' input[id="selectedIds"]').val();
	 var checkboxValue=$(event).children("TD").children("input[type=checkbox]").val();
	 if($(event).children("TD").children("input[type=checkbox]").attr("checked")){
		 	$("tbody tr").children("td").children("input[type=checkbox]").attr("checked",false);
			$(event).children("TD").children("input[type=checkbox]").removeAttr("checked");
	 }else{
		 $("tbody tr").children("td").children("input[type=checkbox]").attr("checked",false);
			$(event).children("TD").children("input[type=checkbox]").attr("checked",'true');
			 $('.'+classForm+' input[id="selectedIds"]').val(checkboxValue);
	 }
}


function kanList_deleteConfirm(cacheId,noSelectMsg,selectedMsg){
	var selectedSize = kanList_getSelectedLength(cacheId);
	if(selectedSize == 0){
		alert(noSelectMsg);
		return false;
	}
	
	return confirm(selectedMsg.replace('X',' ' + selectedSize + ' '));
};

function kanList_activeConfirm(cacheId){
	var selectedSize = kanList_getSelectedLength(cacheId);
	
	if(selectedSize == 0){
		alert('请选择要激活的记录！');
		return false;
	}
	
	return confirm('激活 ' + selectedSize + ' 条选中记录？');
};

function kanList_getSelectedLength(cacheId){
	if(cacheId == null || cacheId.trim() == ''){
		cacheId = '#selectedIds';
	}else{
		cacheId = '#' + cacheId;
	}
	
	var selectedIds = $(cacheId).val();
	var selectedSize = 0;
	
	if(selectedIds != ''){
		selectedSize = $(cacheId).val().split(",").length;
	}
	
	return selectedSize;
};
