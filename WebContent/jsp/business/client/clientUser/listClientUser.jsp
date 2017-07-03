<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<tiles:insert definition="com.kan.hro.classical" flush="true">
  <tiles:put name="title" type="String"><bean:message key="user.title.list"/></tiles:put>
  <tiles:put name="body" value="/contents/business/client/clientUser/listClientUserBody.jsp" />
</tiles:insert>