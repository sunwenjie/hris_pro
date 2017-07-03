<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="clientModalId">
    <div class="modal-header" id="clientHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#clientModalId').addClass('hide');$('#shield').hide();">��</a>
        <label>�ͻ�����</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSearch" id="btnSearch" value="<bean:message bundle="public" key="button.search" />" onclick="searchClient();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetClientSearch()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="clientAction.do?proc=list_object_ajax" styleClass="listClient_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>�ͻ�ID <img title="�ͻ�ID��֧��ģ����ѯ�������뾫ȷֵ��" src="images/tips.png"/></label> 
						<input type="text" name="clientId" maxlength="10" class="searchClient_clientId" />
					</li>
					<li>
						<label>�������</label>
						<input type="text" name="number" maxlength="20" class="searchClient_number" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>�ͻ����ƣ����ģ�</label> 
						<input type="text" name="nameZH" maxlength="100" class="searchClient_nameZH" />
					</li>
					<li>
						<label>�ͻ����ƣ�Ӣ�ģ�</label> 
						<input type="text" name="nameEN" maxlength="100" class="searchClient_nameEN" />
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="clientTableWrapper">
			<logic:present name="clientHolder">
				<!-- ����Tabҳ�� -->
				<jsp:include page="/popup/table/searchClientTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// ������������
	function resetClientSearch(){
		<logic:equal name="role" value="1">
			$('.searchClient_clientId').val('');
		</logic:equal>
		$('.searchClient_number').val('');
		$('.searchClient_nameZH').val('');
		$('.searchClient_nameEN').val('');
	};
	
	// ��ȷ����ѡ���¼�
	function selectClient(){
		var selectedId = $('#clientTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		
		if(selectedId != null && selectedId.trim() != ''){
			$('#clientId').val( selectedId );
			$('#clientId').trigger('keyup');
			$('#clientModalId').addClass('hide');
			$('#shield').hide();
			// ���������Ϣ
			$('#clientId').next('label').remove();
			$('#clientId').next('label').removeClass('error');
		}else{
			alert("��ѡ��ͻ���¼��");
		}
		
	};

	// ��֤����
	function checkSearchClient(){
		var flag = false;
		
		// ��֤����������
		$('select[class^="searchClient_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// ��֤�ı�������
		$('input[class^="searchClient_"]').each(function(){
			<logic:equal name="role" value="2">
				if($(this).attr('class') != 'searchClient_clientId'){
			</logic:equal>
			if($(this).val()){
		 		flag = true;
				return false;
			}
			<logic:equal name="role" value="2">
				}
			</logic:equal>
		});
		
		if(!flag){
			$('#clientTableWrapper').html('');
			alert("����������������");
		}
		
		return flag;
	}
	
	// ���������¼�
	function searchClient(){
		if(checkSearchClient()){
			submitForm('listClient_form', null, null, 'clientId', 'desc', 'clientTableWrapper');
		}
	};
	
	// ����ģ̬����
	function popupClientSearch(){
		$('#clientModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc�����¼� - ���ص�����
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#clientModalId').addClass('hide');
	    	$('#shield').hide();
	    } 
	});
	
</script>