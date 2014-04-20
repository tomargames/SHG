import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/*
 * Created on Mar 22, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
 
/**
 * @author marie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Dialog
{
	public static final int CANCELLED = 0;
	public static final int VALID = 1;
	public static final int GARBAGE = 3;
	private String message;
	private int input;
	private int x = 350;
	private int y = 20;
	private int width = 600;
	private int height = 600;
	private Font font = new Font("Verdana", Font.PLAIN, 12);
	private tmButton cancelButton;
	private tmButton[] people; 
	
	public Dialog(String[] names)
	{
		cancelButton = new tmButton(x + 10, y + 560, tmColors.LIGHTGRAY, 70, 35, "Cancel");
		input = 0;
		people = new tmButton[Tile.letterSet.length()];
		for (int i = 0; i < Tile.letterSet.length(); i++)
		{
			String lbl = Tile.letterSet.substring(i, i + 1) + "  " + names[i]; 
			if (i < 12)
			{	
				people[i] = new tmButton(x + 10, y + (36 * (i + 1)), tmColors.LIGHTGRAY, 190, 35, lbl);
			}
			else if (i < 24)
			{	
				people[i] = new tmButton(x + 210, y + (36 * (i - 11)), tmColors.LIGHTGRAY, 190, 35, lbl);
			}
			else
			{	
				people[i] = new tmButton(x + 410, y + (36 * (i - 23)), tmColors.LIGHTGRAY, 190, 35, lbl);
			}
			people[i].setFont(new Font("Verdana", Font.BOLD, 10));
		}
	}
	public int processMouseInput(int x, int y)
	{
		if (getCancelButton().clicked(x, y))
		{
			return CANCELLED;
		}
		for (int i = 0; i < Tile.letterSet.length(); i++)
		{
			if (people[i].clicked(x, y))
			{
				input = i;
				return VALID;
			}
		}
		return GARBAGE;
	}

	public void draw(Graphics og)
	{
		og.setColor(new Color(222,180,180));
		og.fillRoundRect(x, y, width, height, 5, 5);
		og.setColor(new Color(0,0,0));
		og.drawRoundRect(x, y, width, height, 5, 5);
		og.setFont(font);
		og.drawString(message, x + 5, y + 15);
		cancelButton.draw(og);
		for (int i = 0; i < Tile.letterSet.length(); i++)
		{
			people[i].draw(og);
		}
	}	
	public tmButton getCancelButton()
	{
		return cancelButton;
	}

	public int getInput()
	{
		return input;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
}
