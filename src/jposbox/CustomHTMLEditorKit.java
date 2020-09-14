package jposbox;


import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Micael
 */
public class CustomHTMLEditorKit extends HTMLEditorKit{

    @Override
    public ViewFactory getViewFactory() {
        return new CustomHTMLFactory();
    }
    
    class CustomHTMLFactory extends HTMLFactory{

        @Override
        public View create(Element elmnt) {
            Object o = elmnt.getAttributes().getAttribute(StyleConstants.NameAttribute);
            if(o instanceof HTML.Tag){
                HTML.Tag kind = (HTML.Tag) o;
                if (kind == HTML.Tag.IMG){
                    return new CustomImageView(elmnt);
                }
            }
            return super.create(elmnt);
        }
                
    }
    
}
