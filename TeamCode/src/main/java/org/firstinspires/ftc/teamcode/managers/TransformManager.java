package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.teamcode.util.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;



public class TransformManager {
	boolean isinit = false;
	static double offsetX = 10;
	private GoBildaPinpointDriver odo = null;
	private IMU imu = null;
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

	// imu_hwMap is jst a different name to make the inside of the function easier
	public void init(GoBildaPinpointDriver odo, IMU imu_hwMap){
		this.odo = odo;
		odo.setOffsets(offsetX, offsetY, DistanceUnit.MM);
		odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
		odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
		odo.resetPosAndIMU();
		this.imu = imu_hwMap;
		// CONTROL HUB ORIENTATION SETTINGS
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection,usbDirection);
        // Set the IMU (Gyro) Variable
        // this is fucked rn because i think that there may or may not be an imu in
        // the actual control hub
        imu.initialize(new IMU.Parameters(orientationOnRobot));
		// only angle supported for now
		imu.resetYaw();
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
	// gets the orientation from the IMU.
	// Warning: This may become unreliable over time as yaw drift may occur.
	public double imu_getOrientation() {
		YawPitchRollAngles robotOrientation;
		robotOrientation = imu.getRobotYawPitchRollAngles();
		return robotOrientation.getYaw(AngleUnit.RADIANS);
	}
	
	public void resetOdo() {
		odo.resetPosAndIMU();
	}
	// this needs to be called on every iteration of the loop
	public void updateOdo() {
		odo.update();
	}
}

