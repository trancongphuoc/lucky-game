function ajax() {
    var areaId, action, param, afterCallback, errorCallback, retType, showLoading;
    var _url;

    switch( arguments.length ) {
        case 2:
            areaId = arguments[0];
            action = arguments[1];
            param = [];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 3:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 4:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 5:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = "text/html";
            showLoading = true;
            break;
        case 6:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = true;
            break;
        case 7:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = arguments[6];
            break;
    }

    _url = action;
    if( param.length > 0 ) {
        _url += "?" + param.join( "&" );
    }

    if( showLoading ) {
    // dialog.showLoadingDialog();
    }

    dojo.io.bind({
        
        preventCache:true,
        url:_url,
        error: function( type, data, evt ) {
            
            if( showLoading ) {
            // dialog.closeLoadingDialog();
            }

            // [ Display error info
            var str = "<div style='color:red;font-weight:bold;'>Error in Ajax: </div>";
            for( var prop in data ) {
                str += prop + " = " + data[prop] + "<br/>";
            }
            //  dialog.warning( Dialog.SYS_WARNING_DIALOG_TITLE, str );
            // ]
            if( errorCallback != null || errorCallback != undefined ) {
                errorCallback( areaId, data );
            }
        },
        handler: function( type, data, e ) {
            
            try {
                if( showLoading ) {
                //     dialog.closeLoadingDialog();
                }
                if( areaId != null || areaId != undefined ) {
                    byId( areaId ).innerHTML = data;
                    var xmlParser = new dojo.xml.Parse();
                    var node = dojo.byId( areaId );                    
                    var frag  = xmlParser.parseElement( node, null, true );
                    dojo.widget.getParser().createSubComponents( frag, dojo.widget.byId( areaId ) );

                    try {
                        var strut = new struts.widget.Bind();
                        strut._executeScripts( strut.parse( data ).scripts );
                    } catch( e ) {
                    //      dialog.warning( "Custom error: " + e.message );
                    }
                }
                if( afterCallback != null || afterCallback != undefined ) {
                    afterCallback( areaId, data );
                }
            }catch( e ) {
            //   dialog.warning( "Datbt: " + e.message );
            }
        },
        mimetype: retType
    });

    // [ inner functions
    function byId( id ) {
        return document.getElementById( id );
    }
// ] inner functions
}
