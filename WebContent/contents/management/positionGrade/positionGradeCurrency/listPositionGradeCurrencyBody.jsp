<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.PositionGradeCurrencyAction"%>
<%@ page import="com.kan.base.web.actions.management.PositionGradeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%					
	final PagedListHolder positionGradeCurrencyHolder = ( PagedListHolder )request.getAttribute( "positionGradeCurrencyHolder" );
%>

<div id="content">
	<!-- PositionGrade-information -->
	<div class="box" id="PositionGrade-information">
		<div class="head">
			<label id="pageTitle">
				<logic:equal value="1" name="role">ְλ�ȼ����ⲿ����ѯ</logic:equal>
				<logic:equal value="2" name="role">ְλ�ȼ���ѯ</logic:equal>
			</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEditPositionGrade" id="btnEditPositionGrade" value="<bean:message bundle="public" key="button.edit" />" /> 
				</kan:auth>
				
				<kan:auth right="list" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<!-- Include table jsp ���༭header��Ϣ��jsp�ļ���  --> 
			<jsp:include page="/contents/management/positionGrade/positionGrade/form/managePositionGradeForm.jsp" flush="true"/> 
		</div>
	</div>
	
	<!-- PositionGradeCurrency-information -->
	<div class="box" id="PositionGradeCurrencyDiv" style="display: none;">
		<!-- Inner -->
		<div class="head">
			<label>ָ������</label>
		</div>						
		<div class="inner">		
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DETAIL">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=PositionGradeCurrencyAction.accessAction%>">
					<input type="button" id="btnEditPositionGradeCurrency" name="btnEditPositionGradeCurrency" value="<bean:message bundle="public" key="button.add" />" />
				</kan:auth>
				
				<kan:auth right="list" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelPositionGradeCurrency" id="btnCancelPositionGradeCurrency" value="ȡ��" style="display:none" />
				</kan:auth>	
				
				<kan:auth right="delete" action="<%=PositionGradeCurrencyAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listPositionGradeCurrency_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- positionGradeCurrencyFormWrapper -->
			<div id="positionGradeCurrencyFormWrapper" style="display:none" >
				<jsp:include page="/contents/management/positionGrade/positionGradeCurrency/form/managePositionGradeCurrencyForm.jsp" flush="true"/> 
			</div>
			
			<!-- if exits bean positionGradeCurrencyHolder -->		
			<logic:notEmpty name="positionGradeCurrencyHolder">						
				<html:form action="mgtPositionGradeCurrencyAction.do?proc=list_object" styleClass="listPositionGradeCurrency_form">
					<input type="hidden" name="positionGradeId" value="<bean:write name="mgtPositionGradeForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="positionGradeCurrencyHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="positionGradeCurrencyHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="positionGradeCurrencyHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="positionGradeCurrencyHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />		
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����table��Ӧ��jsp�ļ� --> 
					<jsp:include page="/contents/management/positionGrade/positionGradeCurrency/table/listPositionGradeCurrencyTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>	
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_PositionGrades').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();

		// �ֶ�OnChange�¼�
		$('.managePositionGradeCurrency_currencyType').change(function(){
			var flag = true;
			$('input[id^="currencyType"]').each(function(i) {
	    		if($(this).val() ==  $('.managePositionGradeCurrency_currencyType').val()){    
	    			alert('�û��������Ѿ����ڣ�������ѡ��');
					$('.managePositionGradeCurrency_currencyType').val('0');
					flag = false;
	    		}	
	    	});
			
			if(flag){
				var currencyType = $('.managePositionGradeCurrency_currencyType').val();
				var positionGradeId = '<bean:write name="mgtPositionGradeForm" property="encodedId"/>';
				loadHtml('#managePositionGradeCurrency_moreinfo', 'mgtPositionGradeCurrencyAction.do?proc=to_objectModify_ajax&currencyType=' + currencyType + "&positionGradeId=" + positionGradeId, false);
			}
		});
		
		// �༭��ť����¼� Position Grade
		$('#btnEditPositionGrade').click( function () { 
			if($('.positionGrade_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('positionGrade_form');
				// �޸��ˡ��޸�ʱ�䲻�ɱ༭
				$('.decodeModifyBy').attr('disabled', 'disabled');
				$('.decodeModifyDate').attr('disabled', 'disabled');
				// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.positionGrade_form input#subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEditPositionGrade').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.positionGrade_form').attr('action', 'mgtPositionGradeAction.do?proc=modify_object');
        		// ����Page Title
        		if('<bean:write name="role"/>' == 1 ){
	    			$('#pageTitle').html('ְλ�ȼ����ⲿ���༭');
        		}else{
	    			$('#pageTitle').html('ְλ�ȼ��༭');
        		}
        	}else{
        		var flag = 0;
    			flag = flag + validate("managePositionGrade_gradeNameZH", true, "common", 100, 0);
    			flag = flag + validate("managePositionGrade_status", true, "select", 0, 0);
    			flag = flag + validate("managePositionGrade_weight", false, "numeric", 0, 0);
    			flag = flag + validate("managePositionGrade_description", false, "common", 500, 0);
    			
    			if(flag == 0){
    				submit("positionGrade_form");
    			}
        	}
		});

		// �༭��ť����¼� Position Grade Currency
		$('#btnEditPositionGradeCurrency').click(function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.managePositionGradeCurrency_form input#subAction').val() == ""){
				// ��ʾCancel��ť
				$('#btnCancelPositionGradeCurrency').show();
				//	��ʾdetail�޸�������Ϣ
				$('#positionGradeCurrencyFormWrapper').show();
				// �޸İ�ť��ʾ����
				$('#btnEditPositionGradeCurrency').val('����');
				// ����SubActionΪ�½�
				$('.managePositionGradeCurrency_form input#subAction').val('createObject');
			}else if($('.managePositionGradeCurrency_form input#subAction').val() == 'viewObject'){
				//	enable����form
				enableForm('managePositionGradeCurrency_form');
				// �޸İ�ť��ʾ����
				$('#btnEditPositionGradeCurrency').val('����');
				// ����SubActionΪ�༭
				$('.managePositionGradeCurrency_form input#subAction').val('modifyObject');
				//	�������Ͳ��ɱ༭
				$('.managePositionGradeCurrency_currencyType').attr('disabled', 'disabled');
				// ����Form Action
	    		$('.managePositionGradeCurrency_form').attr('action', 'mgtPositionGradeCurrencyAction.do?proc=modify_object');
			}else{
				// �ڴ�����ӻ��޸ĵ���֤
				var flag = 0;
				//	������д 
				flag = flag + validate('managePositionGradeCurrency_currencyType', true, 'select', 0, 0);
				flag = flag + validate('managePositionGradeCurrency_status', true, 'select', 0, 0);
				
				if(flag == 0){
					submit('managePositionGradeCurrency_form');
				}
			}
		});
		
		// �б�ť����¼� - Position Grade
		$('#btnList').click( function () {
			if (agreest())
				link("mgtPositionGradeAction.do?proc=list_object");
		});
		
		// ȡ����ť����¼�  Position Grade Currency
		$('#btnCancelPositionGradeCurrency').click(function(){
			if(agreest())
				link('mgtPositionGradeCurrencyAction.do?proc=list_object&positionGradeId=<bean:write name="mgtPositionGradeForm" property="encodedId"/>"');
		});
		
		// �½����༭ģʽ��JS����
		if( getSubAction() != 'createObject' ){
			// ��Form��Ϊ���ɱ༭״̬
			disableForm('positionGrade_form');
			// ���İ�ť��ʾ��
    		$('#btnEditPositionGrade').val('<bean:message bundle="public" key="button.edit" />');
    		// ����Page Title
    		if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('ְλ�ȼ����ⲿ����ѯ');
			}else{
				$('#pageTitle').html('ְλ�ȼ���ѯ');
			}
    		$('#PositionGradeCurrencyDiv').show();
		} else if( getSubAction() == 'createObject' ){
			// ���İ�ť��ʾ��
    		$('#btnEditPositionGrade').val('����');
    		$('.decodeModifyDate').val('');
    		// ����Page Title
			if('<bean:write name="role"/>' == 1 ){
				$('#pageTitle').html('ְλ�ȼ����ⲿ������');
			}else{
				$('#pageTitle').html('ְλ�ȼ�����');
			}
		}
	})(jQuery);
	
	//	��������ӣ�ajax�����޸�ҳ��
	function to_positionGradeCurrencyModify( currencyId ){
		//	����ajax����
		loadHtmlWithRecall('#positionGradeCurrencyFormWrapper', 'mgtPositionGradeCurrencyAction.do?proc=to_objectModify_ajax&currencyId=' + currencyId, true, null );
		//	��ʾȡ����ť
		$('#btnCancelPositionGradeCurrency').show();
		//	��ʾpositionGradeCurrencyFormWrapper
		$('#positionGradeCurrencyFormWrapper').show();
		//	�޸İ�ť��ʾ����
		$('#btnEditPositionGradeCurrency').val('�༭');
		//	����subActionֵ������add����modify
		$('.managePositionGradeCurrency_form input#subAction').val('viewObject');
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.positionGrade_form input#subAction').val();
	};
</script>