import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;


abstract public class MullShape {
	protected Shape shape_ = null;
	protected int moveTime_ = 1000;
	protected double size_ = 1;

	protected double bufferX_ = 0;
	protected double bufferY_ = 0;
	
	protected int score_ = 4;
	protected Text text_ = new Text();
	
	private Scene scene_ = null;
	private ArrayList<MullShape> shapes_ = null;
	
	private Mull parent_ = null;
	
	private Timeline timeline_ = null;
	
	public MullShape(Scene scene, ArrayList<MullShape> shapes) {
		scene_ = scene;
		shapes_ = shapes;
	}
	
	public void setParent(Mull parent) {
		parent_ = parent;
	}

	public Shape getShape() {
		return shape_;
	}
	
	protected void initialise() {
		double r = Math.random() * 255;
		double g = Math.random() * 255;
		double b = Math.random() * 255;
		shape_.setFill(Color.rgb((int)r, (int)g, (int)b));
		
		text_.setText(String.valueOf(score_));
		text_.setDisable(true);
		Group root = (Group)scene_.getRoot();
		root.getChildren().add(shape_);
		root.getChildren().add(text_);
		
		placeShape();
		
		timeline_ = new Timeline();
		timeline_.setCycleCount(4);

		KeyFrame kf = new KeyFrame(Duration.millis(moveTime_), e -> placeShape());
		timeline_.getKeyFrames().add(kf);
		timeline_.play();	
		
		shape_.setOnMouseClicked(e -> clicked());
	}
	
	private void clicked() {
		if (!parent_.isGameFinished()) {
			parent_.addScore((int)((double)score_ * size_));
			remove();
		}
	}
	
	private void placeShape() {
		score_--;
		if (score_ == 0) {
			parent_.addScore(-1);
			remove();
			return;
		}

		do {
			double x = Math.random() * (scene_.getWidth() - bufferX_);
			double y = Math.random() * (scene_.getHeight() - bufferY_);
			setPosition(x, y);
            setText(x - 4, y + 4);
		} while (!canPlace());

		text_.setText(String.valueOf(score_));
	}
	
	private boolean canPlace() {
		for (MullShape shape : shapes_) {
			if (shape.getShape() == shape_) continue;
			if (Shape.intersect(shape.getShape(), shape_).getBoundsInLocal().getWidth() > 0) {
				return false;
			}
		}
		return true;
	}
	
	public void remove() {
		timeline_.stop();
		
		FadeTransition ft = new FadeTransition(Duration.millis(300), shape_);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.play();
		
		ft.setOnFinished(e -> removeFull());
	}
	
	public void removeFull() {
		Group root = (Group)scene_.getRoot();
		root.getChildren().remove(shape_);
		root.getChildren().remove(text_);
		shapes_.remove(this);
	}
	
	public void stop() {
		timeline_.stop();
	}
	
	abstract protected void setPosition(double x, double y);
	abstract protected void setText(double x, double y);
}
