import java.awt.Graphics;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;

public class ImageFromURL	 implements Serializable, Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1134383734738692260L;
    private ImageIcon ii;
    String url;
    Hashtable ht;
    String ch;
	
	public ImageFromURL(Hashtable ht, String ch, String url, int width, int height) throws Exception
	{
		this.ht = ht;
		this.url = url;
		this.ch = ch;
		Thread t = new Thread(this);
		t.start();
	}
	public void draw(Graphics og, int c, int r)
	{
		ii.paintIcon(null, og, c, r);
	}
	public void run()
	{
   		try 
   		{
			ii = new ImageIcon(new URL(url));
        }
   		catch (MalformedURLException mue)
   		{
   			ii = new ImageIcon(url);
   		}
   		catch (Exception e)
   		{
			SHG.log("Error from ImageFromURL: " + e);
   		}
		ht.put(ch, this);
	}
}
	
