package frc.robot.Subsystems.Indexter;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class IndexterSubsystem extends SubsystemBase {

    private final SparkMax Indexter06Max =
            new SparkMax(6, MotorType.kBrushed);

    private Idstate currentIdstate = Idstate.OFF;

    public enum Idstate {
        OFF,
        LOW,
        SHOOTER,
        OUTTAKE,

    }

    public IndexterSubsystem() {

        SparkMaxConfig Indexter06Config = new SparkMaxConfig();

        Indexter06Config.idleMode(IdleMode.kCoast);

        Indexter06Max.configure(
                Indexter06Config,
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters
        );
    }

    public void toggleIndexter() {

        if (currentIdstate == Idstate.SHOOTER) {

            currentIdstate = Idstate.LOW;

        } else {

            currentIdstate = Idstate.SHOOTER;
        }
    }


    // Alterna entre OUTTAKE e LOW
    public void toggleOuttake() {

        if (currentIdstate == Idstate.OUTTAKE) {

            currentIdstate = Idstate.LOW;

        } else {

            currentIdstate = Idstate.OUTTAKE;
        }
    }

    public void setShooter() {

        currentIdstate = Idstate.SHOOTER;
    }

    public void setOuttake() {

        currentIdstate = Idstate.OUTTAKE;
    }

    public void setLOW() {

        currentIdstate = Idstate.LOW;
    }

    public void setOFF() {

        currentIdstate = Idstate.OFF;
    }

    @Override
    public void periodic() {

        switch (currentIdstate) {

            case OFF:

                Indexter06Max.set(0);

                break;


            case LOW:

                Indexter06Max.set(-0.25);

                break;


            case SHOOTER:

                Indexter06Max.set(-0.5);

                break;


            case OUTTAKE:

                Indexter06Max.set(0.5);

                break;
        }
    }
    @Override
    public void simulationPeriodic() {

    }
}