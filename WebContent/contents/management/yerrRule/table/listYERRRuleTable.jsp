<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder yerrRuleHolder = (PagedListHolder)request.getAttribute("yerrRuleHolder");
%>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="kanList_chkSelectAll" name="chkSelectAll" value="" />
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("rating")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'rating', '<%=yerrRuleHolder.getNextSortOrder("rating")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.rating" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("distribution")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'distribution', '<%=yerrRuleHolder.getNextSortOrder("distribution")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.distribution" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritRateRMB")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritRateRMB', '<%=yerrRuleHolder.getNextSortOrder("meritRateRMB")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.merit.cn" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritRateHKD")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritRateHKD', '<%=yerrRuleHolder.getNextSortOrder("meritRateHKD")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.merit.hk" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritRateSGD")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritRateSGD', '<%=yerrRuleHolder.getNextSortOrder("meritRateSGD")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.merit.sg" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritAndPromotionRateRMB")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritAndPromotionRateRMB', '<%=yerrRuleHolder.getNextSortOrder("meritAndPromotionRateRMB")%>', 'tableWrapper');">
					<bean:message bundle="performance" key="yerr.rule.promotion.cn" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritAndPromotionRateHKD")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritAndPromotionRateHKD', '<%=yerrRuleHolder.getNextSortOrder("meritAndPromotionRateHKD")%>', 'tableWrapper');">
					
					<bean:message bundle="performance" key="yerr.rule.promotion.hk" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("meritAndPromotionRateSGD")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'meritAndPromotionRateSGD', '<%=yerrRuleHolder.getNextSortOrder("meritAndPromotionRateSGD")%>', 'tableWrapper');">
					
					<bean:message bundle="performance" key="yerr.rule.promotion.sg" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("bounsRate")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'bounsRate', '<%=yerrRuleHolder.getNextSortOrder("bounsRate")%>', 'tableWrapper');">
					
					<bean:message bundle="performance" key="yerr.rule.bonus.payout" />
				</a>
			</th>
			<th class="header <%=yerrRuleHolder.getCurrentSortClass("status")%>">
				<a onclick="submitForm('listYERRRule_form', null, null, 'status', '<%=yerrRuleHolder.getNextSortOrder("status")%>', 'tableWrapper');"><bean:message bundle="public" key="public.status" /></a>
			</th>
			<th class="header-nosort"><bean:message bundle="public" key="public.modify.by" /></th>
			<th class="header-nosort"><bean:message bundle="public" key="public.modify.date" /></th>
		</tr>
	</thead>			
	<logic:notEqual name="yerrRuleHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="yerrRuleVO" name="yerrRuleHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>'>
					<td>
						<input type="checkbox" id="kanList_chkSelectRecord_<bean:write name="yerrRuleVO" property="ruleId"/>" name="chkSelectRow[]" value="<bean:write name="yerrRuleVO" property="ruleId"/>" />
					</td>
					<td class="left">
						<a onclick="link('yerrRuleAction.do?proc=to_objectModify&encodedId=<bean:write name="yerrRuleVO" property="encodedId"/>');">
							<bean:write name="yerrRuleVO" property="rating"/>
						</a>
					</td>
					<td class="right"><bean:write name="yerrRuleVO" property="distribution" format="##%"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritRateRMB" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritRateHKD" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritRateSGD" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritAndPromotionRateRMB" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritAndPromotionRateHKD" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="meritAndPromotionRateSGD" format="#0.000"/></td>
					<td class="right"><bean:write name="yerrRuleVO" property="bounsRate" format="#0.000"/></td>
					<td class="left"><bean:write name="yerrRuleVO" property="decodeStatus"/></td>
					<td class="left"><bean:write name="yerrRuleVO" property="decodeModifyBy"/></td>
					<td class="left"><bean:write name="yerrRuleVO" property="decodeModifyDate"/></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="yerrRuleHolder">
		<tfoot>
			<tr class="total">
				<td colspan="13" class="left"> 
					 <label>&nbsp;<bean:message bundle="public" key="page.total" />£º <bean:write name="yerrRuleHolder" property="holderSize" /></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />£º<bean:write name="yerrRuleHolder" property="indexStart" /> - <bean:write name="yerrRuleHolder" property="indexEnd" /></label>
					 <label>&nbsp;&nbsp;<a href="#" onclick="submitForm('listYERRRule_form', null, '<bean:write name="yerrRuleHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listYERRRule_form', null, '<bean:write name="yerrRuleHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listYERRRule_form', null, '<bean:write name="yerrRuleHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label>
					 <label>&nbsp;<a href="#" onclick="submitForm('listYERRRule_form', null, '<bean:write name="yerrRuleHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label>
					 <label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />£º<bean:write name="yerrRuleHolder" property="realPage" />/<bean:write name="yerrRuleHolder" property="pageCount" /></label>&nbsp;
				</td>					
			</tr>
		</tfoot>
	</logic:present>
</table>