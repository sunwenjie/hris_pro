<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants" %>

<%
	Map<String,Integer> users = KANConstants.userBlackList;
	Iterator iterator = users.entrySet().iterator();
	while(iterator.hasNext(  )){
	   Map.Entry entry =  (Map.Entry )iterator.next(  );
	   out.print(entry.getKey()+":"+entry.getValue(  ));
	   if((Integer)entry.getValue()  >=6){
	      out.print("###########");
	   }
	   out.print("<br/>");
	}
	
	%>