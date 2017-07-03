<%@ page pageEncoding="GBK" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_SETTLE_ORDER_BATCH_TEMP", "batchTempForm", true ) %>
</div>

<div id="popupWrapper">
	<logic:equal name="role" value="1">
		<jsp:include page="/popup/searchClient.jsp"></jsp:include>
		<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	</logic:equal>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>
						
<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_SETTLE_ORDER_BATCH_TEMP", null, null, null ) %>
		
		$('#pageTitle').html('������������');

		// ��ӡ������ͻ���Ϣ��
		$('#clientIdLI').append('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="�����ͻ���¼" /></a>');
		// ��ӡ�����������Ϣ��
		$('#orderIdLI').append('<a onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="����������¼" /></a>');
		// ��ӡ�����������Ϣ��¼��
		$('#contractIdLI').append('<a onclick="popupContractSearch();" class="kanhandle"><img src="images/search.png" title="����������Ϣ��¼" /></a>');

		// ��Ӱ���ѡ��
		$('#accountPeriodLI').parent().after( 
			'<ol class="auto"><li><label>�˵�����</label><div style="width: 215px;"><span><input type="checkbox" name="containSalary" value="1" checked> ���� &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containSB" value="1" checked> �籣������ &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containCB" value="1" checked> �̱�<br/><input type="checkbox" name="containOther" id="containOther" value="1" checked> ���� &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containServiceFee" id="containServiceFee" value="1" checked> �����</span></div></li></ol>'
		);
		
		// �����ͷ������Ҫͬʱѡ��
		$('#containServiceFee').click(function(){
			if($(this).attr('checked') == 'checked'){
				$('#containOther').attr('checked', 'checked');
			}else{
				$('#containOther').removeAttr('checked');
			}
		});
		
		// �����ͷ������Ҫͬʱѡ��
		$('#containOther').click(function(){
			if($(this).attr('checked') == 'checked'){
				$('#containServiceFee').attr('checked', 'checked');
			}else{
				$('#containServiceFee').removeAttr('checked');
			}
		});
		
		// ���û���ڼ����Сʱ��
		$('#accountPeriod').attr('onFocus', "WdatePicker({minDate:'" + $('#accountPeriod').val() + "'})");
	})(jQuery);
</script>

