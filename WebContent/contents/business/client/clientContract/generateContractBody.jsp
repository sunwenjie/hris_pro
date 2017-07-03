<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<div id="content">
	<div id="contract" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">商务合同内容查询</label>
		</div>
		<div class="inner" style="overflow: auto;">
			<div class="top">
				<kan:auth right="modify" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="export" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="导出PDF" style="display: none;" onclick="exportPDF();" />
				</kan:auth>
				<kan:auth right="previous" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnPrevious" id="btnPrevious" value="上一步" />
				</kan:auth>
				<kan:auth right="submit" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />  
				</kan:auth>
				<kan:auth right="sealed" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnChop" id="btnChop" value="盖章" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="archive" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnArchive" id="btnArchive" value="归档" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="list" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
				<kan:auth right="export" action="<%=ClientContractAction.accessAction%>">
					<a id="exportPDF" name="exportPDF" class="commonTools" title="导出PDF文件" style="display: none;" onclick="exportPDF();"><img src="images/appicons/pdf_16.png" /></a>
				</kan:auth>
			</div>
			<div class="kantab" style="width: 649px; padding: 75px 56px 75px 56px;">
				<html:form action="clientContractAction.do?proc=modify_object_step2" styleClass="manageContract_form">
					<%= BaseAction.addToken( request ) %>
					<input type="hidden" name="id" id="id" value="<bean:write name="clientContractForm" property="encodedId" />" />
					<input type="hidden" name="subAction" id="subAction" value="<bean:write name="clientContractForm" property="subAction" />" />
					<input type="hidden" name="status" id="status" value="<bean:write name="clientContractForm" property="status" />" />
					<bean:write name="clientContractForm" property="content" filter="false" />
				</html:form>
			</div>
		</div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {	
		// 设置菜单选择样式
		$('#menu_client_Modules').addClass('current');	
		$('#menu_client_Business').addClass('selected');
		$('#menu_client_Contract').addClass('selected');

		// 编辑按钮事件
		$('#btnEdit').click( function () { 
			if($('.manageContract_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageContract_form');
				// 更改Subaction
        		$('.manageContract_form input#subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// 更换Page Title
    			$('#pageTitle').html('商务合同内容编辑');
        	}else{
				submit('manageContract_form');
        	}
		});
		
		// 上一步按钮事件
		$('#btnPrevious').click(function(){
			link('clientContractAction.do?proc=to_objectModify&id=<bean:write name="clientContractForm" property="encodedId" />');
		});
		
		// 提交按钮事件
		$('#btnSubmit').click( function () { 
			// 更改当前Form的SubAction
			$('.manageContract_form input#subAction').val('submitObject');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// 盖章按钮事件
		$('#btnChop').click( function () { 
			// 更改Form Action
    		$('.manageContract_form').attr('action', 'clientContractAction.do?proc=chop_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// 归档按钮事件
		$('#btnArchive').click( function () { 
			// 更改Form Action
    		$('.manageContract_form').attr('action', 'clientContractAction.do?proc=archive_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});

		// 列表按钮事件
		$('#btnList').click(function(){
			if (agreest())
			link('clientContractAction.do?proc=list_object');
		});	
		
		// 查看模式
		if($('.manageContract_form input#subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('manageContract_form');
			// 更改按钮显示名
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// 更换Page Title
			$('#pageTitle').html('商务合同内容查询');
		}
		
		if($('#status').val() == '1' || $('#status').val() == '4'){
			$('#btnEdit').show();
			$('#btnSubmit').show();
		}
		
		if($('#status').val() == '3'){
			$('#btnChop').show();
		}
		
		if($('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' || $('#status').val() == '7'){
			$('#btnExportPDF').show();
			$('#exportPDF').show();
		}
		
		if($('#status').val() == '5'){
			$('#btnArchive').show();
		}
		
		setInputValueForPage();
	})(jQuery);
	
	function exportPDF(){
		link('clientContractAction.do?proc=export_contract_pdf&id=<bean:write name="clientContractForm" property="encodedId" />');
	};
	
	function setInputValueForPage(){
		
		$.ajax({
			url: 'clientContractAction.do?proc=setInputValueForPage&contractId='+$("#id").val(), 
			type: 'POST', 
			traditional: true,
			data:$('.otBackForm').serialize(),
			dataType : 'json',
			async:false,
			success: function(result){
				for(var i = 0 ; i < result.length ; i++){
					if($("#"+result[i].id).length !=0){
						if($("#"+result[i].id)[0].tagName = "INPUT"){
							if ((result[i].value != null && result[i].value != "") || ($('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' || $('#status').val() == '7') ) {
								$("#"+result[i].id).val(result[i].value);
								$("#"+result[i].id).attr("readonly","readonly");
							}
						}
						if($("#"+result[i].id)[0].tagName = "TEXTAREA"){
							if ((result[i].value != null && result[i].value != "") || ($('#status').val() == '3' || $('#status').val() == '5' || $('#status').val() == '6' || $('#status').val() == '7') ) {
								$("#"+result[i].id).val(result[i].value);
								$("#"+result[i].id).attr("readonly","readonly");
							}
						}
					}
				}
			} 
		});
	}
</script>

