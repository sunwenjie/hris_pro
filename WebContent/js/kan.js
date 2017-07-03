 //增加回车事件
document.onkeydown = function(e){
    var ev = document.all ? window.event : e;
    if(ev.keyCode==13) {

    	//焦点所在对象
    	var focueObj= document.activeElement;
    	if(isInSearchDiv(0,focueObj)){
//	    		$("#searchBtn").focus();
    		$("#searchBtn").trigger("onclick");
    	}
     }
};

//判断焦点是否在searchDiv 中 向上找五层
function isInSearchDiv(i,obj){
	if(i>5)return false;
	if(obj == null || obj==undefined)return false;
	if('searchDiv'==obj.id){
		return true;
	}else{
		return isInSearchDiv(i++,obj.parentNode);
	}
}
function resize_ol_li (){
	if(document.body.clientWidth < 640){
		$("ol.auto>li").css("width", "100%");
		$(".historyrecord ol li").css("width", "100%");
		$(".searchForm form ol li").css("width", "100%");
		$(".accountEntityHeaderSpan").hide();
		$('.menuSSUl').hide();
		$(".kanMenuDiv").removeClass("menu_s");
		$(".kanMenuDiv").addClass("menu_s_s");
		$('.menuLink').show();
	}else if(document.body.clientWidth < 960){
		$("ol.auto>li").css("width", "50%") ;
		$(".historyrecord ol li").css("width", "50%");
		$(".searchForm form ol li").css("width", "50%");
		$(".accountEntityHeaderSpan").show();
		$('.menuSSUl').hide();
		$(".kanMenuDiv").removeClass("menu_s");
		$(".kanMenuDiv").addClass("menu_s_s");
		$('.menuLink').show();
	}else if(document.body.clientWidth < 1280){
		$("ol.auto>li").css("width", "50%") ;
		$(".historyrecord ol li").css("width", "33%");
		$(".searchForm form ol li").css("width", "33%");
		$(".accountEntityHeaderSpan").show();
		$('.menuSSUl').show();
		$(".kanMenuDiv").removeClass("menu_s_s");
		$(".kanMenuDiv").addClass("menu_s");
		$('.menuLink').hide();
	}else{
		$("ol.auto>li").css("width", "50%") ;
		$(".historyrecord ol li").css("width", "25%");
		$(".searchForm form ol li").css("width", "25%");
		$(".accountEntityHeaderSpan").show();
		$('.menuSSUl').show();
		$(".kanMenuDiv").removeClass("menu_s_s");
		$(".kanMenuDiv").addClass("menu_s");
		$('.menuLink').hide();
	}
	
	bindEvent();
};

function closeWindow() {
	if (confirm('退出系统？'))
		window.close();
	else
		return;
};


function linkBatch(url) {

	if ($("input#selectedIds").val() != null
			&& $("input#selectedIds").val() != '') {
		var selectedIdArray = $("input#selectedIds").val();
		var parameters = 'ajax=true&date=' + new Date();
		var ss = getQueryString(encodeURI(url), "batchId");
		if (selectedIdArray.indexOf(ss) > -1) {
			url = url + "&selected=1";
		}
	}
	
	window.location.href = url;
};


function getQueryString(url,name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }

function link(url) {
	window.location.href = url;
};
	
function encodedLink(url) {
	window.location.href = encodeURI(encodeURI(url));
};
	
function linkForm(formClass, subAction, action, parameters) {
	var originalSubAction = $('.' + formClass + ' input#subAction').val();
	
	var url = "";
		
	if(action != null){
		url = action;
	}else{
		url = $("." + formClass).attr('action');
	}
	
	// 改写当前的SubAction
	if(subAction != null){
		$('.' + formClass + ' input#subAction').val(subAction);
	}
		
	url = url + '&' + encodeURI(encodeURI(getFormString(formClass) + '&' + parameters + '&ajax=true&date=' + new Date()));
	
	
	// 还原SubAction
	if(subAction != null){
		$('.' + formClass + ' input#subAction').val(originalSubAction);
	}
		
	window.location.href = url;
};
	
function back() {
	history.back();
};

function submit(formClass){
	$('#shield img').show();
	$('#shield').show();
	$('.' + formClass).submit();
};	

function submitFormWithActionAndCallbackAndDecode(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, js, isEncode) {
	if(subAction != null){
		$('.' + formClass + ' input#subAction').val(subAction);
	}
	if(page != null){
		$('.' + formClass + ' #page').val(page);
	}
	if(sortColumn != null){
		$('.' + formClass + ' #sortColumn').val(sortColumn);
	}
	if(sortOrder != null){
		$('.' + formClass + ' #sortOrder').val(sortOrder);
	}

	if(reloadTarget != null){
		var url = "";
		
		if(action != null){
			url = action;
		}else{
			url = $("." + formClass).attr('action');
		}

		var parameters = getFormString(formClass) + '&ajax=true&date=' + new Date();

		if(isEncode || subAction == 'issueObjects' || subAction == 'deleteObject' || subAction == 'deleteObjects' || subAction == 'approveObject' || subAction == 'approveObjects' || subAction == 'confirmObject' || subAction == 'confirmObjects' || subAction == 'submitObject' || subAction == 'submitObjects' || subAction == 'rollbackObject' || subAction == 'rollbackObjects' || subAction == 'agreeObjects' || subAction == 'refuseObjects' || subAction == null){
			parameters = encodeURI(encodeURI(parameters));
		}
		//增加遮罩
		if(subAction == 'agreeObjects' ||subAction == 'refuseObjects' ||subAction == 'issueObjects' || subAction == 'deleteObject' || subAction == 'deleteObjects' || subAction == 'approveObject' || subAction == 'approveObjects' || subAction == 'confirmObject' || subAction == 'confirmObjects' || subAction == 'submitObject' || subAction == 'submitObjects' || subAction == 'rollbackObject' || subAction == 'rollbackObjects'){
			 $('#shield img').show();
			 $('#shield').show();
		}

		$.ajax({ url: url, type: 'POST', data: parameters, success: function(html){
			$("#" + reloadTarget).html(html);
			if(subAction == 'agreeObjects' ||subAction == 'refuseObjects' ||subAction == 'issueObjects' || subAction == 'deleteObject' || subAction == 'deleteObjects' || subAction == 'approveObject' || subAction == 'approveObjects' || subAction == 'confirmObject' || subAction == 'confirmObjects' || subAction == 'submitObject' || subAction == 'submitObjects' || subAction == 'rollbackObject' || subAction == 'rollbackObjects'){
				 $('#shield img').hide();
				 $('#shield').hide();
			}
			$UserDefineMsg = $("#_USER_DEFINE_MSG");
			if($UserDefineMsg.length>0){
				$('#messageWrapper').html('<div class="' + $UserDefineMsg.attr('class') + '">'+$UserDefineMsg.text()+'<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
				messageWrapperFada();
				$('.' + formClass + ' input#subAction').val('');
			}else{
				if(subAction == 'deleteObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">删除成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'rollbackObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">退回成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'modifyObject' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">编辑成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'confirmObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">确认成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'submitObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">提交成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'submitObject' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">提交成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'agreeObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">同意成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'refuseObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">不同意成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}
			}	
			if(typeof(js)=='string'){
			    eval(js);
			}
				
			kanList_init();
			kanCheckbox_init();
		}});
		
		if($('.' + formClass + ' input#subAction').val() == 'deleteObjects'){
			$('.' + formClass + ' #selectedIds').val('');
		}
	}else{
		submit(formClass);
	}
};

function submitFormWithActionAndCallback(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, js) {
	submitFormWithActionAndCallbackAndDecode(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, js, false);
};

function submitFormWithAction(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action) {
	submitFormWithActionAndCallbackAndDecode(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, null, false);
};

function submitForm(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, js, isEncode) {
	submitFormWithActionAndCallbackAndDecode(formClass, subAction, page, sortColumn, sortOrder, reloadTarget, action, js, isEncode);
};

function loadHtmlWithRecall(reloadTarget, action, disable, js){
	$.ajax({ url: action + '&date=' + new Date(), success: function(html){
		$(reloadTarget).html(html);
		resize_ol_li();

		if(disable){
			$(reloadTarget + ' input, ' + reloadTarget + ' select, ' + reloadTarget + ' textarea').attr('disabled', 'disabled');
		}
		
		if(typeof(js)=='string'){
	    	eval(js);
		}
		if(jQuery.isFunction(js)){
			   js(html);
		}
	}});
};
	
function loadHtml(reloadTarget, action, disable, js){
	$.ajax({ url: action + '&date=' + new Date(), success: function(html){
		$(reloadTarget).html(html);
		resize_ol_li();

		if(disable){
			$(reloadTarget + ' input, ' + reloadTarget + ' select, ' + reloadTarget + ' textarea').attr('disabled', 'disabled');
		}
		
		if(js != null && typeof(js) == 'string'){
	    	eval(js);
		}
	    if(jQuery.isFunction(js)){
		   js(html);
		}
	}});
};
	
function loadHistoryRecord(historyId, action) {
	if($('#historyrecord_' + historyId).html() == ''){
		loadHtml('#historyrecord_' + historyId, action + '&historyId=' + historyId, false);
	}else{
		$('#historyrecord_' + historyId).html('');
	}
};

function resetForm(formClass){
	$("." + formClass).reset();
};
	
function disableForm(formClass){
	$('form.' + formClass + ' input, form.' + formClass + ' select, form.' + formClass + ' textarea').attr('disabled', 'disabled');
    $('form.' + formClass + ' input.calendar').datepicker('disable');
    $('form.' + formClass + ' input[type=button]:not(.internal)').removeAttr('disabled');
    disableLink(formClass);
};
	
function disableDiv(divClass){
	$('div.' + divClass + ' input, div.' + divClass + ' select, div.' + divClass + ' textarea').attr('disabled', 'disabled');
    $('div.' + divClass + ' input.calendar').datepicker('disable');
    $('div.' + divClass + ' input[type=button]').removeAttr('disabled');
};
	
function disableLink(formClass){
	$('form.' + formClass + ' a.kanhandle').each(function(){
		$(this).attr('disabled', true);
		
		if($(this).attr("onclick") != null && $(this).attr("onclick").indexOf('X') < 0){
			$(this).attr("onclick", 'X' + $(this).attr("onclick"));
		}
		
		$(this).addClass("disabled");
	});
};

function disableLinkById(id){
	$(id).attr('disabled', true);
	if($(id).attr("onclick") != null && $(id).attr("onclick").indexOf('X') < 0){
		$(id).attr("onclick", 'X' + $(id).attr("onclick"));
	}
	$(id).addClass("disabled");
};
	
function enableForm(formClass){
	$('form.' + formClass + ' input, form.' + formClass + ' select, form.' + formClass + ' textarea').removeAttr('disabled');
    $('form.' + formClass + ' input.calendar').datepicker('enable');
    enableLink(formClass);
};

function enableLink(formClass){
	$('form.' + formClass + ' a.kanhandle').each(function(){
    	$(this).attr('disabled', false);

    	if($(this).attr("onclick") != null && $(this).attr("onclick").length > 0 && $(this).attr("onclick").indexOf('X') >= 0){
    		$(this).attr("onclick", $(this).attr("onclick").substring(1));
    	}
    	
		$(this).removeClass("disabled");
	});
};

function enableLinkById(id){
	$(id).attr('disabled', false);
	
	if($(id).attr("onclick") != null && $(id).attr("onclick").length > 0 && $(id).attr("onclick").indexOf('X') >= 0){
		$(id).attr("onclick", $(id).attr("onclick").substring(1));
	}
	
	$(id).removeClass("disabled");
};
	
function getFormString(formClass){
	var formElements = $('form.' + formClass + ' input, form.' + formClass + ' select, form.' + formClass + ' textarea');
	var formString = '';
	
	for ( var i = 0; i < formElements.length; i++) {
		if(formElements[i].getAttribute('type') != 'button' && (formElements[i].getAttribute('type') != 'checkbox' || (formElements[i].checked && !formElements[i].disabled))){
			var value = $(formElements[i]).val();
			
			if(value == null || value == '' || value == 'null' || value == "undefined" || value == "输入关键字查看提示..."){
				value = '';
			}
			
			if(formString == ''){
				formString = formElements[i].getAttribute("name") + '=' + value;
			}else{
				formString = formString + '&' + formElements[i].getAttribute("name") + '=' + value;
			}
		}
	}
	
	return formString;
};

function provinceChange(provinceClass, subAction, cityId, id_name){ 
	if(id_name == '' || id_name == null){
		id_name = 'cityId';
	}
	$('.' + id_name).remove();
	$.ajax({ url: 'locationAction.do?proc=list_cities_html&provinceId=' + $('.' + provinceClass).val() + '&id_name=' + id_name + '&date=' + new Date(), success: function(html){
		if(html.trim() != ''){
			$('.' + provinceClass).after(html);
			$('.' + provinceClass).addClass("small");
			$('.' + id_name).addClass("small");
			
			if(subAction == 'searchObject'){
				$('.' + provinceClass).prevAll('label:first').width('265px');
			}
			
			if(subAction == 'viewObject'){
				$('.' + id_name).attr('disabled', 'disabled');
			}
			
			$('.' + id_name).val(cityId);
		}else{
			$('.' + provinceClass).removeClass("small");
		}
	}});
};

function branchChange(branchClass, subAction, positionId, id_name){ 
	$.ajax({ url: 'branchAction.do?proc=list_positions_html&branchId=' + $('.' + branchClass).val() + '&id_name=' + id_name + '&date=' + new Date(), success: function(html){
		$('.' + id_name).html(html);
		$('.' + id_name).val(positionId);
	}});
};

// 树节点展开与收缩
function kantreeNodeClick(id) {
    if($('li[id^="' + id + '_"]').is(":visible")) {
    	$('li[id^="' + id + '_"]').hide();
    	$('#IMG' + id).attr('src', 'images/plus.gif');
    } else {
    	$('li[id^="' + id + '_"]').show();
    	$('#IMG' + id).attr('src', 'images/minus.gif');
    }
};

// Tab切换
function changeTab(cursel, n){ 
	for(i = 1; i <= n; i++){ 
		$('#tabMenu' + i).removeClass('hover');
		$('#tabContent' + i).hide();
	} 
	
	$('#tabMenu' + cursel).addClass('hover');
	$('#tabContent' + cursel).show();
};

function messageWrapperFada(){
	$("div.fadable").delay(5000)
    .fadeOut("slow", function () {
        $("div.fadable").remove();
    }); 
};

function removeMessageWrapperFada(){
	$("div.fadable").remove();
};

function showHistory(){
	if( $('#history-results').is(":visible") ) {
		$('#history-results').hide();
	} else {
		$('#history-results').show();
	}
};
	
function removeExtraObject(targetURL, dom, numberId){
	var msg = lang_en ? "Are you sure to delete the selected items?" : "确定删除选中项？";
	if( !confirm(msg) )
	{ 
		return false; 
	}
	
	if(targetURL != null && targetURL != ''){
		//先删除、正确删除则Remove对应的DOM 
		$.ajax({url: targetURL + "&date=" + new Date(),
			dataType : 'json',
			success: function(data, status){
				if(data.status == 'success'){
					$('#messageWrapper').html('<div class="message success fadable">删除成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					removeExtraObjectDom(dom, numberId);
				}
			}
		});
	}else{
		removeExtraObjectDom(dom, numberId);
	}
};

//jzy add 2014/4/10 解决传递Form中同名字段传递参数问题
function removeExtraObjectByFormPost(targetURL, dom, numberId){
	var msg = lang_en ? "Are you sure to delete the selected items?" : "确定删除选中项？";
	if( !confirm(msg) )
	{ 
		return false; 
	}else{
		if(targetURL != null && targetURL != ''){
			removeExtraObjectDom(dom, numberId);
			//先删除、正确删除则Remove对应的DOM 
			$.ajax({
				url: targetURL + "&date=" + new Date(), 
				type: 'POST', 
				data: $(".submitForm").serialize(),
				dataType: 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">删除成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
					}
				} 
			});
		}else{
			removeExtraObjectDom(dom, numberId);
		}
	}
};

function removeExtraObjectDom(dom, numberId){
	$(dom).parent().remove();
   	if(numberId != null){
   		$(numberId).html(eval(parseInt($(numberId).html()) - 1));
 	}
};

function addExtraObject(targetURL, primaryKey, ajax, numberId){
	if(primaryKey){
		if(ajax){
			$.ajax({url: targetURL + "&date=" + new Date(),
				dataType : 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
						addExtraObjectDom(numberId);
					}
				}
			});
		}
		// 跳转
		else{
			link( targetURL );
		}
	}else{
		//设置重定向跳转的url
		$("#forwardURL").val(targetURL);
		// 尝试提交表单
		$('#btnEdit').click();
		// 定位至第一个错误处
		if($('.error').size()>0){
			$('.error')[0].focus();
		}
	}
};

//jzy add 2014/4/10 解决传递Form中同名字段传递参数问题
function addExtraObjectByFormPost(targetURL, primaryKey, ajax, numberId){
	if(primaryKey){
		if(ajax){
			$.ajax({
				url: targetURL+ "&date=" + new Date(),
				type: 'POST', 
				data: $(".submitForm").serialize(),
				dataType: 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">添加成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
						addExtraObjectDom(numberId);
					}
				} 
			});
		}
		// 跳转
		else{
			link( targetURL );
		}
	}else{
		//设置重定向跳转的url
		$("#forwardURL").val(targetURL);
		// 尝试提交表单
		$('#btnEdit').click();
		// 定位至第一个错误处
		if($('.error').size()>0){
			$('.error')[0].focus();
		}
	}
};

function addExtraObjectDom(numberId){
   	if(numberId != null){
   		$(numberId).html(eval(parseInt($(numberId).html()) + 1));
 	}
};

function popupWindowDrag(dragPoint, dom){
    var oWin1 = document.getElementById( dom );
    var oH1 = document.getElementById( dragPoint );
    var bDrag1 = false;
    var disX1 = disY1 = 0;
    
    oH1.onmousedown = function(event) {
        var event = event || window.event;
        bDrag1 = true;
        disX1 = event.clientX - oWin1.offsetLeft;
        disY1 = event.clientY - oWin1.offsetTop;
        this.setCapture && this.setCapture();
        return false;
    };
    
    document.onmousemove = function(event) {
        if (!bDrag1) return;
        var event = event || window.event;
        var iL = event.clientX - disX1;
        var iT = event.clientY - disY1;
        var maxL = document.documentElement.clientWidth - oWin1.offsetWidth;
        var maxT = document.documentElement.clientHeight - oWin1.offsetHeight;
        iL = iL < 0 ? 0 : iL;
        iL = iL > maxL ? maxL: iL;
        iT = iT < 0 ? 0 : iT;
        iT = iT > maxT ? maxT: iT;

        oWin1.style.marginTop = oWin1.style.marginLeft = 0;
        oWin1.style.left = iL + "px";
        oWin1.style.top = iT + "px";
        return false;
    };
        
    document.onmouseup = window.onblur = oH1.onlosecapture = function() {
        bDrag1 = false;
        oH1.releaseCapture && oH1.releaseCapture();
    };    
};

//使用固定列
function useFixColumn(cNum){
	var results = $("#resultTable tbody tr").size();
	if( results > 0 ) {
		$("#resultTable").fixTable({
			fixColumn:(cNum ==''||cNum==undefined)?3:cNum,//固定列数
			width:0,//显示宽度
			height: $("#resultTable").outerHeight()//显示高度
		});
	}
};

function pageForward(useFixColumn) {
	var value = Number($('#forwardPage_render').val());
	// 如果页数无效自动跳转到第一页
	if(/[^0-9]+/.test(value)){
		value = 1;
	}
	var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
	submitForm('list_form', null, forwardPage_render, null, null, 'tableWrapper', null , ( useFixColumn == '1' ? 'useFixColumn();' : null ) );
};

function submitFormForDownload(formClass){
	var initSubAction = $('.'+ formClass + ' .subAction').val();
	var initURLAction = $('.'+ formClass).attr('action');
	$('.' + formClass + ' .subAction').val('downloadObjects'); 
	$('.' + formClass).attr( 'action', initURLAction + '&ajax=true&date=' + new Date() ); 
	$('.' + formClass).submit();
	$('.' + formClass + ' .subAction').val( initSubAction ); 
	$('.' + formClass).attr( 'action', initURLAction ); 
};

/* ==========快速设置columnIndex移动按钮js ========== */
/* ==========Add by siuvan.xia @2014-07-23 ========== */
var x = null;
var listObj = null;
//鼠标按下不放时的操作
function setTimeStart(type) {
	listObj = document.getElementById('columns');
	//超过0.3秒启动连续的向上(下)的操作
	if (type == "up") {
		x = setTimeout(upListItem, 300);
	} else {
		x = setTimeout(downListItem, 300);
	}
};

//将选中item向上
function upListItem() {
	var selIndex = listObj.selectedIndex;
	if (selIndex < 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("请先选中一项！");
		return;
	}
	if (selIndex == 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("已经移到第一位！");
		return;
	}
	var selValue = listObj.options[selIndex].value;
	var selText = listObj.options[selIndex].text;
	listObj.options[selIndex].value = listObj.options[selIndex - 1].value;
	listObj.options[selIndex].text = listObj.options[selIndex - 1].text;
	listObj.options[selIndex - 1].value = selValue;
	listObj.options[selIndex - 1].text = selText;
	listObj.selectedIndex = selIndex - 1;
	if (selIndex + 1 > 0) {
		x = setTimeout(upListItem, 200);
	}
};

//将选中item向下
function downListItem() {
	var selIndex = listObj.selectedIndex;
	if (selIndex < 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("请先选中一项！");
		return;
	}
	if (selIndex == listObj.options.length - 1) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("已经移到最后一位！");
		return;
	}
	var selValue = listObj.options[selIndex].value;
	var selText = listObj.options[selIndex].text;
	listObj.options[selIndex].value = listObj.options[selIndex + 1].value;
	listObj.options[selIndex].text = listObj.options[selIndex + 1].text;
	listObj.options[selIndex + 1].value = selValue;
	listObj.options[selIndex + 1].text = selText;
	listObj.selectedIndex = selIndex + 1;
	if (selIndex + 1 < listObj.options.length - 1) {
		x = setTimeout(downListItem, 200);
	}
};
/* ==========快速设置columnIndex移动按钮js End========== */

/* ==========多选下拉框js start========== */
function moveToRight(){
	//先判断是否有选中
	if(!$("#leftSelect option").is(":selected")){			
		alert("请选择需要移动的选项")
	}
	//获取选中的选项，删除并追加给对方
	else{
		$('#leftSelect option:selected').appendTo('#rightSelect');
	}	
};

function moveToLeft(){
	//先判断是否有选中
	if(!$("#rightSelect option").is(":selected")){			
		alert("请选择需要移动的选项")
	}
	//获取选中的选项，删除并追加给对方
	else{
		$('#rightSelect option:selected').appendTo('#leftSelect');
	}	
};

function moveAllToRight(){
	//获取全部的选项,删除并追加给对方
	$('#leftSelect option').appendTo('#rightSelect');
};

function moveAllToLeft(){
	//获取全部的选项,删除并追加给对方
	$('#rightSelect option').appendTo('#leftSelect');
};

function moveToRightForDoubleClick(){
	$('#leftSelect').dblclick(function(){ //绑定双击事件
		//获取全部的选项,删除并追加给对方
		$("option:selected",this).appendTo('#rightSelect'); //追加给对方
	});
};

function moveToLeftForDoubleClick(){
	$('#rightSelect').dblclick(function(){ //绑定双击事件
		//获取全部的选项,删除并追加给对方
		$("option:selected",this).appendTo('#leftSelect'); //追加给对方
	});
};

/* ==========多选下拉框js end========== */
// 点击（dl > dt > a）收缩（dl > dd）
function shrinkDD( dom ){
	var parentDom = $(dom).parents('dl');
	if($(dom).parents('dl').children('dd').is(':visible')){
		$(dom).parents('dl').children('dd').hide();
	}else{
		$(dom).parents('dl').children('dd').show();
	}
};

// 点击<a>标签，隐藏或显示<div>
function div_display( divClass ){
	$('#' + divClass + '-LINK' ).click( function(){
		if($('#' + divClass).is(':visible')){
			$('#' + divClass).hide();
		}else{
			$('#' + divClass).show();
		}
	});
};

// 获到顶部
function gotoTop(min_height){ 
	//预定义返回顶部的html代码，它的css样式默认为不显示 
	var gotoTop_html = '<div id="gotoTop">↑</div>'; 
	//将返回顶部的html代码插入页面上id为page的元素的末尾 
	$("#content").append(gotoTop_html); 
	//定义返回顶部点击向上滚动的动画 
	$("#gotoTop").click(function(){
		$('html,body').animate({scrollTop:0},700); 
	}).hover(//为返回顶部增加鼠标进入的反馈效果，用添加删除css类实现 
		function(){$(this).addClass("hover");}, 
		function(){$(this).removeClass("hover"); 
	}); 
	//获取页面的最小高度，无传入值则默认为600像素 
	min_height ? min_height = min_height : min_height = 600; 
		//为窗口的scroll事件绑定处理函数 
	$(window).scroll(function(){ 
		//获取窗口的滚动条的垂直位置 
		var s = $(window).scrollTop(); 
		//当窗口的滚动条的垂直位置大于页面的最小高度时，让返回顶部元素渐现，否则渐隐 
		if( s > min_height){ 
			$("#gotoTop").fadeIn(100); 
		}else{ 
			$("#gotoTop").fadeOut(200); 
		}; 
	}); 
}; 

