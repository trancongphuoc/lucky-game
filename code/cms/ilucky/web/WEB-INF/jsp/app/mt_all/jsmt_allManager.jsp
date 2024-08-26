<%-- 
    Document   : jsmt_all
    Created on : Nov 26, 2009, 11:52:16 AM
    Author     : NhatNT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="../../common/language.jsp" %>
<s:token/>

<script type="text/javascript">

    dojo.event.topic.subscribe("mt_allManagerForm/searchmt_all", function(event, widget) {
//        var msisdn = document.getElementById('mt_allManagerForm.msisdn').value;
//        alert(msisdn);
//        if (document.getElementById('mt_allManagerForm.msisdn') == null || document.getElementById('mt_allManagerForm.msisdn').value == "") {
//            dialog.alert('<fmt:message key="ss.nstb" />');
//            event.cancel = true;
//            return;
//        }
        widget.href = "mt_allManagerAction!searchmt_all.do";
        dialog.showLoadingDialog();
    });
    
    dojo.event.topic.subscribe("mt_allManagerForm/after", function(event, widget) {
        dialog.closeLoadingDialog();
    });  
    
    function downloadExcel(url) {
        
//        widget.href = "mt_allManagerAction!searchmt_all.do";
        var msidn = document.getElementById("mt_allManagerForm.msisdn").value;
        var from_date = "";
        var to_date = "";
        var x = document.getElementsByName("dojo.mt_allManagerForm.datetime");
        if (x != null && x.length > 0) {
          from_date = x[0].value;
        }
        
        x = document.getElementsByName("dojo.mt_allManagerForm.from_datetime");
        if (x != null && x.length > 0) {
          to_date = x[0].value;
        }
        
        var hrf = url + "/download.do?msisdn=" + msidn 
                + "&from_date="+from_date
                + "&to_date="+to_date;
        alert(hrf);
//        widget.href = hrf;
        window.location = hrf;
    };
    
    
    
    var approvedWinner = function(id, msisdn, status) {
         var strConfirm = '';
        if (status === 3) {
            strConfirm = 'are you sure to approved: ' + msisdn + ' ?' ;
        } else if (status === 1) {
            strConfirm = 'are you sure to rollback: ' + msisdn + ' ?' ;
        } else {
            strConfirm = 'are you sure to reject: ' + msisdn + ' ?' ;
        }
        var isDelete = confirm(strConfirm);
        id =  encodeURIComponent(id);
        if (isDelete) {
            var areaId = 'listmt_allManagerDiv';
            var action = 'mt_allManagerAction!approveWinner.do';
            var param = ["id=" + id,"msisdn=" + msisdn,"status=" + status];
            var afterCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            };

            var errorCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            };
            ajax(areaId, action, param, afterCallback, errorCallback);
            dialog.showLoadingDialog();
        }
        else {
            event.cancel = true;
            return;
        }
    };


 function checkAllWinner() 
    {
        //alert("Check all the checkboxes..."); 
        var allRows = document.getElementsByName("winnerchkId");
        var length = allRows.length;
        
        if (length == 0) {
            alert('no winner to choose ');
            return;
        }
        
        var allChecked = document.getElementById("allChecked").value;
        if (allChecked == 0) {
            document.getElementById("allChecked").value = 1;
            
            for (var i=0; i < length; i++) {
                if (allRows[i].type == 'checkbox') 
                {
                    allRows[i].checked = true;
                }
            }
        } else {
            document.getElementById("allChecked").value = 0;
           
            for (var i=0; i < length; i++) {
                if (allRows[i].type == 'checkbox') 
                {
                    allRows[i].checked = false;
                }
            }
        }
            

    }
    
    var approvedWinnerAll = function( status) {
        
        var ids =  getAllWinnerIds();
//        alert(ids);
        
        if (ids == '') {
            alert('no winner to action ');
            return;
        }
        
         var strConfirm = '';
        if (status === 3) {
            strConfirm = 'are you sure to approved?' ;
        } else if (status === 1) {
            strConfirm = 'are you sure to rollback?' ;
        } else {
            strConfirm = 'are you sure to reject?' ;
        }
        var isDelete = confirm(strConfirm);
        
        if (isDelete) {
            var areaId = 'listmt_allManagerDiv';
            var action = 'mt_allManagerAction!approveWinnerAll.do';
            var param = ["ids=" + ids,"status=" + status];
            var afterCallback = function(_areaId, _data) {
//                document.getElementById("mt_allManagerForm").submit();
//                alert('meme');
//                widget.href = "mt_allManagerAction!searchmt_all.do";
                dialog.closeLoadingDialog();
            };

            var errorCallback = function(_areaId, _data) {
                dialog.closeLoadingDialog();
            };
            ajax(areaId, action, param, afterCallback, errorCallback);
            dialog.showLoadingDialog();
        }
        else {
            event.cancel = true;
            return;
        }
    };
    
    function getAllWinnerIds() {
        var allRows = document.getElementsByName("winnerchkId");
        var length = allRows.length;
        
        var ids = '';
        for (var i=0; i < length; i++) {
            if (allRows[i].checked) 
            {
                if (i > 0) {
                    ids +="," + allRows[i].value;
                } else {
                    ids += allRows[i].value;
                }
                
            }
        }
        return ids;
    }
    
</script>