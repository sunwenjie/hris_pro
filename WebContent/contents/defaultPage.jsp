<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<div class="loginForm">
		<table>
			<tr>
				<td >
					<label class="title"><bean:message key="logon.title"/></label>
					<label>ѡ��ϵͳ���</label>
					
					<p>
						<input style="float: left;" onclick="logonURL('logon.do')" type="button" name="btnLogin" id="btnLogin" value="������ҵ���Ա�����" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logoni.do')" type="button" name="btnLogin" value="������ҵ�ڲ�Ա�����" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logonv.do')" type="button" name="btnLogin" id="btnLogin" value="��Ӧ��Ա�����" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logonc.do')" type="button" name="btnLogin"  value="�ͻ�Ա�����" /> 
					</p>
					<br/>
					<p>
						<input style="float: left;" onclick="logonURL('logone.do')" type="button" name="btnLogin" value="��ҵ���Ա���������" /> 
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