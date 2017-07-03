<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
				<tr>
					<td align="left">
						<table border="0" cellpadding="0" cellspacing="0" class="border1"> 
							<tr height="21" class="bg1">
								<td width="30" align="center" valign="middle"><img src="images/item.gif"></td>
								<td valign="middle">
									<SPAN class="font3">HRS Error&nbsp;</SPAN>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
    <tr height="15"><td></td></tr>
    <tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#658D7A" class="font4"> 
				<tr height="20" class="bg1">
					<td align="center" valign="middle" colspan="2"><span class="font3"><b>Error</b></span></td>
				</tr>
				<tr height="150" bgcolor="#ffffff">
					<td>
						<TABLE border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="180" align="right"><img src="images/Caution.gif"></td>
								<td width="640" align="center" valign="middle" class="font4">Error! And it may be caused by: <span class="font5"><html:errors property="error"/><%=request.getParameter("error") != null ? request.getParameter("error") : ""%></span><p><a href="#" class="link2" onclick="history.back()">Back</a></td>
							</tr>
						</TABLE>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
    	<td>&nbsp;</td>
	</tr>
</table>