<%@page import="com.kan.hro.web.actions.biz.performance.BudgetSettingHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="searchDeatail-information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="performance" key="budget.setting" /></label>
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
				<logic:empty name="budgetSettingHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name="btnEditBudgetSettingHeader" id="btnEditBudgetSettingHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="budgetSettingHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=BudgetSettingHeaderAction.ASSESS_ACTION%>">
						<input type="button" class="editbutton" name="btnEditBudgetSettingHeader" id="btnEditBudgetSettingHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=BudgetSettingHeaderAction.ASSESS_ACTION%>">
					<input type="button" class="reset" name="btnListBudgetSettingHeader" id="btnListBudgetSettingHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="budgetSettingHeaderAction.do?proc=add_object" styleClass="manageBudgetSettingHeader_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="headerId" name="id" class="headerId" value="<bean:write name="budgetSettingHeaderForm" property="encodedId"/>" />
				<input type="hidden" id="subAction" name="subAction" value="<bean:write name="budgetSettingHeaderForm" property="subAction"/>" />	
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="performance" key="budget.setting.year" /><em> *</em></label> 	
							<html:select property="year" styleClass="manageBudgetSettingHeader_year" >
								<html:optionsCollection property="years" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>					
					<ol class="auto">	
						<li>
							<label><bean:message bundle="performance" key="budget.setting.self.assessment.period" /><em> *</em></label> 
							<html:text property="startDate" maxlength="10" styleClass="manageBudgetSettingHeader_startDate Wdate small" styleId="startDate" /> 	
							<html:text property="endDate" maxlength="10" styleClass="manageBudgetSettingHeader_endDate Wdate small" styleId="endDate"/>				
						</li>
						<li>
							<label><bean:message bundle="performance" key="budget.setting.invite.assessment.period" /><em> *</em></label> 
							<html:text property="startDate_bl" maxlength="10" styleClass="manageBudgetSettingHeader_startDate_bl Wdate small" styleId="startDate_bl" /> 	
							<html:text property="endDate_bl" maxlength="10" styleClass="manageBudgetSettingHeader_endDate_bl Wdate small" styleId="endDate_bl"/>				
						</li>
						<li>
							<label><bean:message bundle="performance" key="budget.setting.pm.assessment.period" /><em> *</em></label> 
							<html:text property="startDate_pm" maxlength="10" styleClass="manageBudgetSettingHeader_startDate_pm Wdate small" styleId="startDate_pm" /> 	
							<html:text property="endDate_pm" maxlength="10" styleClass="manageBudgetSettingHeader_endDate_pm Wdate small" styleId="endDate_pm"/>				
						</li>
						<li>
							<label><bean:message bundle="performance" key="budget.setting.final.adjustment.period" /><em> *</em></label> 
							<html:text property="startDate_final" maxlength="10" styleClass="manageBudgetSettingHeader_startDate_final Wdate small" styleId="startDate_final" /> 	
							<html:text property="endDate_final" maxlength="10" styleClass="manageBudgetSettingHeader_endDate_final Wdate small" styleId="endDate_final"/>				
						</li>
						<li>
							<label><bean:message bundle="performance" key="budget.setting.bu.upload.period" /><em> *</em></label> 
							<html:text property="startDate_bu" maxlength="10" styleClass="manageBudgetSettingHeader_startDate_bu Wdate small" styleId="startDate_bu" /> 	
							<html:text property="endDate_bu" maxlength="10" styleClass="manageBudgetSettingHeader_endDate_bu Wdate small" styleId="endDate_bu"/>				
						</li>
						<li>
							<label><bean:message bundle="performance" key="budget.setting.maxInvitation" /><em> *</em></label>
							<html:select property="maxInvitation" styleClass="manageBudgetSettingHeader_maxInvitation" styleId="maxInvitation">
								<option value="0"><bean:message bundle="public" key="public.please.select" /> </option>
								<html:option value="1" />
								<html:option value="2" />
								<html:option value="3" />
								<html:option value="4" />
								<html:option value="5" />
							</html:select>
						</li>
						<li class="hide">
							<label><bean:message bundle="performance" key="budget.setting.noticeLetterTemplate" /><em> *</em></label> 	
							<html:select property="noticeLetterTemplate" styleClass="manageBudgetSettingHeader_noticeLetterTemplate" >
								<html:optionsCollection property="templates" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.description" /></label> 
							<html:textarea property="description" styleClass="manageBudgetSettingHeader_description" ></html:textarea>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /><em> *</em></label> 	
							<html:select property="status" styleClass="manageBudgetSettingHeader_status" >
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>	
			</html:form>
		</div>
	</div>
	
	<!-- SearchDetail-information -->
	<div class="box" id="details-information" style="display: none;">
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="performance" key="budget.setting.details" /></label>
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
				<kan:auth right="modify" action="<%=BudgetSettingHeaderAction.ASSESS_ACTION%>">
					<input type="button" id="btnEditBudgetSettingDetail" name="btnEditBudgetSettingDetail" value="<bean:message bundle="public" key="button.add" />" />
					<input type="button" class="reset" name="btnCancelBudgetSettingDetail" id="btnCancelBudgetSettingDetail" value="<bean:message bundle="public" key="button.cancel" />" onclick="cancelSearchDetail()" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listBudgetSettingDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none">
				<!-- Manage Detail Form -->
				<jsp:include page="/contents/performance/form/manageBudgetSettingDetailForm.jsp" flush="true"/> 
			</div>	
			<!-- if exits bean budgetSettingDetailHolder -->		
			<logic:notEmpty name="budgetSettingDetailHolder">													
				<html:form action="budgetSettingDetailAction.do?proc=list_object" styleClass="listBudgetSettingDetail_form">
					<input type="hidden" name="id" value="<bean:write name="budgetSettingHeaderForm" property="encodedId"/>"/>			
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="budgetSettingDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="budgetSettingDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="budgetSettingDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="budgetSettingDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />		
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/performance/table/listBudgetSettingDetailTable.jsp" flush="true"/> 
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
	(function($){
		
		$('#startDate').focus(function(){
			 WdatePicker({
	    		 maxDate:'#F{$dp.$D(\'endDate\')}'
	    	 });
		});
		
		$('#endDate').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate\')}',
	    	 });
		});	
		
		$('#startDate_bl').focus(function(){
			 WdatePicker({
	    		 maxDate:'#F{$dp.$D(\'endDate_bl\')}'
	    	 });
		});
		
		$('#endDate_bl').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate_bl\')}',
	    	 });
		});	
		
		$('#startDate_pm').focus(function(){
			 WdatePicker({
	    		 maxDate:'#F{$dp.$D(\'endDate_pm\')}'
	    	 });
		});
		
		$('#endDate_pm').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate_pm\')}',
	    	 });
		});	
		

		$('#startDate_final').focus(function(){
			 WdatePicker({
	    		 maxDate:'#F{$dp.$D(\'endDate_final\')}'
	    	 });
		});
		
		$('#endDate_final').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate_final\')}',
	    	 });
		});	
		

		$('#startDate_bu').focus(function(){
			 WdatePicker({
	    		 maxDate:'#F{$dp.$D(\'endDate_bu\')}'
	    	 });
		});
		
		$('#endDate_bu').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate_bu\')}',
	    	 });
		});	
		
		var shapeBase = new ShapeBase();
		shapeBase.init();
		
		$('#btnEditBudgetSettingHeader').click( function(){
			shapeBase.editAndSaveBtnBudgetSettingHeaderClick();
		});
		
		$('#btnEditBudgetSettingDetail').click( function(){
			shapeBase.editAndSaveBtnBudgetSettingDetailClick();
		});
		
	})(jQuery);
	
	// Ajax调用修改页面
	function to_objectModify_ajax( detailId ){
		loadHtmlWithRecall('#detailFormWrapper', 'budgetSettingDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true, null );
		//	显示取消按钮
		$('#btnCancelBudgetSettingDetail').show();
		//	显示detailFormWrapper
		$('#detailFormWrapper').show();
		// 设定SubAction值，区分Add和Modify
		$('.manageBudgetSettingDetail_form input#subAction').val('viewObject');
		//	修改按钮显示名称
		$('#btnEditBudgetSettingDetail').val('<bean:message bundle="public" key="button.edit" />');
	};
	
	
	function ShapeBase(){
		this.subAction = $('.manageBudgetSettingHeader_form input#subAction').val();
		this.getSubAction = function(){
			return $('.manageBudgetSettingHeader_form input#subAction').val();
		};
		this.init = function(){
			// 初始化菜单样式
			$('#menu_performance_Modules').addClass('current');
			$('#menu_performance_Configuration').addClass('selected');
			$('#menu_performance_BudgetSetting').addClass('selected');
			// 初始化多选框
			kanList_init();
			kanCheckbox_init();
			// 列表按钮点击事件 - BudgetSetting Header
			this.bindBtnListBudgetSettingHeaderClick();
			// 取消按钮点击事件 - BudgetSetting Detail
			this.bindBtnCancelBudgetSettingDetailClick();
			if( this.subAction != 'createObject' ){
				disableForm('manageBudgetSettingHeader_form');
				$('div#details-information').show();
			} else if ( this.subAction == 'createObject' ) {
				$('#btnEditBudgetSettingHeader').val('<bean:message bundle="public" key="button.save" />');
			}
		};
		
		this.bindBtnListBudgetSettingHeaderClick = function(){
			$('#btnListBudgetSettingHeader').click( function () {
				if (agreest())
				link('budgetSettingHeaderAction.do?proc=list_object');
			});
		};
		
		this.bindBtnCancelBudgetSettingDetailClick = function(){
			$('#btnCancelBudgetSettingDetail').click( function () {
				if (agreest())
				link('budgetSettingDetailAction.do?proc=list_object&id=<bean:write name="budgetSettingHeaderForm" property="encodedId"/>');
			});
		};
		
		this.editAndSaveBtnBudgetSettingHeaderClick = function(){
			if( this.getSubAction() == 'viewObject'){   
				// Enable整个Form
				enableForm('manageBudgetSettingHeader_form');
				// 设置当前Form的SubAction为修改状态
				$('.manageBudgetSettingHeader_form input#subAction').val('modifyObject'); 
				// 更改Form Action
        		$('.manageBudgetSettingHeader_form').attr('action', 'budgetSettingHeaderAction.do?proc=modify_object');
				//	修改按钮名称
				$('#btnEditBudgetSettingHeader').val('<bean:message bundle="public" key="button.save" />');
			}else{
				if( this.validate_manage_primary_form() == 0 ){
					enableForm('manageBudgetSettingHeader_form');
					submit('manageBudgetSettingHeader_form');
				}
			}
		};
		
		this.editAndSaveBtnBudgetSettingDetailClick = function(){
			// 判断是添加、查看还是修改
			if($('.manageBudgetSettingDetail_form input#subAction').val() == ""){
				// 显示Cancel按钮
				$('#btnCancelBudgetSettingDetail').show();
				//	显示List Search Form
				$('#detailFormWrapper').show();
				// 设置SubAction为新建
				$('.manageBudgetSettingDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditBudgetSettingDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageBudgetSettingDetail_form input#subAction').val() == 'viewObject'){
				// Enable整个Form
				enableForm('manageBudgetSettingDetail_form');
				// 设置SubAction为编辑
				$('.manageBudgetSettingDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditBudgetSettingDetail').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
	    		$('.manageBudgetSettingDetail_form').attr('action', 'budgetSettingDetailAction.do?proc=modify_object');
			}else{
				if( this.validate_manage_secondary_form() == 0){
					submit('manageBudgetSettingDetail_form');
				}
			}
		};
		
		this.validate_manage_primary_form = function(){
			var flag = 0;
			flag = flag + validate("manageBudgetSettingHeader_year", true, "select", 0, 0);
			flag = flag + validate("manageBudgetSettingHeader_startDate", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_endDate", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_startDate_bl", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_endDate_bl", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_startDate_pm", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_endDate_pm", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_startDate_final", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_endDate_final", true, "common", 10, 0);
			flag = flag + validate("manageBudgetSettingHeader_maxInvitation", true, "select", 0, 0);
			flag = flag + validate("manageBudgetSettingHeader_status", true, "select", 0, 0);
			flag = flag + validate("manageBudgetSettingHeader_description", false, "common", 500, 0);
			return flag;
		};
		
		this.validate_manage_secondary_form = function() {
			var flag = 0;
			flag = flag + validate("manageBudgetSettingDetail_parentBranchId", true, "select", 0, 0);
			flag = flag + validate("manageBudgetSettingDetail_ttc", true, "currency", 12, 0);
			flag = flag + validate("manageBudgetSettingDetail_bonus", true, "currency", 12, 0);
			flag = flag + validate("manageBudgetSettingDetail_status", true, "select", 0, 0);
		    return flag;
		};
		
	};
</script>