package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Configuration {
	// multipliers for driving and turning
	// stick sensitivity
	public static double TURN_SCALE=1.f;
	public static double DRIVE_SCALE=1.f;
	
	public static double MOTOR_RPM=6000.f;
	public static double GEAR_RATIO=1.f/20.f;
    public static double MOTOR_RPS=(MOTOR_RPM/60)*GEAR_RATIO;
    
    public static double DRIVE_ACCELERATION=5.0/2.0;
    
    //ODO X and Y offsets
    public static double OFFSET_Y = 10;
    public static double OFFSET_X = 10;
    
    
    
}
