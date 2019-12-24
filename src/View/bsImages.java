package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;

public class bsImages {

    private static final String sPath = "C:/Java/x.png";
    private static final BufferedImage bufImg;

    public static final int img_SIZE_X = 30;
    public static final int img_SIZE_Y = 30;

    public static final int img_BACKGROUND = 0;
    public static final int img_BACKGROUND_TARGET_PASS = 1;
    public static final int img_SHIP_ELEMENT = 2;
    public static final int img_SHIP_INJURED_MINE = 3;
    public static final int img_SHIP_INJURED_OPPONENT = 4;
    public static final int img_BACKGROUND_SELECTED = 5;

    public static ArrayList<BufferedImage> imgBSList;

    private void bsImages() {

    }

    static {

        try {
            File file = new File( "C:\\Java\\png\\x.png" );
            bufImg = ImageIO.read(file);
            imgBSList = new ArrayList<>();
            imgBSList.add( bufImg.getSubimage( 0 + img_BACKGROUND * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );               // обычный фон, 'вода'                        img_BACKGROUND
            imgBSList.add( bufImg.getSubimage( 0 + img_BACKGROUND_TARGET_PASS * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );   // обычный фон, 'вода' , мимо                 img_BACKGROUND_TARGET_PASS
            imgBSList.add( bufImg.getSubimage( 0 + img_SHIP_ELEMENT * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );             // палуба корабля (у игрока)                  img_SHIP_ELEMENT
            imgBSList.add( bufImg.getSubimage( 0 + img_SHIP_INJURED_MINE * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // палуба корабля, ранен(убит) (у игрока)     img_SHIP_INJURED_MINE
            imgBSList.add( bufImg.getSubimage( 0 + img_SHIP_INJURED_OPPONENT * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );    // палуба корабля, ранен(убит) (у противника) img_SHIP_INJURED_OPPONENT
            imgBSList.add( bufImg.getSubimage( 0 + img_BACKGROUND_SELECTED * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );      // выделенная обычная ячейка                  img_BACKGROUND_SELECTED
        } catch( IOException ioEx )
        {
            throw new UncheckedIOException( ioEx );
        }


    }

}
