package javabasic.ex14.original;

import java.applet.Applet;
import java.awt.Image;
import java.awt.Graphics;
import java.net.URL;
import java.io.InputStream;

/* This is simple applet class which display given PNG Image  */
public class PNGApplet extends Applet {
    Image img=null;

    public PNGApplet()  {}
    
    public void init() { 
      try {
        String      src = getParameter("source");
        URL         url = new URL(src);
        InputStream inp = url.openStream();
        PNGProducer p   = new PNGProducer(inp);
        img=createImage(p);
      }
      catch(java.net.MalformedURLException e)    {System.err.println(e);}
      catch(java.io.IOException e)               {System.err.println(e);}
    }
    
    public void paint(Graphics g)  {
      if(img!=null)
        g.drawImage(img,0,0,this);
    }
}
