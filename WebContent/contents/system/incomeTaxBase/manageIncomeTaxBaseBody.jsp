<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div id="socialBenefitHeader" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">��˰�������</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/system/incomeTaxBase/form/manageIncomeTaxBaseForm.jsp" flush="true"/>
		</div>
	</div>
</div>
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_IncomeTax').addClass('selected');
		$('#menu_system_IncomeTaxBase').addClass('selected');

		// ���İ�ť��ʾ��
		$('#btnEdit').val('����');
		
		// �༭��ť����¼�
		$('#btnEdit').click( function () { 
			if($('.manageIncomeTaxBase_form input#subAction').val() == 'viewObject'){
				// Enable form
        		enableForm('manageIncomeTaxBase_form');
				// ����SubAction
        		$('.manageIncomeTaxBase_form input.subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('����');
        		// ����Page Title
        		$('#pageTitle').html('�����༭');
				// ����Form Action
        		$('.manageIncomeTaxBase_form').attr('action', 'incomeTaxBaseAction.do?proc=modify_object');
        	}else{
        		var flag = 0;
    			
    			flag = flag + validate("manageIncomeTaxBase_nameZH", true, "common", 100, 0);
    			flag = flag + validate("manageIncomeTaxBase_nameEN", false, "common", 100, 0);
    			flag = flag + validate("manageIncomeTaxBase_base", false, "currency", 20, 0);
    			flag = flag + validate("manageIncomeTaxBase_baseForeigner", false, "currency", 20, 0);
    			flag = flag + validate("manageIncomeTaxBase_description", false, "common", 500, 0);
    			flag = flag + validate("manageIncomeTaxBase_status", true, "select", 0, 0);
    			if(flag == 0){
    				submit('manageIncomeTaxBase_form');
    			}
        	}
		});
		
		$('.manageIncomeTaxRangeHeader_startDate').focus(function(){
			WdatePicker({ 
				maxDate: '#F{$dp.$D(\'endDate\')}' 
			});
		});
		
		$('.manageIncomeTaxRangeHeader_endDate').focus(function(){
			WdatePicker({ 
				minDate: '#F{$dp.$D(\'startDate\')}',
				maxDate:'2020-10-01'
			});
		});
		
		// �鿴ģʽ
		if($('.manageIncomeTaxBase_form input.subAction').val() == 'viewObject'){
			// ��Form��ΪDisable
			disableForm('manageIncomeTaxBase_form');
			// ����Page Title
			$('#pageTitle').html('������ѯ');
			// ������ťValue
			$('#btnEdit').val('�༭');
		}

		// ȡ����ť����¼�
		$('#btnList').click( function () {
			if (agreest())
			link('incomeTaxBaseAction.do?proc=list_object');
		});	
	})(jQuery);
</script>