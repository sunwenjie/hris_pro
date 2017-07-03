<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.TaxAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	PagedListHolder taxHolder = (PagedListHolder) request.getAttribute("taxHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>	
			<th style="width: 8%" class="header <%=taxHolder.getCurrentSortClass("taxId")%>">
				<a onclick="submitForm('listtax_form', null, null, 'taxId', '<%=taxHolder.getNextSortOrder("taxId")%>', 'tableWrapper');">税率ID</a>
			</th>						
			<th style="width: 15%" class="header <%=taxHolder.getCurrentSortClass("nameZH")%>">
				<a onclick="submitForm('listtax_form', null, null, 'nameZH', '<%=taxHolder.getNextSortOrder("nameZH")%>', 'tableWrapper');">税率名（中文）</a>
			</th>
			<th style="width: 15%" class="header <%=taxHolder.getCurrentSortClass("nameEN")%>">
				<a onclick="submitForm('listtax_form', null, null, 'nameEN', '<%=taxHolder.getNextSortOrder("nameEN")%>', 'tableWrapper');">税率名（英文）</a>
			</th>
			<th style="width: 15%" class="header <%=taxHolder.getCurrentSortClass("entityId")%>">
				<a onclick="submitForm('listtax_form', null, null, 'entityId', '<%=taxHolder.getNextSortOrder("entityId")%>', 'tableWrapper');">法务实体</a>
			</th>
			<th style="width: 15%" class="header <%=taxHolder.getCurrentSortClass("businessTypeId")%>">
				<a onclick="submitForm('listtax_form', null, null, 'businessTypeId', '<%=taxHolder.getNextSortOrder("businessTypeId")%>', 'tableWrapper');">业务类型</a>
			</th>
			<th style="width: 8%" class="header <%=taxHolder.getCurrentSortClass("saleTax")%>">
				<a onclick="submitForm('listtax_form', null, null, 'saleTax', '<%=taxHolder.getNextSortOrder("saleTax")%>', 'tableWrapper');">销售税率</a>
			</th>
			<th style="width: 8%" class="header <%=taxHolder.getCurrentSortClass("costTax")%>">
				<a onclick="submitForm('listtax_form', null, null, 'costTax', '<%=taxHolder.getNextSortOrder("costTax")%>', 'tableWrapper');">成本税率</a>
			</th>
			<th style="width: 8%" class="header <%=taxHolder.getCurrentSortClass("actualTax")%>">
				<a onclick="submitForm('listtax_form', null, null, 'actualTax', '<%=taxHolder.getNextSortOrder("actualTax")%>', 'tableWrapper');">实缴税率</a>
			</th>
			<th style="width: 8%" class="header <%=taxHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listtax_form', null, null, 'status', '<%=taxHolder.getNextSortOrder("status")%>', 'tableWrapper');">状态</a>
			</th>
		</tr>
	</thead>
	<logic:notEqual name="taxHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="taxVO" name="taxHolder" property="source" indexId="number">
				<tr class="<%= number % 2 == 1 ? "odd" : "even" %>">
					<td>
						<logic:equal name="taxVO" property="extended" value="2">
							<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="taxVO" property="taxId"/>" name="chkSelectRow[]" value="<bean:write name="taxVO" property="taxId"/>" />
						</logic:equal>
					</td>
					<td class="left">
						<kan:auth right="modify" action="<%=TaxAction.accessAction%>">
							<a onclick="link('taxAction.do?proc=to_objectModify&taxId=<bean:write name="taxVO" property="encodedId"/>');">
						</kan:auth> 
						<bean:write name="taxVO" property="taxId" /> 
						<kan:auth right="modify" action="<%=TaxAction.accessAction%>">
							</a>
						</kan:auth>
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=TaxAction.accessAction%>">
						<a onclick="link('taxAction.do?proc=to_objectModify&taxId=<bean:write name="taxVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="taxVO" property="nameZH"/>
					<kan:auth right="modify" action="<%=TaxAction.accessAction%>">	
						</a>
					</kan:auth>	
					</td>
					<td class="left">
					<kan:auth right="modify" action="<%=TaxAction.accessAction%>">
						<a onclick="link('taxAction.do?proc=to_objectModify&taxId=<bean:write name="taxVO" property="encodedId"/>');">
					</kan:auth>	
						<bean:write name="taxVO" property="nameEN"/>
					<kan:auth right="modify" action="<%=TaxAction.accessAction%>">	
						</a>
					</kan:auth>
					</td>
					<td class="left"><bean:write name="taxVO" property="decodeEntity"/></td>
					<td class="left"><bean:write name="taxVO" property="decodeBusinessType"/></td>
					<td class="left"><bean:write name="taxVO" property="saleTax"/></td>
					<td class="left"><bean:write name="taxVO" property="costTax"/></td>
					<td class="left"><bean:write name="taxVO" property="actualTax"/></td>
					<td class="left"><bean:write name="taxVO" property="decodeStatus"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="taxHolder">
		<tfoot>
			<tr class="total">
				<td  colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="taxHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="taxHolder" property="indexStart" /> - <bean:write name="taxHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listtax_form', null, '<bean:write name="taxHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listtax_form', null, '<bean:write name="taxHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listtax_form', null, '<bean:write name="taxHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listtax_form', null, '<bean:write name="taxHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="taxHolder" property="realPage" />/<bean:write name="taxHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>