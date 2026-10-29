package frc.robot.Subsystems.Indexter;


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

public class IndexterSubsystem extends SubsystemBase {
    private final SparkMax Indexter06Max = new SparkMax(6,MotorType.kBrushed);
    private Idstate currentIdstate = Idstate.OFF;

    public enum Idstate {
        OFF, LOW, INTAKE, OUTTAKE
    }

    public IndexterSubsystem() {
        SparkMaxConfig Indexter06Config = new SparkMaxConfig();
        Indexter06Config.idleMode(IdleMode.kCoast);
        Indexter06Max.configure(Indexter06Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    public void  toggleIndexter() {
        if(currentIdstate == Idstate.INTAKE){
            currentIdstate = Idstate.LOW;}

        else {
            currentIdstate = Idstate.INTAKE;        }
    }

    public void  toggleOuttake() {
        if(currentIdstate == Idstate.OUTTAKE){
            currentIdstate = Idstate.LOW;}

        else {
            currentIdstate = Idstate.OUTTAKE;        }
    }

    public void setLOW() {
        currentIdstate = Idstate.LOW;
    }

    public void setOFF(){
        currentIdstate = Idstate.OFF;
    }

    @Override
    public void periodic() {

        switch (currentIdstate) {
            case OFF: Indexter06Max.set(0);

                break;

            case LOW: Indexter06Max.set(0.25);

                break;

            case INTAKE: Indexter06Max.set(-0.5);

                break;

            case OUTTAKE: Indexter06Max.set(0.5);

                break;

        }

    }

    @Override
    public void simulationPeriodic() {

    }


}