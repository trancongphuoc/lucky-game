/**
 *
 * handsome boy
 */

package com.nh.soap;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Jun 11, 2015
 * @mail hadc@viettel.com.vn
 */
public interface HttpSender {
    
    void sendResponse(String status, String mime, Properties header, String data);
    void sendResponse(String status, String mime, Properties header, InputStream data);
    void sendError(String status, String msg);
    void sendResponse(String msg);
    boolean isClose();
}
