package frc.robot.Subsystems.Intake;


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

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax Intake05Max = new SparkMax(5,MotorType.kBrushed);
    private Istate currentIstate = Istate.OFF;

    public enum Istate {
        OFF, LOW, INTAKE, OUTTAKE
    }

    public IntakeSubsystem() {
        SparkMaxConfig Intake05Config = new SparkMaxConfig();
        Intake05Config.idleMode(IdleMode.kCoast);
        Intake05Max.configure(Intake05Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    public void  toggleIntake() {
        if(currentIstate == Istate.INTAKE){
        currentIstate = Istate.LOW;}

        else {
            currentIstate = Istate.INTAKE;        }
    }

    public void  toggleOuttake() {
        if(currentIstate == Istate.OUTTAKE){
            currentIstate = Istate.LOW;}

        else {
            currentIstate = Istate.OUTTAKE;        }
    }

    public void setLOW() {
        Intake05Max.set(0.25);
    }

    public void setOFF() {
        Intake05Max.set(0);
    }

    @Override
    public void periodic() {

        switch (currentIstate) {
            case OFF: Intake05Max.set(0);

            break;

            case LOW: Intake05Max.set(0.25);

                break;

            case INTAKE: Intake05Max.set(-0.5);

                break;

            case OUTTAKE: Intake05Max.set(0.5);

                break;

        }

    }

    @Override
    public void simulationPeriodic() {

    }


}
