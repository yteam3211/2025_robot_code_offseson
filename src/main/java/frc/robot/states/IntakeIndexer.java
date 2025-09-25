package frc.robot.states;

import frc.lib.util.ITarget;

public enum IntakeIndexer implements ITarget{
    open(1),
    close(0);

    double velocity;
    private IntakeIndexer(double velocity){
        this.velocity = velocity;
    }
    @Override
    public double getTarget() {
        return velocity;
    }
    
}
