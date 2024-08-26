/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nh.soap.handle;

import com.nh.GlobalConfig;
import com.nh.backend.bean.SqlExcutor;
import com.nh.backend.bean.UssdKpi;
import com.nh.backend.database.ConnectionPoolManager;
import com.nh.backend.database.DatabaseConnectionPool;
import com.nh.backend.process.CachingService;
import com.nh.backend.process.ActionLogDao;
import com.nh.soap.SoapRequest;
import com.nh.util.CallWS;
import com.nh.util.CommonUtil;
import com.nh.util.CountryCode;
import com.nh.util.MyLog;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author hac
 */
public class SqlViewHandle  extends AbstractHandler {
    private static final String CMD_SQLX = "@SQLX";
    private static final String MODULE_CODE = "LUCKY";
    private static final String MODULE_APIPORTAL = "LUCKY";
    
    public SqlViewHandle(SoapRequest request) {
        super(request);
    }
    
    @Override
    public String process(SoapRequest request) {
        String result = "1";
        String sqlReturn = "";
        String xml = request.getRawXml();
        String msisdn = StringUtils.substringBetween(xml, "<msisdn>", "</msisdn>"); 
        String transId = StringUtils.substringBetween(xml, "<requestid>", "</requestid>");  
        String sqlCode = StringUtils.substringBetween(xml, "<sql_code>", "</sql_code>"); 
        if (msisdn == null || transId == null || sqlCode == null) {
            MyLog.Error("invalid mandatory params msisdn:" + msisdn + " ,requestid:" + transId + " ,sql_code:" + sqlCode);
            return "1";
        }
        msisdn = CountryCode.formatMobile(msisdn);
        if (!CountryCode.isViettelNumber(msisdn)) {
            MyLog.Error("410|wrong msisdn:  " + msisdn);
            return "410|wrong msisdn:  " + msisdn;
        }
        MyLog.Infor("sqlx start:" + sqlCode + " ,transId:" + transId);

        long start = System.currentTimeMillis();
        long end = -1l;
        long duration = -1l;

        UssdKpi ussdKpi = new UssdKpi(transId, msisdn);
        ussdKpi.setChannel(UssdKpi.CHANNEL_DATABASE);
        ussdKpi.setErrorCode(UssdKpi.ERROR_CODE_FAIL);
        ussdKpi.setRequestTime(new Timestamp(start));

        ussdKpi.setStateId(0);
        ussdKpi.setSteps(MODULE_APIPORTAL);
//        boolean isCloseConnection = true;
        Connection con = null;
        try {
            CachingService cachingeService = new CachingService();
            SqlExcutor sqlExcutor = cachingeService.getSqlExcutor(sqlCode);
            if (sqlExcutor == null) {
                MyLog.Error("SqlExcutor not found code:" + sqlCode + " ,transId:" + transId);
                return result;
            }

//            Database database = cachingeService.getDatabase(sqlExcutor.getDatabaseCode());
//            if (database == null) {
//                MyLog.Error("SqlExcutor not found code:" + secondContent + " ,transId:" + transId);
//                return;
//            }
//            isCloseConnection = database.getReadOnly() != Database.MODE_READONLY;
            ussdKpi.setModuleCode(MODULE_CODE);

            ussdKpi.setAction(sqlCode);
            ussdKpi.setUrl(MODULE_CODE);

            String sql = sqlExcutor.getSql();
            sql = sql.replace(CallWS.WS_MSISDN,  CountryCode.getCountryCode() + msisdn);
            sql = sql.replace(CallWS.WS_ISDN, msisdn);
            sql = sql.replace(CallWS.WS_TRANS_ID, transId);

            sql = parseAPI(sql, request.getDatas());

            ussdKpi.setBody(sql);

            con = DatabaseConnectionPool.getInstance().getConnection().getDatabaseConnection();
            if (con == null) {
                MyLog.Error("sqlx can not get connection transId: " + transId);
                return "1";
            }
            MyLog.Infor("get dbconnection sqlx : transId:"
                    + transId + ", in(ms): " + (System.currentTimeMillis() - start));

            sqlReturn = excuterSql(sql, con, transId, sqlCode);
            
            if (sqlReturn != null) {
                ussdKpi.setErrorCode(UssdKpi.ERROR_CODE_OK);
                result = "0";
                ussdKpi.setResponse(sqlReturn);
            }

        } catch (Exception ex) {
            MyLog.Error("error for: " + transId, ex);
            String exep = CommonUtil.getError(ex);
            ussdKpi.setResponse(exep);
            ussdKpi.setErrorCode(UssdKpi.ERROR_CODE_ERROR);
        } finally {
//            if (isCloseConnection) {
//                ConnectionPoolManager.closeQuite(null, null, con);
//            }
        }

        end = System.currentTimeMillis();
        duration = end - start;
        ussdKpi.setResponseTime(new Timestamp(System.currentTimeMillis()));
        ussdKpi.setDuration(duration);

        ActionLogDao.getInstance().enqueue(ussdKpi);
        MyLog.Infor("sqlx end: response: " + ussdKpi.getErrorCode() + " ,transId:"
                + transId + ", duration(ms): " + (duration) +" ,result: " + result);

        return getRespone(result, sqlReturn);
    }
    
    
    private String excuterSql(String sql, Connection connection,
            String transId, String sqlCode) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        String tmsql = sql;
        long start = System.currentTimeMillis();
        MyLog.Infor("excuterSql :" + sql + " ,transId:" + transId);
        String value = null;
        String sqlType = tmsql.toUpperCase().trim();

        if (sqlType.startsWith("PR")) {
            tmsql = "BEGIN " + tmsql + " ; END;";
            CallableStatement sta = connection.prepareCall(tmsql);
            s = sta;
            sta.setQueryTimeout(30);//30 seconds
            sta.registerOutParameter(1, java.sql.Types.VARCHAR);
            sta.execute();
            value = sta.getString(1);
            ConnectionPoolManager.closeQuite(null, s, null);
        } else if (sqlType.startsWith("SELECT")) {
            s = connection.createStatement();
            s.setQueryTimeout(30);//30 seconds
            rs = s.executeQuery(tmsql);

            ResultSetMetaData rsMeta = rs.getMetaData();
            int metaLength = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<>();

            for (int i = 1; i <= metaLength; i++) {
                String metaColumn = rsMeta.getColumnName(i);
                columnNames.add(metaColumn);
            }

            StringBuilder sb = new StringBuilder("");
            while (rs.next()) {
                sb.append("\n<").append(sqlCode.toLowerCase()).append(">");
                for (String columnName : columnNames) {
                    sb.append("\n<").append(columnName.toLowerCase()).append(">");
                    Object object = rs.getObject(columnName);
                    if (object != null) {
                        String tm = String.valueOf(object);
                        sb.append(getEscape(tm));
                    }
                    sb.append("</").append(columnName.toLowerCase()).append(">");
                }
                sb.append("</").append(sqlCode.toLowerCase()).append(">");
            }

            value = sb.toString();
            ConnectionPoolManager.closeQuite(rs, s, null);
        } else if (sqlType.startsWith("FN")) {
            tmsql = "SELECT " + tmsql + " from dual";
            s = connection.createStatement();
            s.setQueryTimeout(30);//30 seconds
            rs = s.executeQuery(tmsql);
            if (rs.next()) {
                value = rs.getString(1);
            }
            ConnectionPoolManager.closeQuite(rs, s, null);
        } else if (sqlType.startsWith("UPDATE")
                || sqlType.startsWith("INSERT")) {
            s = connection.createStatement();
            s.setQueryTimeout(30);//30 seconds
            int i = s.executeUpdate(tmsql);
            value = String.valueOf(i);
            ConnectionPoolManager.closeQuite(null, s, null);
        } else {
            MyLog.Error("sql type not support:" + tmsql);
            throw new SQLFeatureNotSupportedException("sql type not support:" + tmsql);
        }
        MyLog.Infor("excuterSql end in(ms):" + (System.currentTimeMillis() - start) + ", =>" + sql + " ,transId:" + transId+ " ,value:" + value);
        return value;
    }
    
    private String getEscape(String value) {
        String escape = GlobalConfig.getSql_escape() ;
        if ("xml".equalsIgnoreCase(escape)) {
             return StringEscapeUtils.escapeXml(value);
        }
        return value;
    }
    
    private String parseAPI(String text, Map<String, String> replace) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String result = text;
        for (Map.Entry<String, String> entry : replace.entrySet()) {
            String k = entry.getKey();
            String val = entry.getValue();
            if (k == null || val == null) {
                continue;
            }
            result = result.replace(CallWS.CMD_START_CONTEXT + k.toUpperCase(), val);
            result = result.replace(CallWS.CMD_START_CONTEXT + k, val);
        }

        return result;
    }
    
    
    private static final String RESPONSE_XML_RETURN = "<?xml version=\"1.0\"?>" + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" >" + "<S:Body>"
            + "<sqlResponse xmlns:ns=\"http://service.viettel.com\">"
            + "<return>#RETURN</return>"
            + "<sqlReturn>#SQL_RETURN</sqlReturn>"
            + "</sqlResponse>"
            + "</S:Body>" + "</S:Envelope>" ;

    protected String getRespone(String result, String sqlReturn) {
        String resp = RESPONSE_XML_RETURN;
        resp = resp.replace("#RETURN", result);
        resp = resp.replace("#SQL_RETURN", sqlReturn == null ? "" : sqlReturn);
        return resp;
    }
    
}
