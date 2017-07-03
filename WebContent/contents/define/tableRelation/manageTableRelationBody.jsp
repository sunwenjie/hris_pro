<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="table" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">模块字典关系管理</label>
		</div>
		<div class="inner">
			<div class="top">
				<logic:present name="accountId">
					<logic:equal name="accountId" value="1">
						<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" /> 
					</logic:equal>
				</logic:present>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<html:form action="tableRelationAction.do?proc=to_objectModify" styleClass="tableRelation_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="tablePagedListHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="tablePagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="tablePagedListHolder" property="page" />" /> 
			<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="tableRelationForm" property="subAction" />" /> 
				<fieldset>
					<div id="detailForm">
					<jsp:include page="/contents/define/tableRelation/form/manageTableRelationSelectColumnForm.jsp" flush="true"></jsp:include>
					</div>				
				</fieldset>
				<div id="tableWrapper">
					<jsp:include page="/contents/define/tableRelation/table/listTableRelationDetailTable.jsp" flush="true"></jsp:include>
				</div>
				
			</html:form>	
			
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		
		$('#menu_define_Modules').addClass('current');
		$('#menu_define_table_relationt').addClass('selected');
	
		// 初始化菜单
		$('#menu_define_Modules').addClass('current');			
		//$('#menu_define_TableRelation').addClass('selected');
     	
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('tableRelationAction.do?proc=list_object');
		});	
		
		// 编辑按钮点击事件
        $('#btnEdit').click(function(){
        	var flag = 0;
			flag = flag + validate('manageTableRelationDetail_masterTableId', true, 'select', 0, 0);
			flag = flag + validate('manageTableRelationDetail_slaveTableId', true, 'select', 0, 0);    			
			
        	if($('.tableRelationId').val() != ''){
        		$('.tableRelation_form').attr('action', 'tableRelationAction.do?proc=modify_object');
        	}else{
        		$('.tableRelation_form').attr('action', 'tableRelationAction.do?proc=add_object');
        	}
			
			if(flag == 0){
				submit('tableRelation_form');
			}
        });
		
		
	})(jQuery);
	
	
	
	// 移除已选定字段
	function removeTableRelation( tableRelationId ,masterTableId){
		if(confirm('确认删除？')){ 
			loadHtml('#tableWrapper', 'tableRelationAction.do?proc=to_objectModify&subAction=deleteObjects&tableRelationId=' + tableRelationId + '&masterTableId=' + masterTableId + '&ajax=true', false);
		}
	};
	
	
	function tableRelationModify(tableRelationId)
	{
		loadHtmlWithRecall('#detailForm', 'tableRelationAction.do?proc=to_objectModify_ajax&tableRelationId=' + tableRelationId, false, null);  
	}
	
</script>
