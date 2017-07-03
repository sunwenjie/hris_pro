<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ManagerDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- ManagerHeader Information Start-->
	<div class="box" id="ManagerHeader-Information">
		<div class="head">
			<label id="pageTitle"></label>
			<logic:notEmpty name="managerHeaderForm" property="managerHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="managerHeaderForm" property="managerHeaderId" />)</label>
	        </logic:notEmpty>
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
				<logic:empty name="managerHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditManagerHeader" id="btnEditManagerHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="managerHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ManagerDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditManagerHeader" id="btnEditManagerHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ManagerDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListMangerHeader" id="btnListMangerHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/manager/header/form/manageManagerHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- ManagerHeader Information End-->
	
	<!-- List ManagerDetail Information Start-->
	<div class="box" id="ManagerDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="define" key="define.manager.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=ManagerDetailAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditMangerDetail" name="btnEditMangerDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelManagerDetail" id="btnCancelManagerDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listManagerDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- ManagerDetailForm -->
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/define/manager/detail/form/manageManagerDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean managerDetailHolder -->
			<logic:notEmpty name="managerDetailHolder">
				<html:form action="managerDetailAction.do?proc=list_object" styleClass="listManagerDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="managerHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="managerDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="managerDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="managerDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="managerDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/define/manager/detail/table/listManagerDetailTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- Inner End-->
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Page').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		 // �༭��ť����¼� - Manager Header
		$('#btnEditManagerHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable����Form
        		enableForm('manageManagerHeader_form');
				$('.manageManagerHeader_form input.manageManagerHeader_tableId').attr('disabled','disabled');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageManagerHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageManagerHeader_form').attr('action', 'managerHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditManagerHeader').val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.manager" /> <bean:message bundle="public" key="oper.edit" />');
        		// Table�ֶ�����Disable
        		$('.manageManagerHeader_tableId').attr('disabled','disabled');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageManagerHeader_form');
    				submit('manageManagerHeader_form');
    			}
			}
		});
		 
		// �༭��ť����¼� - Manager Detail
		$('#btnEditMangerDetail').click(function(){
			// ��ȡManagerDetailForm��subAction
			var detailSubAction = $('.manageManagerDetail_form input#subAction').val();
			
			// ����������� 
			if( detailSubAction == '' ){
				// Load Column Options
				loadHtml('#columnId', 'managerDetailAction.do?proc=list_column_options_ajax&subAction=createObject&tableId=<bean:write name="managerHeaderForm" property="tableId" />&managerHeaderId=<bean:write name="managerHeaderForm" property="managerHeaderId" />&columnId' + $('#columnId').val(), getDisable(), null);
				// ��ʾCancel��ť
				$('#btnCancelManagerDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageManagerDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// �鿴
			else if( detailSubAction == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageManagerDetail_form');
				// ����ֶα����Ǳ�����ɱ༭
				if($('#system_isRequired').val() == '1'){
					$('form.manageManagerDetail_form select.manageManagerDetail_isRequired').attr('disabled','disabled'); 
				}
				// �ֶγ���
				$('.manageManagerDetail_form select.manageManagerDetail_columnId').attr('disabled','disabled'); 
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// ����SubActionΪ�༭
				$('.manageManagerDetail_form input#subAction').val('modifyObject');
				// ����Form Action
				$('.manageManagerDetail_form').attr('action', 'managerDetailAction.do?proc=modify_object');
			}
			// �༭
			else{
				// ͨ��JS��֤���ύFORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageManagerDetail_form');
					submit('manageManagerDetail_form');
				}
			}
		});
		 
		// �б�ť����¼� - Manager Header 
		$('#btnListMangerHeader').click( function () {
			if (agreest())
			link('managerHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - Manager Detail
		$('#btnCancelManagerDetail').click(function(){
			if (agreest())
			link('managerDetailAction.do?proc=list_object&id=<bean:write name="managerHeaderForm" property="encodedId"/>');
		});	
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageManagerHeader_form');
			$('#ManagerDetail-Information').show();
			$('.manageManagerHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="define" key="define.manager" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="define" key="define.manager" />');
		    $('#btnEditManagerHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function managerDetailModify( detailId, columnId ){
		// ��ʾCancel��ť
		$('#btnCancelManagerDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditMangerDetail').val('<bean:message bundle="public" key="button.edit" />');
		var callback = "$(\".manageManagerDetail_useTitle\").trigger(\"change\");";
		callback += "loadHtml('#columnId', 'managerDetailAction.do?proc=list_column_options_ajax&subAction=viewObject&tableId=<bean:write name='managerHeaderForm' property='tableId' />&managerHeaderId=<bean:write name='managerHeaderForm' property='managerHeaderId' />&columnId=" + columnId + "', true, null);";
		// Ajax����ManagerDetail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'managerDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
	};
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageManagerHeader_form input#subAction').val();
	};
</script>