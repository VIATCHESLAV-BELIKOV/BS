package View;

import javax.swing.*;

public class bsSplitPane extends JSplitPane {
    private final int location = 338;

    public bsSplitPane(int iValue) {
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
