<!DOCTYPE html>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html lang="zh-cn">
<head>
	<logic:notEqual name="pageFlag" value="0">
	<title>
		 Q & A
	</title>
	</logic:notEqual>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="js/jquery-1.8.2.min.js"></script>
	<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
	<style type="text/css">
		.a{
		background-color: #DAD9CA;
		}
		ul{
			padding: 0px;
		}
		ul li{  
			list-style-type:none;  
		}  
	</style>
</head>
<body>


<div style="margin:5%;">
<img alt="" src="images/logo/iclick-logo.png">

<logic:equal name="pageFlag" value="0">
	<bean:message bundle="wx" key="wx.qa.close" />
</logic:equal>
<logic:notEqual name="pageFlag" value="0">
<html:form action="answerAction.do?proc=add_object" styleClass="answerForm" styleId="answerForm" method="post">
	<%= BaseAction.addToken( request ) %>
	<html:hidden property="weChatId" />
	<html:hidden property="headerId" />
	<logic:notEmpty name="questionHeaderVO">
			<%-- 单选题 --%>
			<logic:equal name="questionHeaderVO" property="isSingle" value="1">
				<div class="radio">
					<p id="quesitonTitle">
						 <%
					    	if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
					     %>
					    	 <bean:write name="questionHeaderVO" property="titleZH" />
					     <%
					    	}else{
					     %>
					    	 <bean:write name="questionHeaderVO" property="titleEN" />
					     <% } %>
					</p>
					<ul>
						<logic:notEmpty name="questionDetailVOs">
							<logic:iterate id="questionDetailVO" name="questionDetailVOs" indexId="optionIndex">
								<li>
									 <label>
									    <input type="radio" class="answer_option" value="<%=optionIndex %>" name="answer" aria-label="...">
									    <%
									    	if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
									    %>
									    	 <bean:write name="questionDetailVO" property="nameZH" />
									    <%
									    	}else{
									    %>
									    	 <bean:write name="questionDetailVO" property="nameEN" />
									    <% } %>
								 	 </label>
								</li>
							</logic:iterate>
						</logic:notEmpty>
					</ul>
				</div>
			</logic:equal>
			
			<%-- 多选题 --%>
			<logic:notEqual name="questionHeaderVO" property="isSingle" value="1">
				<div class="checkbox">
					<p id="quesitonTitle">
						 <%
					    	if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
					     %>
					    	 <bean:write name="questionHeaderVO" property="titleZH" />
					     <%
					    	}else{
					     %>
					    	 <bean:write name="questionHeaderVO" property="titleEN" />
					     <% } %>
					</p>
					<ul>
						<logic:notEmpty name="questionDetailVOs">
							<logic:iterate id="questionDetailVO" name="questionDetailVOs" indexId="optionIndex">
								<li>
									 <label>
									    <input type="checkbox" class="answer_option" value="<%=optionIndex %>" name="answer" aria-label="...">
									    <%
									    	if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
									    %>
									    	 <bean:write name="questionDetailVO" property="nameZH" />
									    <%
									    	}else{
									    %>
									    	 <bean:write name="questionDetailVO" property="nameEN" />
									    <% } %>
								 	 </label>
								</li>
							</logic:iterate>
						</logic:notEmpty>
					</ul>
				</div>
			</logic:notEqual>
	</logic:notEmpty>
	
	<logic:equal name="pageFlag" value="3">
		<input type="button" class="btn btn-primary" id="btnSubmit" value='<bean:message bundle="public" key="button.submit"/>' />
	</logic:equal>
	<logic:equal name="pageFlag" value="2">
		<bean:message bundle="wx" key="wx.qa.submitted" />
	</logic:equal>
	<logic:equal name="pageFlag" value="1">
		<bean:message bundle="wx" key="wx.qa.close" />
	</logic:equal>
	
	<br/><br/><br/>
 	
 	<p id="quesitonTips">
		 <%
	    	if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" )){
	     %>
	    	 <bean:write name="questionHeaderVO" property="tipsZH" />
	     <%
	    	}else{
	     %>
	    	 <bean:write name="questionHeaderVO" property="tipsEN" />
	     <% } %>
	</p>

</html:form>
</logic:notEqual>
</div>
</body>
<script type="text/javascript">
	(function($) {
		// 验证表单
		function validate_form(){
			var flag = 0;
			var optionArray = $('.answer_option');
			optionArray.each( function(){
				if( $(this).is(':checked') == true ){
					flag = flag + 1;
				}
			});
			
			return flag;
		};
	
		$('#btnSubmit').click( function(){
			if(validate_form() != 0){
				$('#answerForm').submit();
			}else{
				alert('<bean:message bundle="wx" key="wx.qa.choose.one" />');
			}
		});
	})(jQuery);
</script>
</html>