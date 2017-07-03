<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="selectCB">
    <div class="modal-header" id="selectCBHeader"> 
        <a class="close" id="popup_class" data-dismiss="modal" onclick="$('#selectCB').addClass('hide');$('#shield').hide();">��</a>
        <label>ѡ���̱�����</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnConrirm" id="btnConrirm" value="ȷ��" onclick="confirmSelect();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetFrom()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="sbAction">
			<input type="hidden" name="javaObjectName" id="javaObjectName" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>�̱�����<img title="Ĭ�ϡ���ѡ�񡱵������У�ѡ��������̱�����������" src="images/tips.png"/></label> 
						<select id="cbId">
							<logic:present name="cbBatchForm" property="cbIds">
								<logic:iterate id="cb" name="cbBatchForm" property="cbIds">
									<option value='<bean:write name="cb" property="mappingId" />'><bean:write name="cb" property="mappingValue" /></option>
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
	// ѡ������
	function resetFrom(){
		$('#cbId').val('0');
	};
	
	// ȷ��ѡ��ģ��
	function confirmSelect(){
		$('#cbId').val($('#cbId').val());
		$('#popup_class').trigger('click');
		<logic:equal name="cbBatchForm" property="pageFlag" value="batch">
	        linkForm('list_form', 'downloadObjects', 'cbAction.do?proc=export_object', 'fileType=excel&javaObjectName=' + $('#javaObjectName').val());
		</logic:equal>
		<logic:equal name="cbBatchForm" property="pageFlag" value="header">
	        linkForm('listHeader_form', 'downloadObjects', 'cbAction.do?proc=export_object', 'fileType=excel&javaObjectName=' + $('#javaObjectName').val());
		</logic:equal>
	};
	
	// ����ģ̬����
	function showPopup( javaObjectName ){
		$('#javaObjectName').val(javaObjectName);
		$('#selectCB').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#selectCB').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>