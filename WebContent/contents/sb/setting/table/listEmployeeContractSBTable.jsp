<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder employeeContractSBHolder = (PagedListHolder) request.getAttribute("employeeContractSBHolder");
%>

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
	<!-- top -->
	<div class="top">
	 	<logic:equal name="isExportExcel" value="1">
			<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=HRO_BIZ_EMPLOYEE_CONTRACT_SB');"><img src="images/appicons/excel_16.png" /></a> 
		</logic:equal>
		<!-- <input type="button" class="function" id="btnAdd" name="btnAdd" value="社保公积金设置" onclick="addEmployeeContractSBs();" /> -->
		<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
	</div>
	<!-- top -->
	<div id="tableWrapper">
	<!-- Include table jsp 包含tabel对应的jsp文件 -->
	<table class="table hover" id="resultTable">
		<thead>
			<tr>
				<th class="checkbox-col"><input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" disabled="disabled" />
				</th>
				<th style="width: 4%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeSBId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeSBId', '<%=employeeContractSBHolder.getNextSortOrder("employeeSBId")%>', 'search-results');">
						<bean:message bundle="sb" key="sb.number.id" />
					</a>
				</th>
				<th style="width: 8%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=employeeContractSBHolder.getNextSortOrder("employeeId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeNameZH")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=employeeContractSBHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("employeeNameEN")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=employeeContractSBHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</a>
				</th>
				<th style="width: 10%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("sbSolutionId")%>">
					<a onclick="submitForm('list_form', null, null, 'sbSolutionId', '<%=employeeContractSBHolder.getNextSortOrder("sbSolutionId")%>', 'search-results');">
						<bean:message bundle="sb" key="sb.solution" />
					</a>
				</th>
				<th style="width: 9%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("startDate")%>">
					<a onclick="submitForm('list_form', null, null, 'startDate', '<%=employeeContractSBHolder.getNextSortOrder("startDate")%>', 'search-results');"><bean:message bundle="sb" key="sb.start.date" /></a>
				</th>
				<th style="width: 9%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("endDate")%>">
					<a onclick="submitForm('list_form', null, null, 'endDate', '<%=employeeContractSBHolder.getNextSortOrder("endDate")%>', 'search-results');"><bean:message bundle="sb" key="sb.end.date" /></a>
				</th>
				<th style="width: 5%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("status")%>">
					<a onclick="submitForm('list_form', null, null, 'status', '<%=employeeContractSBHolder.getNextSortOrder("status")%>', 'search-results');"><bean:message bundle="sb" key="sb.status" /></a>
				</th>
				<th style="width: 10%" class="header-nosort">
					<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.name" /></logic:equal>
					<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.name" /></logic:equal>
				</th>
				<th style="width: 5%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("contractStatus")%>">
					<a onclick="submitForm('list_form', null, null, 'contractStatus', '<%=employeeContractSBHolder.getNextSortOrder("contractStatus")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /></logic:equal>
					</a>
				</th>
				<th style="width: 10%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("certificateNumber")%>">
					<a onclick="submitForm('list_form', null, null, 'certificateNumber', '<%=employeeContractSBHolder.getNextSortOrder("certificateNumber")%>', 'search-results');"><bean:message bundle="public" key="public.certificate.number" /></a>
				</th>
				<logic:equal name="role" value="1">
					<th style="width: 7%" class="header  <%=employeeContractSBHolder.getCurrentSortClass("corpId")%>">
						<a onclick="submitForm('list_form', null, null, 'corpId', '<%=employeeContractSBHolder.getNextSortOrder("corpId")%>', 'search-results');">客户ID</a>
					</th>
				</logic:equal>
				<th style="width: 10%" class="header-nosort"><bean:message bundle="public" key="public.description" /></th>
				<th style="width: 5%" class="header-nosort"><bean:message bundle="public" key="public.oper" /></th>
			</tr>
		</thead>
		<logic:notEqual name="employeeContractSBHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="employeeContractSBVO" name="employeeContractSBHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractSBVO" property="employeeSBId"/>" name="chkSelectRow[]" value="<bean:write name='employeeContractSBVO' property='contractId'/>" disabled="disabled" /></td>
						<td class="left"><a onclick="link('employeeContractSBAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedId"/>&comefrom=setting');"><bean:write name="employeeContractSBVO" property="employeeSBId" /></a></td>								
						<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedEmployeeId"/>');"><bean:write name="employeeContractSBVO" property="employeeId" /></a></td>								
						<td class="left"><bean:write name="employeeContractSBVO" property="employeeNameZH" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="employeeNameEN" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="startDate" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="endDate" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="decodeStatus" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="orderDescription" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="decodeContractStatus" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="certificateNumber" /></td>
						<td class="left"><bean:write name="employeeContractSBVO" property="subStrContractDescription" /></td>
						<!-- 
							<td class="left">
								<logic:notEmpty name="employeeContractSBVO" property="sbSolutionId">
									<bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /> <logic:notEmpty name="employeeContractSBVO" property="startDate"><bean:write name="employeeContractSBVO" property="startDate" /></logic:notEmpty><logic:notEmpty name="employeeContractSBVO" property="endDate"> ~ <bean:write name="employeeContractSBVO" property="endDate" /></logic:notEmpty><logic:notEqual name="employeeContractSBVO" property="status" value="0">（<bean:write name="employeeContractSBVO" property="decodeStatus" />）</logic:notEqual>
								</logic:notEmpty>
							</td>	 
						 -->
						<logic:equal name="role" value="1">
							<td class="left"><a title='<bean:write name="employeeContractSBVO" property="clientName" />' onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedClientId"/>');"><bean:write name="employeeContractSBVO" property="clientId" /></a></td>
						</logic:equal>
						<td class="left">
							<kan:auth right="new" action="HRO_SB_PURCHASE">
								<logic:notEqual name="employeeContractSBVO" property="contractStatus" value="7 ">
									<a style="text-decoration:none;" onclick="addEmployeeContractSB('<bean:write name="employeeContractSBVO" property="contractId"/>', '<bean:write name="employeeContractSBVO" property="encodedContractId"/>');" class="kanhandle">
										<img src="images/add.png" title="<bean:message bundle="sb" key="sb.solution.new" />" />
									</a>
								</logic:notEqual>
							</kan:auth>
							<logic:notEmpty name="employeeContractSBVO" property="employeeSBId">
								<logic:equal name="employeeContractSBVO" property="status" value="2">
									<kan:auth right="submit" action="HRO_SB_PURCHASE">
										<a style="text-decoration:none;" onclick="rollbackEmployeeContractSB('<bean:write name="employeeContractSBVO" property="employeeSBId"/>');" class="kanhandle">
											<img src="images/deduct.png" title="<bean:message bundle="sb" key="sb.solution.return" />" />
										</a> &nbsp;
									</kan:auth>	
								</logic:equal>
								<logic:equal name="employeeContractSBVO" property="status" value="3">
									<kan:auth right="submit" action="HRO_SB_PURCHASE">
										<a style="text-decoration:none;" onclick="rollbackEmployeeContractSB('<bean:write name="employeeContractSBVO" property="employeeSBId"/>');" class="kanhandle">
											<img src="images/deduct.png" title="<bean:message bundle="sb" key="sb.solution.return" />" />
										</a> &nbsp;
									</kan:auth>		
								</logic:equal>
								<kan:auth right="modify" action="HRO_SB_PURCHASE">
									<a style="text-decoration:none;" onclick="modifyEmployeeContractSB('<bean:write name="employeeContractSBVO" property="employeeSBId"/>');" class="kanhandle">
										<img src="images/modify.png" title="<bean:message bundle="sb" key="sb.solution.edit" />" />
									</a> &nbsp;
								</kan:auth>	
								<logic:equal name="employeeContractSBVO" property="status" value="0">
									<kan:auth right="delete" action="HRO_SB_PURCHASE">
										<a style="text-decoration:none;" onclick="deleteEmployeeContractSB('<bean:write name="employeeContractSBVO" property="encodedId"/>');" class="kanhandle">
											<img src="images/disable.png" title="<bean:message bundle="sb" key="sb.solution.delete" />" />
										</a>
									</kan:auth>	
								</logic:equal>
							</logic:notEmpty>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="employeeContractSBHolder">
			<tfoot>
				<tr class="total">
					<td colspan="15" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractSBHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractSBHolder" property="indexStart" /> - <bean:write name="employeeContractSBHolder" property="indexEnd" /> </label> 
						<label>&nbsp;&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="firstPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="previousPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="nextPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a onclick="submitForm('list_form', null, '<bean:write name="employeeContractSBHolder" property="lastPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractSBHolder" property="realPage" />/<bean:write name="employeeContractSBHolder" property="pageCount" /> </label>&nbsp;
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="employeeContractSBHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
					</td>
				</tr>
			</tfoot>
		</logic:present>
	</table>
	</div>
	<div class="bottom">
		<p>
	</div>
</div>
						
<script type="text/javascript">
	(function($) {
		<logic:present name="MESSAGE">
			messageWrapperFada();
		</logic:present>
		// 检查是否通过提交后的Ajax跳转，是的话清空SelectIds
		<logic:empty name="employeeContractSBForm" property="selectedIds">
			$('.list_form input[id="selectedIds"]').val("");
		</logic:empty>
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);	

	function forward() {
		var value = Number($('#forwardPage').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage = Number($('.forwardPage').val()) - 1;
		submitForm('list_form', null, forwardPage, null, null, 'search-results');
	}
</script>