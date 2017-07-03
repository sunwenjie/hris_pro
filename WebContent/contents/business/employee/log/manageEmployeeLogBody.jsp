<%@page import="com.kan.hro.domain.biz.employee.EmployeeLogVO"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

	<%
		 final EmployeeLogVO employeeLogVO = ( EmployeeLogVO )request.getAttribute( "employeeLogForm" );
		 final String targateId = employeeLogVO.encodedField( employeeLogVO.getEmployeeId() );
		 final String employee = BaseAction.getRole( request, null ).equals( "1" ) ? "��Ա" : "Ա��";
		 
		 final StringBuffer rs = new StringBuffer();
		 rs.append( "<ol class=\"auto\">" );
		 rs.append( "<li id=\"employeeIdLI\" style=\"width: 50%;\">" );
		 rs.append( "<label>" + employee + "ID<em> *</em></label>" );
		 rs.append( "<a href=\"javascript:void(0)\" onclick=\"link(\'employeeAction.do?proc=to_objectModify&id=" + targateId + " />\');\"  >" );
		 rs.append( employeeLogVO.getEmployeeId() );
		 rs.append( "</a></li>");
		 rs.append( "<li id=\"employeeNameLI\" style=\"width: 50%;\">" );
		 rs.append( "<label>" + employee + "����<em> *</em></label>" );
		 rs.append( "<a href=\"javascript:void(0)\" onclick=\"link(\'employeeAction.do?proc=to_objectModify&id=" + targateId + " />\');\"  >" );
		 rs.append( employeeLogVO.getEmployeeName() );
		 rs.append( "</a></li></ol>");

	%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
		<div class="head">
            <label id="pageTitle"><%=employee %> - ��־����</label>
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
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="�༭" />
				<input type="button" class="reset" name="btnList" id="btnList" value="����" />
			</div>
			<html:form action="employeeLogAction.do?proc=add_object" method="post" styleClass="manageEmployeeLog_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="id" id="employeeLogId" value='<bean:write name="employeeLogForm" property="encodedId" />' />
				<input type="hidden" name="subAction" class="subAction" id="subAction" value='<bean:write name="employeeLogForm" property="subAction" />' />
				<input type="hidden" name="employeeId" id="employeeId" value='<bean:write name="employeeLogForm" property="employeeId" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  	
					</ol>
					<%=rs.toString()%>
					<ol class="auto">
						<li>
							<label>���� <em> *</em></label> 
							<html:textarea property="content" styleClass="manageEmployeeLog_content"></html:textarea>
						</li>	
					</ol>
					<ol class="auto">
						<li>
							<label>�޸���</label> 
							<html:text property="decodeModifyBy" maxlength="100" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" styleClass="decodeModifyDate" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
        </div>
	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// ���ò˵�ѡ����ʽ
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Employee').addClass('selected');
		
		// ����btnList�¼�
		$('#btnList').click(function() {
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	    });
		
		// btnEdit����¼�
		$('#btnEdit').click( function() {
			if( getSubAction() == 'viewObject'){
				// Enable form
        		enableForm('manageEmployeeLog_form');
				// ����Subaction
        		$('.manageEmployeeLog_form input#subAction').val('modifyObject');
				// ���İ�ť��ʾ��
        		$('#btnEdit').val('����');
        		// ����Page Title
        		$('#pageTitle').html('<%=employee%>' + ' - ��־���ٱ༭');
				// ����Form Action
        		$('.manageEmployeeLog_form').attr('action', 'employeeLogAction.do?proc=modify_object');
			}else{
        		var flag = 0;
        		
    			flag = flag + validate("manageEmployeeLog_content", true, "common", 500, 0);
    			
    			if( flag == 0 ){
    				submit('manageEmployeeLog_form');
    			}
        	}
		});
		
		if( getSubAction() != 'createObject' ) {
			disableForm('manageEmployeeLog_form');
			$('.manageEmployeeLog_form input#subAction').val('viewObject');
			$('#pageTitle').html('<%=employee%>' + ' - ��־���ٲ�ѯ');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<%=employee%>' + ' - ��־��������');
		    $('#btnEdit').val('����');
		    $('.decodeModifyBy').val('');
		    $('.decodeModifyDate').val('');
		    $('.decodeModifyBy').attr('disabled','disabled');
		    $('.decodeModifyDate').attr('disabled','disabled');
		}
	})(jQuery);
	
	function getSubAction(){
		return $('.manageEmployeeLog_form input#subAction').val();
	};
</script>

