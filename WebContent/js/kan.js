 //���ӻس��¼�
document.onkeydown = function(e){
    var ev = document.all ? window.event : e;
    if(ev.keyCode==13) {

    	//�������ڶ���
    	var focueObj= document.activeElement;
    	if(isInSearchDiv(0,focueObj)){
//	    		$("#searchBtn").focus();
    		$("#searchBtn").trigger("onclick");
    	}
     }
};

//�жϽ����Ƿ���searchDiv �� ���������
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
	if (confirm('�˳�ϵͳ��'))
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
	
	// ��д��ǰ��SubAction
	if(subAction != null){
		$('.' + formClass + ' input#subAction').val(subAction);
	}
		
	url = url + '&' + encodeURI(encodeURI(getFormString(formClass) + '&' + parameters + '&ajax=true&date=' + new Date()));
	
	
	// ��ԭSubAction
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
		//��������
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
					$('#messageWrapper').html('<div class="message success fadable">ɾ���ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'rollbackObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">�˻سɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'modifyObject' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">�༭�ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'confirmObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">ȷ�ϳɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'submitObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">�ύ�ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'submitObject' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">�ύ�ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'agreeObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">ͬ��ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					$('.' + formClass + ' input#subAction').val('');
				}else if(subAction == 'refuseObjects' && !$('#definedMessage').val()){
					$('#messageWrapper').html('<div class="message success fadable">��ͬ��ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
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
			
			if(value == null || value == '' || value == 'null' || value == "undefined" || value == "����ؼ��ֲ鿴��ʾ..."){
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

// ���ڵ�չ��������
function kantreeNodeClick(id) {
    if($('li[id^="' + id + '_"]').is(":visible")) {
    	$('li[id^="' + id + '_"]').hide();
    	$('#IMG' + id).attr('src', 'images/plus.gif');
    } else {
    	$('li[id^="' + id + '_"]').show();
    	$('#IMG' + id).attr('src', 'images/minus.gif');
    }
};

// Tab�л�
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
	var msg = lang_en ? "Are you sure to delete the selected items?" : "ȷ��ɾ��ѡ���";
	if( !confirm(msg) )
	{ 
		return false; 
	}
	
	if(targetURL != null && targetURL != ''){
		//��ɾ������ȷɾ����Remove��Ӧ��DOM 
		$.ajax({url: targetURL + "&date=" + new Date(),
			dataType : 'json',
			success: function(data, status){
				if(data.status == 'success'){
					$('#messageWrapper').html('<div class="message success fadable">ɾ���ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
					messageWrapperFada();
					removeExtraObjectDom(dom, numberId);
				}
			}
		});
	}else{
		removeExtraObjectDom(dom, numberId);
	}
};

//jzy add 2014/4/10 �������Form��ͬ���ֶδ��ݲ�������
function removeExtraObjectByFormPost(targetURL, dom, numberId){
	var msg = lang_en ? "Are you sure to delete the selected items?" : "ȷ��ɾ��ѡ���";
	if( !confirm(msg) )
	{ 
		return false; 
	}else{
		if(targetURL != null && targetURL != ''){
			removeExtraObjectDom(dom, numberId);
			//��ɾ������ȷɾ����Remove��Ӧ��DOM 
			$.ajax({
				url: targetURL + "&date=" + new Date(), 
				type: 'POST', 
				data: $(".submitForm").serialize(),
				dataType: 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">ɾ���ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
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
						$('#messageWrapper').html('<div class="message success fadable">��ӳɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
						addExtraObjectDom(numberId);
					}
				}
			});
		}
		// ��ת
		else{
			link( targetURL );
		}
	}else{
		//�����ض�����ת��url
		$("#forwardURL").val(targetURL);
		// �����ύ��
		$('#btnEdit').click();
		// ��λ����һ������
		if($('.error').size()>0){
			$('.error')[0].focus();
		}
	}
};

//jzy add 2014/4/10 �������Form��ͬ���ֶδ��ݲ�������
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
						$('#messageWrapper').html('<div class="message success fadable">��ӳɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
						addExtraObjectDom(numberId);
					}
				} 
			});
		}
		// ��ת
		else{
			link( targetURL );
		}
	}else{
		//�����ض�����ת��url
		$("#forwardURL").val(targetURL);
		// �����ύ��
		$('#btnEdit').click();
		// ��λ����һ������
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

//ʹ�ù̶���
function useFixColumn(cNum){
	var results = $("#resultTable tbody tr").size();
	if( results > 0 ) {
		$("#resultTable").fixTable({
			fixColumn:(cNum ==''||cNum==undefined)?3:cNum,//�̶�����
			width:0,//��ʾ���
			height: $("#resultTable").outerHeight()//��ʾ�߶�
		});
	}
};

function pageForward(useFixColumn) {
	var value = Number($('#forwardPage_render').val());
	// ���ҳ����Ч�Զ���ת����һҳ
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

/* ==========��������columnIndex�ƶ���ťjs ========== */
/* ==========Add by siuvan.xia @2014-07-23 ========== */
var x = null;
var listObj = null;
//��갴�²���ʱ�Ĳ���
function setTimeStart(type) {
	listObj = document.getElementById('columns');
	//����0.3����������������(��)�Ĳ���
	if (type == "up") {
		x = setTimeout(upListItem, 300);
	} else {
		x = setTimeout(downListItem, 300);
	}
};

//��ѡ��item����
function upListItem() {
	var selIndex = listObj.selectedIndex;
	if (selIndex < 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("����ѡ��һ�");
		return;
	}
	if (selIndex == 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("�Ѿ��Ƶ���һλ��");
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

//��ѡ��item����
function downListItem() {
	var selIndex = listObj.selectedIndex;
	if (selIndex < 0) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("����ѡ��һ�");
		return;
	}
	if (selIndex == listObj.options.length - 1) {
		if (x != null) {
			clearTimeout(x);
		}
		alert("�Ѿ��Ƶ����һλ��");
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
/* ==========��������columnIndex�ƶ���ťjs End========== */

/* ==========��ѡ������js start========== */
function moveToRight(){
	//���ж��Ƿ���ѡ��
	if(!$("#leftSelect option").is(":selected")){			
		alert("��ѡ����Ҫ�ƶ���ѡ��")
	}
	//��ȡѡ�е�ѡ�ɾ����׷�Ӹ��Է�
	else{
		$('#leftSelect option:selected').appendTo('#rightSelect');
	}	
};

function moveToLeft(){
	//���ж��Ƿ���ѡ��
	if(!$("#rightSelect option").is(":selected")){			
		alert("��ѡ����Ҫ�ƶ���ѡ��")
	}
	//��ȡѡ�е�ѡ�ɾ����׷�Ӹ��Է�
	else{
		$('#rightSelect option:selected').appendTo('#leftSelect');
	}	
};

function moveAllToRight(){
	//��ȡȫ����ѡ��,ɾ����׷�Ӹ��Է�
	$('#leftSelect option').appendTo('#rightSelect');
};

function moveAllToLeft(){
	//��ȡȫ����ѡ��,ɾ����׷�Ӹ��Է�
	$('#rightSelect option').appendTo('#leftSelect');
};

function moveToRightForDoubleClick(){
	$('#leftSelect').dblclick(function(){ //��˫���¼�
		//��ȡȫ����ѡ��,ɾ����׷�Ӹ��Է�
		$("option:selected",this).appendTo('#rightSelect'); //׷�Ӹ��Է�
	});
};

function moveToLeftForDoubleClick(){
	$('#rightSelect').dblclick(function(){ //��˫���¼�
		//��ȡȫ����ѡ��,ɾ����׷�Ӹ��Է�
		$("option:selected",this).appendTo('#leftSelect'); //׷�Ӹ��Է�
	});
};

/* ==========��ѡ������js end========== */
// �����dl > dt > a��������dl > dd��
function shrinkDD( dom ){
	var parentDom = $(dom).parents('dl');
	if($(dom).parents('dl').children('dd').is(':visible')){
		$(dom).parents('dl').children('dd').hide();
	}else{
		$(dom).parents('dl').children('dd').show();
	}
};

// ���<a>��ǩ�����ػ���ʾ<div>
function div_display( divClass ){
	$('#' + divClass + '-LINK' ).click( function(){
		if($('#' + divClass).is(':visible')){
			$('#' + divClass).hide();
		}else{
			$('#' + divClass).show();
		}
	});
};

// �񵽶���
function gotoTop(min_height){ 
	//Ԥ���巵�ض�����html���룬����css��ʽĬ��Ϊ����ʾ 
	var gotoTop_html = '<div id="gotoTop">��</div>'; 
	//�����ض�����html�������ҳ����idΪpage��Ԫ�ص�ĩβ 
	$("#content").append(gotoTop_html); 
	//���巵�ض���������Ϲ����Ķ��� 
	$("#gotoTop").click(function(){
		$('html,body').animate({scrollTop:0},700); 
	}).hover(//Ϊ���ض�������������ķ���Ч���������ɾ��css��ʵ�� 
		function(){$(this).addClass("hover");}, 
		function(){$(this).removeClass("hover"); 
	}); 
	//��ȡҳ�����С�߶ȣ��޴���ֵ��Ĭ��Ϊ600���� 
	min_height ? min_height = min_height : min_height = 600; 
		//Ϊ���ڵ�scroll�¼��󶨴����� 
	$(window).scroll(function(){ 
		//��ȡ���ڵĹ������Ĵ�ֱλ�� 
		var s = $(window).scrollTop(); 
		//�����ڵĹ������Ĵ�ֱλ�ô���ҳ�����С�߶�ʱ���÷��ض���Ԫ�ؽ��֣������� 
		if( s > min_height){ 
			$("#gotoTop").fadeIn(100); 
		}else{ 
			$("#gotoTop").fadeOut(200); 
		}; 
	}); 
}; 

