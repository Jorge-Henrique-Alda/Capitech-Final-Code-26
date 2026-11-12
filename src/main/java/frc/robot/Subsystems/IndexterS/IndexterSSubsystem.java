package frc.robot.Subsystems.IndexterS;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class IndexterSSubsystem extends SubsystemBase {

    private final SparkMax IndexterS07Max =
            new SparkMax(7, MotorType.kBrushed);

    private IdSstate currentIdSstate = IdSstate.OFF;

    public enum IdSstate {
        OFF,
        LOW,
        OUTTAKE,
        SHOOTER
    }

    public IndexterSSubsystem() {

        SparkMaxConfig IndexterS07Config = new SparkMaxConfig();

        IndexterS07Config.idleMode(IdleMode.kCoast);

        IndexterS07Max.configure(
                IndexterS07Config,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters
        );
    }

    public void toggleIndexterS() {

        if (currentIdSstate == IdSstate.SHOOTER) {

            currentIdSstate = IdSstate.LOW;

        } else {

            currentIdSstate = IdSstate.SHOOTER;
        }
    }


    // Alterna entre OUTTAKE e LOW
    public void toggleOuttake() {

        if (currentIdSstate == IdSstate.OUTTAKE) {

            currentIdSstate = IdSstate.LOW;

        } else {

            currentIdSstate = IdSstate.OUTTAKE;
        }
    }

    public void setShooter() {

        currentIdSstate = IdSstate.SHOOTER;
    }

    public void setOuttake() {

        currentIdSstate = IdSstate.OUTTAKE;
    }

    public void setLOW() {

        currentIdSstate = IdSstate.LOW;
    }

    public void setOFF() {

        currentIdSstate = IdSstate.OFF;
    }

    @Override
    public void periodic() {

        switch (currentIdSstate) {

            case OFF:

                IndexterS07Max.set(0);

                break;


            case LOW:

                IndexterS07Max.set(-0.25);

                break;


            case SHOOTER:

                IndexterS07Max.set(-0.5);

                break;


            case OUTTAKE:

                IndexterS07Max.set(0.5);

                break;
        }
    }
    @Override
    public void simulationPeriodic() {

    }
}
