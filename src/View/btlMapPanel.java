package View;

import Model.modBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class btlMapPanel extends JPanel {

    private btlImages cgImages;
    public byte[][]   boardMap;
    private boolean   bRightPanel = false;

    public void setVariables( btlImages cImage, boolean bRPanel, byte[][] brdMap ) {
        cgImages = cImage;
        bRightPanel = bRPanel;
        boardMap = brdMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ( ( cgImages == null ) || ( boardMap == null) ) return;
        BufferedImage imgBACKGROUND, imgBACKGROUNDT, imgSHIPELEM, imgSHIPELEMT;
        if ( bRightPanel ) {
            imgBACKGROUND = cgImages.imgBSList.get( btlImages.BACKGROUND );
            imgBACKGROUNDT = cgImages.imgBSList.get( btlImages.BACKGROUND_TARGET_PASS );
            imgSHIPELEM = cgImages.imgBSList.get( btlImages.SHIP_ELEMENT );
            imgSHIPELEMT = cgImages.imgBSList.get( btlImages.SHIP_INJURED_OPPONENT );
        }
        else {
            imgBACKGROUND = cgImages.imgBSList.get(btlImages.BACKGROUND);
            imgBACKGROUNDT = cgImages.imgBSList.get(btlImages.BACKGROUND_TARGET_PASS);
            imgSHIPELEM = cgImages.imgBSList.get( btlImages.SHIP_ELEMENT );
            imgSHIPELEMT = cgImages.imgBSList.get( btlImages.SHIP_INJURED_MINE );
        }
        for ( int i = 0; i < boardMap.length; i ++ )
            for (int j = 0; j < boardMap[i].length; j++)
                g.drawImage( ( boardMap[i][j] == 0 ) ? imgBACKGROUND : ( ( boardMap[i][j] == 1 ) ? imgBACKGROUNDT : ( ( boardMap[i][j] == 2 ) ? imgSHIPELEM : imgSHIPELEMT ) ), btlImages.SIZE_X * i , btlImages.SIZE_Y * j, null );
    }

}
