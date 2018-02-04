/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1155.robot;

import java.io.File;
import java.io.IOException;

import org.usfirst.frc.team1155.robot.commands.*;
import org.usfirst.frc.team1155.robot.subsystems.*;

import com.ctre.phoenix.sensors.PigeonIMU;

import api.Client;
import api.Path;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static OI m_oi;
	
	public static DriveSubsystem driveSubsystem;
	public static LiftSubsystem liftSubsystem;
	public static ClimbSubsystem climbSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static ADXRS450_Gyro Gyro;
	public static File file;
	public static Path path;
	public static int PointTwoMeters[];
	
//	public static PigeonIMU pigeon;
	public static BuiltInAccelerometer accel;
	
	public static Timer timer;
	public static DesCartesianPlane plane;

	public static Client client;

	public static short[] shortArr;
	public static double[] anglesYPR;

	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		driveSubsystem = new DriveSubsystem();
		liftSubsystem = new LiftSubsystem();
		climbSubsystem = new ClimbSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		Gyro = new ADXRS450_Gyro();
        try {
            client = new Client();
        } catch (IOException e) {
            System.out.println("could not start client");
        }
	/*	file = new File("test.path");
		try {
			path = new Path(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/ 
		timer = new Timer();
		accel = new BuiltInAccelerometer();
//		pigeon = new PigeonIMU(driveSubsystem.frontLeftMotor);
		plane = new DesCartesianPlane(timer, accel);
		PointTwoMeters = new int[2];
		shortArr = new short[3];
		anglesYPR = new double[3];
		PointTwoMeters[0] = 0;
		PointTwoMeters[1] = 80;
		System.out.println(plane.getX() + ", " + plane.getY());
		//pigeon.enterCalibrationMode(CalibrationMode.BootTareGyroAccel, 3000);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		System.out.println(plane.getX() + ", " + plane.getY());
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	//	Scheduler.getInstance().run();
		//plane.updatePosition();
		System.out.print("DESCARTES");

		//System.out.println(plane.getX() + ", " + plane.getY());
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		System.out.println(plane.getX() + ", " + plane.getY());
		new WestCoastDriveCommand(OI.leftJoystick, OI.rightJoystick).start();
		//new PlaceCommand().start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
//		pigeon.getYawPitchRoll(anglesYPR);
		plane.updatePosition();
		SmartDashboard.putNumber("Xceleration", plane.getAx());
		SmartDashboard.putNumber("Yceleration", plane.getAy());
		SmartDashboard.putNumber("Xvelocity", plane.getVx());
		SmartDashboard.putNumber("Yvelocity", plane.getVy());
		SmartDashboard.putNumber("Xposition", plane.getX());
		SmartDashboard.putNumber("Yposition", plane.getY());
		
		SmartDashboard.putNumber("Yawwwww", anglesYPR[0]);
		
		SmartDashboard.putNumber("shortArr[0]", shortArr[0]);
		SmartDashboard.putNumber("shortArr[1]", shortArr[1]);
		SmartDashboard.putNumber("shortArr[2]", shortArr[2]);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		System.out.println(plane.getX() + ", " + plane.getY());
	}
}
