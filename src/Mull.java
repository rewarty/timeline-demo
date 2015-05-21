import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Mull extends Application {
	
	private Scene scene_ = null;
	private Group root_ = null;
	
	private ShapeCreator creator_ = null;
	
	private Text scoreText_ = null;
	private Text timeText_ = null;
	private Text livesText_ = null;
	
	private int score_ = 0;
	private int time_ = 0;
	private int lives_ = 5;
	
	private Timeline timeline_ = null;
	
	private final int END_TIME = 60;
	private final String SCORE_TEXT = "Score: ";
	
	private void initialiseScene(Stage primaryStage) {
		root_ = new Group();
		scene_ = new Scene(root_, 800, 600);

		primaryStage.setTitle("Mull");
		primaryStage.setScene(scene_);
		primaryStage.show();
		
		Group group = new Group();
		root_.getChildren().add(group);

		creator_ = new ShapeCreator(scene_, this);
	}
	
	private void initialiseGui() {
		Group gui = new Group();

		scoreText_ = new Text(10, 20, SCORE_TEXT + score_);
		timeText_ = new Text(scene_.getWidth() - 30, 20, String.valueOf(END_TIME));
		livesText_ = new Text(10, 40, "Lives: " + lives_);

		gui.getChildren().add(scoreText_);
		gui.getChildren().add(timeText_);
		gui.getChildren().add(livesText_);
		
		root_.getChildren().add(gui);
	}
	
	public void addScore(int score) {
		if (!isGameFinished()) {
            score_ += score;
            scoreText_.setText(SCORE_TEXT + score_);
            
            if (score == -1) {
            	lives_--;
            	livesText_.setText("Lives: " + lives_);
            	if (lives_ == 0) {
            		time_ = END_TIME;
            		timeline_.stop();
            		gameEnds();
            	}
            }
		}
	}
	
	public boolean isGameFinished() {
		return time_ == END_TIME;
	}

	private void updateTime() {
        time_++;
        timeText_.setText(String.valueOf(END_TIME - time_));
	}

	private void gameEnds() {
		creator_.stop();
		scoreText_.setText("Final " + SCORE_TEXT + score_);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initialiseScene(primaryStage);
		initialiseGui();
		
		timeline_ = new Timeline();
		timeline_.setCycleCount(END_TIME);

		KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> updateTime());
		timeline_.getKeyFrames().add(kf);
		timeline_.setOnFinished(e -> gameEnds());
		timeline_.play();
	}
 
	public static void main(String[] args) {
		launch(args);
	}
 
}