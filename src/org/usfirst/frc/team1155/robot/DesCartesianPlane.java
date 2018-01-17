package org.usfirst.frc.team1155.robot;

import com.ctre.phoenix.sensors.PigeonIMU;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DesCartesianPlane {
	Timer timer;
    private double x = 0;
    private double y = 0;
    private double prevVx = 0;
    private double prevVy = 0;
    private double vx = 0;
    private double vy = 0;
    private double prevAx = 0;
    private double prevAy = 0;
    private final double mpsps = 9.80665;
    private final double ftpsps = 32.185039370079;
    private PigeonIMU accelerometer;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    DesCartesianPlane(Timer t, PigeonIMU a) {
        timer = t;
        accelerometer = a;
        scheduledExecutorService.scheduleAtFixedRate() -> {
            Robot.client.setCoordinates(new int[]{(int)x, (int)y});
            try {
                Robot.client.sendCoordinates();
            } catch (IOException e) {
                System.out.println("error sending coordinates");
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void updatePosition() {
        double ax = accelerometer.getX() * ftpsps;
        double ay = accelerometer.getY() * ftpsps;
        
        if (Math.abs(ax) <= 0.06)
        	ax = 0;
        if (Math.abs(ay) <= 0.06)
        	ay = 0;
        
        if(prevAx == 0 && ax == 0)
        	vx = 0;

        if(prevAy == 0 && ay == 0)
        	vy = 0;
        
        double dt = (timer.getTimeDifference() / 1000);
        vx += (0.5 * dt * (prevAx + ax));
        vy += (0.5 * dt * (prevAy + ay));

        x += (0.5 * dt * (prevVx + vx));
        y += (0.5 * dt * (prevVy + vy));

        prevAx = ax;
        prevAy = ay;

        prevVx = vx;
        prevVy = vy;
    }
    
    public double getAx() {
    	return prevAx;
    }
    
    public double getAy() {
    	return prevAy;
    }
    
    public double getVy() {
    	return prevVy;
    }
    
    public double getVx() {
    	return prevVx;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
