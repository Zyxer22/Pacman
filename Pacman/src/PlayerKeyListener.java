import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


public class PlayerKeyListener implements KeyListener {
	private Pacman pacman;
	private Input input;
	
	public PlayerKeyListener(Pacman pacman){
		this.pacman = pacman;
	}
	
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input input) {
		// TODO Auto-generated method stub
		this.input = input;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		System.out.println("Key pressed!");
		if (key == Input.KEY_DOWN){
			pacman.setDirectionDown();
		}
		else if (key == Input.KEY_UP){
			pacman.setDirectionUp();
		}
		else if (key == Input.KEY_LEFT){
			pacman.setDirectionLeft();
		}
		else if (key == Input.KEY_RIGHT){
			pacman.setDirectionRight();
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		
	}

}
