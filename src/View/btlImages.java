package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;

public class btlImages {

    private static final String sPath = "C:/Java/x.png";
    private static final BufferedImage bufImg;

    public static final int SIZE_X = 30;
    public static final int SIZE_Y = 30;

    public static final int BACKGROUND = 0;
    public static final int BACKGROUND_TARGET_PASS = 1;
    public static final int SHIP_ELEMENT = 2;
    public static final int SHIP_INJURED_MINE = 3;
    public static final int SHIP_INJURED_OPPONENT = 4;
    public static final int BACKGROUND_SELECTED = 5;

    public static ArrayList<BufferedImage> imgBSList;

    static {

        try {
            File file = new File( "C:\\Java\\png\\x.png" );
            bufImg = ImageIO.read(file);
            imgBSList = new ArrayList<>();
            imgBSList.add( bufImg.getSubimage( 0 + BACKGROUND * SIZE_X, 0, SIZE_X, SIZE_Y ) );               // обычный фон, 'вода'                        img_BACKGROUND
            imgBSList.add( bufImg.getSubimage( 0 + BACKGROUND_TARGET_PASS * SIZE_X, 0, SIZE_X, SIZE_Y ) );   // обычный фон, 'вода' , мимо                 img_BACKGROUND_TARGET_PASS
            imgBSList.add( bufImg.getSubimage( 0 + SHIP_ELEMENT * SIZE_X, 0, SIZE_X, SIZE_Y ) );             // палуба корабля (у игрока)                  img_SHIP_ELEMENT
            imgBSList.add( bufImg.getSubimage( 0 + SHIP_INJURED_MINE * SIZE_X, 0, SIZE_X, SIZE_Y ) );        // палуба корабля, ранен(убит) (у игрока)     img_SHIP_INJURED_MINE
            imgBSList.add( bufImg.getSubimage( 0 + SHIP_INJURED_OPPONENT * SIZE_X, 0, SIZE_X, SIZE_Y ) );    // палуба корабля, ранен(убит) (у противника) img_SHIP_INJURED_OPPONENT
            imgBSList.add( bufImg.getSubimage( 0 + BACKGROUND_SELECTED * SIZE_X, 0, SIZE_X, SIZE_Y ) );      // выделенная обычная ячейка                  img_BACKGROUND_SELECTED
        } catch( IOException ioEx )
        {
            throw new UncheckedIOException( ioEx );
        }


    }

}
