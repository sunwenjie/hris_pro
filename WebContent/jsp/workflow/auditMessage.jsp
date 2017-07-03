<%@page import="com.kan.base.domain.workflow.WorkflowActualVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件审核</title>
</head>
<body>
	<table align="center" border="0" cellpadding="0" cellspacing="0" style="width:700px;" width="700">
	<tbody>
		<tr>
			<td style="width:100.0%;">
				<table align="left" border="1" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
					<tbody>
						<tr>
							<td style="width:100%;height:21px;">
								&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table align="right" border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
									<tbody>
										<tr>
											<td style="height:26px;">
												<p align="right">
													<img border="0" height="40" id="_x0000_i1025" src="images/logo/kanlogo_login_new.png" width="50" /></p>
											</td>
											<td style="width:20px;height:26px;">
												<p align="right">
													&nbsp;</p>
											</td>
										</tr>
									</tbody>
								</table>
								<div style="clear:both;">
									&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td style="width:100%;height:32px;">
								&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table border="0" cellpadding="0">
									<tbody>
										<tr>
											<td>
												<table align="center" border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
													<tbody>
														<tr>
															<td>
																<p align="center"><strong>感谢您使用HRIS</strong></p>
															</td>
														</tr>
													</tbody>
												</table>
												<div style="clear:both;">
													&nbsp;</div>
											</td>
										</tr>
										<tr>
											<td>
												<table align="left" border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
													<tbody>
														<tr>
															<td style="width:100.0%;">
																<table border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
																	<tbody>
																		<tr>
																			<td style="width:100.0%;">
																				<p>
																					您好：<br />
																					<br />
																					<bean:write name="workflowMSG"/></p>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" cellpadding="0">
									<tbody>
										<tr>
											<td>
												<table align="center" border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
													<tbody>
														<tr>
															<td style="width:100.0%;">
																<table border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
																	<tbody>
																		<tr>
																			<td>
																				<p>Workflow ID:<bean:write name="workflowActualStepsForm" property="workflowId"/> </p>
																			</td>
																		</tr>
																		<%--<tr>
																			<td><%// 动态包含 展示审批对象的jsp 页面 。 request里面有passObject 和 failObject 可提供使用
																						WorkflowActualVO workflowActualVO = (WorkflowActualVO)request.getAttribute("workflowActualVO");
																						final String  includeJSP = workflowActualVO.getIncludeViewObjJsp();
																						
																						request.setAttribute("role",workflowActualVO.getCorpId()!=null?"2":"1");
																				%>
																				<jsp:include page="<%=includeJSP %>"></jsp:include>
																			</td>
																		</tr --%>
																	</tbody>
																</table>
															</td>
														</tr>
													</tbody>
												</table>
												<div style="clear:both;">
													&nbsp;</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" cellpadding="0">
									<tbody>
										<tr>
											<td>
												<table align="center" border="0" cellpadding="0" cellspacing="0" style="width:100.0%;" width="100%">
													<tbody>
														<tr>
															<td>
																<p>
																	这是您的工作流编号：<bean:write name="workflowActualStepsForm" property="workflowId"/> <br />
																	<br />
																	如果您需要就此问题再次与我们联系，请务必使用该编号。这样我们可以迅速找到您的案例。<br />
																	<br />
															</td>
														</tr>
													</tbody>
												</table>
												<div style="clear:both;">
													&nbsp;</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td style="width:100%;height:21px;">
								&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</tbody>
</table>
<div style="clear:both;">&nbsp;</div>
 
</body>
</html>
  