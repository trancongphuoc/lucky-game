// [ Form table
function toggleDisplayFrmTbl(_div) {
    var div = _div;
    var tbl, trs;
    var isOpened;
    var i;

    tbl = div.parentNode.parentNode.parentNode.parentNode;
    var oldDivBgPos = div.style.backgroundPosition.toString();
    isOpened = (oldDivBgPos.indexOf("-30px") != -1 || oldDivBgPos.length == 0) ? true : false;

    // [ Collapse form content
    if (isOpened) {
        div.style.backgroundPosition = "0% -45px";
        displayTrs(false, tbl, trs);
    }
    // ]
    // [ Expand form content
    else {
        div.style.backgroundPosition = "0% -30px";
        displayTrs(true, tbl, trs);
    }
}

// ]
function byTag(target, tagName) {
    //alert('fda');
    return target.getElementsByTagName(tagName);
}
function displayTrs(state, tbl, trs) {

    var displayValue = (state) ? "" : "none";
    trs = byTag(tbl, 'tr');
    //alert(trs);
    for (i = 1; i < trs.length; i++) {
        trs[i].style.display = displayValue;
    }
}

//[FS]
function toggleDisplayFS(iconSpan) {
    var fs = iconSpan.parentNode.parentNode;// fs = fieldset
    var tbl = fs.getElementsByTagName('table')[0];
    var div = document.getElementById(fs.id + 'Container');

    if (tbl.style.display == 'none') {
        tbl.style.display = '';
        iconSpan.style.backgroundPosition = "-15px -60px";
    } else {
        tbl.style.display = 'none';
        iconSpan.style.backgroundPosition = "-15px -75px";
    }
}

/////////////////////////////////////////////////
////////////////////////////////////////////////

var arrElementToFocus = new Array();
function initWidget() {
    //alert("Hello World!");
    dojo.event.topic.subscribe("errorAction", function(error, request, widget) {
        //alert(error.message + ' ::::: ' + request.responseText);
        widget.targets = "noTarget";
        widget.targetsArray = ["noTarget"];
        var win = window.open('', 100, config = 'height=800,width=900, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, directories=no, status=no');
        win.document.write(request.responseText);
    });

    dojo.event.topic.subscribe("updateBodyContent", function(error, request, widget) {
        //dojo.widget.byId("controlMenu").;
        widget.targets = "bodyContent";
        widget.targetsArray = ["bodyContent"];
    });
    dojo.event.topic.subscribe = function(topic, obj, funcName) {
        var topicName = topic;
        var numbWidgets;
        var topic = this.getTopic(topic);
        topic.subscribe(obj, funcName);

        if (dojo.event.topic.getTopic(topicName).sendMessage$joinpoint != undefined)
        {
            numbWidgets = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after.length;
            if (numbWidgets >= 2)
            {
                var typeObject = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[numbWidgets - 1][0];
                for (i = 0; i <= numbWidgets - 2; i++)
                {
                    if (dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[i][0] == typeObject)
                    {
                        var _joinPoint = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[i][1];
                        _joinPoint = _joinPoint + "$joinpoint";
                        window[_joinPoint] = null;
                        dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after.splice(i, 1);
                        break;
                    }
                }
            }
        }
    };

    //overide dojo function
    dojo.widget.buildWidgetFromParseTree = function(type, frag, parser, parentComp, insertionIndex, localProps) {
        var myWidgetId = "";
        dojo.a11y.setAccessibleMode();
        var stype = type.split(":");
        stype = (stype.length == 2) ? stype[1] : type;
        ///
        if (stype == "bindevent")
        {
            myWidgetId = frag.id;
            for (var i = 0; i < dojo.widget.manager.widgets.length; i++)
            {
                if (myWidgetId == dojo.widget.manager.widgets[i].widgetId)
                {
                    dojo.widget.manager.widgets.splice(i, 1);
                    break;
                }
            }
        }
        else
        {
            if (frag[frag["ns"] + ":" + stype].id != undefined)
            {
                myWidgetId = frag[frag["ns"] + ":" + stype].id[0].value;
                if (dojo.widget.manager.getWidgetById(myWidgetId) != undefined)
                {
                    myWidgetId = frag[frag["ns"] + ":" + stype].id[0].value;
                    var wg = dojo.widget.manager.getWidgetById(myWidgetId);
                    if (wg != undefined)
                    {
                        if (wg.domNode.onclick$joinpoint != undefined)
                        {
                            var _joinPoint = wg.domNode.onclick$joinpoint.after[0][1];
                            _joinPoint = _joinPoint + "$joinpoint";
                            window[_joinPoint] = null;
                        }
                        dojo.widget.manager.removeById(myWidgetId);
                    }
                }
            }
        }
        ///
        var localProperties = localProps || parser.parseProperties(frag[frag["ns"] + ":" + stype]);
        var twidget = dojo.widget.manager.getImplementation(stype, null, null, frag["ns"]);
        if (!twidget) {
            throw new Error("cannot find \"" + type + "\" widget");
        } else {
            if (!twidget.create) {
                throw new Error("\"" + type + "\" widget object has no \"create\" method and does not appear to implement *Widget");
            }
        }
        localProperties["dojoinsertionindex"] = insertionIndex;
        var ret = twidget.create(localProperties, frag, parentComp, frag["ns"]);
        return ret;
    };
    // Datbt edit DateTimePicker

    dojo.widget.DropdownContainer.prototype.templateString = "<span style=\"white-space:nowrap\"><input type=\"hidden\"  name=\"\" value=\"\" dojoAttachPoint=\"valueNode\" /><input name=\"\" type=\"text\" onkeypress=\"onDataPickerKeyPress(event)\" value=\"\" style=\"vertical-align:middle;\" dojoAttachPoint=\"inputNode\" autocomplete=\"off\" /> <img src=\"${this.iconURL}\" alt=\"${this.iconAlt}\" dojoAttachEvent=\"onclick:onIconClick\" dojoAttachPoint=\"buttonNode\" style=\"vertical-align:middle; cursor:pointer; cursor:hand\" /></span>";
    dojo.widget.DropdownDatePicker.prototype.onInputChange = function() {
        var input = dojo.string.trim(this.inputNode.value);
        if (input) {
            var inputDate = dojo.date.parse(input, {
                formatLength: this.formatLength,
                datePattern: this.displayFormat,
                selector: "dateOnly",
                locale: this.lang
            });
            if (!this.datePicker._isDisabledDate(inputDate)) {
                this.setDate(inputDate);
            }
        } else {
            if (input == "") {
                //this.datePicker.setDate("");
            }
            this.valueNode.value = input;
        }
        if (input) {
            this._updateText();
        }
    }
    onDataPickerKeyPress = function(evt)
    {        
        var event = (window.event) ? window.event : evt;
        if (event.keyCode == 40 || event.keyCode == 38)
        {
            var wg = dojo.widget.getWidgetById(event.target.parentNode.id);
            if (!wg.popup.isShowingNow) {
                wg.popup.open(wg.inputNode, wg, wg.buttonNode);
            } else {
                wg.popup.close();
            }
        }
    }
    // end edit DateTimePicker


    // edit Bind
    struts.widget.Bind.prototype.reloadContents = function(evt) {
        dialog.showLoadingDialog();
        if (!dojo.string.isBlank(this.handler)) {
            //use custom handler
            this.log("Invoking handler: " + this.handler);
            window[this.handler](this, this.domNode);
        }
        else {
            try {
                var self = this;
                var request = {
                    cancel: false
                };
                this.notify(this.widgetId, "before", request);
                if (request.cancel) {
                    this.log("Request canceled");
                    return;
                }

                //if the href is null, we still publish the notify topics
                if (dojo.string.isBlank(this.href)) {
                    return;
                }

                //if there is a parent form, and it has a "onsubmit"
                //execute it, validation is usually there, except is validation == true
                //on which case it is ajax validation, instead of client side
                if (!this.validate && this.formNode && this.formNode.onsubmit != null) {
                    var makeRequest = this.formNode.onsubmit.call(evt);
                    if (makeRequest != null && !makeRequest) {
                        this.log("Request canceled by 'onsubmit' of the form");
                        return;
                    }
                }

                //show indicator
                //dojo.html.show(this.indicator);
                if (this.showLoading) {
                    this.setContent(this.loadingText);
                }

                var tmpHref = this.href;
                tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + "struts.enableJSONValidation=true";
                if (!this.ajaxAfterValidation && this.validate) {
                    tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + "struts.validateOnly=true";
                }

                if (dojo.dom.isTag(this.domNode, "INPUT", "input")
                        && this.events == "onclick"
                        && this.domNode.type == "submit"
                        && !dojo.string.isBlank(this.domNode.name)
                        && !dojo.string.isBlank(this.domNode.value)) {
                    var enc = /utf/i.test("") ? encodeURIComponent : dojo.string.encodeAscii
                    tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + enc(this.domNode.name) + "=" + enc(this.domNode.value);
                }

                dojo.io.bind({
                    url: tmpHref,
                    useCache: false,
                    preventCache: true,
                    formNode: self.formNode,
                    formFilter: window[self.formFilter],
                    transport: self.transport,
                    handler: function(type, data, e) {
                        dojo.lang.hitch(self, "bindHandler")(type, data, e);
                    },
                    mimetype: "text/html"
                });
            }
            catch (ex) {
                dialog.closeLoadingDialog();
                if (this.showError) {
                    var message = dojo.string.isBlank(this.errorText) ? ex : this.errorText;
                    this.setContent(message);
                }
            }
        }
    }
    struts.widget.Bind.prototype.bindHandler = function(type, data, e) {
        dojo.html.hide(this.indicator);
        dialog.closeLoadingDialog();

        //publish topics
        this.notify(data, type, e);

        if (type == "load") {
            if (this.validate) {
                StrutsUtils.clearValidationErrors(this.formNode);
                //validation is active for this action
                var errors = StrutsUtils.getValidationErrors(data);
                if (errors && errors.fieldErrors) {
                    //validation failed
                    StrutsUtils.showValidationErrors(this.formNode, errors);
                    return;
                } else {
                    //validation passed
                    if (!this.ajaxAfterValidation && this.formNode) {
                        //regular submit
                        this.formNode.submit();
                        return;
                    }
                }
            }

            // no validation or validation passed
            if (this.executeScripts) {
                //parse text to extract content and javascript
                var parsed = this.parse(data);
                if (this.targets != null)
                {
                    clearWidgetByArea(this.targets);
                }
                //update targets content
                this.setContent(parsed.text);

                //execute scripts
                this._executeScripts(parsed.scripts);
            }
            else {
                if (this.targets != null)
                {
                    clearWidgetByArea(this.targets);
                }
                this.setContent(data);
            }
            // Datbt
            arrElementToFocus = new Array();
            var arrAllElement = document.getElementsByTagName('*');
            if (arrAllElement != undefined)
            {
                for (var index in arrAllElement)
                {
                    var _element = arrAllElement[index];
                    if (_element != undefined)
                    {
                        if (_element.tagName != undefined)
                        {
                            if ((_element.tagName == "INPUT") || (_element.tagName == "SELECT") || (_element.tagName == "TEXTAREA"))
                            {
                                if ((_element.clientHeight != 0) && (_element.clientWidth != 0))
                                {
                                    arrElementToFocus.push(_element);
                                }
                            }
                        }
                    }
                }
            }
            // datbt end

            this.highlight();
        } else {
            if (this.showError) {
                var message = dojo.string.isBlank(this.errorText) ? e.message : this.errorText;
                this.setContent(message);
            }
        }
    }
    // end edit Bind

    // edit ComboBox

    struts.widget.ComboBox.prototype.showResultList = function() {
        // Our dear friend IE doesnt take max-height so we need to calculate that on our own every time
        var childs = this.optionsListNode.childNodes;
        if (childs.length) {

            if (this.itemHeight === 0 || dojo.string.isBlank(this.textInputNode.value)) {
                this.optionsListNode.style.height = this.dropdownHeight + "px";
                this.optionsListNode.style.display = "";
                this.itemHeight = dojo.html.getMarginBox(childs[0]).height;
            }

            //if there is extra space, adjust height
            var totalHeight = this.itemHeight * childs.length;
            if (totalHeight < this.dropdownHeight) {
                this.optionsListNode.style.height = totalHeight + 2 + "px";
            } else {
                this.optionsListNode.style.height = this.dropdownHeight + "px";
            }
            // Datbt
            var span = document.createElement("span");
            dojo.html.addClass(span, "dojoComboBoxOptions");
            span.innerHTML = this.optionsListNode.childNodes[0].innerHTML;
            document.body.appendChild(span); // adding it to the DOM
            // chieu rong toi da cua popup
            var _maxPopUpWidth = 600;
            // so phan tu toi da cua list se duyet do dai
            var _maxListLength = 30;
            // xet xem co scrollBar ko; neu co thi phai cong them do dai
            var _scrollBarWidth = (childs.length > this.maxListLength) ? 18 : 2;
            var _maxTextWidth = span.offsetWidth + _scrollBarWidth;
            document.body.removeChild(span);
            var _listLength = (_maxListLength > childs.length) ? childs.length : _maxListLength;
            for (var k = 0; k < _listLength; k++)
            {
                var span = document.createElement("span");
                dojo.html.addClass(span, "dojoComboBoxOptions");
                span.innerHTML = this.optionsListNode.childNodes[k].innerHTML;

                document.body.appendChild(span);
                if (_maxTextWidth < span.offsetWidth + _scrollBarWidth)
                {
                    _maxTextWidth = span.offsetWidth + _scrollBarWidth;
                }
                document.body.removeChild(span);
            }
            _maxTextWidth = _maxTextWidth;

            var _popUpWidth = (_maxTextWidth > dojo.html.getMarginBox(this.domNode).width - 2) ? _maxTextWidth : dojo.html.getMarginBox(this.domNode).width - 2;
            _popUpWidth = (_popUpWidth < _maxPopUpWidth) ? _popUpWidth : _maxPopUpWidth;
            this.optionsListNode.style.width = _popUpWidth + "px";
            //this.optionsListNode.style.width = this.dropdownWidth === 0 ? (dojo.html.getMarginBox(this.domNode).width-2)+"px" : this.dropdownWidth + "px";
            // Datbt end

            this.popupWidget.open(this.domNode, this, this.downArrowNode);
        } else {
            this._hideResultList();
        }
    }

    struts.widget.ComboBoxDataProvider = function(combobox, node) {
        this.data = [];
        this.searchLimit = combobox.searchLimit;
        this.searchType = "STARTSTRING"; // may also be "STARTWORD" or "SUBSTRING"
        this.caseSensitive = false;
        // for caching optimizations
        this._lastSearch = "";
        this._lastSearchResults = null;

        this.firstRequest = true;

        this.cbox = combobox;
        this.formId = this.cbox.formId;
        this.formFilter = this.cbox.formFilter;
        this.transport = this.cbox.transport;

        this.getData = function(/*String*/url) {
            //show indicator
            dojo.html.show(this.cbox.indicator);

            dojo.io.bind({
                url: url,
                formNode: dojo.byId(this.formId),
                formFilter: window[this.formFilter],
                transport: this.transport,
                handler: dojo.lang.hitch(this, function(type, data, evt) {
                    //hide indicator
                    dojo.html.hide(this.cbox.indicator);

                    //if notifyTopics is published on the first request (onload)
                    //the value of listeners will be reset
                    if (!this.firstRequest || type == "error") {
                        this.cbox.notify.apply(this.cbox, [data, type, evt]);
                    }

                    this.firstRequest = false;
                    var arrData = null;
                    var dataByName = data[dojo.string.isBlank(this.cbox.dataFieldName) ? this.cbox.name : this.cbox.dataFieldName];
                    if (!dojo.lang.isArray(data)) {
                        //if there is a dataFieldName, take it
                        if (dataByName) {
                            if (dojo.lang.isArray(dataByName)) {
                                //ok, it is an array
                                arrData = dataByName;
                            } else if (dojo.lang.isObject(dataByName)) {
                                //it is an object, treat it like a map
                                arrData = [];
                                for (var key in dataByName) {
                                    //arrData.push([key, dataByName[key]]);
                                    //Datbt
                                    arrData.push([dataByName[key], key]);
                                }
                            }
                        } else {
                            //try to find a match
                            var tmpArrData = [];
                            if (data.message == undefined)
                            {
                                for (var key in data) {
                                    //does it start with the field name? take it
                                    if (dojo.string.startsWith(key, this.cbox.name)) {
                                        arrData = data[key];
                                        break;
                                    } else {
                                        //if nathing else is found, we will use values in this
                                        //object as the data
                                        //tmpArrData.push([key, data[key]]);
                                        //Datbt
                                        tmpArrData.push([data[key], key]);
                                    }
                                    //grab the first array found, we will use it if nothing else
                                    //is found
                                    if (!arrData && dojo.lang.isArray(data[key]) && !dojo.lang.isString(data[key])) {
                                        arrData = data[key];
                                    }
                                }
                            }
                            if (!arrData) {
                                arrData = tmpArrData;
                            }
                        }

                        data = arrData;
                    }
                    this.setData(data);
                }),
                mimetype: "text/json"
            });
        };

        this.startSearch = function(searchStr, callback) {
            // FIXME: need to add timeout handling here!!
            this._preformSearch(searchStr, callback);
        };

        this._preformSearch = function(/*String*/searchStr, callback) {
            //
            //  NOTE: this search is LINEAR, which means that it exhibits perhaps
            //  the worst possible speed characteristics of any search type. It's
            //  written this way to outline the responsibilities and interfaces for
            //  a search.
            //
            var st = this.searchType;
            // FIXME: this is just an example search, which means that we implement
            // only a linear search without any of the attendant (useful!) optimizations
            var ret = [];
            if (!this.caseSensitive) {
                searchStr = searchStr.toLowerCase();
            }
            for (var x = 0; x < this.data.length; x++) {
                if (!this.data[x] || !this.data[x][0]) {
                    //needed for IE
                    continue;
                }
                if ((this.searchLimit > 0) && (ret.length >= this.searchLimit)) {
                    break;
                }
                // FIXME: we should avoid copies if possible!
                var dataLabel = new String((!this.caseSensitive) ? this.data[x][0].toLowerCase() : this.data[x][0]);
                if (dataLabel.length < searchStr.length) {
                    // this won't ever be a good search, will it? What if we start
                    // to support regex search?
                    continue;
                }

                if (st == "STARTSTRING") {
                    if (searchStr == dataLabel.substr(0, searchStr.length)) {
                        ret.push(this.data[x]);
                    }
                } else if (st == "SUBSTRING") {
                    // this one is a gimmie
                    if (dataLabel.indexOf(searchStr) >= 0) {
                        ret.push(this.data[x]);
                    }
                } else if (st == "STARTWORD") {
                    // do a substring search and then attempt to determine if the
                    // preceeding char was the beginning of the string or a
                    // whitespace char.
                    var idx = dataLabel.indexOf(searchStr);
                    if (idx == 0) {
                        // implicit match
                        ret.push(this.data[x]);
                    }
                    if (idx <= 0) {
                        // if we didn't match or implicily matched, march onward
                        continue;
                    }
                    // otherwise, we have to go figure out if the match was at the
                    // start of a word...
                    // this code is taken almost directy from nWidgets
                    var matches = false;
                    while (idx != -1) {
                        // make sure the match either starts whole string, or
                        // follows a space, or follows some punctuation
                        if (" ,/(".indexOf(dataLabel.charAt(idx - 1)) != -1) {
                            // FIXME: what about tab chars?
                            matches = true;
                            break;
                        }
                        idx = dataLabel.indexOf(searchStr, idx + 1);
                    }
                    if (!matches) {
                        continue;
                    } else {
                        ret.push(this.data[x]);
                    }
                }
            }
            callback(ret);
        };

        this.addData = function(/*Array*/pairs) {
            // FIXME: incredibly naive and slow!
            this.data = this.data.concat(pairs);
        };

        this.setData = function(/*Array*/pdata) {
            // populate this.data and initialize lookup structures
            this.data = pdata;
            // Datbt
            if (this.data[0] != undefined)
            {
                //all ellements must be a key and value pair
                for (var i = 0; i < this.data.length; i++) {
                    var element = this.data[i];
                    if (!dojo.lang.isArray(element)) {
                        this.data[i] = [element, element];
                    }
                }
                if (this.cbox.getSelectedKey() != this.data[0][1])
                {
                    this.cbox.selectedResult = null;
                    this.cbox.comboBoxSelectionValue.value = "";
                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [null, null, null, this.cbox]);
                }
                if (this.cbox.domNode.childNodes[2].value.toUpperCase() == this.data[0][0] && this.cbox.getSelectedKey() != this.data[0][1] && !this.cbox._hasFocus)
                {
                    this.cbox.selectedResult = [this.data[0][0], this.data[0][1]];
                    this.cbox.comboBoxSelectionValue.value = this.data[0][0];
                    this.cbox.comboBoxValue.value = this.data[0][1];
                    this.cbox.domNode.childNodes[2].value = this.data[0][0];
                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [this.data[0][0], this.data[0][1], this.cbox.getText(), this.cbox]);
                }
            }
            else
            {
                if (this.cbox.getSelectedKey() == "")
                {
                    //                    this.data = new Array();
                    //                    this.cbox.selectedResult = null;
                    //                    this.cbox.comboBoxSelectionValue.value = "";
                    //                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [null, null, null, this.cbox]);
                }
                else
                {
                    if (this.cbox.domNode.childNodes[2].value.toUpperCase() == this.cbox.getSelectedValue())
                    {
                        this.cbox.domNode.childNodes[2].value = this.cbox.getSelectedValue();
                        this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [this.cbox.comboBoxSelectionValue.value, this.cbox.comboBoxSelectionValue.value, this.cbox.comboBoxSelectionValue.value, this.cbox]);
                    }
                }
            }
            // Datbt end
        };


        if (!dojo.string.isBlank(this.cbox.dataUrl) && this.cbox.preload) {
            this.getData(this.cbox.dataUrl);
        } else {
            // check to see if we can populate the list from <option> elements
            if ((node) && (node.nodeName.toLowerCase() == "select")) {
                // NOTE: we're not handling <optgroup> here yet
                var opts = node.getElementsByTagName("option");
                var ol = opts.length;
                var data = [];
                for (var x = 0; x < ol; x++) {
                    var text = opts[x].textContent || opts[x].innerText || opts[x].innerHTML;
                    var keyValArr = [String(text), String(opts[x].value)];
                    // Datbt
                    //var keyValArr = [ String(opts[x].value),String(text)];
                    data.push(keyValArr);
                    if (opts[x].selected) {
                        this.cbox.setAllValues(keyValArr[0], keyValArr[1]);
                    }
                }
                this.setData(data);
            }
        }
    }
    dojo.widget.ComboBox.prototype._focusOptionNode = function(node) {
        if (this._highlighted_option != node) {
            this._blurOptionNode();
            this._highlighted_option = node;
            dojo.html.addClass(this._highlighted_option, "dojoComboBoxItemHighlight");
        }
    }
    dojo.widget.ComboBox.prototype._handleKeyEvents = function(evt) {
        if (evt.ctrlKey || evt.altKey || !evt.key) {
            return;
        }
        this._prev_key_backspace = false;
        this._prev_key_esc = false;
        var k = dojo.event.browser.keys;
        var doSearch = true;
        switch (evt.key) {
            case k.KEY_DOWN_ARROW:
                //evt.preventDefault();
                if (!this.popupWidget.isShowingNow) {
                    this._startSearchFromInput();
                }
                this._highlightNextOption();
                dojo.event.browser.stopEvent(evt);
                return;
            case k.KEY_UP_ARROW:
                this._highlightPrevOption();
                dojo.event.browser.stopEvent(evt);
                return;
            case k.KEY_TAB:
                if (!this.autoComplete && this.popupWidget.isShowingNow && this._highlighted_option) {
                    dojo.event.browser.stopEvent(evt);
                    this._selectOption({
                        "target": this._highlighted_option,
                        "noHide": false
                    });
                    this._setSelectedRange(this.textInputNode, this.textInputNode.value.length, null);
                } else {
                    this._selectOption();
                    return;
                }
                break;
            case k.KEY_ENTER:
                if (mystopEvent)
                {
                    mystopEvent = false;
                    return;
                }
                if (this.popupWidget.isShowingNow) {
                    dojo.event.browser.stopEvent(evt);
                }
                if (this.autoComplete) {
                    this._selectOption();
                    //return;
                }
                // Datbt
                var bFocus = false;
                for (var i = 0; i < arrElementToFocus.length; i++)
                {
                    if (this.domNode.childNodes[2] == arrElementToFocus[i])
                    {
                        bFocus = true;
                        // neu khong phai la phan tu cuoi cung
                        if (i < arrElementToFocus.length - 1)
                        {
                            var bFound = false;
                            for (var j = i + 1; j < arrElementToFocus.length; j++)
                            {
                                var _element = arrElementToFocus[j];
                                if (!_element.disabled)
                                {
                                    bFound = true;
                                    if (_element.getAttribute("dojoattachpoint") == "textInputNode")
                                    {
                                        mystopEvent = true;
                                    }
                                    _element.focus();
                                    return;
                                }
                            }
                            if (!bFound)
                            {
                                for (var j = 0; j < arrElementToFocus.length; j++)
                                {
                                    var _element = arrElementToFocus[j];
                                    if (!_element.disabled)
                                    {
                                        if (_element.getAttribute("dojoattachpoint") == "textInputNode")
                                        {
                                            mystopEvent = true;
                                        }
                                        _element.focus();
                                        return;
                                    }
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                        // neu la phan tu cuoi cung
                        else
                        {
                            for (var j = 0; j < arrElementToFocus.length; j++)
                            {
                                var _element = arrElementToFocus[j];
                                if (!_element.disabled)
                                {
                                    if (_element.getAttribute("dojoattachpoint") == "textInputNode")
                                    {
                                        mystopEvent = true;
                                    }
                                    _element.focus();
                                    return;
                                }
                            }
                        }
                    }
                }
                if (!bFocus)
                {
                    for (var j = 0; j < arrElementToFocus.length; j++)
                    {
                        var _element = arrElementToFocus[j];
                        if (!_element.disabled)
                        {
                            if (_element.getAttribute("dojoattachpoint") == "textInputNode")
                            {
                                mystopEvent = true;
                            }
                            _element.focus();
                            return;
                        }
                    }
                }
                break;
                // Datbt end
            case " ":
                if (this.popupWidget.isShowingNow && this._highlighted_option) {
                    dojo.event.browser.stopEvent(evt);
                    this._selectOption();
                    this._hideResultList();
                    return;
                }
                break;
            case k.KEY_ESCAPE:
                this._hideResultList();
                this._prev_key_esc = true;
                return;
            case k.KEY_BACKSPACE:
                this._prev_key_backspace = true;
                if (!this.textInputNode.value.length) {
                    this.setAllValues("", "");
                    this._hideResultList();
                    doSearch = false;
                }
                break;
                // Datbt
            case k.KEY_DELETE:
                this._prev_key_backspace = true;
                if (!this.textInputNode.value.length) {
                    this.setAllValues("", "");
                    this._hideResultList();
                    doSearch = false;
                }
                break;
                // Datbt end
            case k.KEY_RIGHT_ARROW:
            case k.KEY_LEFT_ARROW:
                doSearch = false;
                break;
            default:
                if (evt.charCode == 0) {
                    doSearch = false;
                }
        }
        if (this.searchTimer) {
            clearTimeout(this.searchTimer);
        }
        if (doSearch) {
            this._blurOptionNode();
            this.searchTimer = setTimeout(dojo.lang.hitch(this, this._startSearchFromInput), this.searchDelay);
        }
    }

    // Datbt
    // khi xu ly phim enter tren comboBox phai them ham nay de dispath event change value
    dojo.widget.ComboBox.prototype._selectOption = function(evt) {
        var tgt = null;
        if (!evt) {
            evt = {
                target: this._highlighted_option
            };
        }
        if (!dojo.html.isDescendantOf(evt.target, this.optionsListNode)) {
            if (!this.textInputNode.value.length) {
                return;
            }
            tgt = dojo.html.firstElement(this.optionsListNode);
            // Datbt
            // neu input.value != cac gia tri autocompleter thi chon gia tri dau tien
            //            if (!tgt || !this._isInputEqualToResult(tgt.getAttribute("resultName"))) {
            if (!tgt) {
                return;
            }
        } else {
            tgt = evt.target;
        }
        while ((tgt.nodeType != 1) || (!tgt.getAttribute("resultName"))) {
            tgt = tgt.parentNode;
            if (tgt === dojo.body()) {
                return false;
            }
        }
        this.textInputNode.value = "";
        this.selectedResult = [tgt.getAttribute("resultName"), tgt.getAttribute("resultValue")];
        this.setAllValues(tgt.getAttribute("resultName"), tgt.getAttribute("resultValue"));
        if (!evt.noHide) {
            this._hideResultList();
            this._setSelectedRange(this.textInputNode, 0, null);
        }
        this._tryFocus();
    }
    dojo.widget.ComboBox.prototype.buttonSrc = null;
    dojo.widget.ComboBox.prototype.templateString = "<span _=\"whitespace and CR's between tags adds &nbsp; in FF\"\n\tclass=\"dojoComboBoxOuter\"\n\t><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n\t\tdojoAttachPoint=\"comboBoxValue\"\n\t><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n\t\tdojoAttachPoint=\"comboBoxSelectionValue\"\n\t><input type=\"text\"  autocomplete=\"off\" class=\"dojoComboBox\"\n\t\tdojoAttachEvent=\"key:_handleKeyEvents; keyUp: onKeyUp; compositionEnd; blur: onBlur; onResize;\"\n\t\tdojoAttachPoint=\"textInputNode\"\n\t><img hspace=\"0\"\n\t\tvspace=\"0\"\n\t\tclass=\"dojoComboBox\"\n\t\tdojoAttachPoint=\"downArrowNode\"\n\t\tdojoAttachEvent=\"onMouseUp: handleArrowClick; onResize;\"\n\t\tsrc=\"${this.buttonSrc}\"\n></span>\n";
    // Datbt
    dojo.widget.ComboBox.prototype.onBlur = function()
    {
        if (this.dataProvider.data[0] != undefined)
        {
            if (this.getSelectedKey() != this.dataProvider.data[0][1])
            {
                if (this.dataProvider.data[0][0].toUpperCase() == this.domNode.childNodes[2].value.toUpperCase())
                {
                    this.selectedResult = [this.dataProvider.data[0][0], this.dataProvider.data[0][1]];
                    this.domNode.childNodes[2].value = this.dataProvider.data[0][0];
                    this.comboBoxValue.value = this.dataProvider.data[0][0];
                    this.comboBoxSelectionValue.value = this.dataProvider.data[0][1];
                    this.notifyTo(this.valueNotifyTopicsArray, [this.dataProvider.data[0][0], this.dataProvider.data[0][1], this.domNode.childNodes[2].value, this]);
                }
            }
        }
    }
    // Datbt end
    dojo.widget.ComboBox.prototype.onResize = function() {
        var inputSize = dojo.html.getContentBox(this.textInputNode);
        if (inputSize.height <= 0) {
            dojo.lang.setTimeout(this, "onResize", 100);
            return;
        }
        var buttonSize = {
            width: inputSize.height,
            height: inputSize.height
        };
        //dojo.html.setContentBox(this.downArrowNode, buttonSize);
    }

    // end edit ComboBox



}