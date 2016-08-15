package com.xiaoxin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {

	public static Node getNode(String node_name, NodeList list) {

		Node node = null;
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {

				if (node_name.equals(n.getNodeName())) {
					node = n;
					break;
				}
			}
		}
		return node;
	}

	public static String getNodeValue(String node_name, NodeList list) {

		return getNode(node_name, list).getTextContent();

	}

	/**
	 * 
	 * @Title: isToMap
	 * @Description: InputStream转换成Map
	 * @param @param is
	 * @param @return
	 * @param @throws JDOMException
	 * @param @throws IOException 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	public static Map<String, String> isToMap(InputStream is)
			throws JDOMException, IOException {
		Map<String, String> xmlData = new HashMap<String, String>();
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(is);
		Element root = doc.getRootElement();

		List<Element> els = root.getChildren();

		for (Element e : els) {
			xmlData.put(e.getName(), e.getText());
		}
		return xmlData;
	}

}
