/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vas.security;

import com.viettel.database.utils.DataStore;
import com.viettel.utils.Constant;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 */
public class CheckSelectBox {

    private int[] arrStatus = {0, 1};
    private int[] arrRequired = {0, 1};
    private int[] arrRequiredRegisted = {0, 1};
    private int[] arrType = {1, 2, 3, 4, 5};
    private int[] arrMessageChannel = {0, 1, 2, 3};
    private int[] arrTypeCommand = {4, 1, 2, 3};
    private int[] arrTypeSMS = {4, 1, 2, 3, 6,8,9,10};
    private int[] arrSubscription = {0, 1};
    private int[] arrPaymentFixTime = {0, 1};
    private int[] arrSubscriptionType = {1, 2, 3, 4, 5, 6,7};
    private int[] arrPaymentDay = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};

    public boolean checkStatus(int choose) {
        for (int i = 0; i < arrStatus.length; i++) {
            if (arrStatus[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUserType(int choose) {
        for (int i = 0; i < arrType.length; i++) {
            if (arrType[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRequired(int choose) {
        for (int i = 0; i < arrRequired.length; i++) {
            if (arrRequired[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRequiredRegisted(int choose) {
        for (int i = 0; i < arrRequiredRegisted.length; i++) {
            if (arrRequiredRegisted[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMessageChannel(int choose) {
        for (int i = 0; i < arrMessageChannel.length; i++) {
            if (arrMessageChannel[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTypeCommand(int choose) {
        for (int i = 0; i < arrTypeCommand.length; i++) {
            if (arrTypeCommand[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTypeSms(int choose) {
        for (int i = 0; i < arrTypeSMS.length; i++) {
            if (arrTypeSMS[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSubscription(int choose) {
        for (int i = 0; i < arrTypeSMS.length; i++) {
            if (arrTypeSMS[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPaymentFixTime(int choose) {
        for (int i = 0; i < arrPaymentFixTime.length; i++) {
            if (arrPaymentFixTime[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSubscriptionType(int choose) {
        for (int i = 0; i < arrSubscriptionType.length; i++) {
            if (arrSubscriptionType[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPaymentDay(int choose) {
        for (int i = 0; i < arrPaymentDay.length; i++) {
            if (arrPaymentDay[i] == choose) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAccount(String choose) {
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        boolean status = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            if (conn == null) {
                return false;
            }
            String sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".users where user_type=4 and username = ? order by username";
            calStat = conn.prepareCall(sqlq);
            calStat.setString(1, choose);
            rs = calStat.executeQuery();
            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }

            if (rs != null) {
                rs.close();
            }
            if (calStat != null) {
                calStat.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception ex) {Constant.LogNo(ex);
            status = false;
        }
        return status;
    }

    public boolean checkService(String serviceName) {
        boolean status = false;
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            if (conn == null) {
                return false;
            }
            String sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".services where service_name = ? order by service_name";
            calStat = conn.prepareCall(sqlq);
            calStat.setString(1, serviceName);
            rs = calStat.executeQuery();

            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (calStat != null) {
                    calStat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                status = false;
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            status = false;
        }
        return status;
    }

    public boolean checkSubService(String subService) {
        boolean status = false;
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            if (conn == null) {
                return false;
            }
            String sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".sub_services where sub_service_name = ? order by sub_service_name";
            calStat = conn.prepareCall(sqlq);
            calStat.setString(1, subService);
            rs = calStat.executeQuery();

            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (calStat != null) {
                    calStat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                status = false;
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            status = false;
        }
        return status;
    }

    public boolean checkProvider(String provider) {
        boolean status = false;
        Connection conn = null;
        CallableStatement calStat = null;
        ResultSet rs = null;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DataStore.getConnection();
            }
            String sqlq = "SELECT * FROM " + Constant.SCHEMA_NAME + ".providers where provider_name=? order by provider_name";
            calStat = conn.prepareCall(sqlq);
            calStat.setString(1, provider);
            rs = calStat.executeQuery();

            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }

            try {
                if (rs != null) {
                    rs.close();
                }
                if (calStat != null) {
                    calStat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {Constant.LogNo(ex);
                status = false;
            }
        } catch (Exception ex) {Constant.LogNo(ex);
            status = false;
        }
        return status;
    }


}
