<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="selectVendor">
    <div class="modal-header" id="selectVendorHeader"> 
        <a class="close" id="popup_class" data-dismiss="modal" onclick="$('#selectVendor').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="business" key="business.vendor.select" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="<bean:message bundle="public" key="button.selected" />" onclick="confirmSelect();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="sbAction">
			<input type="hidden" name="javaObjectName" id="javaObjectName" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.vendor" />&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.select.id.tips" />" src="images/tips.png"/></label> 
						<select id="vendorId">
							<logic:present name="sbBatchForm" property="vendors">
								<logic:iterate id="vendor" name="sbBatchForm" property="vendors">
									<option value='<bean:write name="vendor" property="mappingId" />'><bean:write name="vendor" property="mappingValue" /></option>
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
		$('#vendorId').val('0');
	};
	
	// 确认选择模板
	function confirmSelect(){
		$('#searchVendorId').val($('#vendorId').val());
		$('#popup_class').trigger('click');
		
		<logic:equal name="sbBatchForm" property="pageFlag" value="bill">
	        linkForm('list_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + $('#javaObjectName').val() + '&vendorId=' + $('#vendorId').val());
		</logic:equal>
		<logic:equal name="sbBatchForm" property="pageFlag" value="batch">
	        linkForm('list_form', 'downloadObjects', 'sbAction.do?proc=export_object', 'fileType=excel&javaObjectName=' + $('#javaObjectName').val());
		</logic:equal>
		<logic:equal name="sbBatchForm" property="pageFlag" value="header">
			linkForm('listHeader_form', 'downloadObjects', 'sbHeaderAction.do?proc=list_object', 'fileType=excel&vendorId=' + $('#vendorId').val());
		</logic:equal>
		
// 		$('#vendorId').val('0');
	};
	
	// 弹出模态窗口
	function popupSelectTemplate( javaObjectName ){
		$('#javaObjectName').val(javaObjectName);
		$('#selectVendor').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectVendor').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>