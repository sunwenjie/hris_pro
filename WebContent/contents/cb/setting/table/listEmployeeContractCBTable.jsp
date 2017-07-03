<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder employeeContractCBHolder = (PagedListHolder) request.getAttribute("employeeContractCBHolder");
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
			<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('list_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
		</logic:equal>
		<!-- <input type="button" class="function" id="btnAdd" name="btnAdd" value="商保设置" onclick="addEmployeeContractCBs();" /> -->
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
				<th style="width: 4%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("employeeCBId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeCBId', '<%=employeeContractCBHolder.getNextSortOrder("employeeCBId")%>', 'search-results');">
						<bean:message bundle="sb" key="sb.number.id" />
					</a>
				</th>
				<th style="width: 7%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("employeeId")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeId', '<%=employeeContractCBHolder.getNextSortOrder("employeeId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("employeeNameZH")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameZH', '<%=employeeContractCBHolder.getNextSortOrder("employeeNameZH")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
					</a>
				</th> 
				<th style="width: 10%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("employeeNameEN")%>">
					<a onclick="submitForm('list_form', null, null, 'employeeNameEN', '<%=employeeContractCBHolder.getNextSortOrder("employeeNameEN")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
					</a>
				</th>
				<th style="width: <logic:equal name="role" value="1">36</logic:equal><logic:equal name="role" value="2">43</logic:equal>%" class="header-nosort">
					<bean:message bundle="public" key="public.note" />
				</th>
				<logic:equal name="role" value="1">
					<th style="width: 7%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("clientId")%>">
						<a onclick="submitForm('list_form', null, null, 'clientId', '<%=employeeContractCBHolder.getNextSortOrder("clientId")%>', 'search-results');">客户ID</a>
					</th>
				</logic:equal>
				<th style="width: 7%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("orderId")%>">
					<a onclick="submitForm('list_form', null, null, 'orderId', '<%=employeeContractCBHolder.getNextSortOrder("orderId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
					</a>
				</th>
				<th style="width: 7%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("contractId")%>">
					<a onclick="submitForm('list_form', null, null, 'contractId', '<%=employeeContractCBHolder.getNextSortOrder("contractId")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header  <%=employeeContractCBHolder.getCurrentSortClass("status")%>">
					<a onclick="submitForm('list_form', null, null, 'status', '<%=employeeContractCBHolder.getNextSortOrder("status")%>', 'search-results');">
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.status" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.status" /></logic:equal>
					</a>
				</th>
				<th style="width: 6%" class="header-nosort"><bean:message bundle="public" key="public.oper" /></th>
			</tr>
		</thead>
		<logic:notEqual name="employeeContractCBHolder" property="holderSize" value="0">
			<tbody>
				<logic:iterate id="employeeContractCBVO" name="employeeContractCBHolder" property="source" indexId="number">
					<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
						<td><input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="employeeContractCBVO" property="employeeCBId"/>" name="chkSelectRow[]" value="<bean:write name='employeeContractCBVO' property='contractId'/>" disabled="disabled" /></td>
						<td class="left"><a onclick="link('employeeContractCBAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedId"/>&comefrom=setting');"><bean:write name="employeeContractCBVO" property="employeeCBId" /></a></td>								
						<td class="left"><a onclick="link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedEmployeeId"/>');"><bean:write name="employeeContractCBVO" property="employeeId" /></a></td>								
						<td class="left"><bean:write name="employeeContractCBVO" property="employeeNameZH" /></td>
						<td class="left"><bean:write name="employeeContractCBVO" property="employeeNameEN" /></td>
						<td class="left"><bean:write name="employeeContractCBVO" property="decodeRemark" /></td>	 
						<logic:equal name="role" value="1">
							<td class="left">
								<a title='<bean:write name="employeeContractCBVO" property="clientName" />' onclick="link('clientAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedClientId"/>');"><bean:write name="employeeContractCBVO" property="clientId" /></a>
							</td>
						</logic:equal>
						<td class="left"><a onclick="link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedOrderId"/>');"><bean:write name="employeeContractCBVO" property="orderId" /></a></td>								
						<td class="left"><a title='<bean:write name="employeeContractCBVO" property="contractName" />'onclick="link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedContractId"/>');"><bean:write name="employeeContractCBVO" property="contractId" /></a></td>								
						<td class="left">
							<bean:write name="employeeContractCBVO" property="decodeContractStatus" />
						</td>
						<td class="left">
							<kan:auth right="new" action="HRO_CB_PURCHASE">
								<logic:notEqual name="employeeContractCBVO" property="contractStatus" value="7">
									<a onclick="addEmployeeContractCB('<bean:write name="employeeContractCBVO" property="contractId"/>', '<bean:write name="employeeContractCBVO" property="encodedContractId"/>');" class="kanhandle">
										<img src="images/add.png" title="商保方案新增" />
									</a>
								</logic:notEqual>
							</kan:auth>
							<logic:notEmpty name="employeeContractCBVO" property="employeeCBId">
								<logic:equal name="employeeContractCBVO" property="status" value="2">
									<kan:auth right="submit" action="HRO_CB_PURCHASE">
										<a onclick="rollbackEmployeeContractCB('<bean:write name="employeeContractCBVO" property="employeeCBId"/>');" class="kanhandle">
											<img src="images/deduct.png" title="商保方案退购" />
										</a> &nbsp;
									</kan:auth>
								</logic:equal>
								<logic:equal name="employeeContractCBVO" property="status" value="3">
									<kan:auth right="submit" action="HRO_CB_PURCHASE">
										<a onclick="rollbackEmployeeContractCB('<bean:write name="employeeContractCBVO" property="employeeCBId"/>');" class="kanhandle">
											<img src="images/deduct.png" title="商保方案退购" />
										</a> &nbsp;
									</kan:auth>	
								</logic:equal>
								<kan:auth right="modify" action="HRO_CB_PURCHASE">
									<a onclick="modifyEmployeeContractCB('<bean:write name="employeeContractCBVO" property="employeeCBId"/>');" class="kanhandle">
										<img src="images/modify.png" title="商保方案编辑" />
									</a> &nbsp;
								</kan:auth>		
								<logic:equal name="employeeContractCBVO" property="status" value="0">
									<kan:auth right="delete" action="HRO_CB_PURCHASE">
										<a onclick="deleteEmployeeContractCB('<bean:write name="employeeContractCBVO" property="encodedId"/>');" class="kanhandle">
											<img src="images/disable.png" title="商保方案删除" />
										</a>
									</kan:auth>	
								</logic:equal>
							</logic:notEmpty>
						</td>
					</tr>
				</logic:iterate>
			</tbody>
		</logic:notEqual>
		<logic:present name="employeeContractCBHolder">
			<tfoot>
				<tr class="total">
					<td colspan="14" class="left">
						<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="employeeContractCBHolder" property="holderSize" /> </label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="employeeContractCBHolder" property="indexStart" /> - <bean:write name="employeeContractCBHolder" property="indexEnd" /> </label> 
						<label>&nbsp;&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractCBHolder" property="firstPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.first" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractCBHolder" property="previousPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.previous" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractCBHolder" property="nextPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.next" /></a></label> 
						<label>&nbsp;<a href="#" onclick="submitForm('list_form', null, '<bean:write name="employeeContractCBHolder" property="lastPage" />', null, null, 'search-results');"><bean:message bundle="public" key="page.last" /></a></label> 
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="employeeContractCBHolder" property="realPage" />/<bean:write name="employeeContractCBHolder" property="pageCount" /> </label>&nbsp;
						<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.jump.to" />：<input type="text" id="forwardPage" class="forwardPage" style="width:23px;" value="<bean:write name="employeeContractCBHolder" property="realPage" />" onkeydown="if(event.keyCode == 13){forward();}" /><bean:message bundle="public" key="page.page" /></label>&nbsp;
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
		<logic:empty name="employeeContractCBForm" property="selectedIds">
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