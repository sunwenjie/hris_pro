<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="popup modal large content hide" id="sbDetailTempModalId">
    <div class="modal-header" id="sbDetailTempHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#sbDetailTempModalId').addClass('hide');$('#shield').hide();">��</a>
        <label>��Ŀ�����޸�</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnPopupSave" id="btnPopupSave" onclick="saveSBDetailTemp();" value="����" />
	   		<input type="button" class="delete" name="btnPopupRollback" id="btnPopupRollback" value="�˻�" />
	    </div>
	    <html:form action="sbDetailTempAction.do?proc=modify_object_ajax" styleClass="handleSBDetailTemp_form">
        	<div class="box toggableForm">
	        	<%= BaseAction.addToken( request ) %>
	        	<logic:present name="sbDetailTempForm" >
		        	<input type="hidden" name="detailId" value="<bean:write name="sbDetailTempForm" property="encodedId"/>"/>
		        	<input type="hidden" name="headerId" value="<bean:write name="sbDetailTempForm" property="encodedHeaderId"/>"/>
		        	<ol class="auto">
			        	<li>
			        		<label>���ID��</label><span><bean:write name="sbDetailTempForm" property="headerId"/></span>
						</li>
			        	<li>
			        		<label>��ĿID��</label><span><bean:write name="sbDetailTempForm" property="itemId"/></span>
						</li>
			        	<li>
			        		<label>��Ŀ���ƣ����ģ���</label><span><bean:write name="sbDetailTempForm" property="nameZH"/></span>
						</li>
			        	<li>
			        		<label>��Ŀ���ƣ�Ӣ�ģ���</label><span><bean:write name="sbDetailTempForm" property="nameEN"/></span>
						</li>
					</ol>
					<div class="kantab">
						<ol class="auto">
							<li >
								<label>�ϼƣ���˾��</label>
								<html:text property="amountCompany" maxlength="15" styleClass="handleSBDetailTemp_amountCompany"></html:text>
							</li>
							<li >
								<label>�ϼƣ����ˣ�</label>
								<html:text property="amountPersonal" maxlength="15" styleClass="handleSBDetailTemp_amountPersonal"></html:text>
							</li>
						</ol>
					</div>
				</logic:present>
			</div>
		</html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		
	})(jQuery);

	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#sbDetailTempModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	// ��ʾSBDetail�޸�ģ̬��
	function handleSBDetailTemp(detailId){
		loadHtmlWithRecall('#handlePopupWrapper', 'sbDetailTempAction.do?proc=get_object_ajax_popup&detailId=' + detailId, null, 'showPopup();');
	};
	
	// ��ʾ������
	function showPopup(){
		$('#sbDetailTempModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// ���ص�����
	function hidePopup(){
		$('#sbDetailTempModalId').addClass('hide');
    	$('#shield').hide();
	};
	
	// ҳ���ύ
	function saveSBDetailTemp(){
		submitForm('handleSBDetailTemp_form', "modifyObject", null, null, null, 'tableWrapper', null, hidePopup());
	};
</script>