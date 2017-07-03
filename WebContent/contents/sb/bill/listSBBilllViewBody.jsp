<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_SBBILL", "com.kan.hro.domain.biz.sb.SBDTO", "sbBillDetailViewForm" ) %> 
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, "com.kan.hro.domain.biz.sb.SBDTO", "JAVA_OBJECT_SBBILL" ) %>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectVendor.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_sb_Modules').addClass('current');
		$('#menu_sb_Bill').addClass('selected');
		<logic:equal name="role" value="5">
			$('#menu_sb_Bill').addClass('current');
        </logic:equal>
		$('#searchDiv').hide();
		
	    // 加载特殊下拉框
		loadHtml('#special_select', 'sbBillViewAction.do?proc=load_special_html&sbType=<bean:write name="sbBillDetailViewForm" property="sbType" />&groupColumn=<bean:write name="sbBillDetailViewForm" property="groupColumn" />&contractStatus=<bean:write name="sbBillDetailViewForm" property="contractStatus" />&sbStatuses=<bean:write name="sbBillDetailViewForm" property="sbStatus" />&monthly=<bean:write name="sbBillDetailViewForm" property="monthly" />&employeeSBId=<bean:write name="sbBillDetailViewForm" property="employeeSBId" />&status=<bean:write name="sbBillDetailViewForm" property="status" />&flag=<bean:write name="sbBillDetailViewForm" property="flag" />', false, null );
		<logic:equal name="role" value="5">
			$('#employeeId').parent().remove();
			$('#batchId').parent().remove();
			$('#employeeNameZH').parent().remove();
			$('#employeeNameEN').parent().remove();
			$('#contractId').parent().remove();
			$('#clientId').parent().remove();
			$('#clientNameZH').parent().remove();
			$('#clientNameEN').parent().remove();
			$('#vendorNameZH').parent().remove();
			$('#clientNo').parent().remove();
	    </logic:equal>
		// 搜索框加tip
		$('.list_form input#batchId').parent('li').find('label').append('<img style="hight 12px;width :12px;" src="images/tips.png" title="<bean:message bundle="sb" key="sb.bill.batch.id.tips" />" />');
		
		// 除去原有的“onclick”事件
		$('#searchBtn').attr('onclick','');
		// 重新绑定“onclick”事件
		$('#searchBtn').click( function(){
			
			var flag = -1;
			// 如果“申报月份”为“请选择”，其他搜索条件必须填写一项
			if( $('#monthly').val() == '0'){
				flag = 0;
				$('.list_form input:visible[type="text"]').each( function() {
					if( $(this).val() != '' ){
						flag = flag + 1;
					}
				});
				
				$('.list_form input:visible[type="checkbox"]').each( function() {
					if( $(this).is(':checked') == true ){
						flag = flag + 1;
					}
				});
				
				$('.list_form select').not('#monthly').each( function() {
					if( $(this).val() != '0' ){
						flag = flag + 1;
					}
				});
			}
			
			if( flag == 0 ){
				alert('“申报月份”为“请选择”，其他搜索条件必须填写一项；');
			}else{
				submitForm('list_form', 'searchObject', null, null, null, null);
			}
		});
	})(jQuery);
	
	<%--generate reset --%> 
	<%--<%= SearchRender.generateSpecialSearchReset( request, "com.kan.hro.domain.biz.sb.SBDTO" )%>--%>
	
	function resetForm(){
		var currDate = new Date();
		var monthly = currDate.getFullYear() + '/' + ( (currDate.getMonth() + 1) > 10 ? (currDate.getMonth() + 1) : '0' + (currDate.getMonth() + 1) );
		$('#monthly').val(monthly);
		$('#searchDiv input[type="text"]').val('');
		$('#searchDiv select').not('#monthly').val('0');
		$('div input[id^="sbStatus_"][type="checkbox"]').attr("checked",false);
	};
	<logic:equal name="role" value="4">
	function popupSelectTemplate(){
		linkForm('list_form', 'downloadObjects', 'sbBillViewAction.do?proc=list_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.sb.SBDTO');
	}
	</logic:equal>
	<logic:equal name="role" value="5">
	function popupSelectTemplate(){
		linkForm('list_form', 'downloadObjects', 'sbBillViewAction.do?proc=list_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.sb.SBDTO');
	}
	</logic:equal>
</script>
