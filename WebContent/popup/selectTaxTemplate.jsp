<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="selectTaxTemplate">
    <div class="modal-header" id="selectTaxTemplateHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#selectTaxTemplate').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="management" key="management.tax.template.select" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.selected" />" onclick="confirmSelect();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="payslipViewAction">
			<input type="hidden" name="javaObjectName" id="javaObjectName" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label><bean:message bundle="management" key="management.tax.template" />&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.select.id.tips" />" src="images/tips.png"/></label> 
						<select id="taxTemplateId">
							<logic:present name="payslipDetailViewForm" property="taxTemplates">
								<logic:iterate id="taxTemplate" name="payslipDetailViewForm" property="taxTemplates">
									<option value='<bean:write name="taxTemplate" property="mappingId" />'><bean:write name="taxTemplate" property="mappingValue" /></option>
								</logic:iterate>
							</logic:present>
						</select>
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 选择重置
	function resetFrom(){
		$('#taxTemplateId').val('0');
	};
	
	// 确认选择模板
	function confirmSelect(){
		$('.close').trigger('click');
        linkForm('list_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + $('#javaObjectName').val() + '&templateType=2&templateId=' + $('#taxTemplateId').val());
	};
	
	// 弹出模态窗口
	function popupSelectTemplate( javaObjectName ){
		$('#javaObjectName').val(javaObjectName);
		$('#selectTaxTemplate').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectTaxTemplate').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>