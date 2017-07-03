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
		// 显示订单名称
		$('.required').parent().after('<ol class="auto"><li><label>订单ID</label><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderDetailForm" property="orderHeaderId"/></a></li></ol>');
		
		// 界面选择费用方式”选择按人头时，“计算方式”、“折算方式”才出现
		$('#packageType').change(function(){
			if( $(this).val() == 1 ){
				$('#calculateTypeLI').show();
				$('#divideTypeLI').show();
			}else{
				$('#calculateTypeLI').hide();
				$('#divideTypeLI').hide();
			}
		});		
		
		// 界面初始Load触发
		$('#packageType').change();
		
		//	“基数来源”不为请选择时，隐藏相关项
		$('#baseFrom').change( function () {
			if( $(this).val() != null && $(this).val() != 0 ){
				$('#percentageLI').show();
				$('#fixLI').show();
			}else{
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});
		// 界面初始Load触发
		$('#baseFrom').change();
		
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_DETAIL", null, null, null, null ) %>
		
		$("#btnList").val('返回');
		
		// 返回按钮点击事件
		$("#btnList").click(function(){
			if(agreest())
	        link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailForm" property="encodedOrderHeaderId"/>');
		});
		
		// “计算结果上限”键盘数入事件（小于“计算结果下限”，则取“计算结果下限”）
		$("#resultCap").keyup(function(){
			if($("#resultCap").val() != null && $("#resultFloor").val() != null){
				if(parseFloat($("#resultCap").val()) < parseFloat($("#resultFloor").val()) ){
					$("#resultCap").val($("#resultFloor").val());
				}
			}
		});
		
		// “计算结果下限”键盘数入事件（大于“计算结果上限”，则取“计算结果上限”）
		$("#resultFloor").keyup(function(){
			if($("#resultCap").val() != null && $("#resultFloor").val() != null){
				if(parseFloat($("#resultCap").val()) < parseFloat($("#resultFloor").val())){
					$("#resultFloor").val($("#resultCap").val());
				}
			}
		});

		//	添加Tab
		loadHtml('#special_info', 'clientOrderDetailAction.do?proc=list_special_info_html&orderDetailId=<bean:write name="clientOrderDetailForm" property="encodedId"/>', !isCreate());	
	})(jQuery);
	
	// 当前是否新建情况
	function isCreate(){
		if(getSubAction() == 'createObject'){
			return true;
		}else{
			return false;
		}
	};
	
	function init(){	
		// 销售方式为1固定金 - 隐藏费用来源，结果上限，结果下限；2加价 - 不变；打包 - 隐藏费用来源，结果上限，结果下限，计算公式，更多信息
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

