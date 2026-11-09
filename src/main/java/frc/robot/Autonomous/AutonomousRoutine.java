package frc.robot.Autonomous;

public interface AutonomousRoutine {

    // Inicia o Autonomous
    void start();

    // Executa continuamente a lógica do Autonomous
    void periodic();

    // Verifica se o Autonomous terminou
    boolean isFinished();

    // Encerra o Autonomous
    void end();
}
