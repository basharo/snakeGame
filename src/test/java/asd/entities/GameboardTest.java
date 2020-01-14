package asd.entities;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameboardTest {
	JFXPanel initJfxPanel = new JFXPanel();
	Group testGroup = new Group();
	Score score = new Score(testGroup);

	@Before
	public void init() {
		Label start = new Label("start");
		testGroup.getChildren().add(start);
	}

	@Test
	void testSetStartInfo() {
		Label startInfo = new Label("More start info");
		testGroup.getChildren().add(startInfo);
		assertTrue(testGroup.getChildren().contains(startInfo));
	}

	@Test
	void testSetMessageLabel() {
		Label statusLabel = new Label("test" + score.getScore());
		testGroup.getChildren().add(statusLabel);
		assertTrue(testGroup.getChildren().contains(statusLabel));
	}


}
