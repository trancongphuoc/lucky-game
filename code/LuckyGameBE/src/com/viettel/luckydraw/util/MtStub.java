/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;
import utils.Hex;
import utils.Protocol;
/**
 *
 * @author HALT14
 */
public class MtStub {
    protected Protocol protocol;
  private Object lock = new Object();
  private Logger logger = Logger.getLogger(MtStub.class);
  private HttpClient httpclient;
  private BASE64Encoder encoder;
  private String xmlns;
  private String username;
  private String password;
  
  public MtStub(String url, String xmlns, String username, String password)
  {
    this.protocol = new Protocol(url);
    this.xmlns = xmlns;
    this.username = username;
    this.password = password;
    this.encoder = new BASE64Encoder();
    instance();
  }
  
  public void close() {}
  
  public  void instance()
  {
    this.httpclient = new HttpClient();
    if (this.httpclient != null)
    {
      HttpConnectionManager conMgr = this.httpclient.getHttpConnectionManager();
      HttpConnectionManagerParams conPars = conMgr.getParams();
      conPars.setConnectionTimeout(20000);
      conPars.setSoTimeout(90000);
    }
  }
  
  public void setHttpclientTimeout(int connectionTimeout, int soTimeout)
  {
    if (this.httpclient != null)
    {
      HttpConnectionManager conMgr = this.httpclient.getHttpConnectionManager();
      HttpConnectionManagerParams conPars = conMgr.getParams();
      conPars.setConnectionTimeout(connectionTimeout);
      conPars.setSoTimeout(soTimeout);
    }
  }
  
  public void reload(String url, String xmlns, String username, String password)
  {
    if ((!this.protocol.getUrl().equals(url)) || (!this.xmlns.equals(xmlns)) || (!this.username.equals(username)) || (!this.password.equals(password)))
    {
      this.protocol.setUrl(url);
      this.xmlns = xmlns;
      this.username = username;
      this.password = password;
      close();
      instance();
    }
  }
  
  private int sendMT(String sessionId, String serviceId, String sender, String receiver, String contentType, String content, String status)
  {
    synchronized (this.lock)
    {
      PostMethod post = new PostMethod(this.protocol.getUrl());
      String response = "";
      int error;
      try
      {
        String soapAction = this.xmlns + "receiverMO";
        String reqContent = 
          "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Header>    <AuthHeader xmlns=\"" + 
          

          this.xmlns + "\">" + 
          "      <Username>#USER_NAME#</Username>" + 
          "      <Password>#PASS_WORD#</Password>" + 
          "    </AuthHeader>" + 
          "  </soap:Header>" + 
          "  <soap:Body>" + 
          "    <sendMT xmlns=\"" + this.xmlns + "\">" + 
          "       <SessionId>" + sessionId + "</SessionId>" + 
          "       <ServiceId>" + serviceId + "</ServiceId>" + 
          "       <Sender>" + sender + "</Sender>" + 
          "       <Receiver>" + receiver + "</Receiver>" + 
          "       <ContentType>" + contentType + "</ContentType>" + 
          "       <Content>" + content + "</Content>" + 
          "       <Status>" + status + "</Status>" + 
          "    </sendMT>" + 
          "  </soap:Body>" + 
          "</soap:Envelope>";
        

        logger.info(receiver + "=>Rquest:" + reqContent);
        reqContent = reqContent.replace("#USER_NAME#", this.username);
        reqContent = reqContent.replace("#PASS_WORD#", this.password);
    
        RequestEntity entity = new StringRequestEntity(reqContent, "text/xml", "UTF-8");
        post.setRequestEntity(entity);
        
        post.setRequestHeader("SOAPAction", soapAction);
        this.logger.info("session " + sessionId + " send request to smsgw " + this.protocol.getUrl());
        this.httpclient.executeMethod(post);
        this.logger.info("session " + sessionId + " receive response from smsgw " + this.protocol.getUrl());
        response = post.getResponseBodyAsString();
       
        this.logger.info(receiver + "=>response:" + response);
        
        int start = response.indexOf("<sendMTResult>") + "<sendMTResult>".length();
        int end = response.lastIndexOf("</sendMTResult>");
        error = Integer.parseInt(response.substring(start, end));
      }
      catch (Exception ex)
      {
        this.logger.error("soap message error " + ex.getMessage(), ex);
        this.logger.error("response content:" + response);
        this.httpclient = new HttpClient();
        error = 1;
      }
      finally
      {
        post.releaseConnection();
      }
      return error;
    }
  }
  
  public int send(String sessionId, String serviceId, String sender, String receiver, String contentType, String content, String status)
  {
      
      /*
    if (content == null) {
      content = "";
    }
    content = this.encoder.encode(content.getBytes());
    try
    {
      content = URLEncoder.encode(content, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
     logger.error(e.getMessage(), e);
    }
    return sendMT(sessionId, serviceId, sender, receiver, contentType, content, status);
    
    */
      
   return sendSmsMps(sessionId, sender, receiver, content); 
      
    
  }
  
  public int send(String sessionId, String serviceId, String sender, String receiver, String contentType, byte[] content, String status)
  {
    if (content == null) {
      content = new byte[0];
    }
    String soapContent = Hex.encode(content);
    return sendMT(sessionId, serviceId, sender, receiver, contentType, soapContent, status);
  }
  
  
  private int sendSmsMps(String sessionId, String sender, String receiver, String content) {
        synchronized (this.lock) {
            PostMethod post = new PostMethod(this.protocol.getUrl());
            String response = "";
            int error;
            try {

                String reqMpsSms = "<?xml version=\"1.0\" ?>"
                        + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                        + "<S:Body>"
                        + "   <smsRequest xmlns=\"http://smsws/xsd\">"
                        + "       <username>" + this.username + "</username>"
                        + "       <password>" + this.password + "</password>"
                        + "       <msisdn>" + receiver + "</msisdn>"
                        + "       <content>" + content + "</content>"
                        + "       <shortcode>" + sender + "</shortcode>"
                        + "       <params>TEXT</params>"
                        + "   </smsRequest>"
                        + "</S:Body>"
                        + "</S:Envelope>";

                logger.info(receiver + "=>Rquest SMS Mps:" + reqMpsSms);

                RequestEntity entity = new StringRequestEntity(reqMpsSms, "text/xml", "UTF-8");
                post.setRequestEntity(entity);

                this.logger.info("session " + sessionId + " send request to smsgw " + this.protocol.getUrl());
                this.httpclient.executeMethod(post);
                this.logger.info("session " + sessionId + " receive response from smsgw " + this.protocol.getUrl());
                response = post.getResponseBodyAsString();

                this.logger.info(receiver + "=>response:" + response);

                int start = response.indexOf("<return>") + "<return>".length();
                int end = response.lastIndexOf("</return>");
                error = Integer.parseInt(response.substring(start, end));
            } catch (Exception ex) {
                this.logger.error("soap message error " + ex.getMessage(), ex);
                this.logger.error("response content:" + response);
                this.httpclient = new HttpClient();
                error = 1;
            } finally {
                post.releaseConnection();
            }
            return error;
        }
    }
  
  
  
}
