package com.viettel.client.form;

/**
 * @author tiennv_telsoft
 */
public class ModuleInfoForm {

    private String code;
    private String name;
    private String strStatus;

    public ModuleInfoForm() {
        code = "";
        name = "";
        strStatus = "";
    }

    public ModuleInfoForm(String code, String name, String strStatus) {
        this.code = code;
        this.name = name;
        this.strStatus = strStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

}
