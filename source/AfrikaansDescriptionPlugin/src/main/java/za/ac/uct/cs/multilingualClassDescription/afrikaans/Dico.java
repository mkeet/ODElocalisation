package za.ac.uct.cs.multilingualClassDescription.afrikaans;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;
import java.util.Hashtable;

/**
 * Author: Toky Raboanary<br>
 * UCT - University of Cape Town<br>
 * Computer Science Department<br>
 * Date: 13-Nov-2019<br><br>
 *
 *     This class is used to access dico.xml
 */

public class Dico {

    private Hashtable<EnumWord, String> elements = new Hashtable<>();
    private String error ="";
    public Dico() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("dico.xml");
        try {
            this.Init(resourceAsStream);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void Init(InputStream stream) throws Exception{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
        Node nNodeRoot = nList.item(0);
        NodeList children = nNodeRoot.getChildNodes();

        error+=  nNodeRoot.getNodeName()+" =  "+children.getLength()+"\n\r";

        for (int temp = 0; temp < children.getLength(); temp++) {

            Node nNode = children.item(temp);
            error+=nNode.getNodeName()+"\r\n";
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                EnumWord enumWord = EnumWord.valueOf(eElement.getTagName());
                String value = eElement.getTextContent();
                this.elements.putIfAbsent(enumWord, value);
            }
        }
    }

    public void printAll(){
        this.elements.forEach(
                (k, v) -> System.out.println("Key : " + k + ", Value : " + v));
    }

    public String getWord(EnumWord enumWord) {
        if (!this.elements.isEmpty())
           return this.elements.get(enumWord);
        else JOptionPane.showMessageDialog(null, "Dico.xml is empty!");
        return "error";
    }
}
