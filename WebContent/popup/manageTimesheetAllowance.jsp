<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="timesheetAllowanceModualId">
    <div class="modal-header" id="employeeContractSBHeader">
        <a class="close" data-dismiss="modal" onclick="hidePopup();">×</a>
        <label>工资性补助</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input title="保存记录" type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="保存"/>
	    	<input title="重置输入框信息" type="button" class="reset" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
       <html:form action="timesheetAllowanceAction.do?proc=add_object" styleClass="manageTimesheetAllowance_form">
			<%= BaseAction.addToken( request ) %>
			<input type="hidden" id="allowanceId" name="allowanceId" value="<bean:write name="timesheetAllowanceForm" property="encodedId"/>" />
			<input type="hidden" id="headerId" name="headerId" value="<bean:write name="timesheetHeaderForm" property="headerId"/>" />
			<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="timesheetAllowanceForm" property="subAction"/>" /> 
			<fieldset>
				<ol class="auto">
					<li class="required">
						<label><em>* </em>必填字段</label>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>科目  <em>* </em></label> 
						<html:select property="itemId" styleClass="manageTimesheetAllowance_itemId" >
							<option value="0">请选择</option>
							<html:optionsCollection property="salaryItems" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label>基数  <em> *</em></label>
						<html:text property="base" styleClass="manageTimesheetAllowance_base"  />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>备注</label> 
						<html:textarea property="description" styleClass="manageTimesheetAllowance_description"></html:textarea>
					</li>
					<li>
						<label>状态  <em> *</em></label> 
						<html:select property="status" styleClass="manageTimesheetAllowance_status" styleId="status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
			</fieldset>
		</html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 保存按钮点击事件
		$('#btnPopupSave').click( function () {	
			 if($('.manageTimesheetAllowance_form #subAction').val() == 'viewObject' ){
				// Enable整个Form
				enableForm('manageTimesheetAllowance_form');
				$('#btnPopupSave').val('保存');
				// 设置SubAction为编辑
				$('.manageTimesheetAllowance_form input#subAction').val('modifyObject');
				// 更改Form Action
        		$('.manageTimesheetAllowance_form').attr('action', 'timesheetAllowanceAction.do?proc=modify_object');
			}else{
				var flag = 0;
				
				flag = flag + validate( 'manageTimesheetAllowance_itemId', true, 'select', 0, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_base', true, 'currency', 0, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_description', false, 'select', 500, 0 );
				flag = flag + validate( 'manageTimesheetAllowance_status', true, 'select', 0, 0 );
				
				if( flag == 0){
					submit('manageTimesheetAllowance_form'); 
				}
			}
		});
	})(jQuery);

	// 搜索区域重置
	function resetPopup(){
		$('.manageTimesheetAllowance_itemId').val('0');
		$('.manageTimesheetAllowance_base').val('');
		$('.manageTimesheetAllowance_description').val('');
		$('.manageTimesheetAllowance_status').val('0');
	};
	
	// 显示弹出框
	function showPopup(){
		$('#timesheetAllowanceModualId').removeClass('hide');
    	$('#shield').show();
    	if( getPopSubAction() == 'viewObject'){
    		$('#btnPopupSave').val('编辑');
    		$('#btnPopupSave').removeAttr('disabled');
    	}else{
    		resetPopup();
    		enableForm('manageTimesheetAllowance_form');
    		$('.manageTimesheetAllowance_form').attr('action', 'timesheetAllowanceAction.do?proc=add_object');
    		$('.manageTimesheetAllowance_status').val('1');
    		$('#btnPopupSave').val('保存');
    	}
    	
    	// 清空错误
    	cleanAllError();
	};

	// 隐藏弹出框
	function hidePopup(){
		$('#timesheetAllowanceModualId').addClass('hide');
    	$('#shield').hide();
    	resetPopup();
    	$('.manageTimesheetAllowance_form input#subAction').val('');
	};
	
	// 清空错误
	function cleanAllError(){
		cleanError('manageTimesheetAllowance_itemId');
		cleanError('manageTimesheetAllowance_base');
		cleanError('manageTimesheetAllowance_description');
		cleanError('manageTimesheetAllowance_status');
	};
	
	// 获取当前SubAction
	function getPopSubAction(){
		return $('.manageTimesheetAllowance_form input[id="subAction"]').val();
	};

	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>