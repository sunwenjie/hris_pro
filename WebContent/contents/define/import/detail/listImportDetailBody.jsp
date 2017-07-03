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
			<label id="pageTitle">导入查询</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>	
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/define/import/header/form/manageImportHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- ListHeader-information -->
	<div class="box" id="listDetail-information">	
		<!-- Inner -->
		<div class="head">
			<label>导入字段</label>
		</div>						
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE_DETAIL">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="editbutton" id="btnEditImportDetail" name="btnEditImportDetail" value="<bean:message bundle="public" key="button.add" />"  />
				<input type="button" class="reset" name="btnCancelImportDetail" id="btnCancelImportDetail" value="取消" style="display:none" />
				<input type="button" class="export" name="btnExportImportDetail" id="btnExportImportDetail" value="导出模板"  />
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
								<label>字段<em> *</em></label>
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
				<!-- Include table jsp 包含table对应的jsp文件 -->  
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
		// 初始化菜单
		$('#menu_define_Modules').addClass('current');			
		$('#menu_define_Import').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		//Header FORM不可编辑
		disableForm('importHeader_form');

		// 字段OnChange事件
		// Code reviewed by Kevin at 2013-07-09
		$('.manageImportDetail_columnId').change(function(){
			var flag = true;
			$('input[id^="columnId"]').each(function(i) {
	    		if($('.manageImportDetail_columnId').val()!=1 && $(this).val() ==  $('.manageImportDetail_columnId').val()){    
	    			alert('该字段已经存在，请重新选择！');
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
		
		// 编辑按钮点击事件 - List Detail
		$('#btnEditImportDetail').click(function(){
			// 判断是添加、查看还是修改
			if($('.importDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelImportDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.importDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditImportDetail').val('保存');
			}else if($('.importDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('importDetail_form');
				// 设置SubAction为编辑
				$('.importDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditImportDetail').val('保存');
				// 更改Form Action
        		$('.importDetail_form').attr('action', 'importDetailAction.do?proc=modify_object');
				// 字段依旧Disable
        		$('.manageImportDetail_columnId').attr('disabled','disabled');	
			}else{
				// 在此做添加或修改的验证
				var flag = 0;
				
				flag = flag + validate('manageImportDetail_columnId', true, 'select', 0, 0);
				flag = flag + validate('manageImportDetail_nameZH', true, 'common', 100, 0);	
				flag = flag + validate('manageImportDetail_nameEN', true, 'common', 100, 0);	
				
				// 如果用户使用百分比，列宽只能填2位数；如果用户使用固定值，列宽则能填3位数
				if($('.manageImportDetail_columnWidthType').val() == 1){
					flag = flag + validate('manageImportDetail_columnWidth', false, 'numeric', 2, 0);
				}else{
					flag = flag + validate('manageImportDetail_columnWidth', false, 'numeric', 3, 0);
				}
					
				flag = flag + validate('manageImportDetail_columnIndex', true, 'numeric', 2, 0);	
				
				// 如果使用超链接，则需要验证链接地址
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
		
    	// 取消按钮点击事件 - List Header
		$('#btnCancelImportHeader').click( function () {
			if(agreest())
			link('importHeaderAction.do?proc=list_object');
		});
		
		// 取消按钮点击事件 - List Detail
		$('#btnCancelImportDetail').click(function(){
			if(agreest())
			link('importDetailAction.do?proc=list_object&importHeaderId=<bean:write name="importHeaderForm" property="encodedId"/>');
		});	
		
		// 导出模板按钮点击事件
		$('#btnExportImportDetail').click(function(){
			link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=<bean:write name="importHeaderForm" property="encodedId"/>');		
			//linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE');
		});
		
	})(jQuery);
	
	
	//点击List Detail 的链接，Ajax调用修改页面	
	function objectModify(importDetailId ,columnId){
		loadHtmlWithRecall('#manageImportDetail_moreinfo', 'importDetailAction.do?proc=to_objectModify_ajax&importDetailId=' + importDetailId, true, 'disableForm("importDetail_form");');
		// 设置ColumnId
		$('.manageImportDetail_columnId').val(columnId);
		// 显示Cancel按钮
		$('#btnCancelImportDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 设定SubAction值，区分Add和Modify
		$('.importDetail_form input#subAction').val('viewObject');
		// 修改按钮显示名称
		$('#btnEditImportDetail').val('编辑');		
	};
</script>