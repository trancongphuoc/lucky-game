<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"  %>
<%@taglib uri="/struts-dojo-tags" prefix="sx"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<s:i18n name="com/viettel/config/Language">
    <html>
        <head>
                  <tiles:insertTemplate template="/WEB-INF/jsp/layout/external_CSS_JS.jsp" />
        </head>
        <script type="text/javascript">
            var djConfig = {
                isDebug:false,
                parseOnLoad:true
            };
        </script>
        <body >
           
                <div class="border">
                    
                        <div class="clr"></div>
                        <div id="element-box">
                            <div class="t">
                                <div class="t">
                                    <div class="t"></div>
                                </div>
                            </div>
                            <div class="m">
                                <sx:div id="bodyContent">
                                <div class="clr"></div><tiles:insertAttribute name="body"/>
                                <tiles:insertTemplate template="/WEB-INF/jsp/layout/external_CSS_JS_Project.jsp" />
                                </sx:div>
                            </div>
                            <div class="b">
                                <div class="b">
                                    <div class="b"></div>
                                </div>
                            </div>
                        </div>
                        <div class="clr"></div>                   
                </div>           
        </body>
    </html>
</s:i18n>