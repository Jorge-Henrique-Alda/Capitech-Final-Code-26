package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Driver.DriverSubsystem;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final DriverSubsystem drivetrain = new DriverSubsystem();

  private final XboxController controller1 = new XboxController(0);


  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }


  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        break;
      case kDefaultAuto:
      default:

        break;
    }
  }


  @Override
  public void teleopInit() {}


  @Override
  public void teleopPeriodic() {
    double velocidade = -MathUtil.applyDeadband(controller1.getLeftY(), 0.1);
    double giro = -MathUtil.applyDeadband(controller1.getLeftX(), 0.1);

    drivetrain.arcadeDrive(velocidade, giro);
  }


  @Override
  public void disabledInit() {
    drivetrain.stop();
  }


  @Override
  public void disabledPeriodic() {}


  @Override
  public void testInit() {}


  @Override
  public void testPeriodic() {}


  @Override
  public void simulationInit() {}


  @Override
  public void simulationPeriodic() {}
}
