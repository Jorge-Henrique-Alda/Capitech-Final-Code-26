package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Driver.DriverSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final DriverSubsystem drivetrain = new DriverSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();

  private final XboxController controller1 = new XboxController(0);
  private final XboxController controller2= new XboxController(1);

  private boolean lastButtonAState = false;
  private boolean lastButtonBState = false;
  private boolean lastButtonXState = false;
  private boolean lastButtonYState = false;
  private boolean lastRightButtonState = false;
  private boolean lastLeftButtonState = false;
  private boolean lastRightTriggerState = false;
  private boolean lastLeftTriggerState = false;

  boolean currentA = controller1.getAButton();
  boolean currentB = controller1.getBButton();
  boolean currentX = controller1.getXButton();
  boolean currentY = controller1.getYButton();
  boolean currentRB = controller1.getRightBumperButton();
  boolean currentLB = controller1.getLeftBumperButton();
  boolean currentRT = controller1.getRightTriggerAxis() > 0.1;
  boolean currentLT = controller1.getLeftTriggerAxis() > 0.1;

  private void setAllOFF() {
    intake.setOFF();
    shooter.setOFF();
  }

  private void setAllLOW() {
    intake.setLOW();
    shooter.setLOW();
  }


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

    if (currentRT && !lastRightTriggerState) {
      shooter.toggleShooter();
    }

    if (currentY && !lastButtonYState) {
      intake.toggleIntake();
    }

    if (currentA && !lastButtonAState) {
      intake.toggleOuttake();
      shooter.toggleSOuttake();
    }

    if (currentB && !lastButtonBState) {
      setAllOFF();
    }

    if (currentX && !lastButtonXState) {
      setAllLOW();
    }


    lastButtonAState = currentA;
    lastButtonYState = currentY;
    lastButtonBState = currentB;
    lastButtonXState = currentX;
    lastRightButtonState = currentRB;
    lastLeftButtonState = currentLB;
    lastRightButtonState = currentRT;
    lastLeftTriggerState = currentLT;

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
