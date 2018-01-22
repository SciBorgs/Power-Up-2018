package org.usfirst.frc.team1155.robot.subsystems;

import org.usfirst.frc.team1155.robot.PortMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem{

	public TalonSRX leftIntakeMotor, rightIntakeMotor, leftIntakeMotor2, rightIntakeMotor2;
	public DoubleSolenoid armSolenoid, tiltSolenoid;
	
	public void initDefaultCommand() {

		leftIntakeMotor = new TalonSRX(PortMap.DRIVE_FRONT_LEFT_TALON);
		rightIntakeMotor = new TalonSRX(PortMap.DRIVE_FRONT_RIGHT_TALON);
		leftIntakeMotor2 = new TalonSRX(PortMap.DRIVE_BACK_LEFT_TALON);
		rightIntakeMotor2 = new TalonSRX(PortMap.DRIVE_BACK_RIGHT_TALON);

		armSolenoid = new DoubleSolenoid(PortMap.INTAKE_SOLENOID_LEFT[0], PortMap.INTAKE_SOLENOID_LEFT[1]);
		tiltSolenoid = new DoubleSolenoid(PortMap.INTAKE_SOLENOID_RIGHT[0], PortMap.INTAKE_SOLENOID_RIGHT[1]);
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		stop();
	}	
	
	public void stop() {
		leftIntakeMotor.set(ControlMode.PercentOutput, 0);
		rightIntakeMotor.set(ControlMode.PercentOutput, 0);
		leftIntakeMotor2.set(ControlMode.PercentOutput, 0);
		rightIntakeMotor2.set(ControlMode.PercentOutput, 0);
	}
	
	public void setSpeed(double speed){
		leftIntakeMotor.set(ControlMode.PercentOutput, -speed);
		rightIntakeMotor.set(ControlMode.PercentOutput, -speed);
		leftIntakeMotor2.set(ControlMode.PercentOutput, speed);
		rightIntakeMotor2.set(ControlMode.PercentOutput, speed);
	}
	public void extendArmPiston() {
		armSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void retractArmPiston() {
		armSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public void extendTiltPiston() {
		tiltSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void retractTiltPiston() {
		tiltSolenoid.set(DoubleSolenoid.Value.kReverse);
	}		
	
}
