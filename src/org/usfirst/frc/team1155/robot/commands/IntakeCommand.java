package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.OI;
import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command{
	public IntakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (OI.rightJoystick.getPOV() == 180) {
			Robot.intakeSubsystem.setSpeed(.3);
		}
		if (OI.rightJoystick.getPOV() == 0) {
			Robot.intakeSubsystem.setSpeed(-.3);
		}
		if (OI.rightJoystick.getPOV() == -1)
			Robot.intakeSubsystem.setSpeed(0);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0);
		Robot.intakeSubsystem.retractPiston();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.intakeSubsystem.setSpeed(0);
		Robot.intakeSubsystem.retractPiston();
	}
}