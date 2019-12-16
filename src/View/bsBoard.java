package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.util.ArrayList;

public class bsBoard extends JPanel implements MouseListener, MouseMotionListener {

    private bsImages imgs;
    private byte btX, btY;

    public bsBoard() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    void SetImages( bsImages imgList ) {

        imgs = imgList;

    }

    public void paintComponent(Graphics g) {

        int iX = this.getWidth();
        int iY = this.getHeight();

        int iOffsetX = 30;
        int iOffsetY = 30;

        ArrayList<BufferedImage> iList = imgs.imgBSList;

        g.setColor(Color.BLACK);
        //g.drawOval(0, 0, iX, iY);

        for (int i = 0; i < 10; i ++ ) {
            g.drawRect(iOffsetX + i * bsImages.img_SIZE_X, 0, bsImages.img_SIZE_X - 1, bsImages.img_SIZE_Y - 1 );
            for (int j = 0; j < 10; j++) {
                g.drawImage(iList.get(0), iOffsetX + i * bsImages.img_SIZE_X, iOffsetY + j * bsImages.img_SIZE_Y, null);
                g.drawRect(0, iOffsetY + j * bsImages.img_SIZE_Y, bsImages.img_SIZE_X - 1, bsImages.img_SIZE_Y - 1 );
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked()");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed()");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased()");
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
