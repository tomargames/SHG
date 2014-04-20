import java.applet.Applet;
import java.awt.*;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Tile implements Serializable
{   
	private static final long serialVersionUID = -3300346236223304057L;
	private static final String characters = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHHIIIIJJJJ" +
		"KKKKLLLLMMMMNNNNOOOOPPPPQQQQRRRRSSSSTTTT" +
		"UUUUVVVVWWWWXXXXYYYYZZZZ0000111122223333" +
		"444455556666777788889999";
	public static final String letterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int NUMTILES = 145;
    public static Tile[] tiles = new Tile[NUMTILES];
    private static String characterUniverse;
	private static final int size = 70;
    public static final Color[] levelColors = {tmColors.LIGHTCYAN,
    	tmColors.CHARTREUSE,tmColors.DARKGREEN, tmColors.DARKRED};
    public static final Color hiColor = tmColors.ORANGE;
	private int column;
    private int row;
    private int level;
    private int tileToLeft;
    private int tileToRight;
    private int tileAbove;
    private int tileNumber;
    private boolean taken;
    private boolean selected;
    private String character;
	private String name;
    private Color textColor = tmColors.BLACK;
    private static Hashtable nameTable = new Hashtable(36);
    private static Hashtable imageTable = new Hashtable(36);
    public static Hashtable chars = new Hashtable(36);
    private static int[] under144 = {140, 141, 142, 143, 129, 130, 133, 134, 102, 103, 108, 109, 35, 36, 47, 48};
    public static Vector under144s;
    public static Vector doubles;
    public static boolean fixFlag;
    public static int seed = 0;
    public static int problemCount = 0;
    
	public String getName()
	{
		return name;
	}
    public static Tile[] getTiles()
    {
    	return tiles;
    }
    public static String getName(int idx)
    {
    	return (String) nameTable.get(letterSet.substring(idx, idx +1));
    }
    public static String[] getNames()
    {
    	String[] arr = new String[letterSet.length()];
    	for (int i = 0; i < letterSet.length(); i++)
    	{
    		arr[i] = getName(i);
    	}
    	return arr;
    }
    public static void newPuzzle(int s, Applet a)
    {
		under144s = new Vector();
		fixFlag = false;
		chars = new Hashtable(36);
		seed = s;
       	characterUniverse = new String(characters);
       	for (int i = 0; i < letterSet.length(); i++)
    	{
       		chars.put(letterSet.substring(i, i+1), new Vector());
    	}	
//		SHG.log("Loaded " + letterSet.substring(i, i+1) + chars.get(letterSet.substring(i, i+1)));
		Random rnd = new Random(seed);
		for (int i = NUMTILES - 1; i > -1; i--)
        {
	       	tiles[i] = new Tile(i);
	        if (i != 85)	// 85 and 144 are the same tile
			{	
				getTiles()[i].setCharacter(rnd);
				getTiles()[i].setTaken(false);
			}
			else
			{
				getTiles()[i].setTaken(true);
				getTiles()[i].setCharacter(getTiles()[144].getChar());
			}
        }
       	for (int i = 0; i < letterSet.length(); i++)
    	{
       		Tile t1 = tiles[((Integer) ((Vector) chars.get(letterSet.substring(i, i+1))).elementAt(0)).intValue()];
       		Tile t2 = tiles[((Integer) ((Vector) chars.get(letterSet.substring(i, i+1))).elementAt(1)).intValue()];
       		Tile t3 = tiles[((Integer) ((Vector) chars.get(letterSet.substring(i, i+1))).elementAt(2)).intValue()];
       		Tile t4 = tiles[((Integer) ((Vector) chars.get(letterSet.substring(i, i+1))).elementAt(3)).intValue()];
       		if (t1.getRow() == t2.getRow() && t1.getRow() == t3.getRow() && t1.getRow() == t4.getRow())
       		{
       			// Case: 3 tiles in same row on same level, 4th tile is under the middle of the 3
       			// We know they're all in the same row, but are 3 on the same level?
       			if (t1.getLevel() == t2.getLevel() && t2.getLevel() == t3.getLevel())
       			{
       				if (t4.getLevel() >= t1.getLevel())
       				{
       					continue;
       				}
       				if (t4.getColumn() != t2.getColumn())
       				{	
           				continue;
       				}
       				// 37 cases - move the hidden tile to Tile 0
       				String ch = tiles[11].getChar();
       				tiles[11].setCharacter(t4.getChar());
       				t4.setCharacter(ch);
//     				System.out.println("" + seed + ": " + t1.toString());
       			}	
       		}
    	}	
    }
    public String toString()
    {
    	return "Tile " + tileNumber + ", row " + row + ", col " + column + ", level " + level + ", " + character;
    }
    public int getX()
    {
    	if  (column == 16) 
        {
            return (1 + (int) (6.5 * size));
        }
        return (1 + ((column - 1) * size));
    }
    public int getY()
    {
        if  (row < 9)
        {
             return (row * size);
        }
        return ((int)(size * 4.5));
    }
    public Tile(int num)
    {
		row = new Integer(tileData[num].substring(0, 1).trim()).intValue();
    	column = new Integer(tileData[num].substring(3, 5).trim()).intValue();
    	level = new Integer(tileData[num].substring(6, 7).trim()).intValue() - 1;
    	tileToLeft = new Integer(tileData[num].substring(9, 12).trim()).intValue() - 1;
    	tileToRight = new Integer(tileData[num].substring(12, 15).trim()).intValue() - 1;
    	tileAbove = new Integer(tileData[num].substring(15, 18).trim()).intValue() - 1;
    	tileNumber = num;
//    	System.out.println("Tile " + tileNumber + ", Level " + level + ", Row " + row + ", Column " + column);
        taken = false;
        selected = false;
        character = "X";
    }
    public int getTileNumber()
	{
		return tileNumber;
	}
	public static void loadData(Applet a)
    {
		String jpgFile = "";
		String path = ""; 
    	for (int i = 0; i < letterSet.length(); i++)
    	{
	   		try
	   		{
	   			String param = "data" + ToMarUtils.formatNumber(i+1, 2);
	   			jpgFile = a.getParameter(param);
 				path = a.getParameter("site") + "SHG/images/";
	   			String jpgName = jpgFile.substring(0, jpgFile.indexOf("."));
	   			String jpgLetter = letterSet.substring(i, i+1); 
	   			nameTable.put(jpgLetter, jpgName);
				new ImageFromURL(imageTable, jpgLetter, path + jpgFile, size, size);
		   	}
		   	catch (Exception e)
		   	{
		   		SHG.log("Error with image " + jpgFile + ": " + e);
		   	}
    	}
    }
    
    public void setSelected(boolean value)
    {
        selected = value;
    }
    public boolean isSelected()
    {
        return selected;
    }    
    public boolean wasClicked(int cx, int cy)
    {
        if  (cx >= getX() && cx <= getX() + size)
        {
            if  (cy >= getY() && cy <= getY() + size)
            {
                return true;
            }
        }
        return false;
    }    
	public void setCharacter(String s)
	{
		character = s;
        name = (String) nameTable.get(character);
	}
	public Vector countUnder144()
	{
		for (int i = 0; i < under144.length; i++)
		{
			if (this.getTileNumber() == under144[i])
			{
				if (this.getChar().equalsIgnoreCase(getTiles()[144].getChar()))
				{
					under144s.addElement(new Integer(this.getTileNumber()));
				}
				break;
			}	
		}
		return under144s;
	}
	public Vector countDoubles(Tile t1)
	{
		doubles = new Vector();
    	if (t1.tileAbove > -1)
    	{
    		Tile t2 = tiles[t1.tileAbove];
    		if (character.equalsIgnoreCase(tiles[tileAbove].getChar()))
    		{
				doubles.addElement(new Integer(t1.getTileNumber()));
    		}
    		if (t2.getTileAbove() > -1)
    		{
	    		Tile t3 = tiles[t2.getTileAbove()];
	    		if (character.equalsIgnoreCase(tiles[t2.getTileAbove()].getChar()))
	    		{
					doubles.addElement(new Integer(t1.getTileNumber()));
	    		}
	    		if (t3.getTileAbove() > -1)
	    		{
		    		Tile t4 = tiles[t3.getTileAbove()];
		    		if (character.equalsIgnoreCase(tiles[t3.getTileAbove()].getChar()))
		    		{
						doubles.addElement(new Integer(t1.getTileNumber()));
		    		}
		    		if (t4.getTileAbove() > -1)
		    		{
			    		if (character.equalsIgnoreCase(tiles[t4.getTileAbove()].getChar()))
			    		{
							doubles.addElement(new Integer(t1.getTileNumber()));
			    		}
		    		}
	    		}
    		}
    	}
    	return doubles;
	}
    public void setCharacter(Random rnd)
    {
    	if (tileNumber == 0 & fixFlag)
    	{
    		character = tiles[144].getChar();
    	}
    	else
    	{	
	    	int n = 0;
	    	while (true)
	    	{	
		   		n = rnd.nextInt();
		   		n = (n < 0) ? -n : n;
		    	n %= characterUniverse.length();
		    	character = characterUniverse.substring(n, n + 1);
		    	if (countDoubles(this).size() > 1)
	    		{
	   				// fixes 1526 puzzles - by choosing a different character if there are already 2 of that on this stack
	   				continue;
	   			}
		    	if (countUnder144().size() > 2)
		    	{
		    		// 60 puzzles have all 3 144 matches underneath it. 
	//	    		System.out.println("Seed " + seed + " has under144 problem on tile " + this.tileNumber + "; others are " + under144s.elementAt(0) + " and " + under144s.elementAt(1));
		    		tiles[0].setCharacter(character);
		    		fixFlag = true;
		        	characterUniverse = characterUniverse.substring(0, n) + characterUniverse.substring(n + 1);
		    		under144s.removeElementAt(under144s.size() - 1);
		    		continue;
		    	}
	   			break;
	    	}
	    	characterUniverse = characterUniverse.substring(0, n) + characterUniverse.substring(n + 1);
    	}	
        name = (String) nameTable.get(character);
        ((Vector) chars.get(character)).addElement(new Integer(tileNumber));
    }
    
    public String getChar()
    {
        return character;
    }
    
    public int getColumn()
    {
        return column;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public boolean isTaken()
    {
        return taken;
    }    
    public void setTaken(boolean value)
    {
        taken = value;
    }    
    public int getTileToLeft()
    {
        return tileToLeft;
    }    
    public int getTileToRight()
    {
        return tileToRight;
    }    
    public int getTileAbove()
    {
        return tileAbove;
    }    
    public int getLevel()
    {
        return level;
    }    
    public void draw(Graphics og)
    {
       if  (!taken)
       {
			if  (!selected)
            {
				og.setColor(levelColors[level]);
            }
			else
            {
				og.setColor(hiColor);
            }
			int r = this.getY() + 15;
        	og.fill3DRect(this.getX(), r, size, size, true);
        	try
        	{
        		((ImageFromURL) imageTable.get(getChar())).draw(og, this.getX() + 10, r + 10);
        	}
        	catch (Exception e)
        	{	
        		og.setColor(textColor);
        		og.setFont(new Font("Arial",Font.PLAIN,42));
    			int c = this.getX();
    			if (character.equals("W"))
    			{
    				c -= 4;
    			}
    			else if (character.equals("I"))
    			{
    				c += 4;
    			}
            	og.drawString(getChar(), c + 20, r + 50);
        	}
    		og.setColor(textColor);
		}
    }
    public boolean isFree()
    {   
        // check to see if tile on top is there
        if  (tileAbove > -1 && tiles[tileAbove].isTaken() == false)
        {
            return false;
        }
        // check for tile on right
        if  (tileToRight > -1)
        {   
            if  (tiles[tileToRight].isTaken() == false)
            {
                // right is blocked, check left
                if  (tileToLeft > -1)
                {
                    if  (tiles[tileToLeft].isTaken() == false)
                    {
                        return false;
                    }
                    else if  (tileToLeft == 41 && tiles[53].isTaken() == false)
                    {
                       // special logic for tile 86
                       return false;
                    }
                    else
                    {	
                        return true;
                    }    
                }
            }    
        }
        return true;
    }    
    public boolean isVisible()
    {   
        // check to see if tile on top is there
        if  (tileAbove > -1)
        {   
            if  (tiles[tileAbove].isTaken() == false)
            {
                return false;
            }    
        }
        return true;
    }    
 	private static final String tileData[] = {
		"1  2  1  0  2  0  ",   //001
		"1  3  1  1  3  0  ",   //002
		"1  4  1  2  4  0  ",   //003
		"1  5  1  3  5  0  ",   //004
		"1  6  1  4  6  0  ",   //005
		"1  7  1  5  7  0  ",   //006
		"1  8  1  6  8  0  ",   //007
		"1  9  1  7  9  0  ",   //008
		"1  10 1  8  10 0  ",   //009
		"1  11 1  9  11 0  ",   //010
		"1  12 1  10 12 0  ",   //011
		"1  13 1  11 0  0  ",   //012
		"2  4  1  0  14 0  ",   //013
		"2  5  1  13 15 89 ",   //014
		"2  6  1  14 16 90 ",   //015
		"2  7  1  15 17 91 ",   //016
		"2  8  1  16 18 92 ",   //017
		"2  9  1  17 19 93 ",   //018
		"2  10 1  18 20 94 ",   //019
		"2  11 1  19 0  0  ",   //020
		"3  3  1  0  22 0  ",   //021
		"3  4  1  21 23 0  ",   //022
		"3  5  1  22 24 95 ",   //023
		"3  6  1  23 25 96 ",   //024
		"3  7  1  24 26 97 ",   //025
		"3  8  1  25 27 98 ",   //026
		"3  9  1  26 28 99 ",   //027
		"3  10 1  27 29 100",   //028
		"3  11 1  28 30 0  ",   //029
		"3  12 1  29 0  0  ",   //030
		"4  2  1  85 32 0  ",   //031
		"4  3  1  31 33 0  ",   //032
		"4  4  1  32 34 0  ",   //033
		"4  5  1  33 35 101",   //034
		"4  6  1  34 36 102",   //035
		"4  7  1  35 37 103",   //036
		"4  8  1  36 38 104",   //037
		"4  9  1  37 39 105",   //038
		"4  10 1  38 40 106",   //039
		"4  11 1  39 41 0  ",   //040
		"4  12 1  40 42 0  ",   //041
		"4  13 1  41 87 0  ",   //042
		"5  2  1  85 44 0  ",   //043
		"5  3  1  43 45 0  ",   //044
		"5  4  1  44 46 0  ",   //045
		"5  5  1  45 47 107",   //046
		"5  6  1  46 48 108",   //047
		"5  7  1  47 49 109",   //048
		"5  8  1  48 50 110",   //049
		"5  9  1  49 51 111",   //050
		"5  10 1  50 52 112",   //051
		"5  11 1  51 53 0  ",   //052
		"5  12 1  52 54 0  ",   //053
		"5  13 1  53 87 0  ",   //054
		"6  3  1  0  56 0  ",   //055
		"6  4  1  55 57 0  ",   //056
		"6  5  1  56 58 113",   //057
		"6  6  1  57 59 114",   //058
		"6  7  1  58 60 115",   //059
		"6  8  1  59 61 116",   //060
		"6  9  1  60 62 117",   //061
		"6  10 1  61 63 118",   //062
		"6  11 1  62 64 0  ",   //063
		"6  12 1  63 0  0  ",   //064
		"7  4  1  0  66 0  ",   //065
		"7  5  1  65 67 119",   //066
		"7  6  1  66 68 120",   //067
		"7  7  1  67 69 121",   //068
		"7  8  1  68 70 122",   //069
		"7  9  1  69 71 123",   //070
		"7  10 1  70 72 124",   //071
		"7  11 1  71 0  0  ",   //072
		"8  2  1  0  74 0  ",   //073
		"8  3  1  73 75 0  ",   //074
		"8  4  1  74 76 0  ",   //075
		"8  5  1  75 77 0  ",   //076
		"8  6  1  76 78 0  ",   //077
		"8  7  1  77 79 0  ",   //078
		"8  8  1  78 80 0  ",   //079
		"8  9  1  79 81 0  ",   //080
		"8  10 1  80 82 0  ",   //081
		"8  11 1  81 83 0  ",   //082
		"8  12 1  82 84 0  ",   //083
		"8  13 1  83 0  0  ",   //084
		"9  1  1  0  31 0  ",   //085
		"9  16 1  0  0  0  ",   //086
		"9  14 1  42 88 0  ",   //087
		"9  15 1  87 0  0  ",   //088
		"2  5  2  0  90 0  ",   //089
		"2  6  2  89 91 0  ",   //090
		"2  7  2  90 92 0  ",   //091
		"2  8  2  91 93 0  ",   //092
		"2  9  2  92 94 0  ",   //093
		"2  10 2  93 0  0  ",   //094
		"3  5  2  0  96 0  ",   //095
		"3  6  2  95 97 125",   //096
		"3  7  2  96 98 126",   //097
		"3  8  2  97 99 127",   //098
		"3  9  2  98 100128",   //099
		"3  10 2  99 0  0  ",   //100
		"4  5  2  0  1020  ",   //101
		"4  6  2  101103129",   //102
		"4  7  2  102104130",   //103
		"4  8  2  103105131",   //104
		"4  9  2  104106132",   //105
		"4  10 2  1050  0  ",   //106
		"5  5  2  0  1080  ",   //107
		"5  6  2  107109133",   //108
		"5  7  2  108110134",   //109
		"5  8  2  109111135",   //110
		"5  9  2  110112136",   //111
		"5  10 2  1110  0  ",   //112
		"6  5  2  0  1140  ",   //113
		"6  6  2  113115137",   //114
		"6  7  2  114116138",   //115
		"6  8  2  115117139",   //116
		"6  9  2  116118140",   //117
		"6  10 2  1170  0  ",   //118
		"7  5  2  0  1200  ",   //119
		"7  6  2  1191210  ",   //120
		"7  7  2  1201220  ",   //121
		"7  8  2  1211230  ",   //122
		"7  9  2  1221240  ",   //123
		"7  10 2  1230  0  ",   //124
		"3  6  3  0  1260  ",   //125
		"3  7  3  1251270  ",   //126
		"3  8  3  1261280  ",   //127
		"3  9  3  1270  0  ",   //128
		"4  6  3  0  1300  ",   //129
		"4  7  3  129131141",   //130
		"4  8  3  130132142",   //131
		"4  9  3  1310  0  ",   //132
		"5  6  3  0  1340  ",   //133
		"5  7  3  133135143",   //134
		"5  8  3  134136144",   //135
		"5  9  3  1350  0  ",   //136
		"6  6  3  0  1380  ",   //137
		"6  7  3  1371390  ",   //138
		"6  8  3  1381400  ",   //139
		"6  9  3  1390  0  ",   //140
		"4  7  4  0  142145",   //141
		"4  8  4  1410  145",   //142
		"5  7  4  0  144145",   //143
		"5  8  4  1430  145",   //144
		"9  16 1  0  0  0  "   //086 loaded again
			};
 	public static void main (String args[])
 	{
 		Applet a = new Applet();
		Tile.loadData(a);
		for (int i = 0; i < 100000; i++)
		{	
			Tile.newPuzzle(i, a);
		}
		System.out.println("done");
 	}	
}