<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
	final boolean isHRFunction = BaseAction.isHRFunction( request, null );
	request.setAttribute( "isHRFunction", isHRFunction ? "1" : "2" );
%>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="selectBankTemplate">
    <div class="modal-header" id="selectBankTemplateHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#selectBankTemplate').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label>
        	<!-- HRService -->
        	<logic:equal name="role" value="1">
        		<bean:message bundle="management" key="management.salary.template.selelct1" />
        	</logic:equal>
        	
        	<!-- InHouse -->
        	<logic:equal name="role" value="2">
	        	<logic:equal name="isHRFunction" value="1">
	        		<bean:message bundle="management" key="management.salary.template.selelct1" />
	        	</logic:equal>
	        	<logic:notEqual name="isHRFunction" value="1">
	        		<bean:message bundle="management" key="management.salary.template.selelct2" />
	        	</logic:notEqual>
        	</logic:equal>
        	
        	<logic:equal name="role" value="4">
	        	<bean:message bundle="management" key="management.salary.template.selelct1" />
        	</logic:equal>
        </label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.selected" />" />
	   		<logic:notEqual name="role" value="4">
		   		<logic:equal name="isHRFunction" value="1">
		    		<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
		    	</logic:equal>
		    </logic:notEqual>
	    	<logic:equal name="role" value="4">
	    		<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
	    	</logic:equal>	
	    </div>
        <html:form action="payslipViewAction">
			<input type="hidden" name="javaObjectName" id="javaObjectName" value="" />
			<fieldset>
				<ol class="auto">
					<!-- HRService -->
					<logic:equal name="role" value="1">
						<li>
							<label><bean:message bundle="management" key="management.salary.template" />&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.select.id.tips" />" src="images/tips.png"/></label> 
							<select id="bankTemplateId">
								<logic:present name="payslipDetailViewForm" property="bankTemplates">
									<logic:iterate id="bankTemplate" name="payslipDetailViewForm" property="bankTemplates">
										<option value='<bean:write name="bankTemplate" property="mappingId" />'><bean:write name="bankTemplate" property="mappingValue" /></option>
									</logic:iterate>
								</logic:present>
							</select>
						</li>
					</logic:equal>
					
					<!-- InHouse -->
					<logic:equal name="role" value="2">
						<li <logic:notEqual name="isHRFunction" value="1">style="display :none;"</logic:notEqual>>
							<label><bean:message bundle="management" key="management.salary.template" />&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.select.id.tips" />" src="images/tips.png"/></label> 
							<select id="bankTemplateId">
								<logic:present name="payslipDetailViewForm" property="bankTemplates">
									<logic:iterate id="bankTemplate" name="payslipDetailViewForm" property="bankTemplates">
										<option value='<bean:write name="bankTemplate" property="mappingId" />'><bean:write name="bankTemplate" property="mappingValue" /></option>
									</logic:iterate>
								</logic:present>
							</select>
						</li>
					</logic:equal>
					
					<logic:equal name="role" value="4">
						<li>
							<label><bean:message bundle="management" key="management.salary.template" />&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.select.id.tips" />" src="images/tips.png"/></label> 
							<select id="bankTemplateId">
								<logic:present name="payslipDetailViewForm" property="bankTemplates">
									<logic:iterate id="bankTemplate" name="payslipDetailViewForm" property="bankTemplates">
										<option value='<bean:write name="bankTemplate" property="mappingId" />'><bean:write name="bankTemplate" property="mappingValue" /></option>
									</logic:iterate>
								</logic:present>
							</select>
						</li>
					</logic:equal>
				</ol>
			</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 选择模板导出事件
		$('#btnConrirm').click( function() {
			// 提交“状态”添加导出功能
				var originalAction = $('.list_form').attr('action');
				 linkForm('list_form', 'downloadObjects', 'paymentAction.do?proc=export_object', 'fileType=excel&javaObjectName=' + $('#javaObjectName').val() + '&templateType=1&templateId=' + $('#bankTemplateId').val());
				$('.list_form').attr('action',originalAction);
		});
	})(jQuery);

	// 选择重置
	function resetFrom(){
		$('#bankTemplateId').val('0');
	};
	
	// 弹出模态窗口
	function popupSelectTemplate( javaObjectName ){
		$('#javaObjectName').val(javaObjectName);
		$('#selectBankTemplate').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectBankTemplate').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
</script>