package Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class modBoard {

   private byte[][]           boardMap;
   private ArrayList<modShip> boardShips;

   public modBoard() {
      this.boardMap = new byte[10][10];
      this.boardShips = new ArrayList<>();
   }

   public byte[][]           getBoardMap() {
      return boardMap;
   }
   public ArrayList<modShip> getBoardShips() { return boardShips; }

   private boolean PointBelongsToShip( Point pt, modShip ship ) {
      boolean bret = false;
      if ( ship.getOrientation() == modShip.SHIP_ORIENTATION_HORZ ) {
         if ( ( pt.x >= 0 ) && ( pt.x <= 9 ) && ( pt.y >= 0 ) && ( pt.y <= 9 ) )
            for ( int i = ship.x;  i < ( ship.x + ship.getType() ); i++ )
               if ( ( pt.x == i ) && ( pt.y == ship.y ) ) {
                  bret = true;
                  break;
               }
      } else {
         if ( ( pt.x >= 0 ) && ( pt.x <= 9 ) && ( pt.y >= 0 ) && ( pt.y <= 9 ) )
            for ( int j = ship.y;  j < ( ship.y + ship.getType() ); j++ )
               if ( ( pt.x == ship.x ) && ( pt.y == j ) ) {
                  bret = true;
                  break;
               }
      }
      return bret;
   }

   private void UpdateShipState( modShip ship ) {
      byte ret = modShip.SHIP_STATE_UNDEF;
      if ( ship.getOrientation() == modShip.SHIP_ORIENTATION_HORZ ) {
         if ( ( ship.x >= 0 ) && ( ( ship.x + ship.getType() - 1 ) <= 9 ) && ( ship.y >= 0 ) && ( ship.y <= 9 ) ) {
            int countX = 0;
            for ( int i = ship.x; i < ( ship.x + ship.getType() ); i++ )
               if ( boardMap[i][ship.y] > 0 ) countX++;
            ret = ( countX == 0 ) ? modShip.SHIP_STATE_HEALTHY : ( ( countX == ( ship.getType() * 2 ) ) ? modShip.SHIP_STATE_KILLED : modShip.SHIP_STATE_INJURED );
         }
      } else {
         if ( ( ship.x >= 0 ) && ( ship.x  <= 9 ) && ( ship.y >= 0 ) && ( ( ship.y + ship.getType() - 1 ) <= 9 ) ) {
            int countX = 0;
            for ( int j = ship.y; j < ( ship.y + ship.getType() ); j++ )
               if ( boardMap[ship.x][j] > 0 ) countX++;
            ret = ( countX == 0 ) ? modShip.SHIP_STATE_HEALTHY : ( ( countX == ( ship.getType() * 2 ) ) ? modShip.SHIP_STATE_KILLED : modShip.SHIP_STATE_INJURED );
         }
      }
      ship.setState( ret );
   }

   public boolean IsAvailableNewShip( Point pt, byte btShipType, boolean bShipOrientation ) {
      boolean ret = false;
      Point ptTopLeft, ptBottomRight;
      ptTopLeft = new Point(pt);
      ptBottomRight = new Point(pt);
      if ( ( ptTopLeft.x >= 0 ) && ( ptTopLeft.y >= 0 ) )
         ptTopLeft.translate( ( ( ptTopLeft.x > 0 ) ? -1 : 0 ), ( ( ptTopLeft.y > 0 ) ? -1 : 0 ) );
      else
         return ret;
      if (bShipOrientation == modShip.SHIP_ORIENTATION_HORZ)
         ptBottomRight.x += ( btShipType - 1 );
      else
         ptBottomRight.y += ( btShipType - 1 );
      if ( ( ptBottomRight.x <= 9 ) && ( ptBottomRight.y <= 9 ) )
         ptBottomRight.translate( ( ( ptBottomRight.x < 9 ) ? 1 : 0 ), ( ( ptBottomRight.y < 9 ) ? 1 : 0 ) );
      else
         return ret;
      int countX = 0;
      for ( int i = ptTopLeft.x; i <= ptBottomRight.x; i++ )
         for ( int j = ptTopLeft.y; j <= ptBottomRight.y; j++ )
            countX += boardMap[i][j];
      if ( countX == 0 )
         ret = true;
      return ret;
   }

   public void UpdateShipStates() { boardShips.forEach( (b) -> UpdateShipState(b) ); }

   public int countShipsByType( byte btType ) { return (int)boardShips.stream().filter( (t) -> t.getType() == btType ).count(); }

   public modShip getShipFromPoint( Point pt ) {
      modShip ret = null;
      for ( modShip ship : boardShips )
         if ( PointBelongsToShip( pt, ship ) ) {
            ret = ship;
            break;
         }
      return ret;
   }

   public int getShipIndexFromPoint( Point pt ) {
      int ret = -1;
      for ( int i = 0; i < boardShips.size(); i++ )
         if ( PointBelongsToShip( pt, boardShips.get(i) ) ) {
            ret = i;
            break;
         }
      return ret;
   }

   public void addShip( Point pt, boolean bOrientation, byte btType ) {
      if ( ( pt.x >= 0 ) && ( pt.x <= 9 ) && ( pt.y >= 0 ) && ( pt.y <= 9 ) ) {
         modShip ship = new modShip( pt.x, pt.y, bOrientation, btType );
         boardShips.add(ship);
         if ( bOrientation == modShip.SHIP_ORIENTATION_HORZ ) {
            for ( int i = pt.x; i < ( pt.x + btType ); i++ )
               boardMap[i][pt.y] = 2;
         } else {
            for ( int j = pt.y; j < (pt.y + btType); j++ )
               boardMap[pt.x][j] = 2;
         }
      }
   }

   public void removeShip( int index ) {
      modShip ship = boardShips.remove(index);
      if ( ship.getOrientation() == modShip.SHIP_ORIENTATION_HORZ ) {
         for ( int i = ship.x; i < ( ship.x + ship.getType() ); i++ )
            boardMap[i][ship.y] = 0;
      } else {
         for ( int j = ship.y; j < ( ship.y + ship.getType() ); j++ )
            boardMap[ship.x][j] = 0;
      }
   }

   public void resetBoard() {
      for ( int i = 0; i < boardMap.length; i ++ )
         for (int j = 0; j < boardMap[i].length; j++)
            boardMap[i][j] = 0;
      boardShips.clear();
   }

}
