///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.viettel.luckydraw.util;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPConstants;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPEnvelope;
//import javax.xml.soap.SOAPException;
//import javax.xml.soap.SOAPHeader;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.soap.SOAPPart;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpConnectionManager;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.commons.httpclient.methods.StringRequestEntity;
//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
//import org.apache.log4j.Logger;
//import org.w3c.dom.DOMException;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import utils.Hex;
//
///**
// *
// * @author liemnh
// */
//public class SendSmsUtils {
//
//    final static Logger logger = Logger.getLogger(SendSmsUtils.class);
//
//    public static void sendSms(String phone, String message) {
//        if (message == null || "".equals(message)) {
//            return;
//        }
//        callSendMtSoap(phone, message, "sendMT", "http://tempuri.org/");
//    }
//
//    public static String callSendMtSoap(String phone, String message, String soapMethod,
//            String targetNamespace) {
//        try {
//            return createSOAPRequest(phone, message, soapMethod, targetNamespace);
//        } catch (Exception e) {
//            logger.error(e);
//        }
//        return null;
//    }
//
//    private static String createSOAPRequest(String phone, String message, String soapMethod, String serverURI) throws Exception {
//        logger.info("Send SMS >> " + phone + ", Content:" + message);
//        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
//        SOAPMessage soapMessage = messageFactory.createMessage();
//        SOAPPart soapPart = soapMessage.getSOAPPart();
//        // SOAP Envelope
//        SOAPEnvelope envelope = soapPart.getEnvelope();
//        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
//        if (envelope.getHeader() != null) {
//            envelope.getHeader().detachNode();
//        }
//        SOAPHeader header = envelope.addHeader();
//        SOAPElement security = header.addChildElement("AuthHeader", "", serverURI);
//        SOAPElement username = security.addChildElement("Username");
//        username.addTextNode(Config.getInstance().getOtpUser());
//        SOAPElement password = security.addChildElement("Password");
//        password.addTextNode(Config.getInstance().getOtpPass());
//
//        SOAPBody soapBody = envelope.getBody();
//        SOAPElement soapBodyElem = soapBody.addChildElement(soapMethod, "", serverURI);
//
//        SOAPElement SessionId = soapBodyElem.addChildElement("SessionId");
//        SessionId.addTextNode("0");
//
//        SOAPElement ServiceId = soapBodyElem.addChildElement("ServiceId");
//        ServiceId.addTextNode(Config.getInstance().getOtpServiceId());
//
//        SOAPElement Sender = soapBodyElem.addChildElement("Sender");
//        Sender.addTextNode(Config.getInstance().getOtpSender());
//
//        SOAPElement Receiver = soapBodyElem.addChildElement("Receiver");
//        if (phone.startsWith(Config.getInstance().getOtpPrefix())) {
//            Receiver.addTextNode(phone);
//        } else {
//            StringBuilder phoneBuilder = new StringBuilder(Config.getInstance().getOtpPrefix());
//            phoneBuilder.append(phone);
//            Receiver.addTextNode(phoneBuilder.toString());
//        }
//
//        SOAPElement ContentType = soapBodyElem.addChildElement("ContentType");
//        ContentType.addTextNode(Config.getInstance().getOtpContentType());
//
//        SOAPElement Content = soapBodyElem.addChildElement("Content");
//        if (message == null) {
//            message = "";
//        }
//        try {
//         
////            message = Base64.encodeBase64String(message.getBytes());
////            message = URLEncoder.encode(message, "UTF-8");
//            byte[] tmp = message.getBytes("UTF16");
//            message = Hex.encode(tmp);
//            logger.info(message);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        Content.addTextNode(message);
//
//        SOAPElement Status = soapBodyElem.addChildElement("Status");
//        Status.addTextNode(Config.getInstance().getOtpStatus());
//
//        soapMessage.saveChanges();
//
//        /* Print the request message */
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        soapMessage.writeTo(out);
//        String strMsg = new String(out.toByteArray());
//        StringBuilder strMessageBuilder = new StringBuilder();
//        strMessageBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        strMessageBuilder.append(strMsg);
//
//        PostMethod post = new PostMethod(Config.getInstance().getOtpUrl());
//        RequestEntity entity = new StringRequestEntity(strMessageBuilder.toString(), "text/xml", "UTF-8");
//        post.setRequestEntity(entity);
//        post.setRequestHeader("SOAPAction", "http://tempuri.org/" + soapMethod);
//
//        String responseBody = "";
//
//        try {
//            HttpClient httpclient = new HttpClient();
//            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
//            HttpConnectionManagerParams conPars = conMgr.getParams();
//            conPars.setConnectionTimeout(30000);
//            conPars.setSoTimeout(30000);
//            logger.info("status=" + httpclient.executeMethod(post));
////            responseBody = post.getResponseBodyAsString();
//            responseBody = post.getResponseBodyAsString();
//            return parseResult(responseBody);
//        } catch (IOException ex) {
//            logger.error(ex);
//        }
//        logger.info(responseBody);
//        return null;
//    }
//
//    private static String parseResult(String response) throws SOAPException {
//        String result = null;
//        try {
//            logger.info("Response:" + response);
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
//            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
//            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//            factory.setXIncludeAware(false);
//            factory.setExpandEntityReferences(false);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            InputSource is = new InputSource(new StringReader(response));
//            Document doc = builder.parse(is);
//            NodeList nodeList = doc.getElementsByTagName("sendMTResult");
//            Element element = (Element) nodeList.item(0);
//            result = element.getTextContent();
//            logger.info("code: " + result);
//        } catch (IOException | ParserConfigurationException | DOMException | SAXException ex) {
//            logger.error(ex, ex);
//        }
//        return result;
//    }
//}
