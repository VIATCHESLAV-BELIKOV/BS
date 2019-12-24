package View;

import Model.bsPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class bsBoard extends JPanel implements MouseListener, MouseMotionListener {

    private bsImages imgs;

    private JPopupMenu popup;

    private bsPoint ptTemp = null;

    public bsBoard(bsImages imgList) {

        addMouseListener(this);
        addMouseMotionListener(this);

        popup = new JPopupMenu();
        // add menu items to popup
        popup.add(new JMenuItem("однопалубный"));
        popup.add(new JMenuItem("двухпалубный"));
        popup.add(new JMenuItem("трехпалубный"));
        popup.add(new JMenuItem("четырехпалубный"));
        popup.addSeparator();
        popup.add(new JMenuItem("SelectAll"));

        imgs = imgList;

     }

    public bsPoint BoardPoint2Cell(int X, int Y ) {

        bsPoint pt = new bsPoint();

        pt.X = X - bsImages.img_SIZE_X;
        pt.Y = Y - bsImages.img_SIZE_Y;
        pt.X = ((pt.X <= 0) || (pt.X >= 10 * bsImages.img_SIZE_X) ) ? -1 : (int) Math.floor(pt.X / bsImages.img_SIZE_X);
        pt.Y = ((pt.Y <= 0 ) || (pt.Y >= 10 * bsImages.img_SIZE_Y)) ? -1 : (int) Math.floor(pt.Y / bsImages.img_SIZE_Y);

        return pt;

    }

    private void bsDrawImg( Graphics g, int X, int Y, int iImg ) {
        g.drawImage(imgs.imgBSList.get(iImg), bsImages.img_SIZE_X * (X + 1) , bsImages.img_SIZE_Y * (Y + 1), null);
    }

    private void bsDrawRect( Graphics g, int X, int Y ) {
        g.drawRect(X, Y, bsImages.img_SIZE_X - 1, bsImages.img_SIZE_Y - 1 );
    }

    public void paintComponent(Graphics g) {

        int iX = this.getWidth();
        int iY = this.getHeight();


        g.setColor(Color.BLACK);

        for (int i = 0; i < 10; i ++ ) {
            bsDrawRect( g,bsImages.img_SIZE_X * (i + 1), 0 );
            for (int j = 0; j < 10; j++) {
                bsDrawImg(g, i, j, bsImages.img_BACKGROUND);
                bsDrawRect( g,0, bsImages.img_SIZE_Y * (i + 1) );
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked()");
        if ( e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
            ptTemp = BoardPoint2Cell(e.getX(), e.getY());
            bsDrawImg( e.getComponent().getGraphics(), ptTemp.X, ptTemp.Y, bsImages.img_BACKGROUND_SELECTED );
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed()");
        if (ptTemp != null) {
            bsDrawImg( e.getComponent().getGraphics(), ptTemp.X, ptTemp.Y, bsImages.img_BACKGROUND );
            ptTemp = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        System.out.println("mouseReleased()");

  /*      if(e.isPopupTrigger()) {
            ptTemp = BoardPoint2Cell(e.getX(), e.getY());
            bsDrawImg( e.getComponent().getGraphics(), ptTemp.X, ptTemp.Y, bsImages.img_BACKGROUND_SELECTED );
            popup.show(e.getComponent(), e.getX(), e.getY());
        }*/

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered()");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited()");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseDragged()");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int iX = e.getX() - bsImages.img_SIZE_X;
        int iY = e.getY() - bsImages.img_SIZE_Y;
        iX = ((iX <= 0) || (iX >= 10* bsImages.img_SIZE_X) ) ? -1 : (int) Math.floor(iX / bsImages.img_SIZE_X);
        iY = ((iY <= 0 ) || (iY >= 10* bsImages.img_SIZE_Y)) ? -1 : (int) Math.floor(iY / bsImages.img_SIZE_Y);
        System.out.println("mouseMoved() " + iX + " / " + iY );
    }

}
