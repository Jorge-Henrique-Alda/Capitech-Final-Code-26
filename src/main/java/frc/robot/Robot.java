package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.Autonomous.AutonomousManager;

import frc.robot.Subsystems.Driver.DriverSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Indexter.IndexterSubsystem;

public class Robot extends TimedRobot {

    // === OPÇÕES DE AUTONOMOUS ===
    private static final String kDefaultAuto = "DEFAULT";
    private static final String kCustomAuto1 = "AUTO_1";

    private String m_autoSelected;

    private final SendableChooser<String> m_chooser =
            new SendableChooser<>();

    private final DriverSubsystem drivetrain = new DriverSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final IndexterSubsystem indexter = new IndexterSubsystem();

    // === AUTONOMOUS MANAGER ===
    private final AutonomousManager autonomous =
            new AutonomousManager(
                    drivetrain,
                    intake,
                    indexter,
                    shooter);

    private final XboxController controller1 = new XboxController(0);
    private final XboxController controller2 = new XboxController(1);

    private enum Modo {
        PARADO,
        LOW,
        INTAKE,
        TIRO,
        OUTTAKE
    }

    private Modo modo = Modo.PARADO;
    private final Timer tiroTimer = new Timer();
    private boolean indexterFeedStarted = false;
    private boolean lastRightTriggerState = false;

    private void setModo(Modo novo) {

        modo = novo;

        tiroTimer.stop();
        tiroTimer.reset();

        indexterFeedStarted = false;

        intake.setOFF();
        indexter.setOFF();
        shooter.setOFF();

        switch (novo) {

            case INTAKE:
                intake.setIntake();
                indexter.setIntake();

                break;

            case TIRO:
                shooter.setShooter();
                tiroTimer.restart();

                break;


            case OUTTAKE:
                intake.setOuttake();
                indexter.setOuttake();
                shooter.setOuttake();

                break;


            case LOW:
                intake.setLOW();
                indexter.setLOW();
                shooter.setLOW();

                break;

            case PARADO:
                intake.setOFF();
                indexter.setOFF();
                shooter.setOFF();
                break;
        }
    }

    public Robot() {

        m_chooser.setDefaultOption(
                "Default Auto",
                kDefaultAuto);

        m_chooser.addOption(
                "Auto 1",
                kCustomAuto1);

        SmartDashboard.putData(
                "Auto choices",
                m_chooser);
    }

    @Override
    public void robotPeriodic() {

        // Execução manual das State Machines
        intake.periodic();
        indexter.periodic();
        shooter.periodic();
    }

    @Override
    public void autonomousInit() {

        m_autoSelected = m_chooser.getSelected();

        System.out.println(
                "Auto selected: " + m_autoSelected
        );

        autonomous.start(m_autoSelected);
    }

    @Override
    public void autonomousPeriodic() {

        autonomous.periodic();
    }

    @Override
    public void teleopInit() {

        autonomous.stop();

        setModo(Modo.PARADO);

        lastRightTriggerState =
                controller2.getRightTriggerAxis() > 0.5;
    }


    @Override
    public void teleopPeriodic() {

        double velocidade =
                -MathUtil.applyDeadband(
                        controller1.getLeftY(), 0.1);

        double giro =
                -MathUtil.applyDeadband(
                        controller1.getLeftX(), 0.1);

        drivetrain.arcadeDrive(velocidade, giro);

        boolean currentA = controller2.getAButton();
        boolean currentB = controller2.getBButton();
        boolean currentX = controller2.getXButton();
        boolean currentY = controller2.getYButton();

        boolean pressedA = controller2.getAButtonPressed();
        boolean pressedB = controller2.getBButtonPressed();
        boolean pressedX = controller2.getXButtonPressed();
        boolean pressedY = controller2.getYButtonPressed();

        boolean currentRT =
                controller2.getRightTriggerAxis() > 0.5;

        boolean pressedRT =
                currentRT && !lastRightTriggerState;

        lastRightTriggerState = currentRT;

        if (pressedB) {

            setModo(Modo.PARADO);

        } else if (pressedX) {

            setModo(Modo.LOW);

        } else if (pressedY) {

            setModo(
                    modo == Modo.INTAKE ? Modo.PARADO : Modo.INTAKE);

        } else if (pressedA) {

            setModo(
                    modo == Modo.OUTTAKE ? Modo.PARADO : Modo.OUTTAKE);

        } else if (pressedRT) {

            setModo(
                    modo == Modo.TIRO ? Modo.PARADO : Modo.TIRO);
        }

        if (
                modo == Modo.TIRO
                        && !indexterFeedStarted
                        && tiroTimer.hasElapsed(0.4)) {

            indexter.setIntake();

            indexterFeedStarted = true;
        }

        SmartDashboard.putBoolean("C2 A", currentA);
        SmartDashboard.putBoolean("C2 B", currentB);
        SmartDashboard.putBoolean("C2 X", currentX);
        SmartDashboard.putBoolean("C2 Y", currentY);
        SmartDashboard.putBoolean("C2 RT", currentRT);

        SmartDashboard.putString(
                "Teleop Mode",
                modo.toString());
    }

    @Override
    public void disabledInit() {

        autonomous.stop();

        setModo(Modo.PARADO);

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