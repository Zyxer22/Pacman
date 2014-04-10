import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;


public class PlayerKeyListener implements KeyListener {
	private Pacman pacman;
	private GhostManager gm;
	private Input input;
	int _switch = 0;
	
	//debug to test ghost animations
	int state = 0;
	
	public PlayerKeyListener(Pacman pacman, GhostManager gm){
		this.pacman = pacman;
		this.gm = gm;
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
		else if (key == Input.KEY_K){
			pacman.setStateDead();
		}
		else if (key == Input.KEY_J){
			pacman.setStateMoving();
		}
		else if (key == Input.KEY_L){
			//state is Running, Chasing, Eaten, Orb, and 4 directions
			state = state % 8;
			
			Ghost[] ghosts = gm.getGhosts();
			for (Ghost ghost : ghosts){
				if (state == 0){
					ghost.setDirectionDown();
					if (_switch%2 == 0){
						ghost.setStateChasing();
					}
					else{
						System.out.println("State = eaten");
						ghost.setStateEaten();
					}
				}
				else if (state == 1){
					ghost.setDirectionLeft();
				}
				else if (state == 2){
					ghost.setDirectionUp();
				}
				else if (state == 3){
					ghost.setDirectionRight();
				}
				else if (state == 4){
					ghost.setStateRunning();
				}
				else if (state == 5){
					ghost.setStateChasing();
				}
				else if (state == 6){
					ghost.setStateOrb();
				}
				else if (state == 7){
					ghost.setStateFlashing();
					
					System.out.println("_swtich=" + _switch);
				}
			}
			if (state == 7){
				_switch++;
			}
			state++;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		
	}

}
