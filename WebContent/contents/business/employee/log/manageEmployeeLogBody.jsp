<%@page import="com.kan.hro.domain.biz.employee.EmployeeLogVO"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

	<%
		 final EmployeeLogVO employeeLogVO = ( EmployeeLogVO )request.getAttribute( "employeeLogForm" );
		 final String targateId = employeeLogVO.encodedField( employeeLogVO.getEmployeeId() );
		 final String employee = BaseAction.getRole( request, null ).equals( "1" ) ? "雇员" : "员工";
		 
		 final StringBuffer rs = new StringBuffer();
		 rs.append( "<ol class=\"auto\">" );
		 rs.append( "<li id=\"employeeIdLI\" style=\"width: 50%;\">" );
		 rs.append( "<label>" + employee + "ID<em> *</em></label>" );
		 rs.append( "<a href=\"javascript:void(0)\" onclick=\"link(\'employeeAction.do?proc=to_objectModify&id=" + targateId + " />\');\"  >" );
		 rs.append( employeeLogVO.getEmployeeId() );
		 rs.append( "</a></li>");
		 rs.append( "<li id=\"employeeNameLI\" style=\"width: 50%;\">" );
		 rs.append( "<label>" + employee + "姓名<em> *</em></label>" );
		 rs.append( "<a href=\"javascript:void(0)\" onclick=\"link(\'employeeAction.do?proc=to_objectModify&id=" + targateId + " />\');\"  >" );
		 rs.append( employeeLogVO.getEmployeeName() );
		 rs.append( "</a></li></ol>");

	%>

<div id="content">
	<!-- Information Manage Form -->
	<div class="box">
		<div class="head">
            <label id="pageTitle"><%=employee %> - 日志跟踪</label>
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
				<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="编辑" />
				<input type="button" class="reset" name="btnList" id="btnList" value="返回" />
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
							<label>内容 <em> *</em></label> 
							<html:textarea property="content" styleClass="manageEmployeeLog_content"></html:textarea>
						</li>	
					</ol>
					<ol class="auto">
						<li>
							<label>修改人</label> 
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
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Employee').addClass('selected');
		
		// 更改btnList事件
		$('#btnList').click(function() {
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	    });
		
		// btnEdit点击事件
		$('#btnEdit').click( function() {
			if( getSubAction() == 'viewObject'){
				// Enable form
        		enableForm('manageEmployeeLog_form');
				// 更改Subaction
        		$('.manageEmployeeLog_form input#subAction').val('modifyObject');
				// 更改按钮显示名
        		$('#btnEdit').val('保存');
        		// 更换Page Title
        		$('#pageTitle').html('<%=employee%>' + ' - 日志跟踪编辑');
				// 更改Form Action
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
			$('#pageTitle').html('<%=employee%>' + ' - 日志跟踪查询');
		} else if ( getSubAction() == 'createObject' ) {
			$('#pageTitle').html('<%=employee%>' + ' - 日志跟踪新增');
		    $('#btnEdit').val('保存');
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

