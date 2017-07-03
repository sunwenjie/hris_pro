<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="rightRuleModalId">
    <div class="modal-header" id="rightRuleHeader">
        <a class="close" data-dismiss="modal" title="<bean:message bundle="public" key="button.close" />" onclick="$('#rightRuleModalId').addClass('hide');$('#shield').hide();">��</a>
        <label><bean:message bundle="security" key="security.position.group.set.right" /></label>
    </div>
    
    <div class="modal-body" id="modal-body">
	   	<div class="top">
	   		<input type="button" class="save" name="btnSaveRightRule" id="btnSaveRightRule" value="<bean:message bundle="public" key="button.save" />" onclick="disableBtn();saveRightRule();"/>
	    </div>
		<html:form action="moduleAction.do?proc=list_object" styleClass="module_form">
			<%= BaseAction.addToken( request ) %>
			<input type="hidden" name="moduleId" id="moduleId" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<div id="rightAndRuleDiv">
			</div>
			<ol class="static" id="combo_rule"></ol>
		</html:form>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// ����ģ̬����
	function showPopup(reloadTarget, disable){
		$('#rightRuleModalId').removeClass('hide');
    	$('#shield').show();

		if(disable){
			$(reloadTarget + ' input, ' + reloadTarget + ' select, ' + reloadTarget + ' textarea').attr('disabled', 'disabled');
		}
	};
	
	// ����ģ̬����
	function hidePopup(reloadTarget, disable){
		$('#rightRuleModalId').addClass('hide');
    	$('#shield').hide();

		if(disable){
			$(reloadTarget + ' input, ' + reloadTarget + ' select, ' + reloadTarget + ' textarea').attr('disabled', 'disabled');
		}
		
	};

	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#rightRuleModalId').addClass('hide');
	    	$('#shield').hide();
	    } 
	});
	
	// У��Right��Rule��Combo�Ƿ���ѡ������
	function checkImg(){
		
		// ���Right��Rule��Combo��ѡ������
		if($('#rightIdArray:checked').val() != null || $('#ruleIdArray').val() != null){
			$('img[id^="' + $('#moduleIdArray').val() + '"]').each(function(i) {
				$(this).attr('src', 'images/enable.png');
			});
		// ���Right��Rule��Comboû��ѡ������
		}else{
			$('img[id^="' + $('#moduleIdArray').val() + '"]').each(function(i) {
				$(this).attr('src', 'images/empty.png');
			});
		}
		
	};
	
	// ����Button��ť
	function disableBtn(){
		$('#btnSaveRightRule').attr('class', 'reset');
		$('.module_form input[type="button"]').each(function(i){
			$(this).prop('disabled', true);
			$(this).attr('class', 'reset');
		});
	};
	
	// ����Button��ť
	function enableBtn(){
		$('#btnSaveRightRule').attr('class', 'save');
		$('.module_form input[type="button"]').each(function(i){
			$(this).prop('disabled', false);
			$(this).attr('class', 'save');
		});
	};
</script>