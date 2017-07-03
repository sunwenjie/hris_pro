<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div class="loginForm">
	<html:form action="securityAction.do?proc=choosePosition" styleClass="login_form">
		<table>
			<tr>
				<td >
					<label class="title"><bean:message key="logon.title"/></label>
					<label>½ÇÉ«</label>
					<logic:notEmpty name="positions" >
						<select name="positionId" id="positionId" class="login_accountName">
						<logic:iterate id="positions" name="positions"  indexId="number">
							<option value='<bean:write name="positions" property="mappingId"/>'><bean:write name="positions" property="mappingValue"/></option>
						</logic:iterate>
						</select>
					</logic:notEmpty>
					<p>
						<input style="float: left;" type="submit" name="btnLogin" id="btnLogin" value="<bean:message bundle="public" key="button.login" />" /> 
					</p>
				</td>
			</tr>
		</table>	
	</html:form>
</div>

<script type="text/javascript">
	function choolsePosition(positionId){
		link("securityAction.do?proc=choosePosition&positionId=" + positionId+"redirectUrl="+"<%=request.getAttribute("redirectUrl")%>");
	};

	function clickCheckBox(){
		$('#cbPersistentCookie').click();
	};
</script>