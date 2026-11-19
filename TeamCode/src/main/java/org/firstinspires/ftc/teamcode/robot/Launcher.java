package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.Configuration;
import org.firstinspires.ftc.teamcode.util.Conversion;
import org.firstinspires.ftc.teamcode.Components.MotorController;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

enum FLYWHEEL_STATE{
        FEEDING_BALL,
        FIRING,
        IDLE,
};

public class Launcher {
    // PID Settings
    MotorController launch_motor;
    // we have 2 motors for the intake motor. 
    // There is a left side motor and right side motor.
    // If they go the wrong direction, change the motor direction on initialization
    MotorController intakeMotorL;
    MotorController intakeMotorR;   
    
    FLYWHEEL_STATE current_state = FLYWHEEL_STATE.IDLE;

    public Launcher(HardwareMap hardwareMap) {
        launch_motor = new MotorController(hardwareMap, "launch_motor", DcMotorSimple.Direction.REVERSE);
        intakeMotorL = new MotorController(hardwareMap, "intakeL", DcMotorSimple.Direction.REVERSE);
        intakeMotorR = new MotorController(hardwareMap, "intakeR", DcMotorSimple.Direction.FORWARD);
                                                                 // ^^^^ Change this if the motors fight each other
    
        }

    public void launch_ball(){
        // calculate speed to go backwards and feed ball in rads/sec
        double speed = ((Configuration.MOTOR_RPM/Configuration.FLYWHEEL_GEAR_RATIO)*Configuration.LAUNCHER_FEED_SPEED)*(Math.PI/30);
        speed*=-1;
        // set backwards & wait until the motor 
        launch_motor.setVelocity(speed);
        current_state = FLYWHEEL_STATE.FEEDING_BALL;

    }
    
    public bool isReadyToFire(){
        if(current_state = FLYWHEEL_STATE.IDLE){
            return true;
        }
        return false;
    }

    public void update(Telemetry telemetry){
        // send the motor's current to the telemetry console
        telemetry.addData("Launcher Motor Current", String.format("%.3f", launch_motor.getCurrent(CurrentUnit.AMPS)));
        
        if(current_state == FLYWHEEL_STATE.FEEDING_BALL){
            // if we detect the endstop while going backwards,
            // switch the launcher to firing mode and spin full speed forward
            if(detect_endstop(launch_motor)){
                current_state = FLYWHEEL_STATE.FIRING;
                launch_motor.setVelocity(Configuration.MOTOR_RPM*Configuration.LAUNCH_MULTIPLIER);
            }
        }
        else if(current_state == FLYWHEEL_STATE.FIRING){
            if(detect_endstop(launch_motor)){
                launch_motor.setVelocity(0);
                current_state = FLYWHEEL_STATE.IDLE;
            }
        }
    }

    private boolean detect_endstop(MotorController motor){
        if(motor.getCurrent(CurrentUnit.AMPS)>=Configuration.ENDSTOP_THRESHOLD_CURRENT){
            return true;
        }
        return false;
    }
}
