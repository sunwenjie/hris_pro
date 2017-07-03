<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.management.CalendarDetailAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box" id="calendarHeader - information">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.new" />&nbsp;<bean:message bundle="management" key="management.calendar" /></label>
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
				<logic:empty name="calendarHeaderForm" property="encodedId">
					<input type="button" class="editbutton" name=btnEditCalendarHeader id="btnEditCalendarHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
				</logic:empty>
				<logic:notEmpty name="calendarHeaderForm" property="encodedId">
					<kan:auth right="modify" action="<%=CalendarDetailAction.accessAction%>">
						<input type="button" class="editbutton" name=btnEditCalendarHeader id="btnEditCalendarHeader" value="<bean:message bundle="public" key="button.edit" />" /> 
					</kan:auth>
				</logic:notEmpty>
				<kan:auth right="list" action="<%=CalendarDetailAction.accessAction%>">
					<input type="button" class="reset" name="btnListCalendarHeader" id="btnListCalendarHeader" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� -->  
			<jsp:include page="/contents/management/calendar/header/form/manageCalendarHeaderForm.jsp" flush="true"/>
		</div>	
	</div>
	
	<!-- CalendarDetail - information -->
	<div class="box" id="CalendarDetail-Information" style="display: none;">		
		<!-- Inner -->
		<div class="head">
			<label><bean:message bundle="management" key="management.calendar.detail.search.title" /></label>
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
				<input type="button" class="editbutton" name="btnEditCalendarDetail" id="btnEditCalendarDetail" value="<bean:message bundle="public" key="button.add" />" /> 
				<input type="button" class="reset" name="btnCancelCalendarDetail" id="btnCancelCalendarDetail" value="<bean:message bundle="public" key="button.cancel" />" style="display:none" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listCalendarDetail_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>	
			<div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP ����Form��Ӧ��jsp�ļ� --> 
				<jsp:include page="/contents/management/calendar/detail/form/manageCalendarDetailForm.jsp" flush="true"/>
			</div>																
			<!-- if exist bean calendarDetailHolder -->
			<logic:notEmpty name="calendarDetailHolder">
				<html:form action="calendarDetailAction.do?proc=list_object" styleClass="listCalendarDetail_form">
					<input type="hidden" name="headerId" value="<bean:write name="calendarHeaderForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="calendarDetailHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="calendarDetailHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="calendarDetailHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="calendarDetailHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />					
				</html:form>
				<div id="tableWrapper">
					<!-- Include table jsp ����tabel��Ӧ��jsp�ļ� -->  
					<jsp:include page="/contents/management/calendar/detail/table/listCalendarDetailTable.jsp" flush="true"/>
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

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>	

<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		if('<bean:write name="accountId"/>' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_Calendar').addClass('selected');
		}else{
			$('#menu_attendance_Modules').addClass('current');
			$('#menu_attendance_Configuration').addClass('selected');
			$('#menu_attendance_Calendar').addClass('selected');
		}	
		
		// ��ʼ����ѡ��
		kanList_init();
		kanCheckbox_init();
		
		// ��ӡ����ٲ鿴������
		$('#btnListCalendarHeader').after('<a class="kanhandle" onclick="quickCalendarPopup(\'<bean:write name="calendarHeaderForm" property="encodedId" />\');"><img src="images/search.png" title="���ٲ鿴����" /></a>');
		
		// �����ǰ��ϵͳ�������ҵ�ǰ�û���Super�����ذ�ť��  
		if('<bean:write name="calendarHeaderForm" property="accountId" />' == '1' && '<bean:write name="accountId" />' != '1'){
			$('#btnEditCalendarHeader').hide();
			$('#btnEditCalendarDetail').hide();
			$('#btnDelete').hide();
		}
		
		// �༭��ť����¼� - Calendar Header
		$('#btnEditCalendarHeader').click(function(){
			if( getSubAction() == 'viewObject' ){  
				// Enable����Form
        		enableForm('manageCalendarHeader_form');
        		// ���õ�ǰForm��SubActionΪ�޸�״̬
        		$('.manageCalendarHeader_form input#subAction').val('modifyObject'); 
        		// ����Form Action
        		$('.manageCalendarHeader_form').attr('action', 'calendarHeaderAction.do?proc=modify_object');
        		// �޸İ�ť��ʾ����
        		$('#btnEditCalendarHeader').val('<bean:message bundle="public" key="button.save" />');
        		// �޸�Page Title
        		$('#pageTitle').html('<bean:message bundle="management" key="management.calendar" /> <bean:message bundle="public" key="oper.edit" />');
			}else{
				if( validate_manage_primary_form() == 0){
					submit('manageCalendarHeader_form');
				}
			}
		});
		
		// �༭��ť����¼� - Calendar Detail
		$('#btnEditCalendarDetail').click(function(){	
			// �ж�����ӡ��鿴�����޸� 
			if($('.manageCalendarDetail_form input#subAction').val() == ''){
				$('.manageCalendarDetail_status').val('1');
				// ��ʾCancel��ť
				$('#btnCancelCalendarDetail').show();
				// ��ʾList Detail Form
				$('#detailFormWrapper').show();	
				// ����SubActionΪ�½�
				$('.manageCalendarDetail_form input#subAction').val('createObject');
				// �޸İ�ť��ʾ����
				$('#btnEditCalendarDetail').val('<bean:message bundle="public" key="button.save" />');
			}else if($('.manageCalendarDetail_form input#subAction').val() == 'viewObject'){	
				// �༭������Enable����Form
				enableForm('manageCalendarDetail_form');
				// ����SubActionΪ�༭
				$('.manageCalendarDetail_form input#subAction').val('modifyObject');
				// �޸İ�ť��ʾ����
				$('#btnEditCalendarDetail').val('<bean:message bundle="public" key="button.save" />');
				// ����Form Action
        		$('.manageCalendarDetail_form').attr('action', 'calendarDetailAction.do?proc=modify_object');
        
			}else{
				if( validate_manage_secondary_form() == 0 ){
					submit('manageCalendarDetail_form');
				}
			}
		});
		
		// Calendar Header List ��ť
		$('#btnListCalendarHeader').click(function(){
			if (agreest())
			link('calendarHeaderAction.do?proc=list_object');
		});
		
		// Calendar Detail Cancel ȡ��
		$('#btnCancelCalendarDetail').click( function () {
			if (agreest())
			link('calendarDetailAction.do?proc=list_object&headerId=<bean:write name="calendarHeaderForm" property="encodedId"/>');
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageCalendarHeader_form');
			$('#CalendarDetail-Information').show();
			$('.manageCalendarHeader_form input#subAction').val('viewObject');
			$('#pageTitle').html('<bean:message bundle="management" key="management.calendar" /> <bean:message bundle="public" key="oper.view" />');
		} else if ( getSubAction() == 'createObject' ) {
		    $('#btnEditCalendarHeader').val('<bean:message bundle="public" key="button.save" />');
		}
		
})(jQuery);
	
	
	// ��������change�¼�
	function dayType_change(){
		if($('.manageCalendarDetail_dayType').val() == '1'){
			$('#changeDay_LI').show();
		}else{
			$('#changeDay_LI').hide();
		}
		$('.manageCalendarDetail_dayType').change(function(){
			if($(this).val() == '1'){
				$('#changeDay_LI').show();
			}else{
				$('#changeDay_LI').hide();
			}
		});
	};
	
	// ��������ӣ�ajax����ȥ�޸�ҳ��
	function socialDetailModify( detailId ){
		// ��ʾCancel��ť
		$('#btnCancelCalendarDetail').show();
		// ��ʾList Detail Form
		$('#detailFormWrapper').show();	
		// Ajax����Calendar Detail�޸�ҳ��
		loadHtmlWithRecall('#detailFormWrapper', 'calendarDetailAction.do?proc=to_objectModify_ajax&detailId=' + detailId, true , "$('.manageCalendarDetail_dayType').trigger('change');");
		// �޸İ�ť��ʾ����
		$('#btnEditCalendarDetail').val('<bean:message bundle="public" key="button.edit" />');		
	
	};
	
	// Get SubAction Value
	function getSubAction(){
		return $('.manageCalendarHeader_form input#subAction').val();
	};
</script>