<%@page import="com.kan.base.web.actions.management.AnnualLeaveRuleHeaderAction"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
 	<div class="box" id="AnnualLeaveHeaderRule-Information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.annual.leave.rule" /></label>
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
				<logic:empty name="annualLeaveRuleHeaderForm" property="encodedId">
					<input type="button" class="save" name="btnEditAnnualLeaveRuleHeader" id="btnEditAnnualLeaveRuleHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				
				<logic:notEmpty name="annualLeaveRuleHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
						<input type="button" class="save" name="btnEditAnnualLeaveRuleHeader" id="btnEditAnnualLeaveRuleHeader" value="<bean:message bundle="public" key="button.edit" />" />
					</kan:auth>
				</logic:notEmpty>
				
				<kan:auth right="list" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP 包含Form对应的jsp文件 -->  
			<jsp:include page="/contents/management/annualLeaveRule/header/form/manageAnnualLeaveRuleHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- AnnualLeaveDetailRule-Information -->
	<div class="box" id="AnnualLeaveDetailRule-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.annual.leave.rule.detail.search.title" /></label>
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
				<kan:auth right="modify" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="save" name="btnEditAnnualLeaveRuleDetail" id="btnEditAnnualLeaveRuleDetail" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelAnnualLeaveRuleDetail" id="btnCancelAnnualLeaveRuleDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listAnnualLeaveRuleDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP 包含Form对应的jsp文件 --> 
				<jsp:include page="/contents/management/annualLeaveRule/detail/form/listAnnualLeaveRuleDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exist bean annualLeaveRuleDetailHolder -->	
			<logic:notEmpty name="annualLeaveRuleDetailHolder">														
				<html:form action="annualLeaveRuleDetailAction.do?proc=list_object" styleClass="listAnnualLeaveRuleDetail_form">
					<input type="hidden" name="id" value="<bean:write name="annualLeaveRuleHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="annualLeaveRuleDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="annualLeaveRuleDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="annualLeaveRuleDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="annualLeaveRuleDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/management/annualLeaveRule/detail/table/listAnnualLeaveRuleDetailTable.jsp" flush="true"/>
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
		// 设置菜单选择样式，如果当前用户是Super
		$('#menu_salary_Modules').addClass('current');			
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_AnnualLeave_Rule').addClass('selected');
		
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		// 编辑按钮点击事件 - Annual Leave Header
		$('#btnEditAnnualLeaveRuleHeader').click(function(){
			if( getSubAction() == 'viewObject'){  
				// Enable整个Form
        		enableForm('manageAnnualLeaveRuleHeader_form');
        		// 设置当前Form的SubAction为修改状态
        		$('.manageAnnualLeaveRuleHeader_form input#subAction').val("modifyObject");
        		// 更改Form Action
        		$('.manageAnnualLeaveRuleHeader_form').attr('action', 'annualLeaveRuleHeaderAction.do?proc=modify_object');
        		// 修改按钮显示名称
        		$('#btnEditAnnualLeaveRuleHeader').val('<bean:message bundle="public" key="button.save" />');
        		// 修改Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.annual.leave.rule" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageAnnualLeaveRuleHeader_form');
				}
			}
		}); 
		
		// 编辑按钮点击事件 - sickLeaveSalary Detail
		$('#btnEditAnnualLeaveRuleDetail').click(function(){	
			// 判断是添加、查看还是修改 
			if($('.manageAnnualLeaveRuleDetail_form input#subAction').val() == ''){
				// 显示Cancel按钮
				$('#btnCancelAnnualLeaveRuleDetail').show();
				// 显示List Detail Form
				$('#detailFormWrapper').show();	
				// 设置SubAction为新建
				$('.manageAnnualLeaveRuleDetail_form input#subAction').val('createObject');
				// 修改按钮显示名称
				$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageAnnualLeaveRuleDetail_form input#subAction').val() == 'viewObject'){	
				// 编辑操作，Enable整个Form
				enableForm('manageAnnualLeaveRuleDetail_form');
				// 设置SubAction为编辑
				$('.manageAnnualLeaveRuleDetail_form input#subAction').val('modifyObject');
				// 修改按钮显示名称
				$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.save" />');
				// 更改Form Action
        		$('.manageAnnualLeaveRuleDetail_form').attr('action', 'annualLeaveRuleDetailAction.do?proc=modify_object');
        		// 科目下拉框依旧Disable 
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('manageAnnualLeaveRuleDetail_form');
				}
			}
		}); 
		
		// Header 列表
	 	$('#btnList').click( function () {
	 		if (agreest())
				link('annualLeaveRuleHeaderAction.do?proc=list_object');
		});	 
		
		// Detail 取消
		$('#btnCancelAnnualLeaveRuleDetail').click( function () {
			 if(agreest())
			link('sickLeaveSalaryDetailAction.do?proc=list_object&id='+$("#headerId").val());
		}); 
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageAnnualLeaveRuleHeader_form');
			$('#AnnualLeaveDetailRule-Information').show();
			$('.manageAnnualLeaveRuleHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.annual.leave.rule" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<bean:message bundle="public" key="oper.new" /> <bean:message bundle="management" key="management.annual.leave.rule" />');
		    $('#btnEditAnnualLeaveRuleHeader').val('<bean:message bundle="public" key="button.save" />');
		} 
	})(jQuery);
	
	// 点击超链接，ajax调用去修改页面
	function annualLeaveRuleDetailModify( detailId ){
		// 显示Cancel按钮
		$('#btnCancelsickLeaveSalaryDetail').show();
		// 显示List Detail Form
		$('#detailFormWrapper').show();	
		// Ajax加载sickLeaveSalary Detail修改页面
		loadHtml('#detailFormWrapper', "annualLeaveRuleDetailAction.do?proc=to_objectModify_ajax&detailId=" + detailId, true );
		// 修改按钮显示名称
		$('#btnEditAnnualLeaveRuleDetail').val('<bean:message bundle="public" key="button.edit" />');		
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageAnnualLeaveRuleHeader_form input#subAction').val();
	}; 
</script>