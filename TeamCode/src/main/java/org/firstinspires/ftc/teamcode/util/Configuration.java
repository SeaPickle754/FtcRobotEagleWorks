package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {
	// multipliers for driving and turning
	// stick sensitivity
	public static double TURN_SCALE=1.f;
	public static double DRIVE_SCALE=1.f;
	
	// motor RPM for movement/flywheel motors
	public static double MOTOR_RPM=6000.f;

	public static double GEAR_RATIO=1.f/20.f;
    public static double MOTOR_RPS=(MOTOR_RPM/60)*GEAR_RATIO;
    
    public static double DRIVE_ACCELERATION=5.0/2.0;
    
    //ODO X and Y offsets
    public static double OFFSET_Y = 10;
    public static double OFFSET_X = 10;
    // PID for launcher
    public static double LAUNCHER_PID_P=10.0;
    public static double LAUNCHER_PID_I=3.0;
    public static double LAUNCHER_PID_D=0.0;

	// LAUNCHER CONSTANTS
	// ================================================================
	// Current drawn by launcher flywheel motor when it is
	// stalled on an endstop. 
	public static double ENDSTOP_THRESHOLD_CURRENT = 1.0f; // A
	// 1:FLYWHEEL_GEAR_RATIO
	// eg. if FLYWHEEL_GEAR_RATIO was 5 then gearbox is 5:1
	public static double FLYWHEEL_GEAR_RATIO = 3; 
	public static double LAUNCHER_FEED_SPEED = 1/3;

	public static double LAUNCH_MULTIPLIER = 1.f;

	public static double INTAKE_SPEED = 6*Math.PI;
    
}
