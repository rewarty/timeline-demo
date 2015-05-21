import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;


public class CircleShape extends MullShape {
	public CircleShape(Scene scene, ArrayList<MullShape> shapes) {
		super(scene, shapes);
		
		moveTime_ = 1950;

		Circle shape = new Circle(0, 0, Math.random() * 25 + 25);

		shape_ = shape;
		bufferX_ = bufferY_ = shape.getRadius() * 2;
		size_ =  (Math.PI * 50 * 50) / (Math.PI * shape.getRadius() * shape.getRadius());

		initialise();
	}

	@Override
	protected void setPosition(double x, double y) {
		Circle shape = (Circle)shape_;
		shape.setCenterX(x + bufferX_ / 2);
		shape.setCenterY(y + bufferY_ / 2);
	}

	@Override
	protected void setText(double x, double y) {
		text_.setX(x + bufferX_ / 2);
		text_.setY(y + bufferY_ / 2);
	}
}
