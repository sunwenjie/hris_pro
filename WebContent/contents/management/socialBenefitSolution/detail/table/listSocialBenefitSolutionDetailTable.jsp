<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final PagedListHolder socialBenefitSolutionDetailHolder = ( PagedListHolder )request.getAttribute( "socialBenefitSolutionDetailHolder" );
%>

<logic:notEmpty name="socialBenefitHeaderForm">
	<input type="hidden" name="attribute" id="attribute" value="<bean:write name="socialBenefitHeaderForm" property="attribute" />">
	<input type="hidden" name="effective" id="effective" value="<bean:write name="socialBenefitHeaderForm" property="effective" />">
	<input type="hidden" name="startDate" id="startDate" value="<bean:write name="socialBenefitHeaderForm" property="startDateLimit" />">
	<input type="hidden" name="endDate" id="endDate" value="<bean:write name="socialBenefitHeaderForm" property="endDateLimit" />">
	<input type="hidden" name="startRule" id="startRule" value="<bean:write name="socialBenefitHeaderForm" property="startRule" />">
	<input type="hidden" name="startRuleRemark" id="startRuleRemark" value="<bean:write name="socialBenefitHeaderForm" property="startRuleRemark" />">
	<input type="hidden" name="endRule" id="endRule" value="<bean:write name="socialBenefitHeaderForm" property="endRule" />">
	<input type="hidden" name="endRuleRemark" id="endRuleRemark" value="<bean:write name="socialBenefitHeaderForm" property="endRuleRemark" />">
</logic:notEmpty>

<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="checkbox-col">
				<input type="checkbox" id="chk_all_detail" name="chk_all_detail" value="" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.item.id" />
			</th>
			<th style="width: 6%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.item.no" />
			</th>
			<th style="width: 18%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.item.name" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.percent.company" />
			</th>
			<th style="width: 10%" class="header-nosort"> 
				<bean:message bundle="management" key="management.sb.solution.detail.percent.personal" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.floor.cap.company" />
			</th>
			<th style="width: 15%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.floor.cap.personal" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.fix.amount.company" />
			</th>
			<th style="width: 10%" class="header-nosort">
				<bean:message bundle="management" key="management.sb.solution.detail.fix.amount.personal" />
			</th>
		</tr>
	</thead>
	<logic:notEqual name="socialBenefitSolutionDetailHolder" property="holderSize" value="0">
		<tbody>
			<logic:iterate id="socialBenefitSolutionDetailVO" name="socialBenefitSolutionDetailHolder" property="source" indexId="number">
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %> header'>
					<td>
						<input type="checkbox" id="indexArray_<%= number %>" name="indexArray" value="<%= number %>" />
						<input type="hidden" name="sysDetailId" id="manageSocialBenefitSolutionDetail_sysDetailId" value="<bean:write name="socialBenefitSolutionDetailVO" property="detailId"/>"/>
						<input type="hidden" name="statusArray" id="statusArray_<%= number %>" class="" value="<bean:write name="socialBenefitSolutionDetailVO" property="status"/>"/>
						<input type="hidden" name="sysDetailIdArray" class="" value="<bean:write name="socialBenefitSolutionDetailVO" property="sysDetailId"/>"/>
						<input type="hidden" name="detailIdArray" id="detailIdArray_<%= number %>" class="" value="<bean:write name="socialBenefitSolutionDetailVO" property="encodedId"/>"/>
						<input type="hidden" name="itemIdArray" class="" value="<bean:write name="socialBenefitSolutionDetailVO" property="itemId"/>"/>
					</td>
					<td class="left" onclick="showTrDetail(this);" style="cursor: pointer;">
						<bean:write name="socialBenefitSolutionDetailVO" property="itemId"/>&nbsp;&nbsp;
						<img src="images/content/toggle-up.png" class="up" style="display: none;" title="查看更多……">
						<img src="images/content/toggle-down.png" class="down" title="查看更多……">
					</td>
					<td class="left" onclick="showTrDetail(this);" style="cursor: pointer;"><bean:write name="socialBenefitSolutionDetailVO" property="itemNo"/></td>
					<td class="left" onclick="showTrDetail(this);" style="cursor: pointer;">
						<bean:write name="socialBenefitSolutionDetailVO" property="decodeItem"/>
					</td>
					<td class="left">
						<select name="companyPercentArray" id="companyPercentArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_companyPercentArray_<%= number %>">
							<logic:iterate id="section" name="socialBenefitSolutionDetailVO" property="companyPercentSection">
								<logic:equal name="section"  property="mappingId" value="${socialBenefitSolutionDetailVO.companyPercent}">
									<option value="<bean:write name="section" property="mappingId"/>" selected="selected">
										<bean:write name="section" property="mappingValue"/>
									</option>
								</logic:equal>
								<logic:notEqual name="section" property="mappingId" value="${socialBenefitSolutionDetailVO.companyPercent}">
									<option value="<bean:write name="section" property="mappingId"/>">
										<bean:write name="section" property="mappingValue"/>
									</option>
								</logic:notEqual>
							</logic:iterate>
						</select>
					</td>
					<td class="left">
						<select name="personalPercentArray" id="personalPercentArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_personalPercentArray_<%= number %>">
							<logic:iterate id="section" name="socialBenefitSolutionDetailVO" property="personalPercentSection">
								<logic:equal name="section"  property="mappingId" value="${socialBenefitSolutionDetailVO.personalPercent}">
									<option value="<bean:write name="section" property="mappingId"/>" selected="selected">
										<bean:write name="section" property="mappingValue"/>
									</option>
								</logic:equal>
								<logic:notEqual name="section" property="mappingId" value="${socialBenefitSolutionDetailVO.personalPercent}">
									<option value="<bean:write name="section" property="mappingId"/>">
										<bean:write name="section" property="mappingValue"/>
									</option>
								</logic:notEqual>
							</logic:iterate>
						</select>
					</td>
					<td class="left">
						<input type="text" name="companyFloorArray" id="companyFloorArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_companyFloorArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="companyFloor"/>"/>&nbsp;~&nbsp;<input type="text" name="companyCapArray" id="companyCapArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_companyCapArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="companyCap"/>"/>
					</td>
					<td class="left">
						<input type="text" name="personalFloorArray" id="personalFloorArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_personalFloorArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="personalFloor"/>"/>&nbsp;~&nbsp;<input type="text" name="personalCapArray" id="personalCapArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_personalCapArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="personalCap"/>"/>
					</td>
					<td class="left">
						<input type="text" name="companyFixAmountArray" id="companyFixAmountArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_companyFixAmountArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="companyFixAmount"/>"/>
					</td>
					<td class="left">
						<input type="text" name="personalFixAmountArray" id="personalFixAmountArray_<%= number %>" class="small-ex manageSocialBenefitSolutionDetail_personalFixAmountArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="personalFixAmount"/>"/>
					</td>
				</tr>
				<tr class='<%= number % 2 == 1 ? "odd" : "even" %>' style="display: none;">
					<td></td>
					<td colspan="9">
						<fieldset>
							<ol class="auto">
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.effective" /></label>
									<select name="effectiveArray" id="effectiveArray_<%= number %>" class="manageSocialBenefitSolutionDetail_effectiveArray_<%= number %>">
										<logic:iterate id="effective" name="socialBenefitSolutionDetailVO" property="effectives">
											<logic:equal name="effective" property="mappingId" value="${socialBenefitSolutionDetailVO.effective}">
												<option value="<bean:write name="effective" property="mappingId"/>" selected="selected">
													<bean:write name="effective" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="effective" property="mappingId" value="${socialBenefitSolutionDetailVO.effective}">
												<option value="<bean:write name="effective" property="mappingId"/>">
													<bean:write name="effective" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.attribute" /></label>
									<select name="attributeArray" id="attributeArray_<%= number %>" class="manageSocialBenefitSolutionDetail_attributeArray_<%= number %>">
										<logic:iterate id="attribute" name="socialBenefitSolutionDetailVO" property="attributes">
											<logic:equal name="attribute" property="mappingId" value="${socialBenefitSolutionDetailVO.attribute}">
												<option value="<bean:write name="attribute" property="mappingId"/>" selected="selected">
													<bean:write name="attribute" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="attribute" property="mappingId" value="${socialBenefitSolutionDetailVO.attribute}">
												<option value="<bean:write name="attribute" property="mappingId"/>">
													<bean:write name="attribute" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.start.date.limit" /></label> 
									<select name="startDateLimitArray" id="startDateLimitArray_<%= number %>" class="manageSocialBenefitSolutionDetail_startDateLimitArray_<%= number %>">
										<logic:iterate id="date" name="socialBenefitSolutionDetailVO" property="dates">
											<logic:equal name="date" property="mappingId" value="${socialBenefitSolutionDetailVO.startDateLimit}">
												<option value="<bean:write name="date" property="mappingId"/>" selected="selected">
													<bean:write name="date" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="date" property="mappingId" value="${socialBenefitSolutionDetailVO.startDateLimit}">
												<option value="<bean:write name="date" property="mappingId"/>">
													<bean:write name="date" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.end.date.limit" /></label> 
									<select name="endDateLimitArray" id="endDateLimitArray_<%= number %>" class="manageSocialBenefitSolutionDetail_endDateLimitArray_<%= number %>">
										<logic:iterate id="date" name="socialBenefitSolutionDetailVO" property="dates">
											<logic:equal name="date" property="mappingId" value="${socialBenefitSolutionDetailVO.endDateLimit}">
												<option value="<bean:write name="date" property="mappingId"/>" selected="selected">
													<bean:write name="date" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="date" property="mappingId" value="${socialBenefitSolutionDetailVO.endDateLimit}">
												<option value="<bean:write name="date" property="mappingId"/>">
													<bean:write name="date" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.start.rule" /></label> 
									<select name="startRuleArray" id="startRuleArray_<%= number %>" class="manageSocialBenefitSolutionDetail_startRuleArray_<%= number %>">
										<logic:iterate id="startRule" name="socialBenefitSolutionDetailVO" property="rules">
											<logic:equal name="startRule" property="mappingId" value="${socialBenefitSolutionDetailVO.startRule}">
												<option value="<bean:write name="startRule" property="mappingId"/>" selected="selected">
													<bean:write name="startRule" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="startRule" property="mappingId" value="${socialBenefitSolutionDetailVO.startRule}">
												<option value="<bean:write name="startRule" property="mappingId"/>">
													<bean:write name="startRule" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.start.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
									<input type="text" name="startRuleRemarkArray" id="startRuleRemarkArray_<%= number %>" class="manageSocialBenefitSolutionDetail_startRuleRemarkArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="startRuleRemark"/>"/>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.end.rule" /></label> 
									<select name="endRuleArray" id="endRuleArray_<%= number %>" class="manageSocialBenefitSolutionDetail_endRuleArray_<%= number %>">
										<logic:iterate id="endRule" name="socialBenefitSolutionDetailVO" property="rules">
											<logic:equal name="endRule" property="mappingId" value="${socialBenefitSolutionDetailVO.endRule}">
												<option value="<bean:write name="endRule" property="mappingId"/>" selected="selected">
													<bean:write name="endRule" property="mappingValue"/>
												</option>
											</logic:equal>
											<logic:notEqual name="endRule" property="mappingId" value="${socialBenefitSolutionDetailVO.endRule}">
												<option value="<bean:write name="endRule" property="mappingId"/>">
													<bean:write name="endRule" property="mappingValue"/>
												</option>
											</logic:notEqual>
										</logic:iterate>
									</select>
								</li>
								<li>
									<label><bean:message bundle="management" key="management.sb.solution.header.end.rule.remark" /> <img src="images/tips.png" title="<bean:message bundle="system" key="system.sb.header.rule.remark.tips" />" /></label> 
									<input type="text" name="endRuleRemarkArray" id="endRuleRemarkArray_<%= number %>" class="manageSocialBenefitSolutionDetail_endRuleRemarkArray_<%= number %>" value="<bean:write name="socialBenefitSolutionDetailVO" property="endRuleRemark"/>"/>
								</li>
							</ol>
						</fieldset>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
		<tfoot>
			<tr class="total">
				<td colspan="10" class="left"> 
					<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="socialBenefitSolutionDetailHolder" property="holderSize" /></label>
					<label>&nbsp;&nbsp;</label>
					<label>&nbsp;&nbsp;</label>
					<label>&nbsp;</label>
					<label>&nbsp;</label>
					<label>&nbsp;</label>
					<label>&nbsp;&nbsp;</label>&nbsp;
				</td>					
			</tr>
		</tfoot>
</table>

<script type="text/javascript">
	(function($) {
		// 当前社保公积金方案状态为启用的，则选中多选框。
		
		$('#resultTable tr.header').each(function(i){
			var checked = false;
			var status = $('#resultTable tr.header').eq(i).find('td:nth-child(1) input[id^="statusArray_"][type="hidden"]').val();
			if( status == 1){
				checked = true;
			}
			$('#resultTable tr.header').eq(i).find('td:first-child input[id^="indexArray_"]').attr("checked",checked);
		});
		
		$('#resultTable tr.header td:first-child input[id^="detailIdArray_"]').each(function(i){
			if($('#resultTable tr.header:nth-child('+(i+1)+') td:nth-child(1) input[id^="detailIdArray_"][type="hidden"]').val() == '' ){
				$(this).val('0');
			}
		});
		// 初始化多选框
		if($('.manageSocialBenefitSolutionHeader_form #subAction').val() == 'createObject'){
			$('input[id^="indexArray_"]').attr("checked",true);
			$('#chk_all_detail').attr("checked",true); 
		}
		// 多选框点击事件
		$('#chk_all_detail').click(function(){
			var flag = $(this).attr("checked");
			if(flag){
				$("input[id^='indexArray_']").attr("checked",true);
			}else{
				$("input[id^='indexArray_']").attr("checked",false);
			}
		});
		$('input[id^="indexArray_"]').click(function(){
			var selectorCheckboxes = $('input[id^="indexArray_"]');
			var isAllChecked = (selectorCheckboxes.size() == selectorCheckboxes.filter(':checked').size());
			$('#chk_all_detail').attr('checked', isAllChecked);
		});
		
		$('#resultTable input[type=text]').blur(function(){
			if($(this).val()==""){
				$(this).val("0");
			}
		});
		
		// 所有社保公积金详情状态为启用
		$('.manageSocialBenefitSolutionHeader_form select[id^="statusArray_"]').val('1');
		
	})(jQuery);
	
	function showTrDetail(dom){
		return;
		$td = $(dom);
		if($td.parents('tr').next().is(':visible')){
			$td.parents('tr').next().hide();
			$td.parents('tr').find('img.up').hide();
			$td.parents('tr').find('img.down').show();
		}else{
			$td.parents('tr').next().show();
			$td.parents('tr').find('img.up').show();
			$td.parents('tr').find('img.down').hide();
		}
	};
</script>