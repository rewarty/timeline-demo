import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;


public class RectShape extends MullShape {
	public RectShape(Scene scene, ArrayList<MullShape> shapes) {
		super(scene, shapes);
		
		moveTime_ = 1650;

		Rectangle shape = new Rectangle(0, 0, Math.random() * 25 + 25, Math.random() * 25 + 25);

		shape_ = shape;
		bufferX_ = shape.getWidth();
		bufferY_ = shape.getHeight();
		
		size_ = 50 * 50 / (bufferX_ * bufferY_);

		initialise();
	}

	@Override
	protected void setPosition(double x, double y) {
		Rectangle shape = (Rectangle)shape_;
		shape.setX(x);
		shape.setY(y);
	}

	@Override
	protected void setText(double x, double y) {
		Rectangle shape = (Rectangle)shape_;
		text_.setX(x + shape.getWidth() / 2);
		text_.setY(y + shape.getHeight() / 2);
	}
}
