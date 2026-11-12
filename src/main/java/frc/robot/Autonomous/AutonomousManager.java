package frc.robot.Autonomous;

import frc.robot.Subsystems.Driver.DriverSubsystem;
import frc.robot.Subsystems.IndexterS.IndexterSSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Subsystems.Indexter.IndexterSubsystem;

public class AutonomousManager {

    // === SUBSYSTEMS ===
    private final DriverSubsystem drivetrain;
    private final IntakeSubsystem intake;
    private  final IndexterSubsystem indexter;
    private final ShooterSubsystem shooter;

    // === AUTONOMOUS ATUAL ===
    private AutonomousRoutine activeRoutine;

    public AutonomousManager(DriverSubsystem drivetrain, IntakeSubsystem intake, IndexterSubsystem indexter, IndexterSSubsystem indexterShooter, ShooterSubsystem shooter) {

        this.drivetrain = drivetrain;
        this.intake = intake;
        this.indexter = indexter;
        this.shooter = shooter;
    }
    // === INICIAR AUTONOMOUS ===
    public void start(String selectedAuto) {

        // Encerra qualquer rotina anterior
        stop();

        // Seleciona a rotina
        switch (selectedAuto == null ? "" : selectedAuto) {
            case "AUTO_1":
                activeRoutine = new Saida1Auto(
                        drivetrain,
                        intake,
                        indexter,
                        shooter
                );

                break;

            case "DEFAULT":
                activeRoutine = null;
                break;

        }
        if (activeRoutine != null) {
            activeRoutine.start();
        }
    }

    // === ATUALIZAR AUTONOMOUS ===
    public void periodic() {
        if (activeRoutine == null) {
            return;
        }
        activeRoutine.periodic();

        if (activeRoutine.isFinished()) {
            activeRoutine.end();

            activeRoutine = null;
        }
    }

    // === ENCERRAR AUTONOMOUS ===
    public void stop() {
        if (activeRoutine != null) {
            activeRoutine.end();

            activeRoutine = null;
        }

        drivetrain.stop();

        intake.setOFF();
        indexter.setOFF();
        shooter.setOFF();

    }
}
