<%@page import="com.kan.wx.web.actions.QuestionHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="QuestionHeader-Information">
		<div class="head">
			<label id="pageTitle">Q & A</label>
			<logic:notEmpty name="questionHeaderForm" property="headerId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="questionHeaderForm" property="headerId" />)</label>
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
				<logic:empty name="questionHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditQuestionHeader" id="btnEditQuestionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="questionHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=QuestionHeaderAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditQuestionHeader" id="btnEditQuestionHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="reset" name="btnListQuestionHeader" id="btnListQuestionHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/wx/question/form/manageQuestionHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<div class="box" id="questionDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label>Option</label>
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
				<kan:auth right="modify" action="<%=QuestionHeaderAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditQuestionDetail" name="btnEditQuestionDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelQuestionDetail" id="btnCancelQuestionDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listQuestionDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/wx/question/form/manageQuesitonDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean questionDetailHolder -->
			<logic:notEmpty name="questionDetailHolder">
				<html:form action="questionDetailAction.do?proc=list_object" styleClass="listQuestionDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="questionHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="questionDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="questionDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="questionDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="questionDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/wx/question/table/listQuestionDetailTable.jsp" flush="true"/> 
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
		$('#menu_interaction_Modules').addClass('current');
		$('#menu_interaction_QA').addClass('selected');
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		 // �༭��ť����¼� - List Header
		$('#btnEditQuestionHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable����Form
        		enableForm('manageQuestionHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageQuestionHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageQuestionHeader_form').attr('action', 'questionHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$(this).val('<bean:message bundle="public" key="button.save" />');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageQuestionHeader_form');
    				submit('manageQuestionHeader_form');
    			}
			}
		});
		 
		$('#btnEditQuestionDetail').click(function(){
			var detailSubAction = $('.manageQuestionDetail_form input#subAction').val();
			
			// ����������� 
			if( detailSubAction == '' ){
				// ��ʾCancel��ť
				$('#btnCancelQuestionDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageQuestionDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// �鿴
			else if( detailSubAction == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageQuestionDetail_form');
				// �޸İ�ť��ʾ����
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// ����SubActionΪ�༭
				$('.manageQuestionDetail_form input#subAction').val('modifyObject');
				// ����Form Action
				$('.manageQuestionDetail_form').attr('action', 'questionDetailAction.do?proc=modify_object');
			}
			// �༭
			else{
				// ͨ��JS��֤���ύFORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageQuestionDetail_form');
					submit('manageQuestionDetail_form');
				}
			}
		});
		
		// �б�ť����¼� - Question Header 
		$('#btnListQuestionHeader').click( function () {
			if (agreest())
			link('questionHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - Question Detail
		$('#btnCancelQuestionDetail').click(function(){
			if(agreest())
			link('quesitionDetailAction.do?proc=list_object&id=<bean:write name="questionHeaderForm" property="encodedId"/>');
		});	
		 
		if( getSubAction() != 'createObject' ) {
			disableForm('manageQuestionHeader_form');
			$('#questionDetail-Information').show();
			$('.manageQuestionHeader_form input#subAction').val('viewObject');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditQuestionHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function questionDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelQuestionDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditQuestionDetail').val('<bean:message bundle="public" key="button.edit" />');
		loadHtmlWithRecall('#detailFormWrapper', 'questionDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, null);
	};
	
	// ��ǰ�Ƿ���ҪDisable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageQuestionHeader_form input#subAction').val();
	};
</script>