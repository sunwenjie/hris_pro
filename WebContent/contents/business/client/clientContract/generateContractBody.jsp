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
			<label id="pageTitle">�����ͬ���ݲ�ѯ</label>
		</div>
		<div class="inner" style="overflow: auto;">
			<div class="top">
				<kan:auth right="modify" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.save" />" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="export" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnExportPDF" id="btnExportPDF" value="����PDF" style="display: none;" onclick="exportPDF();" />
				</kan:auth>
				<kan:auth right="previous" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="" name="btnPrevious" id="btnPrevious" value="��һ��" />
				</kan:auth>
				<kan:auth right="submit" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" style="display: none;" />  
				</kan:auth>
				<kan:auth right="sealed" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnChop" id="btnChop" value="����" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="archive" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="function" name="btnArchive" id="btnArchive" value="�鵵" style="display: none;" /> 
				</kan:auth>
				<kan:auth right="list" action="<%=ClientContractAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
				<kan:auth right="export" action="<%=ClientContractAction.accessAction%>">
					<a id="exportPDF" name="exportPDF" class="commonTools" title="����PDF�ļ�" style="display: none;" onclick="exportPDF();"><img src="images/appicons/pdf_16.png" /></a>
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
		// ���ò˵�ѡ����ʽ
		$('#menu_client_Modules').addClass('current');	
		$('#menu_client_Business').addClass('selected');
		$('#menu_client_Contract').addClass('selected');

		// �༭��ť�¼�
		$('#btnEdit').click( function () { 
			if($('.manageContract_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageContract_form');
				// ����Subaction
        		$('.manageContract_form input#subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('<bean:message bundle="public" key="button.save" />');
        		// ����Page Title
    			$('#pageTitle').html('�����ͬ���ݱ༭');
        	}else{
				submit('manageContract_form');
        	}
		});
		
		// ��һ����ť�¼�
		$('#btnPrevious').click(function(){
			link('clientContractAction.do?proc=to_objectModify&id=<bean:write name="clientContractForm" property="encodedId" />');
		});
		
		// �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			// ���ĵ�ǰForm��SubAction
			$('.manageContract_form input#subAction').val('submitObject');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// ���°�ť�¼�
		$('#btnChop').click( function () { 
			// ����Form Action
    		$('.manageContract_form').attr('action', 'clientContractAction.do?proc=chop_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});
		
		// �鵵��ť�¼�
		$('#btnArchive').click( function () { 
			// ����Form Action
    		$('.manageContract_form').attr('action', 'clientContractAction.do?proc=archive_object');
    		enableForm('manageContract_form');
			submit('manageContract_form');
		});

		// �б�ť�¼�
		$('#btnList').click(function(){
			if (agreest())
			link('clientContractAction.do?proc=list_object');
		});	
		
		// �鿴ģʽ
		if($('.manageContract_form input#subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('manageContract_form');
			// ���İ�ť��ʾ��
    		$('#btnEdit').val('<bean:message bundle="public" key="button.edit" />');
			// ����Page Title
			$('#pageTitle').html('�����ͬ���ݲ�ѯ');
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

