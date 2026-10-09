package frc.robot.Subsystems.Driver;


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
import com.studica.frc.AHRS;


public class DriverSubsystem extends SubsystemBase {
    public final SparkMax RightLeader01Max = new SparkMax(1, MotorType.kBrushed);
    public final SparkMax RightFollower02Max = new SparkMax(2, MotorType.kBrushed);
    public final SparkMax LeftLeader03Max = new SparkMax(3, MotorType.kBrushed);
    public final SparkMax LeftFollower04Max = new SparkMax(4, MotorType.kBrushed);

    public final DifferentialDrive driver = new DifferentialDrive(LeftLeader03Max, RightLeader01Max);

    public final AHRS gyroZeppelin = new AHRS(AHRS.NavXComType.kMXP_SPI);

    public DriverSubsystem() {
        SparkMaxConfig Right01Config = new SparkMaxConfig();
        Right01Config.idleMode(IdleMode.kBrake);
        RightLeader01Max.configure(Right01Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkMaxConfig Right02Config = new SparkMaxConfig();
        Right02Config.idleMode(IdleMode.kBrake).follow(1);
        RightFollower02Max.configure(Right02Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkMaxConfig Left03Config = new SparkMaxConfig();
        Left03Config.idleMode(IdleMode.kBrake);
        LeftLeader03Max.configure(Left03Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        SparkMaxConfig Left04Config = new SparkMaxConfig();
        Left04Config.idleMode(IdleMode.kBrake).follow(3);
        LeftFollower04Max.configure(Left04Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    public void arcadeDrive(double velocidade, double rotacao) {

        System.out.println(
                "DRIVE: velocidade=" + velocidade +
                        " rotacao=" + rotacao
        );

        driver.arcadeDrive(velocidade, rotacao);
    }

    public double getAngulo() {
        return gyroZeppelin.getAngle();
    }

    public void stop() {
        driver.stopMotor();
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {

    }


}
