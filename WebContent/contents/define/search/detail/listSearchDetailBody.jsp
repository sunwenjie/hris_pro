<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.SearchDetailAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!--searchDeatail-information-->
<div id="content">
	<div class="box" id="searchDeatail-information">
		<div class="head">
			<label id="pageTitle"></label>
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
				<logic:empty name="searchHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditSearchHeader" id="btnEditSearchHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="searchHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=SearchDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditSearchHeader" id="btnEditSearchHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=SearchDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListSearchHeader" id="btnListSearchHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/search/header/form/manageSearchHeaderForm.jsp" flush="true"/>
		</div>
	</div>
	
	<!-- SearchDetail-information -->
	<div class="box" id="searchDetail-information" style="display: none;">
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="define" key="define.search.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=SearchDetailAction.accessAction%>">
					<input type="button" id="btnEditSearchDetail" name="btnEditSearchDetail" value="<bean:message bundle="public" key="button.add" />" />
					<input type="button" class="reset" name="btnCancelSearchDetail" id="btnCancelSearchDetail" value="<bean:message bundle="public" key="button.cancel" />" onclick="cancelSearchDetail()" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none">
				<!-- Manage Detail Form -->
				<jsp:include page="/contents/define/search/detail/form/manageSearchDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exits bean searchDetailHolder -->		
			<logic:notEmpty name="searchDetailHolder">													
				<html:form action="searchDetailAction.do?proc=list_object" styleClass="listDetail_form">
					<input type="hidden" name="id" value="<bean:write name="searchHeaderForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="searchDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="searchDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="searchDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="searchDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />		
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/define/search/detail/table/listSearchDetailTable.jsp" flush="true"/> 
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->
			<div class="bottom">
				<p></p>
			</div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Search').addClass('selected');	
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
	
		// �༭��ť����¼� - Search Header
		$('#btnEditSearchHeader').click(function(){
			if($('.searchHeader_form input#subAction').val() == 'viewObject'){   
				// Enable����Form
				enableForm('searchHeader_form');
				// ���õ�ǰForm��SubActionΪ�޸�״̬
				$('.searchHeader_form input#subAction').val('modifyObject'); 
				// ����Form Action
        		$('.searchHeader_form').attr('action', 'searchHeaderAction.do?proc=modify_object');
				//	�޸İ�ť����
				$('#btnEditSearchHeader').val('<bean:message bundle="public" key="button.save" />');
				//	�޸ı�������
				$('#pageTitle').html('<bean:message bundle="define" key="define.search" /> <bean:message bundle="public" key="oper.edit" />');
				$('.manageSearchHeader_useJavaObject').attr('disabled','disabled');
        		$('.manageSearchHeader_tableId').attr('disabled','disabled');
			}else{
				if( validate_manage_primary_form() == 0 ){
					enableForm('searchHeader_form');
					submit('searchHeader_form');
				}
			}
		});
		// �༭��ť����¼� - Search Detail
		// Code reviewed by Kevin at 2013-07-09
		$('#btnEditSearchDetail').click(function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.manageSearchDetail_form input#subAction').val() == ""){
				// ��ʾCancel��ť
				$('#btnCancelSearchDetail').show();
				//	��ʾList Search Form
				$('#detailFormWrapper').show();
				// ����SubActionΪ�½�
				$('.manageSearchDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditSearchDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageSearchDetail_form input#subAction').val() == 'viewObject'){
				// Enable����Form
				enableForm('manageSearchDetail_form');
				// ����SubActionΪ�༭
				$('.manageSearchDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditSearchDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.manageSearchDetail_form').attr('action', 'searchDetailAction.do?proc=modify_object');
	    		// �ֶ�����Disable
        		$('.manageSearchDetail_columnId').attr('disabled','disabled');
			}else{
				if( validate_manage_secondary_form() == 0){
					submit('manageSearchDetail_form');
				}
			}
		});
		
		// �б�ť����¼� - Search Header
		$('#btnListSearchHeader').click( function () {
			if (agreest())
			link('searchHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - Search Detail
		$('#btnCancelSearchDetail').click(function(){
			if(agreest())
			link('searchDetailAction.do?proc=list_object&id=<bean:write name="searchHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ){
			disableForm('searchHeader_form');
			$('#searchDetail-information').show();
			$('#pageTitle').html('<bean:message bundle="define" key="define.search" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="define" key="define.search" />');
		    $('#btnEditSearchHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);

	//	ʹ�����빦��OnChange�¼�
	// Code reviewed by Kevin at 2013-07-09
	function useThinkingChange () { 
		if($('.manageSearchDetail_useThinking').val() == '1'){
			$('#thinkingActionLi').show();
		}else{
			$('#thinkingActionLi').hide();
		}
	};
	
	// ���Search Detail�����ӣ�Ajax�����޸�ҳ��
	// Code reviewed by Kevin at 2013-07-09
	function objectModify( searchDetailId, columnId ){
		var callback = "$('.manageSearchDetail_columnId').val( '" + columnId + "')";
		loadHtmlWithRecall('#detailFormWrapper', 'searchDetailAction.do?proc=to_objectModify_ajax&searchDetailId=' + searchDetailId, true, callback );
		//	��ʾȡ����ť
		$('#btnCancelSearchDetail').show();
		//	��ʾdetailFormWrapper
		$('#detailFormWrapper').show();
		// �趨SubActionֵ������Add��Modify
		$('.manageSearchDetail_form input#subAction').val('viewObject');
		//	�޸İ�ť��ʾ����
		$('#btnEditSearchDetail').val('<bean:message bundle="public" key="button.edit" />');
	};
	
	// Get SubAction
	function getSubAction(){
		return $('.searchHeader_form input#subAction').val();
	};
</script>