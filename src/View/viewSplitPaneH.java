package View;

import javax.swing.*;

public class viewSplitPaneH extends JSplitPane {

    private final int location = 335;

    public viewSplitPaneH(int iValue) {
        super(iValue);
    }

    //  @Override
    //  public int setDividerLocation() {
    //      setDividerLocation( location );
    //  }

    @Override
    public int getDividerLocation() {
        return location ;
    }

    @Override
    public int getLastDividerLocation() {
        return location ;
    }

    @Override
    public void setEnabled( boolean bValue ) {
        super.setEnabled(bValue);
    }

}
