<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.TaxTemplateDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- TaxTemplateHeader Information Start-->
	<div class="box" id="TaxTemplateHeader-Information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.tax.template" /></label>
			<logic:notEmpty name="taxTemplateHeaderForm" property="templateHeaderId" >
	        	<label class="recordId"> &nbsp; (ID: <bean:write name="taxTemplateHeaderForm" property="templateHeaderId" />)</label>
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
				<logic:empty name="taxTemplateHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditTaxTemplateHeader" id="btnEditTaxTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="taxTemplateHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=TaxTemplateDetailAction.accessAction%>">
						<input type="button" class="editbutton" name="btnEditTaxTemplateHeader" id="btnEditTaxTemplateHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=TaxTemplateDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListTaxTemplateHeader" id="btnListTaxTemplateHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/define/taxTemplate/header/form/manageTaxTemplateHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	<!-- TaxTemplateHeader Information End-->
	
	<!-- List TaxTemplateDetail Information Start-->
	<div class="box" id="TaxTemplateDetail-Information" style="display: none;">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.tax.template.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=TaxTemplateDetailAction.accessAction%>">
					<input type="button" class="editbutton" id="btnEditTaxTemplateDetail" name="btnEditTaxTemplateDetail" value="<bean:message bundle="public" key="button.add" />"  />
					<input type="button" class="reset" name="btnCancelTaxTemplateDetail" id="btnCancelTaxTemplateDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listTaxTemplateDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>
			<!-- TaxTemplateDetailForm -->
			<div id="detailFormWrapper" style="display: none;">
				<jsp:include page="/contents/define/taxTemplate/detail/form/manageTaxTemplateDetailForm.jsp" flush="true"/> 
			</div>
			<!-- if exist bean taxTemplateDetailHolder -->
			<logic:notEmpty name="taxTemplateDetailHolder">
				<html:form action="taxTemplateDetailAction.do?proc=list_object" styleClass="listTaxTemplateDetail_form">
					<fieldset>		
						<input type="hidden" name="id" value="<bean:write name="taxTemplateHeaderForm" property="encodedId"/>"/>			
						<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="taxTemplateDetailHolder" property="sortColumn" />" /> 
						<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="taxTemplateDetailHolder" property="sortOrder" />" />
						<input type="hidden" name="page" id="page" value="<bean:write name="taxTemplateDetailHolder" property="page" />" />
						<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="taxTemplateDetailHolder" property="selectedIds" />" />
						<input type="hidden" name="subAction" id="subAction" value="" />					
					</fieldset>
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含table对应的jsp文件 -->  
					<jsp:include page="/contents/define/taxTemplate/detail/table/listTaxTemplateDetailTable.jsp" flush="true"/> 
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
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_IncomeTaxTemplate').addClass('selected');
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		 // 编辑按钮点击事件 - List Header
		$('#btnEditTaxTemplateHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable整个Form
        		enableForm('manageTaxTemplateHeader_form');
        		// 银行字段依旧Disable
				$('.manageTaxTemplateHeader_form input.manageTaxTemplateHeader_taxId').attr('disabled','disabled');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageTaxTemplateHeader_form input#subAction').val('modifyObject'); 
        		// 更改Form Action
        		$('.manageTaxTemplateHeader_form').attr('action', 'taxTemplateHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$(this).val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.tax.template" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
    			if( validate_manage_primary_form() == 0 ){
    				enableForm('manageTaxTemplateHeader_form');
    			    submit('manageTaxTemplateHeader_form');
    			}
			}
		});
		 
		$('#btnEditTaxTemplateDetail').click(function(){
			// 获取TaxTemplateDetailForm的subAction
			var detailSubAction = $('.manageTaxTemplateDetail_form input#subAction').val();
			
			// 如果是添加添加 
			if( detailSubAction == '' ){
				// 显示Cancel按钮
				$('#btnCancelTaxTemplateDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageTaxTemplateDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
			}
			// 查看
			else if( detailSubAction == 'viewObject'){
				// 编辑操作，Enable整个Form
				enableForm('manageTaxTemplateDetail_form');
				// 修改按钮显示名称
				$(this).val('<bean:message bundle="public" key="button.save" />');
				// 设置SubAction为编辑
				$('.manageTaxTemplateDetail_form input#subAction').val('modifyObject');
				// 更改Form Action
				$('.manageTaxTemplateDetail_form').attr('action', 'taxTemplateDetailAction.do?proc=modify_object');
			}
			// 编辑
			else{
				// 通过JS验证，提交FORM
				if( validate_manage_secondary_form() == 0){
					enableForm('manageTaxTemplateDetail_form');
					submit('manageTaxTemplateDetail_form');
				}
			}
		});
		
		// 列表按钮点击事件 - TaxTemplate Header 
		$('#btnListTaxTemplateHeader').click( function () {
			if (agreest())
			link('taxTemplateHeaderAction.do?proc=list_object');
		});
		
		// 取消按钮点击事件 - TaxTemplate Detail
		$('#btnCancelTaxTemplateDetail').click(function(){
			if(agreest())
			link('taxTemplateDetailAction.do?proc=list_object&id=<bean:write name="taxTemplateHeaderForm" property="encodedId"/>');
		});	
		 
		if( getSubAction() != 'createObject' ) {
			disableForm('manageTaxTemplateHeader_form');
			$('#TaxTemplateDetail-Information').show();
			$('.manageTaxTemplateHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.tax.template" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditTaxTemplateHeader').val('<bean:message bundle="public" key="button.save" />');
		}
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function taxTemplateDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelTaxTemplateDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// 修改按钮显示名称
		$('#btnEditTaxTemplateDetail').val('<bean:message bundle="public" key="button.edit" />');
		var callback = "$(\".manageTaxTemplateDetail_valueType\").trigger(\"change\");";
		// Ajax加载TaxTemplateDetail修改页面
		loadHtmlWithRecall('#detailFormWrapper', 'taxTemplateDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
	};
	
	// cityId change事件
	function cityIdChange(){
		cleanError('cityId');
		$.ajax({
			url : "taxTemplateHeaderAction.do?proc=cityId_change_ajax&cityId=" + $('#cityId').val(), 
			dataType : "json",
			success : function(data){
				if(data.success == 'false'){
					var error = $('#cityId option:selected').html() + "已经定义过个税模板；";
					addError('cityId', error);
					$('#cityId').val('0');
				}
			}
		});
	};
	
	// 当前是否需要Disable
	function getDisable(){
		return getSubAction() == 'viewObject' ? true : false;
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageTaxTemplateHeader_form input#subAction').val();
	};
</script>