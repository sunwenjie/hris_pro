<%@ page pageEncoding="GBK"%> 
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLDecoder"%> 
<%@ page import="java.net.URLEncoder"%>
<%@ page import="net.sf.json.JSONObject"%> 
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANAccountConstants"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.domain.MappingVO"%>
<%@ page import="com.kan.base.domain.security.UserVO"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
	String role = BaseAction.getRole( request, response );
	final UserVO userVO  =  BaseAction.getUserVOFromClient(request,response);
	final List< MappingVO > connectCorpUserIds;
	
	if( userVO != null )
	{
		connectCorpUserIds = userVO.getConnectCorpUserIds();
	}else
	{
	    connectCorpUserIds = ( List< MappingVO > ) request.getAttribute( "connectCorpUserIds" );
	}
	
	if( ( (String) request.getAttribute("pageStyle") ).equals("1") )
	{
%> 
		<div id="branding">
			<img src="images/logo/iclick-logo.png" width="200" height="58" alt="HRIS" /> 
			<a href="#" class="subscribe" target="_blank"><bean:message key="header.addcommunity"/></a> 
			<span class="welcome"><bean:message key="header.welcome" /> <span class="highlineFont"><%= ( request.getAttribute("role")!=null && KANConstants.ROLE_IN_HOUSE.equals(request.getAttribute("role")) ? request.getAttribute("clientName") : request.getAttribute("entityName") ) + " (" + request.getAttribute("username") + ")" %></span> <a href="#" class="panelTrigger option"><bean:message key="header.option"/></a>&nbsp;</span>
		</div>
<%
	} 
	else if( ( (String) request.getAttribute("pageStyle") ).equals("2") )
	{
		// 获取本地logoFile路径
		String logoFile = "images/logo/iclick-logo.png";
		final String accountId = (String)request.getAttribute("accountId");
		final KANAccountConstants accountConstants =  KANConstants.getKANAccountConstants(accountId);
		
		if(accountConstants!=null)
		{  
	   		if( role.equals( KANConstants.ROLE_IN_HOUSE ) )
	   		{
	      		logoFile = accountConstants.getClientLogoFileByCorpId(BaseAction.getCorpId(request,null));	      
	   		}
	   		else
	   		{
		  		logoFile =accountConstants.OPTIONS_LOGO_FILE;
	   		}
		}
%>
		<div id="branding_s">
			<div>
				<img src="<%=logoFile %>" width="160" height="38" alt="HRIS" />
				<a href="#" class="panelTrigger option sys_option" style="float: right;"><bean:message key="header.option"/></a>
				<span class="welcome_s">
					<span class="accountEntityHeaderSpan"><bean:message key="header.welcome" /><span class="highlineFont">
					<%
						if ( KANConstants.ROLE_IN_HOUSE.equals( role ) )
						{
				      		if(connectCorpUserIds!= null && connectCorpUserIds.size()>0)
				      		{
						      	out.print( "<span class=\"panelTrigger option connectCorpUserId\">" );
							  	out.print( request.getAttribute( "clientName" ) );
							  	out.print( "</span>" );
					   		}
				      		else
				      		{
						  		out.print( request.getAttribute( "clientName" ) );
					   		}
				   		}
						else if( KANConstants.ROLE_CLIENT.equals( role ) )
						{
				      		out.print( request.getAttribute( "clientName" ) );
				   		}
						else if( KANConstants.ROLE_HR_SERVICE.equals( role ) )
						{
				      		out.print( request.getAttribute( "entityName" ) );
				   		}
						else if( KANConstants.ROLE_EMPLOYEE.equals( role ) )
						{
				      		out.print( request.getAttribute( "entityName" ) );
				  		 }
					%>
				 	</span></span>
					<span id="usernameHeaderSpan" class="highlineFont">
						<%  
							out.print( "(" + request.getAttribute( "username" ) );
							
							if( userVO != null && !userVO.getAccountId().trim().equals("1") && userVO.getPositions() != null && userVO.getPositions().size() > 1 ) {
							   out.print( " - <span class=\"panelTrigger option position\">" + request.getAttribute("positionName") + "</span>" );
							}
							
							out.print( ")" );
						%>
					</span>&nbsp;
				</span> 
			</div>
		</div>
<%
	}
%>
<%
	    
	if( connectCorpUserIds != null && connectCorpUserIds.size() > 0 )
	{
		out.println("<div class=\"panelContainer_connectCorpUserIds option-position-menu\">");
		out.println("<ul>");
		
		for(MappingVO mappingVO : connectCorpUserIds)
		{
		   out.println( "<li><a onclick=\"changeLogonUser(" + mappingVO.getMappingId() + ");\" >" + mappingVO.getMappingValue() + "</a></li>" );
		}
		
		out.println("</ul>");
		out.println("</div>");
	}   
	   
	final List< MappingVO > positions = userVO != null ? userVO.getPositions() : ( List< MappingVO > ) request.getAttribute( "positions" );
   		
	if( positions != null && positions.size() > 1 )
	{
		out.println("<div class=\"panelContainer_positions option-position-menu\">");
		out.println("<ul>");
		
		for(MappingVO mappingVO : positions)
		{
			out.println( "<li><a onclick=\"changePosition(" + mappingVO.getMappingId() + ");\" >" + mappingVO.getMappingValue() + "</a></li>" );
		}

		out.println("</ul>");
		out.println("</div>");
	}
%>

<div class="panelContainer option-menu">
	<ul>
		<%-- <li><a href="#" onclick="popupSystemUpdatePage()"><bean:message key="header.option.updateList"/></a></li>
		<li><a href="#" target="_blank"><bean:message key="header.option.helptrain"/></a></li>
		<li><a href=mailto:jack.sun@i-click.com ><bean:message key="header.option.contactus"/></a></li> --%>
		<li><a href="#" onclick="popupChangePassword();"><bean:message key="header.option.changepassword"/></a></li>
		<logic:notEqual name="role" value="5">
			<logic:notEqual name="role" value="4">
				<%-- <li><a href="#" onclick="link('settingAction.do?proc=to_objectModify');"><bean:message key="header.option.setting"/></a></li> --%>
			</logic:notEqual>
		</logic:notEqual>
		<li><a href="#" onclick="if(confirm('<bean:message bundle="public" key="popup.confirm.exit.current.user" />')) link('securityAction.do?proc=logout');"><bean:message key="header.option.exit"/></a></li>
	</ul>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/changePassword.jsp"></jsp:include>
</div>

<div id="popupWrapper">
	<jsp:include page="sysUpdateList.jsp"></jsp:include>
</div>

<script type="text/javascript">
// 切换用户的PositionID
function changePosition(positionId){
	$.post("securityAction.do?proc=changePosition",{"positionId":positionId,"d":new Date()},function(data){
		if(data=="success"){
			if ($.browser.msie) {
				window.location.reload(true);
			}else {
				if(window.location.href.indexOf("securityAction.do?proc=logon")>0 || window.location.href.indexOf("securityAction.do?proc=choosePosition")>0){
					link("userAction.do?proc=list_object");
				}else{
					window.location.reload(true);
				}
			}
		}else if(data=="logon"){
			link("securityAction.do?proc=logon");
		}
	},"text");
	return 0;
};

// 切换用户的PositionID
function changeLogonUser(logonUserId){
	var redirectURL = location.href.split( "/" ); 
	redirectURL = redirectURL[redirectURL.length-1];
	var url = "securityAction.do?proc=changeLogonUser&logonUserId="+logonUserId+"&redirectUrl="+redirectURL+"&d="+new Date();
	link(url);
	return 0;
};
</script>
