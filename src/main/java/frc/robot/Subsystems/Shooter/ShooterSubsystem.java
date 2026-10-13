package frc.robot.Subsystems.Shooter;


import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

import com.revrobotics.spark.SparkMax; //Importação para utilizar SparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType; //Importação para decidir qual motor vai ser (Brushed ou Brusheless)
import com.revrobotics.spark.config.SparkMaxConfig; // Importação para criar e aplicar configurações no SparkMax
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;  // Importação para definir o modo de parada do motor (Brake ou Coast)
import java.util.function.DoubleSupplier;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax Shooter06Max = new SparkMax(6,MotorType.kBrushed);
    private Sstate currentSstate = Sstate.OFF;

    public enum Sstate {
        OFF, LOW, SHOOTER, OUTTAKE
    }

    public ShooterSubsystem() {
        SparkMaxConfig Shooter06Config = new SparkMaxConfig();
        Shooter06Config.idleMode(IdleMode.kCoast);
        Shooter06Max.configure(Shooter06Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    public void  toggleShooter() {
        if(currentSstate == Sstate.SHOOTER){
            currentSstate = Sstate.LOW;}

        else {
            currentSstate = Sstate.SHOOTER;        }
    }

    public void  toggleSOuttake() {
        if(currentSstate == Sstate.OUTTAKE){
            currentSstate = Sstate.LOW;}

        else {
            currentSstate = Sstate.OUTTAKE;        }
    }

    public void setLOW() {
        Shooter06Max.set(0.25);
    }

    public void setOFF() {
        Shooter06Max.set(0);
    }

    @Override
    public void periodic() {

        switch (currentSstate) {
            case OFF: Shooter06Max.set(0);

                break;

            case LOW: Shooter06Max.set(0.25);

                break;

            case SHOOTER: Shooter06Max.set(-0.5);

                break;

            case OUTTAKE: Shooter06Max.set(0.5);

                break;

        }

    }

    @Override
    public void simulationPeriodic() {

    }


}
