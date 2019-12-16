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


    public static ArrayList<BufferedImage> imgBSList;

    private void bsImages() {

    }

    static {

        try {
            File file = new File( "C:\\Java\\png\\x.png" );
            bufImg = ImageIO.read(file);
            imgBSList = new ArrayList<>();
            imgBSList.add( bufImg.getSubimage( 0 + 0 * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // обычный фон, 'вода'
            imgBSList.add( bufImg.getSubimage( 0 + 1 * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // обычный фон, 'вода' , мимо
            imgBSList.add( bufImg.getSubimage( 0 + 2 * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // палуба корабля (у игрока)
            imgBSList.add( bufImg.getSubimage( 0 + 3 * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // палуба корабля, ранен(убит) (у игрока)
            imgBSList.add( bufImg.getSubimage( 0 + 4 * img_SIZE_X, 0, img_SIZE_X, img_SIZE_Y ) );        // палуба корабля, ранен(убит) (у противника)
        } catch( IOException ioEx )
        {
            throw new UncheckedIOException( ioEx );
        }


    }

}
