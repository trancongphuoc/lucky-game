<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@taglib tagdir="/WEB-INF/tags/vcl/" prefix="v" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/message/" prefix="vm" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/form/" prefix="vf" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/dialog/" prefix="vd" %>
<%@taglib tagdir="/WEB-INF/tags/vcl/button/" prefix="vb" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<% request.setAttribute("contextPath", request.getContextPath());%>
<s:div id="inputFormDiv">
    <%--<s:if test="#request.msgFlag == 'notice'">
        <vm:notice id="msgDiv" value="${msgContent}"/>
    </s:if>
    <s:if test="#request.msgFlag == 'alert'">
        <vm:alert id="msgDiv" value="${msgContent}"/>
    </s:if>--%>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
        </head>
        <body>
            <table width="100%" align="center" border="0px">
                <tr>
                    <td colspan="2">
                        <s:if test="#attr.empty==1 ">
                            <script type="text/javascript">
                                
                            </script>
                        </s:if>
                        <s:else>
                            <script type="text/javascript" language="JavaScript">
                                window.open('${contextPath}','','toolbar=yes,scrollbars=yes');
                                
                            </script>
                                <a href="${contextPath}/download.do?" style="color:red">Ấn vào đây để tải báo cáo xuống nếu trình duyệt không tự tải về.</a>
                        </s:else>


                       
                    </td>
                </tr>
            </table>
           
        </body>
    </html>
    <%--<script>
    window.open( '${contextPath}${pathOut}', '_blank' );

    </script>--%>
</s:div>
