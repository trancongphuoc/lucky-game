/**
 *
 * handsome boy
 */

package com.nh.soap.handle;

import com.nh.soap.SoapRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author hadc <Apllication Development Department - Viettel Global>
 * @since Jun 11, 2015
 * @mail hadc@viettel.com.vn
 */
public abstract  class AbstractHandler{
    protected final static Logger log = Logger.getLogger(AbstractHandler.class);
    protected SoapRequest request = null;
     
    public AbstractHandler(SoapRequest request) {
        this.request = request;
    }
 
    /**
     * @param request
     * @return DATA=data&SIG=sig
     * when error
     */
    public abstract String process(SoapRequest request);
    
}
