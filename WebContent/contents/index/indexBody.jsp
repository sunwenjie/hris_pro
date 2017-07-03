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
									<SPAN class="font3">User information&nbsp;&nbsp;</SPAN>
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
     	<td height="20" align="center" valign="middle" class="bg1">
     		<logic:notEmpty name="hrUserVO">
				<span class="font3">UserId:</span>
				<span class="font6"><bean:write name="hrUserVO" property="userId"/></span>&nbsp;
				<span class="font3">Username:</span>
				<span class="font6"><bean:write name="hrUserVO" property="username"/></span>&nbsp;
				<span class="font3">Create Date:</span>
				<span class="font6"><bean:write name="hrUserVO" property="createDate" format="yyyy-MM-dd"/></span>
			</logic:notEmpty> 
		</td>
	</tr>
	<tr height="15"><td></td></tr>
	<tr><td class="font1" align="center">Dear user, you can click the menu on the left to use Human Resource system.</td></tr>	
	<tr height="24"><td></td></tr>
</table>