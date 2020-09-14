package jposbox;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Micael
 */
public class CustomImageView extends javax.swing.text.html.ImageView {

    private byte[] imageBytes = new byte[0];

    public CustomImageView(Element elmnt) {
        super(elmnt);
        String imgData = "";
        elmnt.getDocument();
        Enumeration attrSet = elmnt.getAttributes().getAttributeNames();
        while (attrSet.hasMoreElements()) {
            Object key = attrSet.nextElement();
            if (key instanceof HTML.Attribute) {
                if (key == HTML.Attribute.SRC) {
                    String srcData = (String) elmnt.getAttributes().getAttribute(key);
                    String[] splittedSrcData = srcData.split(",");
                    if (splittedSrcData.length == 2) {
                        String base64ImageData = splittedSrcData[1];
                        imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64ImageData);
                    }
                }
            }
        }
    }

    @Override
    public Image getImage() {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException ex) {
            System.err.println(ex);
            return super.getImage();
        }
    }

}
