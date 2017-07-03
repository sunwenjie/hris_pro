<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.settlement.AdjustmentHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<!-- SBAdjustmentHeader - information -->
<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
	    <div class="head">
	        <label id="pageTitle">�˵�����</label>
	         <label class="recordId"> &nbsp; (ID: <bean:write name="adjustmentHeaderForm" property="adjustmentHeaderId" />)</label>
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
				<logic:empty name="adjustmentHeaderForm" property="encodedId">
					<kan:auth right="new" action="<%=AdjustmentHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnEdit" id="btnEdit" value="����" />
					</kan:auth>
				</logic:empty>
				<logic:notEmpty name="adjustmentHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=AdjustmentHeaderAction.accessAction%>">
						<input type="button" class="save" name="btnEdit" id="btnEdit" value="����" />
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="submit" action="<%=AdjustmentHeaderAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" style="display: none;" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=AdjustmentHeaderAction.accessAction%>">
					 <input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />"/>
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/settlement/adjustment/header/form/manageAdjustmentHeaderForm.jsp" flush="true"></jsp:include>		
	    </div>
	</div>
</div>

<!-- AdjustmentDetail - information -->
<div class="box" id="AdjustmentDetail-information" style="display: none;">		
	<!-- Inner -->	
	<div class="head">
		<label>������ϸ</label>
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
			<kan:auth right="modify" action="<%=AdjustmentHeaderAction.accessAction%>">			
				<input type="button" class="editbutton" name="btnEditAdjustmentDetail" id="btnEditAdjustmentDetail" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelAdjustmentDetail" id="btnCancelAdjustmentDetail" value="ȡ��" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listAdjustmentDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</kan:auth>
		</div>	
		<div id="detailFormWrapper"  style="display: none;">
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/settlement/adjustment/detail/form/manageAdjustmentDetailForm.jsp" flush="true"></jsp:include>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="adjustmentDetailHolder">	
			<html:form action="adjustmentDetailAction.do?proc=list_object" styleClass="listAdjustmentDetail_form">
					<input type="hidden" name="id" value="<bean:write name="adjustmentHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="adjustmentDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="adjustmentDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="adjustmentDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="adjustmentDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/settlement/adjustment/detail/table/listAdjustmentDetailTable.jsp" flush="true"></jsp:include>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom">
			<p>
		</div>	
	</div>		
	<!-- inner -->		
</div>		
<!-- search-results -->	

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>			

<script type="text/javascript">
	(function($) {
		// ��ʼ���˵���ʽ
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_BillAdjustment').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		
		// ��ʼ�����ſؼ�
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// �󶨲���Change�¼�
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// detail form ��Ŀ�����
		kanThinking_column('manageAdjustmentDetail_itemName', 'manageAdjustmentDetail_itemId', 'itemAction.do?proc=list_object_json&type=1', false);
		
		// ������ģʽ����ӿ��ٲ���
		if( getSubAction() != 'createObject' ){
			$('#employeeIdLI label').append('&nbsp;<a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="�鿴<logic:equal name="role" value="1">��Ա</logic:equal><logic:equal name="role" value="2">Ա��</logic:equal>��¼" /></a>');
			$('#contractIdLI label').append('&nbsp;<a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderForm" property="encodedContractId" />\');" ><img src="images/find.png" title="�鿴<logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>��¼" /></a>');
			$('#clientIdLI label').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="adjustmentHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="�鿴�ͻ���¼" /></a>');
		}
		
		// ��ӡ���������Э����Ϣ��
		$('#contractId').after('<a onclick="popupContractSearch();" class="kanhandle"><img src="images/search.png" title="����<logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>" /></a>');
		
		if ( getSubAction() != 'createObject' ) {
		    disableForm('manageAdjustmentHeader_form');
		    $('.manageAdjustmentHeader_form input.subAction').val('viewObject');
		    $('#pageTitle').html('�˵�������ѯ');
		    $('#btnEdit').val('�༭');
		    $('#AdjustmentDetail-information').show();
		} else if ( getSubAction() == 'createObject' ) {
		    $('#pageTitle').html('�˵���������');
		    $('#contractId').addClass('important');
		    disabled_other();
		}
		
		if( getStatus() == '1' || getStatus() == '4' ){
			$('#btnSubmit').show();
		}else if( getStatus() == '2' || getStatus() == '3' || getStatus() == '5'){
			$('#btnEdit').hide();
		}
	    
	    // �ύ��ť�¼�
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// ���ĵ�ǰForm��SubAction
				if($('.manageAdjustmentHeader_form input#subAction').val() != 'createObject'){
					$('.manageAdjustmentHeader_form').attr('action', 'adjustmentHeaderAction.do?proc=modify_object');
				}
				$('.manageAdjustmentHeader_form input#subAction').val('submitObject');
		    	enableForm('manageAdjustmentHeader_form');
		    	submitForm('manageAdjustmentHeader_form');
			}
		});
		// ��������Ϣchange�¼�
		$('#contractId').bind('keyup',function(){
			if($(this).val() != ''){
				$.ajax({
					 url : 'employeeContractAction.do?proc=get_object_ajax&contractId=' + $(this).val() + '&date=' + new Date(),
					 dataType : "json",
					 success : function(data){
						 cleanError('manageAdjustmentHeader_contractId');
						 if(data.success=='true'){
							 $('.manageAdjustmentHeader_entityId').val(data.entityId);
							 $('.manageAdjustmentHeader_businessTypeId').val(data.businessTypeId);
							 $('.manageAdjustmentHeader_employeeId').val(data.employeeId);
							 $('.manageAdjustmentHeader_employeeNameZH').val(data.employeeNameZH);
							 $('.manageAdjustmentHeader_employeeNameEN').val(data.employeeNameEN);
							 $('.manageAdjustmentHeader_clientId').val(data.clientId);
							 $('.manageAdjustmentHeader_orderId').val(data.orderId);
							 $('.manageAdjustmentHeader_clientNameZH').val(data.clientNameZH);
							 $('.manageAdjustmentHeader_clientNameEN').val(data.clientNameEN);
						 }else{
							 $('.manageAdjustmentHeader_entityId').val('0');
							 $('.manageAdjustmentHeader_businessTypeId').val('0');
							 $('.manageAdjustmentHeader_employeeId').val('');
							 $('.manageAdjustmentHeader_employeeNameZH').val('');
							 $('.manageAdjustmentHeader_employeeNameEN').val('');
							 $('.manageAdjustmentHeader_clientId').val('');
							 $('.manageAdjustmentHeader_orderId').val('');
							 $('.manageAdjustmentHeader_clientNameZH').val('');
							 $('.manageAdjustmentHeader_clientNameEN').val('');
							 
							 addError('manageAdjustmentHeader_contractId', '<logic:equal name="role" value="1">������Ϣ</logic:equal><logic:equal name="role" value="2">�Ͷ���ͬ</logic:equal>ID��Ч��');
						 }
					 }
				});
			}
		});
		
		// Adjustment Header ���桢�༭Click
		$('#btnEdit').click(function() {
		    if ($('.manageAdjustmentHeader_form input#subAction').val() == 'viewObject') {
		        enableForm('manageAdjustmentHeader_form');
		        $('.manageAdjustmentHeader_form input#subAction').val('modifyObject');
		        $('#btnEdit').val('����');
		        $('.manageAdjustmentHeader_form').attr('action', 'adjustmentHeaderAction.do?proc=modify_object');
		        $('#pageTitle').html('�˵������༭');
		        $('#contractId').addClass('important');
		        disabled_other();
		    } else {
		        var flag = 0;
		        flag = validate_manage_primary_form();
		        if (flag == 0) {
		            submit('manageAdjustmentHeader_form');
		        }
		    }
		});
		
		// Adjustment Header �б�Click
		$('#btnList').click(function(){
			if (agreest())
			link('adjustmentHeaderAction.do?proc=list_object');
		});
		
		// Adjustment Detail ���桢�༭Click�¼�
		$('#btnEditAdjustmentDetail').click(function(){
			if( $('.manageAdjustmentHeader_form #headerId').val() != ''){
				var detailSubAction = $('.manageAdjustmentDetail_form input#subAction').val();
				// �ж�����ӡ��鿴�����޸� 
				if( detailSubAction == '' ){
					// ״̬Ĭ������
					$('.manageAdjustmentDetail_form .manageAdjustmentDetail_status').val('1');
					$('.manageAdjustmentDetail_form .manageAdjustmentDetail_billAmountPersonal').val('0');
					$('.manageAdjustmentDetail_form .manageAdjustmentDetail_billAmountCompany').val('0');
					$('.manageAdjustmentDetail_form .manageAdjustmentDetail_costAmountPersonal').val('0');
					$('.manageAdjustmentDetail_form .manageAdjustmentDetail_costAmountCompany').val('0');
					// ��ʾCancel��ť
					$('#btnCancelAdjustmentDetail').show();
					// ��ʾList Detail Form
					$('#detailFormWrapper').show();	
					// ����SubActionΪ�½�
					$('.manageAdjustmentDetail_form input#subAction').val('createObject');
					// �޸İ�ť��ʾ����
					$('#btnEditAdjustmentDetail').val('����');
				}else if( detailSubAction == 'viewObject'){
					// �༭������Enable����Form
					enableForm('manageAdjustmentDetail_form');
					// ����SubActionΪ�༭
					$('.manageAdjustmentDetail_form input#subAction').val('modifyObject');
					// �޸İ�ť��ʾ����
					$('#btnEditAdjustmentDetail').val('����');
					// ����Form Action
	        		$('.manageAdjustmentDetail_form').attr('action', 'adjustmentDetailAction.do?proc=modify_object');
				}else {
					// �ڴ�����ӻ��޸ĵ���֤
					var flag = 0;
					flag = flag + validate("manageAdjustmentDetail_itemName", true, "common", 100, 0);
					flag = flag + validate("manageAdjustmentDetail_billAmountPersonal", true, "currency", 8, 0, 10000, 0);
					flag = flag + validate("manageAdjustmentDetail_billAmountCompany", true, "currency", 8, 0, 10000, 0);
					flag = flag + validate("manageAdjustmentDetail_costAmountPersonal", true, "currency", 8, 0, 10000, 0);
					flag = flag + validate("manageAdjustmentDetail_costAmountCompany", true, "currency", 8, 0, 10000, 0);
					flag = flag + validate("manageAdjustmentDetail_description", false, "common", 500, 0);
					flag = flag + validate("manageAdjustmentDetail_status", true, "select", 0, 0);
					if(flag == 0){
						$('.manageAdjustmentDetail_form').submit();
					}
				}
			}else {
				var flag = validate_manage_primary_form();
				if( flag == 0 ){
					submit('manageAdjustmentHeader_form');
				}
			}
		});
		
		// Adjustment Detail ȡ����ť
		$('#btnCancelAdjustmentDetail').click(function(){
			if(agreest())
			link('adjustmentDetailAction.do?proc=list_object&id=<bean:write name="adjustmentHeaderForm" property="encodedId" />');
		});
		
		
		if( getSubAction() == 'viewObject' ){
			$('#contractId').trigger('keyup');
		}
	})(jQuery);
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function adjustmentDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelAdjustmentDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����SBAdjustment Detail�޸�ҳ��
		var callback = "kanThinking_column('manageAdjustmentDetail_itemName', 'manageAdjustmentDetail_itemId', 'itemAction.do?proc=list_object_json&type=1', disable);";
		loadHtmlWithRecall('#detailFormWrapper', 'adjustmentDetailAction.do?proc=to_objectModify_ajax&id=' + detailId, true, callback);
		// �޸İ�ť��ʾ����
		$('#btnEditAdjustmentDetail').val('�༭');		
	};
	
	// ��ȡ״̬
	function getStatus(){
		return $('.manageAdjustmentHeader_form select.manageAdjustmentHeader_status').val();
	};
	
	// ��ȡsubAction
	function getSubAction(){
		return $('.manageAdjustmentHeader_form input#subAction').val();
	};
	
	// disabled other
	function disabled_other(){
		$('.manageAdjustmentHeader_status').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_entityId').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_businessTypeId').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_employeeId').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_employeeNameZH').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_employeeNameEN').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_clientId').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_orderId').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_clientNameZH').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_clientNameEN').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_billAmountPersonal').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_billAmountCompany').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_costAmountPersonal').attr('disabled', 'disabled');
		$('.manageAdjustmentHeader_costAmountCompany').attr('disabled', 'disabled');
	};
</script>
