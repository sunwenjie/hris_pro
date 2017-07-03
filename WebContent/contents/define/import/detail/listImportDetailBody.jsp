<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.renders.define.ImportRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final PagedListHolder importDetailHolder = (PagedListHolder)request.getAttribute("importDetailHolder");
%>

<div id="content">
	<div class="box" id="listDeatail-information">
		<div class="head">
			<label id="pageTitle">�����ѯ</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>	
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/import/header/form/manageImportHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- ListHeader-information -->
	<div class="box" id="listDetail-information">	
		<!-- Inner -->
		<div class="head">
			<label>�����ֶ�</label>
		</div>						
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_DETAIL">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="editbutton" id="btnEditImportDetail" name="btnEditImportDetail" value="<bean:message bundle="public" key="button.add" />"  />
				<input type="button" class="reset" name="btnCancelImportDetail" id="btnCancelImportDetail" value="ȡ��" style="display:none" />
				<input type="button" class="export" name="btnExportImportDetail" id="btnExportImportDetail" value="����ģ��"  />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listListDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>	
			<div id="detailFormWrapper" style="display:none">
				<html:form action="importDetailAction.do?proc=add_object" styleClass="importDetail_form">
					<%= BaseAction.addToken( request ) %>
					<input type="hidden" name="subAction" class="subAction" id="subAction" value=""/>
      				<input type="hidden" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>"/>
					<fieldset>
						<ol class="auto">
     						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
    					</ol>
						<ol class="auto">
							<li>
								<label>�ֶ�<em> *</em></label>
								<html:select property="columnId" styleClass="manageImportDetail_columnId">
									<html:optionsCollection property="columns" value="mappingId" label="mappingValue"/>
								</html:select>
							</li>
						</ol>
						<div id="manageImportDetail_moreinfo">
							<%= ImportRender.getImportDetailManageHtml( request, null) %>
						</div>
					</fieldset>	
				</html:form>
			</div>																	
			<!-- ListnDetail - information -->	
			<html:form action="importDetailAction.do?proc=list_object" styleClass="listListDetail_form">
				<fieldset>		
					<input type="hidden" name="importHeaderId" value="<bean:write name="importHeaderForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="importDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="importDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="importDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="importDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</fieldset>
			</html:form>
			<div id="tableWrapper">
				<!-- Include table jsp ����table��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/define/import/detail/table/listImportDetailTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Import').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		//Header FORM���ɱ༭
		disableForm('importHeader_form');

		// �ֶ�OnChange�¼�
		// Code reviewed by Kevin at 2013-07-09
		$('.manageImportDetail_columnId').change(function(){
			var flag = true;
			$('input[id^="columnId"]').each(function(i) {
	    		if($('.manageImportDetail_columnId').val()!=1 && $(this).val() ==  $('.manageImportDetail_columnId').val()){    
	    			alert('���ֶ��Ѿ����ڣ�������ѡ��');
					$('.manageImportDetail_columnId').val('0');
					flag = false;
	    		}	
	    	});
			
			if(flag){
				var columnId = $('.manageImportDetail_columnId').val();
				var importHeaderId = '<bean:write name="importHeaderForm" property="encodedId"/>';
				loadHtml('#manageImportDetail_moreinfo', 'importDetailAction.do?proc=to_objectModify_ajax&columnId=' + columnId + "&importHeaderId=" + importHeaderId, false);
			}
		});
		
		// �༭��ť����¼� - List Detail
		$('#btnEditImportDetail').click(function(){
			// �ж�����ӡ��鿴�����޸�
			if($('.importDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelImportDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.importDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditImportDetail').val('����');
			}else if($('.importDetail_form input#subAction').val() == 'viewObject'){
				// Enable����Form
				enableForm('importDetail_form');
				// ����SubActionΪ�༭
				$('.importDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditImportDetail').val('����');
				// ����Form Action
        		$('.importDetail_form').attr('action', 'importDetailAction.do?proc=modify_object');
				// �ֶ�����Disable
        		$('.manageImportDetail_columnId').attr('disabled','disabled');	
			}else{
				// �ڴ�����ӻ��޸ĵ���֤
				var flag = 0;
				
				flag = flag + validate('manageImportDetail_columnId', true, 'select', 0, 0);
				flag = flag + validate('manageImportDetail_nameZH', true, 'common', 100, 0);	
				flag = flag + validate('manageImportDetail_nameEN', true, 'common', 100, 0);	
				
				// ����û�ʹ�ðٷֱȣ��п�ֻ����2λ��������û�ʹ�ù̶�ֵ���п�������3λ��
				if($('.manageImportDetail_columnWidthType').val() == 1){
					flag = flag + validate('manageImportDetail_columnWidth', false, 'numeric', 2, 0);
				}else{
					flag = flag + validate('manageImportDetail_columnWidth', false, 'numeric', 3, 0);
				}
					
				flag = flag + validate('manageImportDetail_columnIndex', true, 'numeric', 2, 0);	
				
				// ���ʹ�ó����ӣ�����Ҫ��֤���ӵ�ַ
				if($('.manageImportDetail_isLinked').val() == 1){
					flag = flag + validate('manageImportDetail_linkedAction', true, 'common', 100, 0);	
				}
				
				flag = flag + validate('manageImportDetail_description', false, 'common', 500, 0);					
				flag = flag + validate('manageImportDetail_status', true, 'select', 0, 0);
				
				if(flag == 0){
					submit('importDetail_form');
				}
			}
		});
		
    	// ȡ����ť����¼� - List Header
		$('#btnCancelImportHeader').click( function () {
			if(agreest())
			link('importHeaderAction.do?proc=list_object');
		});
		
		// ȡ����ť����¼� - List Detail
		$('#btnCancelImportDetail').click(function(){
			if(agreest())
			link('importDetailAction.do?proc=list_object&importHeaderId=<bean:write name="importHeaderForm" property="encodedId"/>');
		});	
		
		// ����ģ�尴ť����¼�
		$('#btnExportImportDetail').click(function(){
			link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<bean:write name="importHeaderForm" property="encodedId"/>');		
			//linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE');
		});
		
	})(jQuery);
	
	
	//���List Detail �����ӣ�Ajax�����޸�ҳ��	
	function objectModify(importDetailId ,columnId){
		loadHtmlWithRecall('#manageImportDetail_moreinfo', 'importDetailAction.do?proc=to_objectModify_ajax&importDetailId=' + importDetailId, true, 'disableForm("importDetail_form");');
		// ����ColumnId
		$('.manageImportDetail_columnId').val(columnId);
		// ��ʾCancel��ť
		$('#btnCancelImportDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �趨SubActionֵ������Add��Modify
		$('.importDetail_form input#subAction').val('viewObject');
		// �޸İ�ť��ʾ����
		$('#btnEditImportDetail').val('�༭');		
	};
</script>