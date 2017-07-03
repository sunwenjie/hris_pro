<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="vendorModalId">
    <div class="modal-header" id="vendorHeader" style="cursor:move;"> 
        <a class="close" data-dismiss="modal" onclick="$('#vendorModalId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="business" key="business.vendor.search" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnSearch" id="btnSearch" value="<bean:message bundle="public" key="button.search" />" onclick="searchVendor();" />
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetVendorSearch()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="vendorAction.do?proc=list_object_ajax" styleClass="listVendor_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="" />
			<input type="hidden" name="page" id="page" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<fieldset>
				<ol class="auto">
					<li>
						<label>
							<bean:message bundle="business" key="business.vendor.id" />
							&nbsp;&nbsp;<img title="<bean:message bundle="public" key="popup.common.id.tips" />" src="images/tips.png"/>
						</label> 
						<input type="text" name="vendorId" maxlength="10" class="searchVendor_vendorId" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.vendor.name.cn" /></label> 
						<input type="text" name="nameZH" maxlength="100" class="searchVendor_nameZH" />
					</li>
					<li>
						<label><bean:message bundle="business" key="business.vendor.name.en" /></label> 
						<input type="text" name="nameEN" maxlength="100" class="searchVendor_nameEN" />
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
    <div class="modal-body-appand">
		<div id="vendorTableWrapper">
			<logic:present name="vendorHolder">
				<!-- 包含Tab页面 -->
				<jsp:include page="/popup/table/searchVendorTable.jsp"></jsp:include>
			</logic:present>
		</div>
	</div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 搜索区域重置
	function resetVendorSearch(){
		$('.searchVendor_vendorId').val('');
		$('.searchVendor_nameZH').val('');
		$('.searchVendor_nameEN').val('');
	};
	
	// “确定”选择事件
	function selectVendor(){
		var selectedId = $('#vendorTableWrapper input[id^="kanList_chkSelectRecord_"]:checked').val();
		
		if(selectedId != null && selectedId != ''){
			$('#vendorId').val( selectedId );
			$('#vendorId').trigger('keyup');
			$('#vendorModalId').addClass('hide');
			$('#shield').hide();
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	};
	
	// 验证数据
	function checkSearchVendor(){
		var flag = false;
		
		// 验证下拉框数据
		$('select[class^="searchVendor_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// 验证文本框数据
		$('input[class^="searchVendor_"]').each(function(){
			<logic:equal name="role" value="2">
				if($(this).attr('class') != 'searchVendor_clientId'){
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
			$('#vendorTableWrapper').html('');
			alert('<bean:message bundle="public" key="popup.not.input.search.condition" />');
		}
		
		return flag;
	}
	
	// “搜索”事件
	function searchVendor(){
		if(checkSearchVendor()){
			submitForm('listVendor_form', null, null, 'vendorId', 'desc', 'vendorTableWrapper');
		}
	};
	
	// 验证数据
	function checkSearchClient(){
		var flag = false;
		
		// 验证下拉框数据
		$('select[class^="searchClient_"]').each(function(){
			if($(this).val() && $(this).val() != "0"){
		 		flag = true;
				return false;
			}
		});
		
		// 验证文本框数据
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
		
		return flag;
	}
	
	// “搜索”事件
	function searchClient(){
		if(checkSearchClient()){
			submitForm('listClient_form', null, null, 'clientId', 'desc', 'clientTableWrapper');
		}else{
			alert('<bean:message bundle="public" key="popup.not.input.search.condition" />');
		}
	};
	
	// 弹出模态窗口
	function popupVendorSearch(){
		$('#vendorModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#vendorModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
</script>