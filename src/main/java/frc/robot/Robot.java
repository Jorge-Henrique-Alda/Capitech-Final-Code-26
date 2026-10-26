
package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.Subsystems.Driver.DriverSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Indexter.IndexterSubsystem;

public class Robot extends TimedRobot {

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";

    private String m_autoSelected;

    private final SendableChooser<String> m_chooser =
        new SendableChooser<>();

    private final DriverSubsystem drivetrain = new DriverSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final IndexterSubsystem indexter = new IndexterSubsystem();

    private final XboxController controller1 = new XboxController(0);
    private final XboxController controller2 = new XboxController(1);

    

    private void setAllOFF() {
        intake.setOFF();
        shooter.setOFF();
        indexter.setOFF();
    }

    private void setAllLOW() {
        intake.setLOW();
        shooter.setLOW();
        indexter.setLOW();
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

        // === CONTROLLER 1: DIREÇÃO ===
        double velocidade =
            -MathUtil.applyDeadband(controller1.getLeftY(), 0.1);

        double giro =
            -MathUtil.applyDeadband(controller1.getLeftX(), 0.1);

        drivetrain.arcadeDrive(velocidade, giro);


        // === LEITURA ATUALIZADA DO GATILHO 2 ===
        boolean currentRT = controller2.getRightTriggerAxis() > 0.1;
        boolean currentLT = controller2.getLeftTriggerAxis() > 0.1;


        // === RT: SHOOTER ===
        if (currentRT && !lastRightTriggerState) {
            shooter.toggleShooter();
        }

        // === Y: INTAKE ===
        if (controller2.getYButtonPressed()) {
            intake.toggleIntake();
        }

        // === A: OUTTAKE ===
        if (controller2.getAButtonPressed()) {
            intake.toggleOuttake();
            shooter.toggleSOuttake();
        }

        // === B: DESLIGAR TUDO ===
        if (controller2.getBButtonPressed()) {
            setAllOFF();
        }

        // === X: MODO LOW ===
        if (controller2.getXButtonPressed()) {
            setAllLOW();
        }


        SmartDashboard.putBoolean("C2 A", currentA);
        SmartDashboard.putBoolean("C2 B", currentB);
        SmartDashboard.putBoolean("C2 X", currentX);
        SmartDashboard.putBoolean("C2 Y", currentY);
        SmartDashboard.putBoolean("C2 RT", currentRT);
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
