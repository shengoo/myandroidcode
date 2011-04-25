package com.sheng00.epublib.Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sheng00.epublib.R;
import com.sheng00.epublib.Models.Book;
import com.sheng00.epublib.Models.Item;
import com.sheng00.epublib.Models.Metadata;
import com.sheng00.epublib.Models.NavPoint;

import android.content.Context;


public class XmlManager {
	private Context mContext;

	private PrefsManager prefsManager;
	private String bookroot;

	private Book book;

	private File opfFile;
	private File tocFile;

	public XmlManager(Context context) {
		mContext = context;
		prefsManager = new PrefsManager(mContext);
		bookroot = prefsManager.prefsGetString(mContext
				.getString(R.string.book_root_path));
	}

	public void parseXml() {
		parseContainer();
		parseOpf();
		parseToc();
	}

	private void parseContainer() {
		File containerFile = new File(prefsManager.prefsGetString(mContext
				.getString(R.string.meta_file_path)));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(containerFile);
			Element root = dom.getDocumentElement();
			Element rootfilesElement = (Element) root.getElementsByTagName(
					"rootfiles").item(0);
			Element rootFileElement = (Element) rootfilesElement
					.getElementsByTagName("rootfile").item(0);
			String opf = rootFileElement.getAttribute("full-path");
			opfFile = new File(bookroot + opf);
			parseOpf();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void parseOpf() {
		book = new Book();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(opfFile);
			Element root = dom.getDocumentElement();
			Element metadataElement = (Element) root.getElementsByTagName(
					"metadata").item(0);
			Element titleElement = (Element) metadataElement
					.getElementsByTagName("dc:title").item(0);
			Element authorElement = (Element) metadataElement
					.getElementsByTagName("dc:author").item(0);
			Element identifierElement = (Element) metadataElement
					.getElementsByTagName("dc:identifier").item(0);
			Element publisherElement = (Element) metadataElement
					.getElementsByTagName("dc:publisher").item(0);
			Element dateElement = (Element) metadataElement
					.getElementsByTagName("dc:date").item(0);
			Element languagElement = (Element) metadataElement
					.getElementsByTagName("dc:language").item(0);
			Element creatorElement = (Element) metadataElement
					.getElementsByTagName("dc:creator").item(0);
			Metadata metadata = new Metadata();
			metadata.setTitle(titleElement != null ?
					(titleElement.getFirstChild() != null ? titleElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setAuthor(authorElement != null ?
					(authorElement.getFirstChild() != null ? authorElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setIdentifier(identifierElement != null ?
					(identifierElement.getFirstChild() != null ? identifierElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setPublisher(publisherElement != null ?
					(publisherElement.getFirstChild() != null ? publisherElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setDate(dateElement != null ?
					(dateElement.getFirstChild() != null ? dateElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setCreator(creatorElement != null ?
					(creatorElement.getFirstChild() != null ? creatorElement.getFirstChild().getNodeValue() : "")
					:"");
			metadata.setLanguage(languagElement != null ?
					(languagElement.getFirstChild() != null ? languagElement.getFirstChild().getNodeValue() : "")
					:"");
			
			book.setMetadata(metadata);
			
			Element manifestElement = (Element) root.getElementsByTagName("manifest").item(0);
			Element spineElement = (Element) root.getElementsByTagName("spine").item(0);
			String tocId = spineElement.getAttribute("toc");
			
			NodeList itemElementList = manifestElement.getElementsByTagName("item");
			List<Item> items = new ArrayList<Item>();
			for(int i = 0;i<itemElementList.getLength();i++){
				Item item = new Item();
				Element itemElement = (Element) itemElementList.item(i);
				String tocHref = itemElement.getAttribute("href");
				String id  =itemElement.getAttribute("id");
				String media = itemElement.getAttribute("media-type");
				item.setFile(new File(opfFile.getParentFile().getPath() + File.separator + tocHref));
				item.setHref(tocHref);
				item.setId(id);
				item.setMedia_type(media);
				items.add(item);
				if(itemElement.getAttribute("id").equals(tocId)){
					tocFile = new File(opfFile.getParentFile().getPath() + File.separator + tocHref);
				}
			}
			book.setItems(items);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void parseToc(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(tocFile);
			Element root = dom.getDocumentElement();
			Element navMapElement = (Element) root.getElementsByTagName("navMap").item(0);
			NodeList navPointElementList = navMapElement.getChildNodes();
			List<NavPoint> list = new ArrayList<NavPoint>();
			for(int i=0;i<navPointElementList.getLength();i++){
				Node navNode = navPointElementList.item(i);
				if(navNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element navPoint = (Element) navPointElementList.item(i);
				NavPoint nav = new  NavPoint();
				nav.setId(navPoint.getAttribute("id"));
				nav.setPlayOrder(Integer.parseInt(navPoint.getAttribute("playOrder")));
				Element label = (Element) navPoint.getElementsByTagName("navLabel").item(0);
				Element text = (Element) label.getElementsByTagName("text").item(0);
				nav.setLabel(text.getFirstChild().getNodeValue());
				Element contentElement = (Element) navPoint.getElementsByTagName("content").item(0);
				String srcString = contentElement.getAttribute("src");
				nav.setHref(srcString);
				nav.setContent(new File(tocFile.getParentFile().getPath() + File.separator + srcString));
				if(navPoint.getElementsByTagName("navPoint")!=null){
					NodeList children = navPoint.getElementsByTagName("navPoint");
					List<NavPoint> childrenList = new ArrayList<NavPoint>();
					for(int j=0;j<children.getLength();j++){
						Element childElement = (Element) children.item(j);
						NavPoint childNav = new  NavPoint();
						if(Integer.parseInt(childElement.getAttribute("playOrder")) == nav.getPlayOrder())
							continue;
						childNav.setId(childElement.getAttribute("id"));
						childNav.setPlayOrder(Integer.parseInt(childElement.getAttribute("playOrder")));
						Element label2 = (Element) childElement.getElementsByTagName("navLabel").item(0);
						Element text2 = (Element) label2.getElementsByTagName("text").item(0);
						childNav.setLabel(text2.getFirstChild().getNodeValue());
						Element contentElement2 = (Element) childElement.getElementsByTagName("content").item(0);
						String srcString2 = contentElement2.getAttribute("src");
						childNav.setHref(srcString2);
						childNav.setContent(new File(tocFile.getParentFile().getPath() + File.separator + srcString2));
						childrenList.add(childNav);
					}
					nav.setChildren(childrenList);
				}
				list.add(nav);
			}
			book.setNavPoints(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Book getBook() {
		return book;
	}
}
