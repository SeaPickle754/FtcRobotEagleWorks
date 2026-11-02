package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.teamcode.util.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TransformManager {
	boolean isinit = false;
	static double offsetX = 10;
	GoBildaPinpointDriver odo = null;
	static double offsetY = 10;
	// Bill Pugh Singleton Code
    // ================================================ 
		private TransformManager() {}
		
		private static class Holder {
			private static final TransformManager INSTANCE = new TransformManager();
		}
		
		public static TransformManager getInstance() {
			return Holder.INSTANCE;
		}
	// ================================================

	public void init(GoBildaPinpointDriver odo){
		this.odo = odo;
		odo.setOffsets(offsetX, offsetY, DistanceUnit.MM);
		odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
		odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
		odo.resetPosAndIMU();
		isinit = true;
	}
	// get position from odometer (mm)
	public double odo_getPositionX(){
		return odo.getPosX(DistanceUnit.MM);
	}
	public double odo_getPositionY(){
		return odo.getPosY(DistanceUnit.MM);
	}
	// returns heading in radians
	public double odo_getOrientation() {
		return odo.getHeading();
	}
	public void resetOdo() {
		odo.resetPosAndIMU();
	}
	// this needs to be called on every iteration of the loop
	public void updateOdo() {
		odo.update();
	}
}

