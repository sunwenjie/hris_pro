<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_DETAIL", "clientOrderDetailForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʾ��������
		$('.required').parent().after('<ol class="auto"><li><label>����ID</label><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderDetailForm" property="orderHeaderId"/></a></li></ol>');
		
		// ����ѡ����÷�ʽ��ѡ����ͷʱ�������㷽ʽ���������㷽ʽ���ų���
		$('#packageType').change(function(){
			if( $(this).val() == 1 ){
				$('#calculateTypeLI').show();
				$('#divideTypeLI').show();
			}else{
				$('#calculateTypeLI').hide();
				$('#divideTypeLI').hide();
			}
		});		
		
		// �����ʼLoad����
		$('#packageType').change();
		
		//	��������Դ����Ϊ��ѡ��ʱ�����������
		$('#baseFrom').change( function () {
			if( $(this).val() != null && $(this).val() != 0 ){
				$('#percentageLI').show();
				$('#fixLI').show();
			}else{
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});
		// �����ʼLoad����
		$('#baseFrom').change();
		
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_DETAIL", null, null, null, null ) %>
		
		$("#btnList").val('����');
		
		// ���ذ�ť����¼�
		$("#btnList").click(function(){
			if(agreest())
	        link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailForm" property="encodedOrderHeaderId"/>');
		});
		
		// �����������ޡ����������¼���С�ڡ����������ޡ�����ȡ�����������ޡ���
		$("#resultCap").keyup(function(){
			if($("#resultCap").val() != null && $("#resultFloor").val() != null){
				if(parseFloat($("#resultCap").val()) < parseFloat($("#resultFloor").val()) ){
					$("#resultCap").val($("#resultFloor").val());
				}
			}
		});
		
		// �����������ޡ����������¼������ڡ����������ޡ�����ȡ�����������ޡ���
		$("#resultFloor").keyup(function(){
			if($("#resultCap").val() != null && $("#resultFloor").val() != null){
				if(parseFloat($("#resultCap").val()) < parseFloat($("#resultFloor").val())){
					$("#resultFloor").val($("#resultCap").val());
				}
			}
		});

		//	���Tab
		loadHtml('#special_info', 'clientOrderDetailAction.do?proc=list_special_info_html&orderDetailId=<bean:write name="clientOrderDetailForm" property="encodedId"/>', !isCreate());	
	})(jQuery);
	
	// ��ǰ�Ƿ��½����
	function isCreate(){
		if(getSubAction() == 'createObject'){
			return true;
		}else{
			return false;
		}
	};
	
	function init(){	
		// ���۷�ʽΪ1�̶��� - ���ط�����Դ��������ޣ�������ޣ�2�Ӽ� - ���䣻��� - ���ط�����Դ��������ޣ�������ޣ����㹫ʽ��������Ϣ
		var salesType = '<bean:write name="clientOrderHeaderVO" property="salesType"/>';
		if(salesType==1){
			$("#baseFromLI").hide();
			$("#resultCapLI").hide();
			$("#resultFloorLI").hide();
		}else if(salesType==3){
			$("#baseFromLI").hide();
			$("#resultCapLI").hide();
			$("#resultFloorLI").hide();
			$("#formularLI").hide();
			$("a[id^='HRO_BIZ_CLIENT_ORDER_DETAIL_']").hide();
		}
	};
</script>

