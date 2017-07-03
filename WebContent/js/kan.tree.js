/***
 * 通过domObj对象获得对应的module对象（json格式）
 * @param domObj
 * @returns
 */
function getElement(domObj){
		var t = $(domObj).siblings("[type=hidden]").val();
		var json; eval("json="+t);
		return json;
	}

function kanTree_deleteConfirm(treeClass,noSelectMsg,selectedMsg){
	var selectedSize = $('.' + treeClass + ' input[type=checkbox]:checked').length;
	if(selectedSize == 0){
		alert(noSelectMsg);
		return false;
	}
	return confirm(selectedMsg.replace('X',' ' + selectedSize + ' '));
};