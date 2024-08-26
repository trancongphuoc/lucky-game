/**
 *
 * handsome boy
 */

package com.nh.soap;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Jun 11, 2015
 * @mail hadc@viettel.com.vn
 */
public interface HttpHandler {
    public final static String METHOD_POST = "POST";
    public final static String METHOD_GET = "GET";
   
    public void process(SoapRequest request);
    
}
