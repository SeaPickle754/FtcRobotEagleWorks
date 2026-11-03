package org.firstinspires.ftc.teamcode;

// imports for the virual class for the opmode
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// robot movement
import org.firstinspires.ftc.teamcode.robot.Movement;

// managers
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.TransformManager;

//needed when we initialize the transform manager
import org.firstinspires.ftc.teamcode.util.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;


@TeleOp
public class ManualDrive extends LinearOpMode {
    public Movement robotMovement;
    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    @Override
    public void runOpMode() {
//        launcher=new Launcher(hardwareMap);
        robotMovement = new Movement(hardwareMap); // Pass the ability to interact with hardware
//        robotSensors = new Sensors(
//                hardwareMap);
        // Wait until the play button is pressed
        



		while (opModeInInit()) {
            telemetry.addData("Status", "Ready to Start");
            telemetry.update();
        }
        IMU imu=hardwareMap.get(IMU.class,"imu");
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
		TransformManager.getInstance().init(odo, imu);
		InputManager.getInstance().init(gamepad1, gamepad2);

        while (opModeIsActive()) {
        	TransformManager.getInstance().updateOdo();
        	
        	send_odo_telemetry();
            update_movement();
		}
    }
    private void update_movement() {
    	robotMovement.updateMovementFromSticks();
    	robotMovement.UpdateRobot(telemetry);
    }
    private void send_odo_telemetry() {
    	telemetry.addData("Odo X", TransformManager.getInstance().odo_getPositionX());
    	telemetry.addData("Odo Y", TransformManager.getInstance().odo_getPositionY());
    	telemetry.addData("Odo Heading", TransformManager.getInstance().odo_getOrientation());
    }
}
