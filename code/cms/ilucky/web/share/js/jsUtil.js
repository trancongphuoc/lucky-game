/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function lTrim(sString)//bo ky tu trang dau cau
{
    while (sString.substring(0,1) == ' ')
    {
        sString = sString.substring(1, sString.length);
    }
    return sString;
}

function rTrim(sString)
{
    while (sString.substring(sString.length-1, sString.length) == ' ')
    {
        sString = sString.substring(0,sString.length-1);
    }
    return sString;
}

function Trim(sString)
{
    if(sString==null){
        return '';
    }
    while (sString.substring(0,1) == ' ')
    {
        sString = sString.substring(1, sString.length);
    }
    while (sString.substring(sString.length-1, sString.length) == ' ')
    {
        sString = sString.substring(0,sString.length-1);
    }
    return sString;
}
function IsPositiveNumber(sText)
{
    var t=Trim(sText);
    var ValidChars = "0123456789";
    var IsNumber=true;
    var Char;

    for (i = 0; i < t.length && IsNumber == true; i++)
    {
        Char = t.charAt(i);
        if (ValidChars.indexOf(Char) == -1)
        {
            IsNumber = false;
            return false;
        }
    }
    return true;
}

function checkInputPreEdit(chkBoxlistId,formId){
    //     var chkBoxID = 'accountForm.checkAccountList';

    var ii=0;
    var k=0;
    var name;
    var myArray=new Array();

    var j=0;
    for (var i = 0; i < document.forms[formId].elements.length; i++) {
        var e = document.forms[formId].elements[i];
        if(e.type=='checkbox')
        {
            if(e.name.match(chkBoxlistId) !=null && e.checked==true)
            {
                name=e;
                k+=1;
                myArray[j]="id"+j+"="+name.value;
                j+=1;
            }
        }
    }

    if(j==0){
            
        return '-1';
    }else{
        myArray[j]="lArray="+j;
        return myArray;
    }
}

function CheckAll(obj,chkBoxListId,formId) {

    for (var i=0;i<document.forms[formId].elements.length;i++)
    {
        var e = document.forms[formId].elements[i];
        if(e.type=='checkbox')
        {
            if(e.name.match(chkBoxListId) !=null)
            {
                e.checked = obj.checked;
            }
        }
    }
}
function $(id){
    if(document.getElementById(id)==null || document.getElementById(id).value==''){
        return '';
    }
    else{
        return document.getElementById(id).value;
    }
}