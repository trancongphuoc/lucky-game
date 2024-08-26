<script language="JavaScript" type="text/javascript">
    function changeLanguage(language) {
       var kvp = document.location.search.substr(1).split('&');
       var lan = escape(language);
        if (kvp == '') {
            document.location.search = '?language=' + lan;
        };
        else {
            var i = kvp.length; 
            var x; 
            while (i--) {
                x = kvp[i].split('=');

                if (x[0] == "language") {
                    x[1] = lan;
                    kvp[i] = x.join('=');
                    break;
                }
            }

            if (i < 0) {
                kvp[0] = '?language=' + lan; 
            }

            location.search = kvp.join('&');
        }
    }
</script>
<%
 String language = request.getParameter("language");
 if("en_US".equals(language) 
         || "vi_VN".equals(language)){
     HttpSession sesstion = request.getSession();
     sesstion.setAttribute("language", language);
 } 
 else {
     HttpSession sesstion = request.getSession();
     sesstion.setAttribute("language", "en_US");
 } 
//  else {
//     HttpSession sesstion = request.getSession();
//     if (sesstion.getAttribute("language") == null) {
//        sesstion.setAttribute("language", request.getLocale());
//     }
// }

%>
<!--<span style="margin: 20px 5px 3px 3px; display: block; float: right">
    <select onchange="changeLanguage(this.value)" >
        <option value="en_US" ${sessionScope.language == 'en_US' ? 'selected' : ''}>English</option>
        <option value="vi_VN" ${sessionScope.language == 'vi_VN' ? 'selected' : ''}>Vietname</option>
    </select>
</span>-->