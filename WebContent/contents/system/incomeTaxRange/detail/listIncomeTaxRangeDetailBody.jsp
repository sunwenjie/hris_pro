<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="incomeTaxRangeHeader - information">
		<div class="head">
			<label id="pageTitle">税率查询</label>
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
				<input type="button" class="editbutton" name=btnEditIncomeTaxRangeHeader id="btnEditIncomeTaxRangeHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				<input type="button" class="reset" name="btnCancelIncomeTaxRangeHeader" id="btnCancelIncomeTaxRangeHeader" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/system/incomeTaxRange/header/from/manageIncomeTaxRangeHeader.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- incomeTaxRangeDetail - information -->
	<div class="box" id="IncomeTaxRangeDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label>税率明细</label>
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
				<input type="button" class="editbutton" name="btnEditIncomeTaxRangeDetail" id="btnEditIncomeTaxRangeDetail" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelIncomeTaxRangeDetail" id="btnCancelIncomeTaxRangeDetail" value="取消" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listIncomeTaxRangeDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<jsp:include page="/contents/system/incomeTaxRange/detail/from/manageIncomeTaxRangeDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exits bean incomeTaxRangeDetailHolder -->
			<logic:notEmpty name="incomeTaxRangeDetailHolder">														
				<html:form action="incomeTaxRangeDetailAction.do?proc=list_object" styleClass="listIncomeTaxRangeDetail_form">
					<input type="hidden" name="id" value="<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="incomeTaxRangeDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="incomeTaxRangeDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="incomeTaxRangeDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="incomeTaxRangeDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
			<div id="tableWrapper">
				<jsp:include page="/contents/system/incomeTaxRange/detail/table/listIncomeTaxRangeDetailTable.jsp" flush="true"/>
			</div>
			</logic:notEmpty>	
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
		// 设置菜单选择样式
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_IncomeTax').addClass('selected');
		$('#menu_system_IncomeTaxRate').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		$('.manageIncomeTaxRangeHeader_startDate').focus(function(){
			WdatePicker({ 
				maxDate: '#F{$dp.$D(\'endDate\')}' 
			});
		});
		
		$('.manageIncomeTaxRangeHeader_endDate').focus(function(){
			WdatePicker({ 
				minDate: '#F{$dp.$D(\'startDate\')}',
				maxDate:'2020-10-01'
			});
		});
		
		 // 编辑按钮点击事件 - IncomeTaxRange Header
		$('#btnEditIncomeTaxRangeHeader').click(function(){
			if($('.manageIncomeTaxRangeHeader_form input#subAction').val() == 'viewObject'){  
				// Enable整个Form
        		enableForm('manageIncomeTaxRangeHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageIncomeTaxRangeHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageIncomeTaxRangeHeader_form').attr('action', 'incomeTaxRangeHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditIncomeTaxRangeHeader').val('保存');
        		// 修改Page Title
        		$('#pageTitle').html('税率编辑');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageIncomeTaxRangeHeader_form');
				}
			}
		});

		// 编辑按钮点击事件 - IncomeTaxRange Detail
		$('#btnEditIncomeTaxRangeDetail').click(function(){	
			// 判断是添加、查看还是修改 
			if($('.manageIncomeTaxRangeDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelIncomeTaxRangeDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageIncomeTaxRangeDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditIncomeTaxRangeDetail').val('保存');
				$('.manageIncomeTaxRangeDetail_status').val('1');
			}else if($('.manageIncomeTaxRangeDetail_form input#subAction').val() == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('manageIncomeTaxRangeDetail_form');
				// 设置SubAction为编辑
				$('.manageIncomeTaxRangeDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditIncomeTaxRangeDetail').val('保存');
				// 更改Form Action
        		$('.manageIncomeTaxRangeDetail_form').attr('action', 'incomeTaxRangeDetailAction.do?proc=modify_object');
			}else{
				// 通过JS验证，提交FORM
				if( validate_manage_secondary_form() == 0){
					submit('manageIncomeTaxRangeDetail_form');
				}
			}
		});
		
		// Header 取消
		$('#btnCancelIncomeTaxRangeHeader').click( function () {
			if(agreest())
			link('incomeTaxRangeHeaderAction.do?proc=list_object');
		});	
		
		// Detail 取消
		$('#btnCancelIncomeTaxRangeDetail').click( function () {
			if(agreest())
			link('incomeTaxRangeDetailAction.do?proc=list_object&id=<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageIncomeTaxRangeHeader_form');
			$('#IncomeTaxRangeDetail-Information').show();
			$('.manageIncomeTaxRangeHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('税率查询');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('税率新增');
		    $('#btnEditIncomeTaxRangeHeader').val('保存');
		}
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function detailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelIncomeTaxRangeDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// Ajax加载Option Detail修改页面
		loadHtmlWithRecall('#detailFormWrapper', 'incomeTaxRangeDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true ,null);
		// 修改按钮显示名称
		$('#btnEditIncomeTaxRangeDetail').val('编辑');	
	};
	
	// Get Header form SubAction
	function getSubAction(){
		return $('form.manageIncomeTaxRangeHeader_form input#subAction').val();
	};
</script>