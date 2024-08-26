/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.luckydraw.ws.form;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author LuatNC
 */
public class SoapWSResponse {
	private String response;
	private Document doc;
	private String errCode;
        private String returnXmlString;

	public SoapWSResponse(String response) throws Exception {
		this.response = response;
		if (response != null && !response.isEmpty()) {
			parserResponse(response);
		}
	}

	private void parserResponse(String response) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                factory.setXIncludeAware(false);
                factory.setExpandEntityReferences(false);
                
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(response));
		doc = builder.parse(is);
	}

	public String getElementXML(String xpath) throws XPathExpressionException, TransformerException {
		if (doc == null)
			return null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpath).evaluate(doc, XPathConstants.NODESET);
		if (nodeList != null && nodeList.getLength() > 0) {
			StringWriter writer = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(nodeList.item(0)), new StreamResult(writer));
			String xml = writer.toString();
			return xml;
		}
		return null;
	}

	public String getTextContent(String xpath) throws XPathExpressionException {
		if (doc == null)
			return null;

		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(xpath).evaluate(doc, XPathConstants.NODESET);
		if (nodeList != null && nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		}
		return null;
	}

	public String getWSResponse() {
		return response;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

        public String getReturnXmlString() {
            return returnXmlString;
        }

        public void setReturnXmlString(String returnXmlString) {
            this.returnXmlString = returnXmlString;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
      
       
}
