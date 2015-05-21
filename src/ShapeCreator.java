import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;


public class ShapeCreator {
	private Scene scene_ = null;
	private Timeline timeline_ = null;
	
	private ArrayList<MullShape> shapes_ = new ArrayList<MullShape>();
	
	private Mull parent_ = null;
	
	public void createShape() {
		MullShape shape = null;
		if (Math.round(Math.random()) == 1) shape = new CircleShape(scene_, shapes_);
		else shape = new RectShape(scene_, shapes_);
		shape.setParent(parent_);
		shapes_.add(shape);
		timeline_.setRate(timeline_.getRate() + 0.02);
	}

	public ShapeCreator(Scene scene, Mull parent) {
		scene_ = scene;
		parent_ = parent;
		
		timeline_ = new Timeline();
		timeline_.setCycleCount(Timeline.INDEFINITE);
	
		KeyFrame kf = new KeyFrame(Duration.millis(1200), e -> createShape());
		timeline_.getKeyFrames().add(kf);
		timeline_.play();
	}
	
	public void stop() {
		timeline_.stop();
		for (MullShape shape : shapes_) shape.stop();
	}
}
