import Control.btlController;
import Model.modBoard;
import View.btlView;

import java.text.ParseException;

public class BattleShip {

    static private btlController ctlr;

    public static void main(String[] args) throws ParseException {

        ctlr = new btlController();
        ctlr.initController();

    }

}
