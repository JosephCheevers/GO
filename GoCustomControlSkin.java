package gogame;

import javafx.scene.control.SkinBase;

//class definition for a skin for the control
//NOTE: to keep JavaFX happy we don't use the skin here
class GoCustomControlSkin extends SkinBase<GoCustomControl> {
	// default constructor for the class
	public GoCustomControlSkin(GoCustomControl rc) {
		super(rc);
	}
}