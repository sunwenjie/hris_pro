<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="orderModalId">
    <div class="modal-header" id="orderHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#orderModalId').addClass('hide');$('#shield').hide();">×</a>
        <label>订单搜索</label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" name="btnSearch" id="btnSearch" class="" value="<bean:message bundle="public" key="button.search" />" onclick="searchOrder();" />
	    	<input type="button" name="btnCancel" id="btnCancel" class="reset" value="<bean:message bundle="public" key="button.reset" />" onclick="resetOrderSearch()" />
	    </div>
        <html:form action="clientOrderHeaderAction.do?proc=list_object_ajax" styleClass="listOrder_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>订单ID <img title="订单ID不支持模糊查询，需输入精确值。" src="images/tips.png"/></label> 
						<input type="text" name="orderHeaderId" maxlength="10" class="searchOrder_orderHeaderId" />
					</li>
					<li>
						<label>客户ID <img title="客户ID不支持模糊查询，需输入精确值。" src="images/tips.png"/></label>
						<input type="text" name="clientId" maxlength="10" class="searchOrder_clientId" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>客户名称（中文）</label> 
						<input type="text" name="clientNameZH" maxlength="100" class="searchOrder_clientNameZH" />
					</li>
					<li>
						<label>客户名称（英文）</label> 
						<input type="text" name="clientNameEN" maxlength="100" class="searchOrder_clientNameEN" />
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="orderTableWrapper">
			<logic:present name="orderHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/searchOrderTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetOrderSearch(){
		$('.searchOrder_orderHeaderId').val('');
		<logic:equal name="role" value="1">
			$('.searchOrder_clientId').val('');
		</logic:equal>
		$('.searchOrder_clientNameZH').val('');
		$('.searchOrder_clientNameEN').val('');
	};
	
	// “确定”选择事件
	function selectOrder(){
		var selectedId = $('#orderTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		
		if(selectedId != null && selectedId != ''){
			$('#orderId').val( selectedId );
			$('#orderId').trigger('keyup');
			$('#orderModalId').addClass('hide');
			$('#shield').hide();
			// 清除错误消息
			$('#orderId').next('label').remove();
			$('#orderId').next('label').removeClass('error');
		}else{
			alert("请选择订单记录！");
		}
	};

	// 验证数据
	function checkSearchOrder(){
		var flag = false;
		
		// 验证下拉框数据
		$('select[class^="searchOrder_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// 验证文本框数据
		$('input[class^="searchOrder_"]').each(function(){
			<logic:equal name="role" value="2">
				if($(this).attr('class') != 'searchOrder_clientId'){
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
			$('#orderTableWrapper').html('');
			alert("请输入搜索条件！");
		}
		
		return flag;
	}
	
	// “搜索”事件
	function searchOrder(){
		if(checkSearchOrder()){
			submitForm('listOrder_form', null, null, 'orderId', 'desc', 'orderTableWrapper');
		}
	};
	
	// 弹出模态窗口
	function popupOrderSearch(){
		$('#orderModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#orderModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>