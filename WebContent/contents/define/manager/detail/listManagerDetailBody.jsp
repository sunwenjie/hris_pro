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
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
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
					<!-- Include table jsp 包含table对应的jsp文件 -->  
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
		// 初始化菜单
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Page').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		 // 编辑按钮点击事件 - Manager Header
		$('#btnEditManagerHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable整个Form
        		enableForm('manageManagerHeader_form');
				$('.manageManagerHeader_form input.manageManagerHeader_tableId').attr('disabled','disabled');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageManagerHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageManagerHeader_form').attr('action', 'managerHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditManagerHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="define" key="define.manager" /> <bean:message bundle="public" key="oper.edit" />');
        		// Table字段依旧Disable
        		$('.manageManagerHeader_tableId').attr('disabled','disabled');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageManagerHeader_form');
    				submit('manageManagerHeader_form');
    			}
			}
		});
		 
		// 编辑按钮点击事件 - Manager Detail
		$('#btnEditMangerDetail').click(function(){
			// 获取ManagerDetailForm的subAction
			var detailSubAction = $('.manageManagerDetail_form input#subAction').val();
			
			// 如果是添加添加 
			if( detailSubAction == '' ){
				// Load Column Options
				loadHtml('#columnId', 'managerDetailAction.do?proc=list_column_options_ajax&subAction=createObject&tableId=<bean:write name="managerHeaderForm" property="tableId" />&managerHeaderId=<bean:write name="managerHeaderForm" property="managerHeaderId" />&columnId' + $('#columnId').val(), getDisable(), null);
				// 显示Cancel按钮
				$('#btnCancelManagerDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageManagerDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// 查看
			else if( detailSubAction == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('manageManagerDetail_form');
				// 如果字段本来是必填，不可编辑
				if($('#system_isRequired').val() == '1'){
					$('form.manageManagerDetail_form select.manageManagerDetail_isRequired').attr('disabled','disabled'); 
				}
				// 字段除外
				$('.manageManagerDetail_form select.manageManagerDetail_columnId').attr('disabled','disabled'); 
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageManagerDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageManagerDetail_form').attr('action', 'managerDetailAction.do?proc=modify_object');
			}
			// 编辑
			else{
				// 通过JS验证，提交FORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageManagerDetail_form');
					submit('manageManagerDetail_form');
				}
			}
		});
		 
		// 列表按钮点击事件 - Manager Header 
		$('#btnListMangerHeader').click( function () {
			if (agreest())
			link('managerHeaderAction.do?proc=list_object');
		});
		
		// 取消按钮点击事件 - Manager Detail
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
	
	// 点击超链接，ajax调用去修改页面
	function managerDetailModify( detailId, columnId ){
		// 显示Cancel按钮
		$('#btnCancelManagerDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 修改按钮显示名称
		$('#btnEditMangerDetail').val('<bean:message bundle="public" key="button.edit" />');
		var callback = "$(\".manageManagerDetail_useTitle\").trigger(\"change\");";
		callback += "loadHtml('#columnId', 'managerDetailAction.do?proc=list_column_options_ajax&subAction=viewObject&tableId=<bean:write name='managerHeaderForm' property='tableId' />&managerHeaderId=<bean:write name='managerHeaderForm' property='managerHeaderId' />&columnId=" + columnId + "', true, null);";
		// Ajax加载ManagerDetail修改页面
		loadHtmlWithRecall('#detailFormWrapper', 'managerDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
	};
	
	// 当前是否需要Disable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageManagerHeader_form input#subAction').val();
	};
</script>