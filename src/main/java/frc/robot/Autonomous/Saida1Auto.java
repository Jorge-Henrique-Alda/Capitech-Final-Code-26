package frc.robot.Autonomous;

import edu.wpi.first.wpilibj.Timer;

import frc.robot.Subsystems.Driver.DriverSubsystem;
import frc.robot.Subsystems.IndexterS.IndexterSSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Indexter.IndexterSubsystem;

public class Saida1Auto implements AutonomousRoutine {

    private DriverSubsystem drivetrain = new DriverSubsystem();
    private IntakeSubsystem intake = new IntakeSubsystem();
    private ShooterSubsystem shooter = new ShooterSubsystem();
    private IndexterSubsystem indexter = new IndexterSubsystem();
    private IndexterSSubsystem indexterShooter = new IndexterSSubsystem();


    // === TEMPORIZADOR ===
    private final Timer timer = new Timer();

    // === STATE MACHINE ===
    private enum AutoState {
        PREPARING,
        DRIVER,
        INTAKE,
        INDEXTER,
        SHOOTER,
        FINISH
    }

    private AutoState currentState = AutoState.FINISH;


    // === CONSTRUTOR ===
    public Saida1Auto(DriverSubsystem drivetrain,
                      IntakeSubsystem intake,
                      IndexterSubsystem indexter,
                      IndexterSSubsystem indexterShooter,
                      ShooterSubsystem shooter) {
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.indexter = indexter;
        this.indexterShooter = indexterShooter;
        this.shooter = shooter;
    }


    // === INICIAR AUTONOMOUS ===
    @Override
    public void start() {
        drivetrain.stop();

        intake.setOFF();
        indexter.setOFF();
        indexterShooter.setOFF();
        shooter.setOFF();

        currentState = AutoState.PREPARING;

        timer.restart();
    }


    // === EXECUTAR STATE MACHINE ===
    @Override
    public void periodic() {

        switch (currentState) {

            case PREPARING:

                drivetrain.stop();


                if (timer.hasElapsed(0.25)) {
                    intake.setLOW();
                    indexter.setLOW();
                    indexterShooter.setLOW();
                    shooter.setLOW();

                    timer.restart();

                    currentState = AutoState.DRIVER;
                }
                break;

            case DRIVER:

                drivetrain.arcadeDrive(0.5, 0.0);

                if (timer.hasElapsed(1)) {
                    drivetrain.arcadeDrive(-0.5, 0.0);

                    intake.setLOW();
                    indexter.setLOW();
                    indexterShooter.setLOW();
                    shooter.setLOW();

                    timer.stop();

                    currentState = AutoState.FINISH;
                }
                else {drivetrain.arcadeDrive(0.5, 0.0);

                    intake.setLOW();
                    indexter.setLOW();
                    indexterShooter.setLOW();
                    shooter.setLOW();}

                break;

            case FINISH:

                drivetrain.stop();

                intake.setOFF();
                indexter.setOFF();
                indexterShooter.setOFF();
                shooter.setOFF();

                break;
        }
    }


    // === VERIFICAR FINALIZAÇÃO ===
    @Override
    public boolean isFinished() {

        return currentState == AutoState.FINISH;
    }


    // === ENCERRAR AUTONOMOUS ===
    @Override
    public void end() {
        timer.stop();

        drivetrain.stop();

        intake.setOFF();
        indexter.setOFF();
        indexterShooter.setOFF();
        shooter.setOFF();

        currentState = AutoState.FINISH;
    }
}
