<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<style type="text/css">
	#ckbDiv
	{
		width: 215px;
		height: 15px;	
		padding: 5px 12px 12px 5px;
	}
	
	#ckbDiv span
	{
	    position: relative;
	    top: 2px;
	    right: -5px;
	}
</style>
	
<html:form action="shiftDetailAction.do?proc=add_object" styleClass="manageShiftDetail_form">
	<%= BaseAction.addToken( request ) %>
	<input type="hidden" name="subAction" id="subAction" class="subAction" value="<bean:write name="shiftDetailForm" property="subAction" />" />
	<input type="hidden" name="detailId" value="<bean:write name="shiftDetailForm" property="encodedId" />" />
	<input type="hidden" name="id" value="<bean:write name="shiftHeaderForm" property="encodedId" />" />
	<fieldset>
		<ol class="auto">
			<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
		</ol>
		<ol class="auto">	
			<li>
				<label><bean:message bundle="management" key="management.shift.detail.date" /><em> *</em></label> 
				<html:text property="shiftDay" maxlength="10" readonly="readonly" styleClass="Wdate manageShiftDetail_shiftDay" onfocus="WdatePicker()"/>
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label><bean:message bundle="management" key="management.shift.detail.period" /><em> *</em></label>
				<div id="ckbDiv">
					<span>
						<input type="checkbox" id="chk_all_d"  /><bean:message bundle="management" key="management.shift.select.all" />
					</span>
					<span style="float: right;position: relative;left: 0px;">
						<input type="checkbox" id="openORClose" class="inquiry"  /><bean:message bundle="management" key="management.shift.show.or.hide" />
					</span>
				</div>
			</li>
		</ol>	
		<ol class="auto">
			<li>
				<label></label>
				<div id="all_time" style="width: 215px;" >
					<logic:iterate id="shiftPeriod" name="shiftDetailForm" property="shiftPeriods" indexId="number">
						<label><input type="checkbox" id="chk_<%=number+1 %>" class="manageShiftDetail_shiftPeriodArray" name="shiftPeriodArray" value="<%=number+1 %>" /><span><bean:write name="shiftPeriod" property="mappingValue" /></span></label>
					</logic:iterate>	
				</div>
			</li>
		</ol>	
	</fieldset>
</html:form>

<script type="text/javascript">
	(function($) {
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		shiftDefineInitHide_d();
		
		// 全选 
		$('form.manageShiftDetail_form #chk_all_d').click(function(){
			var flag = $(this).is(':checked');
			$('form.manageShiftDetail_form input[id^="chk_"]').not(':hidden').attr('checked',flag); 
		});
		
		// 修改状态下，选中checkbox
		if($('form.manageShiftDetail_form input#subAction').val() == 'viewObject'){
			var array = '<bean:write name="shiftDetailForm" property="shiftPeriod" />'.replace('{','').replace('}','').split(',');
			for( var val in array){
				$('#chk_' +array[val] +'').attr('checked',true); 
			}
		}
		
		// 显示、隐藏时间段Click
		$('form.manageShiftDetail_form #openORClose').click(function(){
			if($(this).hasClass('inquiry')){
				shiftDefineShowAll_d();
				$(this).removeClass('inquiry');
			}else{
				shiftDefineInitHide_d();
				$(this).addClass('inquiry');
			}
		});
	})(jQuery);
	
	function shiftDefineInitHide_d(){
		$('form.manageShiftDetail_form #all_time input[type="checkbox"]').each(function(){
			if($(this).val() <= 18 || $(this).val() >= 37){
				$(this).parents('label').hide();
			}
		});
	};
	
	function shiftDefineShowAll_d(){
		$('form.manageShiftDetail_form #all_time input[type="checkbox"]').each(function(){
			if($(this).is(':hidden')){
				$(this).parents('label').show();
			}
		});
	}; 
</script>
