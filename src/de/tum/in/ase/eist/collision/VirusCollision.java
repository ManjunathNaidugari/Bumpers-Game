package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.Dimension2D;
import de.tum.in.ase.eist.Point2D;
import de.tum.in.ase.eist.car.Car;

public class VirusCollision extends Collision {
    private static final double INFECTIVITY_RATE = 20.0;

    public VirusCollision(Car car1, Car car2) {
        super(car1, car2);
    }

    @Override
    public boolean detectCollision() {
        Point2D p1 = getCar1().getPosition();
        Dimension2D d1 = getCar1().getSize();

        Point2D p2 = getCar2().getPosition();
        Dimension2D d2 = getCar2().getSize();

        boolean above = p1.getY() + d1.getHeight() < p2.getY();
        boolean below = p1.getY() > p2.getY() + d2.getHeight();
        boolean right = p1.getX() + d1.getWidth() < p2.getX();
        boolean left = p1.getX() > p2.getX() + d2.getWidth();

        return !above && !below && !right && !left;
    }

    @Override
    public Car evaluate() {
        double car1ViralLoad = this.getCar1().getViralLoad();
        double car2ViralLoad = this.getCar1().getViralLoad();

        if (car1ViralLoad + car2ViralLoad == 0.0) {
            return null;
        }

        this.getCar1().setViralLoad(car1ViralLoad + car2ViralLoad / INFECTIVITY_RATE);
        this.getCar2().setViralLoad(car1ViralLoad + car2ViralLoad / INFECTIVITY_RATE);

        if (car1ViralLoad > car2ViralLoad) {
            return this.getCar1();
        }
        return this.getCar2();
    }
}

