<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.management.ShiftDetailAction"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="shiftHeader - information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.shift" /></label>
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
				<logic:empty name="shiftHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name=btnEditShiftHeader id="btnEditShiftHeader" value="<bean:message bundle="public" key="button.add" />" /> 
				</logic:empty>
				<logic:notEmpty name="shiftHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=ShiftDetailAction.accessAction%>">
						<input type="button" class="editbutton" name=btnEditShiftHeader id="btnEditShiftHeader" value="<bean:message bundle="public" key="button.add" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=ShiftDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnCancelShiftHeader" id="btnCancelShiftHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<html:form action="shiftHeaderAction.do?proc=add_object" styleClass="manageShift_form">
				<%= BaseAction.addToken( request ) %>	
				<input type="hidden" id="subAction" name="subAction" value="<bean:write name="shiftHeaderForm" property="subAction"/>" />
				<input type="hidden" id="headerId" name="id" value="<bean:write name="shiftHeaderForm" property="encodedId"/>" />
				<jsp:include page="/contents/management/shift/header/form/manageShiftHeaderForm.jsp" flush="true"/>
				<div id="special_info">
					<logic:notEqual name="shiftHeaderForm" property="shiftType" value="3">
						<logic:notEmpty name="shiftDetailHolder">
							<jsp:include page="/contents/management/shift/detail/extend/manageShiftDetailExtend.jsp" flush="true"/>
						</logic:notEmpty>
					</logic:notEqual>
				</div>	
			</html:form>
		</div>
	</div>
	
	<!-- Shift Exception - information -->
	<logic:notEmpty name="shiftDetailHolder">
	<div class="box" id="ShiftException-Information">	
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.shift.exception.search.title" /></label>
		</div>	
		<div class="inner">	
			<div id="messageWrapper">
				<logic:present name="MESSAGE_EXCEPTION">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="modify" action="<%=ShiftDetailAction.accessAction%>">
					<input type="button" class="editbutton" name="btnEditShiftException" id="btnEditShiftException" value="<bean:message bundle="public" key="button.add" />" /> 
					<input type="button" class="reset" name="btnCancelShiftException" id="btnCancelShiftException" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listShiftException_form', 'deleteObjects', null, null, null, 'tableWrapper_exception');" />
				</kan:auth>
			</div>	
			<div id="exceptionFormWrapper" style="display:none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
				<jsp:include page="/contents/management/shift/exception/form/manageShiftExceptionForm.jsp" flush="true"/>
			</div>		
			<!-- if exist bean shiftExceptionHolder -->
			<logic:notEmpty name="shiftExceptionHolder">
				<html:form action="shiftExceptionAction.do?proc=list_object" styleClass="listShiftException_form">
					<input type="hidden" name="headerId" value="<bean:write name="shiftHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="shiftExceptionHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="shiftExceptionHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="shiftExceptionHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="shiftExceptionHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper_exception">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/management/shift/exception/table/listShiftExceptionTable.jsp" flush="true"/>
				</div>
			</logic:notEmpty>
			<!-- tableWrapper -->	
			<div class="bottom"><p></div>											
		</div>
	</div>
	</logic:notEmpty>
</div>

<logic:equal name="shiftHeaderForm" property="shiftType" value="3">
<%-- Define Type Shift --%>
<div class="box" id="ShiftDetail-information" style="display: none;">	
	<!-- Inner -->	
	<div class="head">
		<label><bean:message bundle="management" key="management.shift.detail.search.title" /></label>
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
			<input type="button" class="editbutton" name="btnEditShiftDetail" id="btnEditShiftDetail" value="<bean:message bundle="public" key="button.add" />" /> 
			<input type="button" class="reset" name="btnCancelShiftDetail" id="btnCancelShiftDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
			<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listShiftDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
		</div>	
		<div id="detailFormWrapper" style="display:none">
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
			<jsp:include page="/contents/management/shift/detail/form/manageShiftDetailForm.jsp" flush="true"></jsp:include>
		</div>	
		<div id="helpText" class="helpText"></div>	
		<div id="scrollWrapper">
			<div id="scrollContainer"></div>
		</div>																	
		<logic:notEmpty name="shiftDetailHolder">	
			<html:form action="shiftDetailAction?proc=list_object" styleClass="listShiftDetail_form">
				<input type="hidden" name="id" value="<bean:write name="shiftHeaderForm" property="encodedId"/>"/>
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="shiftDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="shiftDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="shiftDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="shiftDetailHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />					
			</html:form>	
		</logic:notEmpty>	
		<div id="tableWrapper">
			<logic:notEmpty name="shiftDetailHolder">	
				<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
				<jsp:include page="/contents/management/shift/detail/table/listShiftDetailTableDefine.jsp" flush="true"></jsp:include>
			</logic:notEmpty>
		</div>	
		<!-- tableWrapper -->	
		<div class="bottom"><p></div>	
	</div>		
	<!-- inner -->		
</div>		
</logic:equal>

<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		if('<bean:write name="accountId"/>' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_WorkShift').addClass('selected');
		}else{
			$('#menu_attendance_Modules').addClass('current');
			$('#menu_attendance_Configuration').addClass('selected');
			$('#menu_attendance_Shift').addClass('selected');	
		}
		kanList_init();
		kanCheckbox_init();
		
		// �����ǰ��ϵͳ�Űಢ�ҵ�ǰ�û���Super�����ذ�ť�� 
		if('<bean:write name="shiftHeaderForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEditShiftHeader').hide();
			$('#btnEditShiftDetail').hide();
			$('#btnEditShiftException').hide();
			$('#btnDelete').hide();
		}
		
		var disable = true;
		if( getSubAction() == 'createObject'){
			disable = false;
			$('#btnEditShiftHeader').val('<bean:message bundle="public" key="button.save" />');
		}else{
			disableForm('manageShift_form');
			$('#btnEditShiftHeader').val('<bean:message bundle="public" key="button.edit" />');
			$('.manageShiftHeader_shiftType').trigger('change');
		}
		
		if( getSubAction() == 'viewObject' && '<bean:write name="shiftHeaderForm" property="shiftType" />' == '3'){
			$('#ShiftDetail-information').show();
		}
		
		$('.manageShiftHeader_startDate').focus(function(){
			WdatePicker({
				onpicked :function(dp){
					if($('.manageShiftHeader_shiftIndex').val()!=''){
						load_special_info();
					}
				}
			});
		});
		
		$('.manageShiftHeader_shiftIndex').blur(function(){
			load_special_info();
		});
		
		$('.manageShiftHeader_shiftIndex').keyup(function(){
			load_special_info();
		});
		
		// Define ShiftDetail Add|Edit Click
		$('#btnEditShiftDetail').click(function(){
			if( $('.manageShiftDetail_form input#subAction').val() == '' ){ 
				// ��ʾCancel��ť
				$('#btnCancelShiftDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageShiftDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditShiftDetail').val('<bean:message bundle="public" key="button.save" />'); 
			}else if( $('.manageShiftDetail_form input#subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageShiftDetail_form');
				// ����SubActionΪ�༭
				$('.manageShiftDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditShiftDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.manageShiftDetail_form').attr('action', 'shiftDetailAction.do?proc=modify_object');
			}else{
				// �ڴ�����ӻ��޸ĵ���֤
				var flag = validate("manageShiftDetail_shiftDay", true, "common", 10, 0);
				if(flag == 0){
					$('.manageShiftDetail_form').submit();
				}
			}
		});
		
		// Define ShiftDetail Cancel Click
		$('#btnCancelShiftDetail').click(function(){
			if (agreest())
			link('shiftDetailAction.do?proc=list_object&id=<bean:write name="shiftHeaderForm" property="encodedId" />');
		});
		
		// Shift Exception Cancel Button Click Event
		$('#btnCancelShiftException').click(function(){
			if (agreest())
			link('shiftDetailAction.do?proc=list_object&id=<bean:write name="shiftHeaderForm" property="encodedId" />');
		});
		
		// Shift Exception Add|Edit Button Click Event
		$('#btnEditShiftException').click( function(){
			if( $('.manageShiftException_form input#subAction').val() == '' ){ 
				// ��ʾCancel��ť
				$('#btnCancelShiftException').show();
				// ��ʾList Detail Form
				$('#exceptionFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageShiftException_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditShiftException').val('<bean:message bundle="public" key="button.save" />'); 
			}else if( $('.manageShiftException_form input#subAction').val() == 'viewObject'){
				// �༭������Enable����Form
				enableForm('manageShiftException_form');
				// ����SubActionΪ�༭
				$('.manageShiftException_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditShiftException').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
	    		$('.manageShiftException_form').attr('action', 'shiftExceptionAction.do?proc=modify_object');
			}else{
				// �ڴ�����ӻ��޸ĵ���֤
				var flag = 0;
				cleanError('manageShiftException_dayType');
			    cleanError('manageShiftException_exceptionDay');
			    flag = flag + validate("manageShiftException_nameZH", true, "common", 100, 0);
			    flag = flag + validate("manageShiftException_exceptionType", true, "select", 0, 0);
			    if( $('.manageShiftException_dayType').val() == 0 && $('.manageShiftException_exceptionDay').val() == ''){
				    var errorMsg = '���߱�����дһ�';
				    addError('manageShiftException_dayType', errorMsg);
				    addError('manageShiftException_exceptionDay', errorMsg);
				    flag = flag + 1;
			    }
				if(flag == 0){
					submit('manageShiftException_form');
				}
			}
		});
	})(jQuery);
	
	// ���SubAction
	function getSubAction(){
		return $('.manageShift_form input#subAction').val();
	};
	
	function load_special_info( ){
		var shiftType = $('.manageShiftHeader_shiftType').val();
		var shiftIndex = $('.manageShiftHeader_shiftIndex').val();
		if( shiftType == '1' ){
			loadHtml('#special_info', 'shiftHeaderAction.do?proc=list_object_html&shiftType=' + shiftType + '&shiftIndex=' + shiftIndex, false, 'initShiftPeriod()'); 
		}else if( shiftType == '2' && validate_part() == 0 ){
			var startDate = $('.manageShiftHeader_startDate').val();
			loadHtml('#special_info', 'shiftHeaderAction.do?proc=list_object_html&shiftType=' + shiftType + '&shiftIndex=' + shiftIndex + '&startDate=' + startDate, false, 'initShiftPeriod()'); 
		}
	};
	
	function initShiftPeriod(){
		$('#resultTable tbody tr td input[type="checkbox"]').each(function(){
			if( parseInt($(this).val()) < 19 || parseInt($(this).val()) > 36 ){
				$(this).hide();
				$(this).next().hide();
			}
		});
	};
	
	// Define ShiftDetail Link To Object Modidy Ajax
	function to_objectModify_ajax( id ){
		// ��ʾCancel��ť
		$('#btnCancelShiftDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditShiftDetail').val('<bean:message bundle="public" key="button.edit" />');
		loadHtmlWithRecall('#detailFormWrapper', 'shiftDetailAction.do?proc=to_objectModify_ajax&detailId=' + id, true, null );
	};
	
	// ShiftException Link To Object Modidy Ajax
	function to_objectModify_ajax_exception( id ){
		// ��ʾCancel��ť
		$('#btnCancelShiftException').show();
		// ��ʾList Detail Form
		$('#exceptionFormWrapper').show();	
		// �޸İ�ť��ʾ����
		$('#btnEditShiftException').val('<bean:message bundle="public" key="button.edit" />');
		loadHtmlWithRecall('#exceptionFormWrapper', 'shiftExceptionAction.do?proc=to_objectModify_ajax&exceptionId=' + id, true, null );
	};
</script>