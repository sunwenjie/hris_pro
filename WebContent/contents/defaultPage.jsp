<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div class="loginForm">
		<table>
			<tr>
				<td >
					<label class="title"><bean:message key="logon.title"/></label>
					<label>选择系统入口</label>
					
					<p>
						<input style="float: left;" onclick="logonURL('logon.do')" type="button" name="btnLogin" id="btnLogin" value="管理企业外包员工入口" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logoni.do')" type="button" name="btnLogin" value="管理企业内部员工入口" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logonv.do')" type="button" name="btnLogin" id="btnLogin" value="供应商员工入口" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logonc.do')" type="button" name="btnLogin"  value="客户员工入口" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logone.do')" type="button" name="btnLogin" value="企业外包员工自助入口" /> 
					</p>
				</td>
			</tr>
		</table>	
</div>

<script type="text/javascript">

function logonURL(url){
	popupWin = window.open(url,'open_window','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars,resizable=no,dependent,width=' + (window.screen.width - 9) + ',height=' + (window.screen.height - 60) + ',left=0,top=0')
	window.opener=null;window.close();
}
	
</script>