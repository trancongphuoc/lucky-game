<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="/WEB-INF/VTdisplaytag.tld" prefix="display" %>
<%@taglib prefix="vm" tagdir="/WEB-INF/tags/message/" %>
<%@ taglib prefix="fn" uri="/WEB-INF/fn.tld" %>
<%@include file="../../common/language.jsp" %>

<s:if test="#attr.Ret_updatemt_all ==1">
    <vm:info value=" ${fn:escapeXml(Ret_updatemt_allMsg)}"/>
</s:if>
<s:if test="#attr.mt_allList !=null && #attr.mt_allList.size() >0"> 
    <sx:div id="pagingmt_allManagerDiv">
        <s:token/>
        <display:table targets="pagingmt_allManagerDiv" requestURI="pagingmt_allManager.do" id="mt_allList" 
                        name="mt_allList" cellpadding="1" cellspacing="1" pagesize="150" 
                        class="dataTable" style="width:100%">           
            <display:column  title="#" style="width:30px;text-align:center">
                ${fn:escapeXml(mt_allList_rowNum)}
            </display:column>    
           
            <display:column title='#' media="html" style="width:30px;text-align:center">
                <s:if test="#attr.mt_allList.status ==1">
                   <input id="winnerchkId" type="checkbox" name="winnerchkId" value="<s:property value="%{#attr.mt_allList.id}"/>" />            
                </s:if>
            </display:column>
            <display:column escapeXml="true" property="rank" title="rank" ></display:column>
             <%--<display:column escapeXml="true" property="id" title="${_winner.id}" ></display:column>--%>
            <display:column escapeXml="true" property="msisdn" title="msisdn" ></display:column>
            <display:column escapeXml="true" property="point" title="point" ></display:column>
            <display:column escapeXml="true" property="win_day" title="win_day" ></display:column>
            <display:column escapeXml="true" property="description" title="description" ></display:column>
            <%--<display:column escapeXml="true" property="message_code" title="message_code" ></display:column>--%>
            <display:column escapeXml="true" property="code" title="code" ></display:column>
            <display:column escapeXml="true" property="award_code" title="award code" ></display:column>
            <display:column escapeXml="true" property="game_type" title="type" ></display:column>
            
            <display:column escapeXml="true" property="award_type" title="award type" ></display:column>
            <%--<display:column escapeXml="true" property="status" title="status" ></display:column>--%>
            <display:column title='status' media="html" style="width:30px;text-align:center">
                <s:if test="#attr.mt_allList.status ==0">
                   Rejected               
                </s:if>
                <s:if test="#attr.mt_allList.status==1">
                   Draft
                </s:if>  
                <s:if test="#attr.mt_allList.status==2">
                   Already Paid
                </s:if> 
                <s:if test="#attr.mt_allList.status==3">
                   Approved
                </s:if>    
            </display:column>
                    
            <s:if test="#attr.userToken.status !=null && #attr.userToken.status ==1 && #attr.userToken.userType ==1">
                <fmt:message key="cmd.tht" var="_tht" />
                <display:column style="width:100px" title="${_tht}" headerClass="sortable" >     
                    <s:if test="#attr.mt_allList.status==1 ">
                        <div onclick="approvedWinner('<s:property escapeJavaScript="true" value="%{#attr.mt_allList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.mt_allList.msisdn}"/>', 3);"
                             class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer; width: 10px">
                            <img src="<%=request.getContextPath()%>/share/img/approved.png" valign="middle" title="approve" alt="approve"/>
                        </div>
                        <div onclick="approvedWinner('<s:property escapeJavaScript="true" value="%{#attr.mt_allList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.mt_allList.msisdn}"/>', 0);"
                             class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer; width: 10px">
                            <img src="<%=request.getContextPath()%>/share/img/unapproved.png" valign="middle" title="reject" alt="reject"/>
                        </div>
                    </s:if>
                     <s:if test="#attr.mt_allList.status==0 || #attr.mt_allList.status==3  ">
                        <div onclick="approvedWinner('<s:property escapeJavaScript="true" value="%{#attr.mt_allList.id}"/>', '<s:property escapeJavaScript="true" value="%{#attr.mt_allList.msisdn}"/>', 1);"
                             class="dInline" style="padding-left:2px;padding-right:2px;cursor: pointer; width: 10px">
                            <img src="<%=request.getContextPath()%>/share/img/play.png" valign="middle" title="rollback" alt="rollback"/>
                        </div>
                    </s:if>
                    
                </display:column>
            </s:if>
            <display:footer>
                <tr>
                    <td colspan="5">
                        <b>winner: ${fn:escapeXml(mt_allList_rowNum)}</b>
                    </td>
                <tr>
                </display:footer>
            </display:table>
        </sx:div>
    </s:if>
 <fmt:message key="cmd.kcdltm" var="_kcdltm" scope="request" />                   
    <s:else>
        <vm:alert value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#_kcdltm"></vm:alert>
    </s:else>
