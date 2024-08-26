package WEB-INF.tags;

<%-- 
    Document   : DislayResult
    Created on : Dec 1, 2008, 5:38:05 PM
    Author     : ThanhNC
    Purpose: Hien thi thong bao sau khi thuc hien 1 action
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@attribute name="property" required="true"%>
<%@attribute name="displayTime"%>
<%@attribute name="type"%>
<%@attribute name="id" required="true"%>
<%
        String msg = (String) request.getAttribute(property);
        request.setAttribute("msg", msg);
        String displayType = "";
        if (type != null && !"".equals(type)) {
            displayType = type;
        }
        request.setAttribute("displayType", displayType);
%>

<s:if test="#request.msg != null && #request.msg !=''">
    <table align="center" width="100%" border="0">
        <tr>
            <td align="center">

                <s:if test="#request.displayType == 'key'">
                    <!--div align="center" id="${id}" style="position:absolute;display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:red"-->
                        <h2 class="txtError"><s:text name="%{#request.msg}"/></h2>
                    <!--/div-->
                </s:if>
                <s:else>
                    <!--div align="center" id="${id}" style="position:absolute;display:none;border:1px solid #0e0;background-color:#efe;padding:2px;margin-top:8px;width:300px;font:normal 12px Arial;color:red"-->
                        <h2 class="txtError"><s:property value="#request.msg"/></h2>
                    <!--/div-->
                </s:else>
            </td>
        </tr>
    </table>
    <script language="javascript" type="text/javascript">
       // showMessage("${id}","${displayTime}");
        showMessage = function()) {
            // display success message
            Element.show('msgResultJavaScript');
            setTimeout("Effect.SwitchOff('msgResultJavaScript');", 3000);

        }
        showMessage = function(id,time) {
            // display success message
            //alert("abc" + id);
            if(id=="")
                id="msgResultJavaScript";
            if(time=="")
                time=3000;
            Element.show(id);
            setTimeout("Effect.SwitchOff("+id+");", time);

        }
    </script>
</s:if>


