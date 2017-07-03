<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ImportHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="table" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="define" key="define.import" /></label>
		</div>
		<div class="inner">
		   <div id="messageWrapper"></div>
            <div class="top">
            	<kan:auth right="export" action="<%=ImportHeaderAction.accessAction%>">
                	<input type="button" class="function" name="btnExportTemplate" id="btnExportTemplate" value="<bean:message bundle="public" key="button.export.template" />" />
                </kan:auth>
                <kan:auth right="list" action="<%=ImportHeaderAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />       
				</kan:auth>
            </div>
			<!-- Include tab JSP ����tab��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/define/import/header/extend/manageImportExtend.jsp" flush="true"/>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// ��ʼ���˵�
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Import').addClass('selected');

		// ����ģ�尴ť����¼�
		$('#btnExportTemplate').click(function(){
			link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<bean:write name="importHeaderForm" property="encodedId"/>');		
		});
		
		// ȡ����ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('importHeaderAction.do?proc=list_object');
		});		
	})(jQuery);
	
	// ���import Detail �����ӣ�Ajax�����޸�ҳ��	
	function importDetailModify(detailId,columnId,columnName)
	{
		var tableId = '<bean:write name="importHeaderForm" property="tableId" />';
		var callback = "$('.manageImportDetail_isLinked').trigger('change');";
		callback += "$('.manageImportDetail_form input#subAction').val('viewObject');";
		callback += "$('#btnSaveStep2').val('<bean:message bundle="public" key="button.edit" />');";
		callback += "$('.manageImportDetail_columnId').html('<option value=\"" +columnId+"\">" + columnName + "</option>');";
		
		callback = "$('#btnSaveStep2').val('<bean:message bundle="public" key="button.edit" />');";
		loadHtmlWithRecall('#detailForm1', 'importDetailAction.do?proc=to_objectModify_ajax&importDetailId=' + detailId, true, callback);  
	};
</script>
