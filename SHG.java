import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.*;
import java.net.URL;
import java.util.*;


public class SHG extends java.applet.Applet 
    implements MouseListener, KeyListener 
{
	/**
	 * <param name='fbId' value='582736628'><param name='i1' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/161265_100001659021424_449499_q.jpg'><param name='i2' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/23091_1388396406_9731_q.jpg'><param name='i3' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186757_586790810_6247390_q.jpg'><param name='i4' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/202842_1481370160_5397126_q.jpg'><param name='i5' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/157829_1768066837_6189715_q.jpg'><param name='i6' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186135_1131928352_7884540_q.jpg'><param name='i7' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/161747_100000182535431_1511406_q.jpg'><param name='i8' value='http://profile.ak.fbcdn.net/static-ak/rsrc.php/v1/y9/r/IB7NOFmPw2a.gif'><param name='i9' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186279_1400107355_4356525_q.jpg'><param name='i10' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/203021_770930723_4584060_q.jpg'><param name='i11' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/187100_638399343_7192170_q.jpg'><param name='i12' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186847_56600253_7328151_q.jpg'><param name='i13' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/41501_1836621866_6649_q.jpg'><param name='i14' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186253_1415293442_135060_q.jpg'><param name='i15' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/195367_1398842154_8142445_q.jpg'><param name='i16' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/49065_1534350427_9287_q.jpg'><param name='i17' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/41623_1625812314_5102_q.jpg'><param name='i18' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/161416_502530857_5836167_q.jpg'><param name='i19' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186121_1060674145_4174586_q.jpg'><param name='i20' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/27415_550651778_3512_q.jpg'><param name='i21' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/211432_1224226164_8285551_q.jpg'><param name='i22' value='http://profile.ak.fbcdn.net/static-ak/rsrc.php/v1/y9/r/IB7NOFmPw2a.gif'><param name='i23' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/195613_35909136_6045025_q.jpg'><param name='i24' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/212003_1481370201_4264890_q.jpg'><param name='i25' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/211267_1209180837_8302534_q.jpg'><param name='i26' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186196_1549840415_3973244_q.jpg'><param name='i27' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/70760_1103689728_7408208_q.jpg'><param name='i28' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/49216_100000214360516_2194_q.jpg'><param name='i29' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/186250_27102213_7618382_q.jpg'><param name='i30' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/41525_1473570573_5624947_q.jpg'><param name='i31' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/174519_43200771_7714904_q.jpg'><param name='i32' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/202831_585985690_6048180_q.jpg'><param name='i33' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/173349_1783555342_499856_q.jpg'><param name='i34' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/161448_1455983060_5069690_q.jpg'><param name='i35' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/49038_29501187_7422123_q.jpg'><param name='i36' value='http://profile.ak.fbcdn.net/hprofile-ak-snc4/48575_1607847641_5712_q.jpg'>
	 * The 36 picture urls will come in from outside the applet
	 * If you start a new game, you'll get a new puzzle, but with the same 36 pictures
	 */
	private static final long serialVersionUID = 6602758910765868626L;
    private int width = 1200;
    private int height = 650;
    private int gameStage = 0;
    private static final int NOTSTARTED = 0;
    private static final int PLAYING = 2;
    private static final int GAMEOVER = 3;
    private Color bgColor = ToMarUtils.toMarBackground;
    public static Color textColor = tmColors.DARKBLUE;
    Image offscreenImg;
    Graphics og;
	public static final int NONE = 0;
	public static final int FIND = 1;
	public static final int HINT = 2;
	public static final int TAKEN1 = 3;
	public static final int TAKEN2 = 4;
	public static final int NEWGAME = 5;
	public static final int QUIT = 6;
    int tilesLeft = 0;
    int lowest = 0;
    int sel[] = {Tile.NUMTILES,Tile.NUMTILES,Tile.NUMTILES,Tile.NUMTILES};
    int moves = 0;
	int seed = 0;
	int totalMoves = 0;
//	long time = 0;
//	int bonus = 0;
//	int points = 0;
//	String comment = "";
	int[] taken1 = new int[72];
    int[] taken2 = new int[72];
	boolean selsInProgress;
	int dialogInProgress;
	boolean alertInProgress;
	int moveToRestore = 0;
	Dialog dialog;
	tmDialog dialog2;
	tmAlert alert;
	tmButton backButton;
	tmButton hintButton;
	tmButton startOverButton;
	tmButton newGameButton;
	tmButton findButton;
	tmButton takenButton;
	String message;
	long startSec;
    
    public void init()
    {
	    setSize(width,height);
		setBackground(bgColor);
	    addMouseListener(this);
 		addKeyListener(this);
		offscreenImg = createImage(getSize().width, getSize().height);
        og = offscreenImg.getGraphics();
        backButton = new tmButton(1080, 50, tmColors.LIGHTBLUE, 110, 30, "Back Up");
        hintButton = new tmButton(1080, 150, tmColors.LIGHTGREEN, 110, 30, "Get a Hint");
        findButton = new tmButton(1080, 250, tmColors.LIGHTPINK, 110, 30, "Find");
        takenButton = new tmButton(1080, 400, tmColors.LIGHTPURPLE, 110, 30, "Taken?");
        startOverButton = new tmButton(1080, 500, tmColors.LIGHTYELLOW, 110, 30, "Start Over");
        newGameButton = new tmButton(1080, 600, tmColors.LIGHTMAGENTA, 110, 30, "New Puzzle");
		message = this.getParameter("message");
		message = (message == null) ? "Welcome to Shanghai!" : message;
		message = message + " Scroll to the right for Help Functions -->";
        gameStage = NOTSTARTED;
        reInit();
    }
    public void resetSel()
    {
        for (int i = 0; i < 4; i++)
        {
        	if  (sel[i] < Tile.NUMTILES)
        	{
        		Tile.getTiles()[sel[i]].setSelected(false);
        	}    
            sel[i] = Tile.NUMTILES;
        }
        selsInProgress = false;
    }
	public static void log(String s)
	{
		System.out.println(ToMarUtils.getDateTimeStamp() + ": " + s);
	}
    public void reInit()
    {
        tilesLeft = lowest = Tile.NUMTILES - 1;
        moves = 0;
        totalMoves = 0;
        resetSel();
        // set up characters on tiles
		seed = (seed == 0)?(new Random()).nextInt():seed;
		seed = seed < 0 ? -seed : seed;
		seed %= 99999;
		Tile.loadData(this);
		dialog = new Dialog(Tile.getNames());
		Tile.newPuzzle(seed, this);
		selsInProgress = alertInProgress = false;
		dialogInProgress = NONE;
   		gameStage = PLAYING;
    }

    public void paint(Graphics g)
    {
        // draw title
        og.setColor(tmColors.DARKGREEN);
        og.setFont(new Font("Verdana",Font.PLAIN,20));
		og.drawString(message, 12, 60);
		og.setColor(textColor);
		if (gameStage == GAMEOVER)
		{
	        og.setFont(new Font("Verdana",Font.PLAIN,20));
			og.drawString("Congratulations!", 12, 4*20);
			og.drawString("You solved it in " +	totalMoves + " moves!" , 12,8*20);
//			og.drawString(comment, 12, 12*20);
			try
			{
		        g.drawImage(offscreenImg, 0, 0, this);
				Thread.sleep(2000);
		        endGame(true);
			}
			catch(Exception e){}
		}
        else  
        {
            og.setFont(new Font("Verdana",Font.PLAIN,24));
//   		long seconds = (gameStage == LOADING) ? 0 : (int)((new Date()).getTime() - startSec)/1000;
			og.drawString("Puzzle #" + seed + 
//					 "   Points: " + points +
					"   TilesLeft: " + tilesLeft + "   Moves: " + totalMoves +
//					"   Time: " + 	ToMarUtils.displayTime(seconds) +
					"   Lowest: " + lowest,	12, 25);
			og.setColor(tmColors.DARKMAGENTA);
			og.drawString("Help Functions", 1000, 25);
            //paint all the tiles
            for (int i = 0; i < Tile.NUMTILES; i++)
            {
            	Tile.getTiles()[i].draw(og);
            }
            backButton.draw(og);
            hintButton.draw(og);
            findButton.draw(og);
            startOverButton.draw(og);
            takenButton.draw(og);
            startOverButton.draw(og);
            newGameButton.draw(og);
        }        
        if (dialogInProgress > NONE)
		{
        	if (isDropDown())
        	{
    			dialog.draw(og);
        	}
        	else
        	{	
        		dialog2.draw(og);
        	}	
		}
		else if (alertInProgress)
		{
			alert.draw(og);
		}
        g.drawImage(offscreenImg, 0, 0, this);
    }
    public void update(Graphics g)
    {
        og.setColor(bgColor);
        og.fillRect(0, 0, width, height);
        paint(g);
    }
    public void restoreGame()
    {
        while (moves > moveToRestore)
        {
            backUp();
        }   
		dialogInProgress = NONE;
    }    
    public void startOver()
    {
        while (moves > 0)
        {
            backUp();
        }   
    }    
    public void mousePressed(MouseEvent e) 
    {
	    if (dialogInProgress > NONE)
		{
	    	int result;
			// don't take other input except the dialog
        	if (isDropDown())
        	{
        		result = dialog.processMouseInput(e.getX(), e.getY());
        	}
        	else
        	{	
        		result = dialog2.processMouseInput(e.getX(), e.getY());
			}
			if (result == Dialog.CANCELLED)
			{
				dialogInProgress = NONE;
			}
			else if (result == Dialog.VALID)
			{
				if (dialogInProgress == FIND)
				{
					int numTiles = doFind(dialog.getInput());
					dialogInProgress = NONE;
					alert = new tmAlert("" + numTiles + " tiles were found. ");
					alertInProgress = true;
				}
				else if (dialogInProgress == HINT)
				{
					takeHint();
				}
				else if (dialogInProgress == TAKEN1)
				{
					int k = dialog.getInput();
					moveToRestore = doLocate(k);
					if (moveToRestore == 999)
					{
						dialogInProgress = NONE;
						alert = new tmAlert(Tile.getName(k) + " has not been taken yet.");
						alertInProgress = true;
					}
					else
					{
						dialogInProgress = TAKEN2;
						dialog2 = new tmDialog(Tile.getName(k) + " was taken at move " + (144 - moveToRestore*2) + ".", 0, tmDialog.NONE);
						dialog2.setErrorMessage("Click Okay to restore game to that move.");
					}
				}
				else if (dialogInProgress == TAKEN2)
				{
					restoreGame();
				}	
				else if (dialogInProgress == NEWGAME)
				{
					seed = Integer.parseInt(dialog2.getInput());
					reInit();
				}	
				else if (dialogInProgress == QUIT)
				{
					dialogInProgress = NONE;
					endGame(false);
				}	
			}
			repaint();
		}
		else if (alertInProgress)
		{
			if (alert.processMouseInput(e.getX(), e.getY()) == tmAlert.VALID)
			{
				alertInProgress = false;
				repaint();
			}
		}	
		else
	    {
	    	if (backButton.clicked(e.getX(), e.getY()))
	    	{
	    		backUp();
	    	}
	    	else if (findButton.clicked(e.getX(), e.getY()))
	    	{
				getFind();
	    	}
	    	else if (hintButton.clicked(e.getX(), e.getY()))
	    	{
	    		getHint();
	    	}
	    	else if (takenButton.clicked(e.getX(), e.getY()))
	    	{
	    		getTaken();
	    	}
	    	else if (startOverButton.clicked(e.getX(), e.getY()))
	    	{
	    		startOver();
	    	}
	    	else if (newGameButton.clicked(e.getX(), e.getY()))
	    	{
	    		newGame();
	    	}
	    	else if (selsInProgress)
			{
				resetSel();
			}
            for (int i = 0; i < Tile.NUMTILES; i++)
            {
            	if  (!(Tile.getTiles()[i].isTaken()) && Tile.getTiles()[i].wasClicked(e.getX(), e.getY()))
                {
                    // tile must be free 
                    if  (Tile.getTiles()[i].isFree())
                    {     
                       // see if something's already selected
                    	if (sel[0] == Tile.NUMTILES)
                        {  
                            sel[0] = i;
                            Tile.getTiles()[i].setSelected(true);
                            setMessage(Tile.getTiles()[i].getChar() + "   " + Tile.getTiles()[i].getName());
//                          setMessage(Tile.getTiles()[i].getChar() + "   " + i);
                        }
                        else
                        {
                            // if this is already selected, unselect it
                            if  (sel[0] == i)
                            {
                                sel[0] = Tile.NUMTILES;
                                Tile.getTiles()[i].setSelected(false);
                                setMessage("                                                                              ");
                            }
                            else
                            {
                            	// tiles characters have to match
                                if  (Tile.getTiles()[i].getChar().equalsIgnoreCase(Tile.getTiles()[sel[0]].getChar()))
                                {
                                	sel[1] = i;	
                                    takeTiles();
                                    setMessage("                                                                              ");
                                }
                                else
                                {
                                	setMessage("Tiles don't match");
                                }   
                            }    
                        }
                    }    
                    else
                    {     
                    	setMessage("Tile not free");
                    }	
            	}   
            }
        }    
		repaint();
    }

    public void takeTiles()
    {
    	Tile.getTiles()[sel[0]].setTaken(true);
    	Tile.getTiles()[sel[1]].setTaken(true);
    	Tile.getTiles()[sel[0]].setSelected(false);
    	Tile.getTiles()[sel[1]].setSelected(false);
        // store tiles by move
        taken1[moves] = sel[0];
        taken2[moves] = sel[1];
        moves += 1;
		totalMoves += 1;
        tilesLeft -= 2;
//    	time = (int)((new Date()).getTime() - startSec)/1000;
//    	long seconds = (time < 1000) ? 1000 - time : 1;
//      points += moves;
        resetSel();
        if  (tilesLeft < lowest)
        {
            lowest = tilesLeft;
        }
        if  (tilesLeft == 0)  // change!!!!!!
        {
        	gameStage = GAMEOVER;
			repaint();
        }
    }   
    private void endGame(boolean solved)
    {
        repaint();
   		try
   		{
   			String encName = (this.getParameter("nm")).replaceAll(" ", "%20");
			String fwd = this.getParameter("site") + "SHG?score=" + totalMoves + "&id=" + this.getParameter("id") + "&nm=" + encName + "&tsp=" + ToMarUtils.getDateTimeStamp();
   			this.getAppletContext().showDocument(new URL(fwd));
   		}
   		catch(Exception e)
   		{
   			log("Error 1: " + e);
   		}
	}

    public void backUp()
    {
        if  (moves > 0)
        {
            moves -= 1;
            // find out what tiles were taken
            Tile.getTiles()[taken1[moves]].setTaken(false);
            Tile.getTiles()[taken2[moves]].setTaken(false);
            tilesLeft += 2;
        }
    }    

    public void mouseReleased(MouseEvent e)     {    }

    public void mouseEntered(MouseEvent e)     {    }

    public void mouseExited(MouseEvent e)     {    }

    public void mouseClicked(MouseEvent e)  {    }

	public boolean doHint()
	{
		boolean hintFound = false;
		resetSel();
		for (int i = 0; i < Tile.NUMTILES; i++)
		{
			if  (!(Tile.getTiles()[i].isTaken()) && Tile.getTiles()[i].isFree())
			{
				for (int j = i + 1; j < Tile.NUMTILES; j++)
				{
					if  (!(Tile.getTiles()[j].isTaken()) && Tile.getTiles()[j].isFree())
					{
						if  (Tile.getTiles()[j].getChar().equalsIgnoreCase(Tile.getTiles()[i].getChar()))
						{
							Tile.getTiles()[i].setSelected(true);
							Tile.getTiles()[j].setSelected(true);
							hintFound = true;
							sel[0] = i;
							sel[1] = j;
							repaint();
							break;
						}
					}
				}
				if  (hintFound == true)
				{
					selsInProgress = true;
					break;
				}
			}
		}
		if  (hintFound == false)
		{
        	setMessage("No moves left");
		}
		return hintFound;
	}	

	public void setMessage(String msg)
	{
		message = msg;
		repaint();
	}
	public int doFind(int index)
	{
		String inChar = Tile.letterSet.substring(index, index + 1);
		resetSel();
		int counter = 0;
		for (int i = 0; i < Tile.NUMTILES; i++)
		{
			if  (!(Tile.getTiles()[i].isTaken()) && Tile.getTiles()[i].isVisible())
			{
				if  (Tile.getTiles()[i].getChar().equalsIgnoreCase(inChar))
				{
					Tile.getTiles()[i].setSelected(true);
					sel[counter++] = i;
					selsInProgress = true;
				}
			}
		}
		repaint();
		return counter;
	}	

	public int doLocate(int index)
	{
		String inChar = Tile.letterSet.substring(index, index + 1);
        for (int i = moves - 1; i > -1 ; i--)
        {
//        	ToMarUtils.log("i = " + i + ", tile is " + tiles[taken1[i]].getChar());       	if  ((tiles[taken1[i]].getChar()).equalsIgnoreCase(inChar))
        	if  ((Tile.getTiles()[taken1[i]].getChar()).equalsIgnoreCase(inChar))
            {
                return i;
            }
        }
		return 999;
	}	

	public void takeHint()
    {
		takeTiles();
//		hints = true;
		dialogInProgress = NONE;
	}
		
	public void newGame()
	{
		dialog2 = new tmDialog("Enter a number between 1 and 99999,", 6, tmDialog.NUMERIC);
//		dialog = new ToMarDialog(browser, "Hit Okay to give up, ", 0, ToMarDialog.NONE);
		dialog2.setErrorMessage("or 0 for a random puzzle.");
		dialog2.setInput("0");
		dialogInProgress = NEWGAME;
	}
	
	public void keyTyped(KeyEvent k){	}
	
	public void keyPressed(KeyEvent k)
	{
		if (dialogInProgress > NONE)
		{
			if (!(isDropDown()))
			{	
				dialog2.processKeyInput(k.getKeyCode(), k.getKeyChar());
			}	
		}
		else 
		{
			String s = ("" + k.getKeyChar()).toUpperCase();
			if ("F".equals(s))
			{
				getFind();
			}
			else if ("B".equals(s))
			{
				backUp();
			}
			else if ("H".equals(s))
			{
				getHint();
			}
			else if ("T".equals(s))
			{
				getTaken();
			}
			else if ("S".equals(s))
			{
				startOver();
			}
			else if ("N".equals(s))
			{
				newGame();
			}
		}
		repaint();
	}
	public void keyReleased(KeyEvent arg0)
	{
	}
	private void getFind()
	{
		dialog.setMessage("Click the name of the person to find...");
		dialogInProgress = FIND;
	}
	private void getTaken()
	{
		dialog.setMessage("Click the name of the person to restore...");
		dialogInProgress = TAKEN1;
	}
	private void getHint()
	{
		if (doHint())
		{
			dialog2 = new tmDialog("Click Okay to remove them.", 0, tmDialog.NONE);
			dialog2.setErrorMessage("Click Cancel to think about it.");
			dialogInProgress = HINT;
		}
		else
		{
			alert = new tmAlert("No moves left.");
			alertInProgress = true;
		}
    }
	private boolean isDropDown()
	{
		if (dialogInProgress == FIND || dialogInProgress == TAKEN1)
		{
			return true;
		}
		return false;
	}
}