<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,1)" class="hover first" ><bean:message bundle="public" key="menu.table.title.ot.record" /> </li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab" >
			<div id="tableWrapper1">
				<div id="tableWrapper">
					<jsp:include page="/contents/business/attendance/ot/table/listOTRecordTable.jsp" flush="true"/>
				</div>
			</div>
		</div> 
	</div> 
</div>	
