package brickbreaker;

import java.util.ArrayList;

import java.util.Random;

import javax.swing.JOptionPane;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class BrickBreaker extends PApplet {
	
	public Random randGen = new Random();
	public String[] brickColors = {"blue", "red", "green", "purple", "black"};
	//public Brick brick;
	public ArrayList<Brick> brickList = new ArrayList<Brick>();
	public ArrayList<Brick> brickList2 = new ArrayList<Brick>();
	public ArrayList<Brick> brickList3 = new ArrayList<Brick>();
	public ArrayList<ThreeBrickLists> superBrickList = new ArrayList<ThreeBrickLists>();
	public ThreeBrickLists bigBrickList;
	public ThreeBrickLists ogBrickList;
	public Paddle paddle;
	public Ball ball;
	//PImage arc;
	public int xVal = -63;
	//public boolean rotateLeft = false;
	//public boolean rotateRight = false;
	//public float rotationVar;
	public int x = 375;
	public int y = 825;
	public boolean ballShouldMove;
	public boolean alreadyRan;
	public int score;
	public int startTime;
	public int timer;
	public int seconds;
	public int pastSeconds;
	public boolean gameOver = false;

	public void setup() {
		size(1000,800);
		for (int i = 0; i < 6; i++) {
			xVal += 150;
			int pickColor = randGen.nextInt(brickColors.length);
			int pickColor2 = randGen.nextInt(brickColors.length);
			int pickColor3 = randGen.nextInt(brickColors.length);
			brickList.add(new Brick(brickColors[pickColor], xVal, -10, brickList));
			brickList2.add(new Brick(brickColors[pickColor2], xVal, -360, brickList2));
			brickList3.add(new Brick(brickColors[pickColor3], xVal, -710, brickList3));
			ogBrickList = new ThreeBrickLists(brickList, brickList2, brickList3);
			bigBrickList = ogBrickList;
			superBrickList.add(bigBrickList);
		}//end loop
		
		startTime = millis();
		
		paddle = new Paddle();
		ball = new Ball(paddle.xPos + 75, 775);
		//arc = loadImage("semiCircle.png");
		
	}//end func

	public void draw() {
		background(209,238,238);
		if (!gameOver)  {
			bigBrickList.update();
			//(superBrickList.size());
			if(superBrickList.size() > 0) {
				bigBrickList.drawBrickList();
			}//end if
			
			paddle.update();
			paddle.drawPaddle();
		
			ball.update();
			ball.drawBall();
			ball.checkBounds();
		
			text("Score: " + score, 10, 10, 150, 100);
			textSize(32);
		
		
		
			timer = millis() - startTime;
			if (timer >= 1000) {
				pastSeconds++;
				timer = millis();
				//System.out.println(pastSeconds);
				if (pastSeconds % 60 == 0) {
					seconds++;
				}	
			}
			
			String secondsString = new String();
			
			if ((60 - seconds) > 9) {
				secondsString = "" + (60 - seconds);
			} else {
				secondsString = "0" + (60 - seconds);
			}
		
			text("Time - 0:" + secondsString, 10, 750, 300, 100);
			
			textSize(32);
			
			if (seconds >= 60) {
				gameOverM();
			}
		} else {
			//gameOverM();
		}
		
	}//end func
	
	public class ThreeBrickLists {
		ArrayList<Brick> brickListf;
		ArrayList<Brick> brickLists;
		ArrayList<Brick> brickListt;
		
		public ThreeBrickLists(ArrayList<Brick> first, ArrayList<Brick> second, ArrayList<Brick> third) {
			brickListf = first;
			brickLists = second;
			brickListt = third;
		}//end func
		
		public void drawBrickList() {
			for (Brick brick: brickListf)  {
				brick.update();
				brick.drawBrick();
			}//end loop
		
			for (Brick brick: brickLists) {
				brick.update();
				brick.drawBrick();
			}//end loop
		
			for (Brick brick: brickListt) {
				brick.update();
				brick.drawBrick();
			}//end loop
		}//end func
		
		public void update() {
			for (Brick brick: brickListf) {
				if (brick.yPos > 700) {
					brick.yPos = 1100;
				}//end if
			}//end loop
			for (Brick brick: brickLists) {
				if (brick.yPos > 700 && brick.yPos < 1000) {
					brick.yPos = 1100;
					superBrickList.clear();
					//ArrayList<Brick> newBrickList = new ArrayList<Brick>();
					ArrayList<Brick> newBrickList2 = new ArrayList<Brick>();
					ArrayList<Brick> newBrickList3 = new ArrayList<Brick>();
					xVal = -63;
					for (int i = 0; i < 6; i++) {
						xVal += 150;
						//int pickColor = randGen.nextInt(brickColors.length);
						int pickColor2 = randGen.nextInt(brickColors.length);
						int pickColor3 = randGen.nextInt(brickColors.length);
						//newBrickList.add(new Brick(brickColors[pickColor], xVal, -10, brickList));
						newBrickList2.add(new Brick(brickColors[pickColor2], xVal, 0, brickList2));
						newBrickList3.add(new Brick(brickColors[pickColor3], xVal, -350, brickList3));
						ThreeBrickLists newBigBrickList = new ThreeBrickLists(brickListt, newBrickList2, newBrickList3);
						superBrickList.add(newBigBrickList);
						bigBrickList = newBigBrickList;
					}//end loop
					
				}//end if
			}//end loop
			
			for (Brick brick: brickListt) {
				if (brick.yPos > 700) {
					brick.yPos = 1100;
				}
			}//end loopa
		
		}//end func
	}//end inner class
	
	public class Brick {
		int health;
		String color;
		float xPos;
		float yPos;
		int width = 75;
		int height = 40;
		ArrayList<Brick> myBrickList;
		
		public Brick(String colorP, float xPosP, float yPosP, ArrayList<Brick> mybricks) {
			color = colorP;
			xPos = xPosP;
			yPos = yPosP;
			myBrickList = mybricks;
		}//end func init
		
		public void drawBrick() {
			switch (color) {
				case "blue":
					fill(0, 178, 238);
					health = 1;
					break;
				case "red":
					fill(255,99,71);
					health = 2;
					break;
				case "green":
					fill(0,255,0);
					health = 3;
					break;
				case "purple":
					fill(153, 50, 204);
					health = 4;
					break;
				case "black":
					fill(0,0,0);
					health = 5;
					break;
				default:
					fill(0, 178, 238);
					health = 6;
					break;
			}//end switch
			
			rect(xPos, yPos, width, height);
		}//end func
		
		public void update() {
			this.yPos += 0.75;
			checkCollision();
		}//end func
		
		public void checkCollision() {
			if (this.xPos + this.width > ball.xPos && this.xPos < ball.xPos + 15) {
				if (this.yPos >= ball.yPos - 20 && this.yPos - this.height < ball.yPos) {
					ball.yspeed = -ball.yspeed;
					if (ball.yspeed > 0) {
						ball.yPos -= 45;
					} else {
						ball.yPos += 45;
					}
					this.health--;
					switch (this.health) {
						case 0:
							this.yPos = 5000;
							this.xPos = 5000;
							score += 10;
							break;
						case 1:
							this.color = "blue";
							score += 5;
							break;
						case 2:
							this.color = "red";
							score += 4;
							break;
						case 3:
							this.color = "green";
							score += 3;
							break;
						case 4:
							this.color = "purple";
							score += 2;
							break;
						case 5:
							this.color = "black";
							score += 1;
							break;
						default:
							System.out.println("Color change did not work");
							break;
					}
				}
			}
		}
		
	}//end inner class
	
	public void keyPressed() {
		 if (key == 'a') {
			paddle.moveLeft = true;
		}
		if (key == 'd') {
			paddle.moveRight = true;
		}
		if (key == ' ') {
			ballShouldMove = true;
		} 
		if (key == 'r') {
			ballShouldMove = false;
			ball.yspeed = 10;
			ball.xPos = paddle.xPos + 75;
			ball.yPos = 775;
		}
	}
	
	public void keyReleased() {
		 if (key == 'a') {
			paddle.moveLeft = false;
		}
		if (key == 'd') {
			paddle.moveRight = false;
		} 
	}
	
	public class Paddle {
		float xPos = 425;
		float width = 150;
		int speed = 10;
		boolean moveRight = false;
		boolean moveLeft = false;
		
		public Paddle() {
			
		}
		
		public void drawPaddle() {
			fill(240,91,106);
			rect (xPos, 765, width, 25);
		}
		
		public void update() {
			if (moveRight) {
				this.xPos += this.speed;
			}
			if (moveLeft) {
				this.xPos -= this.speed;
			}
		}
	}//end inner class
	
	public class Ball {
		float xPos;
		float yPos;
		int yspeed = 10;
		double xspeed = 1.42;
		
		public Ball(float xPos2, int startY) {
			xPos = xPos2;
			yPos = startY;
		}
		
		public void drawBall() {
			fill(0,0,0);
			//xPos = paddle.xPos + 75;
			ellipse (xPos, yPos, 30, 30);
		}
		
		public void update() {
			if (ballShouldMove) {
				this.yPos -= yspeed;
				this.xPos += xspeed;
				this.checkCollision();
				//this.checkBounds();
			} else {
				xPos = paddle.xPos + 75;
				fill(0,0,0);
				this.yPos = 750;
			}
		}
		
		public void checkBounds() {
			if (this.xPos < 0) {
				//fill(0,0,0);
				//ballShouldMove = false;
				//this.speed = 10;
				this.xspeed = -this.xspeed;
				//xPos = paddle.xPos + 75;
				//ellipse (xPos, 775, 30, 30);
			}
			if (this.xPos > 1000) {
				//fill(0,0,0);
				//ballShouldMove = false;
				//this.speed = 10;
				this.xspeed = -this.xspeed;
				//xPos = paddle.xPos + 75;
				//ellipse (xPos, 775, 30, 30);
			}
			if (this.yPos > 825) {
				//fill(0,0,0);
				ballShouldMove = false;
				this.yspeed = 10;
				//xPos = paddle.xPos + 75;
				//ellipse (xPos, 775, 30, 30);
			}
			if (this.yPos < -5) {
				this.yspeed = -this.yspeed;
			}
			
			if (this.yPos < -50) {
				ballShouldMove = false;
				this.yspeed = 10;
			}//end if
		}//end func
		
		public void checkCollision() {
			if(this.xPos < paddle.xPos + paddle.width && this.xPos + 15 > paddle.xPos) {
				if (this.yPos > 755) {
					//System.out.println("collision");
					//System.out.println(this.xPos - (paddle.xPos + paddle.width/2));
					float distance = this.xPos - (paddle.xPos + paddle.width/2);
					//System.out.println(distance);
					//System.out.println(distance * 71/1400);
					this.xspeed = (distance * 71/1400);
					//System.out.println(this.xspeed);
					this.yspeed = -this.yspeed;
					
				}
			}
			
			/* 
			for (Brick brick: bigBrickList.brickListf) {
				
			}
			
			Brick brick1 = bigBrickList.brickListf.get(0);
			Brick brick2 = bigBrickList.brickListf.get(1);
			Brick brick3 = bigBrickList.brickListf.get(2);
			Brick brick4 = bigBrickList.brickListf.get(3);
			Brick brick5 = bigBrickList.brickListf.get(4);
			Brick brick6 = bigBrickList.brickListf.get(5);
			
			if (this.xPos < brick1.xPos + brick1.width && this.xPos + 15 > brick1.xPos) {
				if (brick1.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			else if (this.xPos < brick2.xPos + brick2.width && this.xPos + 15 > brick2.xPos) {
				if (brick2.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			else if (this.xPos < brick3.xPos + brick3.width && this.xPos + 15 > brick3.xPos) {
				if (brick3.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			else if (this.xPos < brick4.xPos + brick4.width && this.xPos + 15 > brick4.xPos) {
				if (brick4.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			else if (this.xPos < brick5.xPos + brick5.width && this.xPos + 15 > brick5.xPos) {
				if (brick5.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			else if (this.xPos < brick6.xPos + brick6.width && this.xPos + 15 > brick6.xPos) {
				if (brick6.yPos > this.yPos - 15) {
					this.yspeed = -this.yspeed;
				}
			}
			
			/* for (Brick brick: bigBrickList.brickLists) {
				if (this.xPos < brick.xPos + brick.width && this.xPos + 15 > brick.xPos) {
					if (brick.yPos > this.yPos - 15) {
						//System.out.println("collision");
						this.yspeed = -this.yspeed;
					}
				}
			} */
			
			/* for (Brick brick: bigBrickList.brickListt) {
				if (this.xPos < brick.xPos + brick.width && this.xPos + 15 > brick.xPos) {
					if (brick.yPos > this.yPos - 15) {
						//System.out.println("collision");
						this.yspeed = -this.yspeed;
					}
				}
			} */
			
			
		}
	} //end inner class
	
	public void gameOverM() {
		gameOver = true;
		ballShouldMove = false;
		ball.yspeed = 0;
		ball.xspeed = 0;
		ball.xPos = paddle.xPos + 75;
		ball.yPos = 775;
		JOptionPane.showMessageDialog(null, "Your final score was " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}
	
}//end class
