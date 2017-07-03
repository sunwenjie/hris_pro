<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<div class="box" id="incomeTaxRangeHeader - information">
		<div class="head">
			<label id="pageTitle">˰�ʲ�ѯ</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE_HEADER">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<input type="button" class="editbutton" name=btnEditIncomeTaxRangeHeader id="btnEditIncomeTaxRangeHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				<input type="button" class="reset" name="btnCancelIncomeTaxRangeHeader" id="btnCancelIncomeTaxRangeHeader" value="<bean:message bundle="public" key="button.cancel" />" /> 
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/system/incomeTaxRange/header/from/manageIncomeTaxRangeHeader.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- incomeTaxRangeDetail - information -->
	<div class="box" id="IncomeTaxRangeDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label>˰����ϸ</label>
		</div>		
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE_DETAIL">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">	
				<input type="button" class="editbutton" name="btnEditIncomeTaxRangeDetail" id="btnEditIncomeTaxRangeDetail" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelIncomeTaxRangeDetail" id="btnCancelIncomeTaxRangeDetail" value="ȡ��" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listIncomeTaxRangeDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<jsp:include page="/contents/system/incomeTaxRange/detail/from/manageIncomeTaxRangeDetailForm.jsp" flush="true"/>
			</div>	
			<!-- if exits bean incomeTaxRangeDetailHolder -->
			<logic:notEmpty name="incomeTaxRangeDetailHolder">														
				<html:form action="incomeTaxRangeDetailAction.do?proc=list_object" styleClass="listIncomeTaxRangeDetail_form">
					<input type="hidden" name="id" value="<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="incomeTaxRangeDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="incomeTaxRangeDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="incomeTaxRangeDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="incomeTaxRangeDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
			<div id="tableWrapper">
				<jsp:include page="/contents/system/incomeTaxRange/detail/table/listIncomeTaxRangeDetailTable.jsp" flush="true"/>
			</div>
			</logic:notEmpty>	
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->		
</div>
<script type="text/javascript">

	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_IncomeTax').addClass('selected');
		$('#menu_system_IncomeTaxRate').addClass('selected');
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
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
		
		 // �༭��ť����¼� - IncomeTaxRange Header
		$('#btnEditIncomeTaxRangeHeader').click(function(){
			if($('.manageIncomeTaxRangeHeader_form input#subAction').val() == 'viewObject'){  
				// Enable����Form
        		enableForm('manageIncomeTaxRangeHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageIncomeTaxRangeHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageIncomeTaxRangeHeader_form').attr('action', 'incomeTaxRangeHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditIncomeTaxRangeHeader').val('����');
        		// �޸�Page Title
        		$('#pageTitle').html('˰�ʱ༭');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageIncomeTaxRangeHeader_form');
				}
			}
		});

		// �༭��ť����¼� - IncomeTaxRange Detail
		$('#btnEditIncomeTaxRangeDetail').click(function(){	
			// �ж�����ӡ��鿴�����޸� 
			if($('.manageIncomeTaxRangeDetail_form input#subAction').val() == ''){
				// ��ʾCancel��ť
				$('#btnCancelIncomeTaxRangeDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageIncomeTaxRangeDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditIncomeTaxRangeDetail').val('����');
				$('.manageIncomeTaxRangeDetail_status').val('1');
			}else if($('.manageIncomeTaxRangeDetail_form input#subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageIncomeTaxRangeDetail_form');
				// ����SubActionΪ�༭
				$('.manageIncomeTaxRangeDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditIncomeTaxRangeDetail').val('����');
				// ����Form Action
        		$('.manageIncomeTaxRangeDetail_form').attr('action', 'incomeTaxRangeDetailAction.do?proc=modify_object');
			}else{
				// ͨ��JS��֤���ύFORM
				if( validate_manage_secondary_form() == 0){
					submit('manageIncomeTaxRangeDetail_form');
				}
			}
		});
		
		// Header ȡ��
		$('#btnCancelIncomeTaxRangeHeader').click( function () {
			if(agreest())
			link('incomeTaxRangeHeaderAction.do?proc=list_object');
		});	
		
		// Detail ȡ��
		$('#btnCancelIncomeTaxRangeDetail').click( function () {
			if(agreest())
			link('incomeTaxRangeDetailAction.do?proc=list_object&id=<bean:write name="incomeTaxRangeHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageIncomeTaxRangeHeader_form');
			$('#IncomeTaxRangeDetail-Information').show();
			$('.manageIncomeTaxRangeHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('˰�ʲ�ѯ');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('˰������');
		    $('#btnEditIncomeTaxRangeHeader').val('����');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function detailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelIncomeTaxRangeDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����Option Detail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'incomeTaxRangeDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true ,null);
		// �޸İ�ť��ʾ����
		$('#btnEditIncomeTaxRangeDetail').val('�༭');	
	};
	
	// Get Header form SubAction
	function getSubAction(){
		return $('form.manageIncomeTaxRangeHeader_form input#subAction').val();
	};
</script>