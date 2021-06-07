
import java.awt.Point;

class ChessBoard
{
   public static final int MAX        = 61;
   public static final int CENTER_X   = MAX / 2;
   public static final int CENTER_Y   = MAX / 2;

   private int[][]     board      = new int [MAX][MAX];
   private boolean[][] squareUsed = new boolean [MAX][MAX];

   /*
     Square Spiral:
     Keep turning left and stepping out when you need to ...

     17 16 15 14 13
     18  5  4  3 12
         6  1  2 11
         7  8  9 10

    135 Deg  45 Deg
         \   /
          \ /
           1    We keep going UP until we find a spare LEFT cell
          / \
         /   \  We keep going RIGHT until we find a spare UP cell
   225 Deg   315 Deg



   -----------------


   */
   public ChessBoard ()
   {
      board [CENTER_X][CENTER_Y] = 1;

      int x = CENTER_X + 1;
      int y = CENTER_Y;

      int xInc = 1;
      int yInc = 0;
      int angle = 0;
      int nextCount = 1;

      while (nextCount <= MAX * MAX)
      {
         // Get Angle from current square to Center Square:
         // Delta X / Delta Y
         // Pi Radians = 180 degrees.  ==>  y = x * 180 / Pi.
         //  X Radians = Y Degrees

         // acos(double a): Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi.
/*
         double angleD = 0.0;
         if (y != CENTER_Y)
         {
            double dXdY = 1.0 * (x - CENTER_X) / (y - CENTER_Y);
            angleD = Math.acos (dXdY) * 180.0 / Math.PI;
         }

         if (angleD >= 315.0)  && (angleD <= 45.0)
         {

         }
*/

         if ((angle == 0) && (board[x][y+1] == 0) )        // Going RIGHT and UP cell is empty ?
         {
            angle = 90;   // Turn UP
            xInc  =  0;   // Move UP
            yInc  =  1;
         }
         else if ((angle == 90) && (board[x-1][y] == 0) )   // Going UP and LEFT cell is empty ?
         {
            angle = 180;  // Turn LEFT
            xInc  =  -1;  // Move LEFT
            yInc  =   0;
         }
         else if ((angle == 180) && (board[x][y-1] == 0) )  // Going LEFT and DOWN cell is empty ?
         {
            angle = 270;  // Turn DOWN
            xInc  =   0;  // Move DOWN
            yInc  =  -1;
         }

         else if ((angle == 270) && (board[x+1][y] == 0) )  // Going DOWN and RIGHT cell is empty ?
         {
            angle =   0;  // Turn RIGHT
            xInc  =   1;  // Move RIGHT
            yInc  =   0;
         }

         x += xInc;
         y += yInc;

         board [x][y] = nextCount;

         //System.out.println ("board [" + x + "][" + y + "]= " + nextCount);

         nextCount++;
      }
   }

   @Override
   public String toString()
   {
      StringBuilder sb    = new StringBuilder();
      int colWidth        = ("" + (MAX * MAX)).length();  // Num digits in largest number.
      String formatStr    = "%" + colWidth + "d";

      for (int row = 0; row < MAX; row++)
      {
         for (int col = 0; col < MAX; col++)
         {
            sb.append (String.format (formatStr, board[row][col]) );

            if (squareUsed [row][col] == true)
               sb.append ("*");

            sb.append ("  ");

         }
         sb.append ("\n");
      }

      return sb.toString();
   }


   /*
      row - 1, col - 2
      row - 2, col - 1

      row - 1, col + 2
      row - 2, col + 1

      row + 1, col - 2
      row + 2, col - 1

      row + 1, col + 2
      row + 2, col + 1

      ---------------------
      |   | X |   | X |   |
      ---------------------
      | X |   |   |   | X |
      ---------------------
      |   |   | S |   |   |
      ---------------------
      | X |   |   |   | X |
      ---------------------
      |   | X |   | X |   |
      ---------------------
   */

   private boolean isNewMinValue (int currMinValue, int row, int col)
   {
      boolean result = false;

      if ((row >= 0) && (row < MAX) &&
          (col >= 0) && (col < MAX) &&
          (currMinValue > board [row][col])  &&
          (squareUsed [row][col] == false)   )
      {
         result = true;
      }

      return result;
   }

   private Point getMinKnightMove (Point startPoint)
   {
      int minValue = Integer.MAX_VALUE;
      int minRow   = 0;
      int minCol   = 0;
      int row      = (int) startPoint.getX ();
      int col      = (int) startPoint.getY ();


      if (isNewMinValue (minValue, row - 1, col - 2) == true)
      {
         minValue = board [row - 1][col - 2];
         minRow   = row - 1;
         minCol   = col - 2;
      }

      if (isNewMinValue (minValue, row - 2, col - 1) == true)
      {
         minValue = board [row - 2][col - 1];
         minRow   = row - 2;
         minCol   = col - 1;
      }

      if (isNewMinValue (minValue, row - 1, col + 2) == true)
      {
         minValue = board [row - 1][col + 2];
         minRow   = row - 1;
         minCol   = col + 2;
      }

      if (isNewMinValue (minValue, row - 2, col + 1) == true)
      {
         minValue = board [row - 2][col + 1];
         minRow   = row - 2;
         minCol   = col + 1;
      }



      if (isNewMinValue (minValue, row + 1, col - 2) == true)
      {
         minValue = board [row + 1][col - 2];
         minRow   = row + 1;
         minCol   = col - 2;
      }

      if (isNewMinValue (minValue, row + 2, col - 1) == true)
      {
         minValue = board [row + 2][col - 1];
         minRow   = row + 2;
         minCol   = col - 1;
      }

      if (isNewMinValue (minValue, row + 1, col +2) == true)
      {
         minValue = board [row + 1][col + 2];
         minRow   = row + 1;
         minCol   = col + 2;
      }

      if (isNewMinValue (minValue, row + 2, col + 1) == true)
      {
         minValue = board [row + 2][col + 1];
         minRow   = row + 2;
         minCol   = col + 1;
      }

      if (minValue < Integer.MAX_VALUE)
      {
         squareUsed [minRow][minCol] = true;

         return new Point (minRow, minCol);
      }
      else
      {
         return null;
      }
   }

   public void makeAllKnightMoves ()
   {
      squareUsed [CENTER_X][CENTER_Y] = true;

      Point currMinPoint = new Point (CENTER_X, CENTER_Y);

      boolean keepGoing = true;

      while (keepGoing == true)
      {
         Point newMinPoint = getMinKnightMove (currMinPoint);

         if (newMinPoint == null)
         {
            System.out.println (" -> Stopped at " + currMinPoint  + ": " +
                                board[(int) currMinPoint.getX()][(int) currMinPoint.getY()] );

            keepGoing = false;
         }

         currMinPoint = newMinPoint;
      }
   }
}



public class TrappedKnight
{
   public static void main (String[] args)
   {
      ChessBoard board = new ChessBoard ();

      board.makeAllKnightMoves ();

      System.out.println (board.toString() );

   }
}