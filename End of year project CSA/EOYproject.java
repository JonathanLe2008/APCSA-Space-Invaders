import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import javax.sound.sampled.*;


public class EOYproject
{
	
	static File file; 
    static AudioInputStream stream; 
    static Clip music;
	public static void main(String...args)throws Exception
	{
		file = new File("music.wav");//File must be .WAV, .AU, or .AIFF
		stream = AudioSystem.getAudioInputStream(file);
		music = AudioSystem.getClip();
		music.open(stream);
		music.start(); //Start the music
		music.loop(Clip.LOOP_CONTINUOUSLY); //Loop the music

		JFrame j = new JFrame();  //JFrame is the window; window is a depricated class
		MyPanel4 m = new MyPanel4();
		j.setSize(m.getSize());
		j.add(m); //adds the panel to the frame so that the picture will be drawn
			      //use setContentPane() sometimes works better then just add b/c of greater efficiency.
		j.setVisible(true); //allows the frame to be shown.
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the dialog box exit when you click the "x" button.
		j.addMouseMotionListener(m);
	}

}

class MyPanel4 extends JPanel implements ActionListener,KeyListener, MouseListener,MouseMotionListener  // extends JPanel to support the swing framework
{
	private Timer time;
	Image endscreen;
	int x, y, z;
	boolean start=false;
	boolean in=false;
	int score=0;
	ArrayList<Integer> highscores=new ArrayList<Integer>(Arrays.asList(0,0,0));
	boolean end=false;
	int location=750;
	boolean hit=false;
	ArrayList<bullet> bulletArrayList=new ArrayList<bullet>();
	ArrayList<turret> turretArrayList=new ArrayList<turret>();
	int module;
	int run=module=80;
	boolean printimage=true;
	int timer=60;
	int hiddentimer=0;
	int hiddenscore=0;
	boolean changed=false;
	int starttimer=1;
	int startswitch=1;

	MyPanel4()
	{
		time = new Timer(1000, this); //sets delay to 15 millis and calls the actionPerformed of this class.
		setSize(1500, 1500);
		setVisible(true); //it's like calling the repaint method.
		time.start();
		
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	

	public void paintComponent(Graphics g)
	{
		if(!start && !end){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1500,1500);

		g.setColor(Color.WHITE);
		for(int i=0;i<10;i++){
		g.fillOval((int)(Math.random()*1501), (int)(Math.random()*1501), 3, 3);
		g.fillOval((int)(Math.random()*1501), (int)(Math.random()*1501), 3, 3);
		}



		//draw start button
		g.drawRoundRect(500, 200, 500, 200, 100, 100);
		if(in){
			g.setColor(Color.GREEN);
			g.fillRoundRect(500,200, 500, 200, 100, 100);
		}
		else{
			g.setColor(Color.RED);
			g.fillRoundRect(500, 200, 500, 200, 100, 100);
		}
		//start word
		Font myFont=new Font("Courier New", 1000, 125);
		g.setFont(myFont);
		g.setColor(Color.white);
		if(startswitch==1){
		g.drawString("START", 565, 340);
		}

		//draw start screen controller
		int[]xpoints={275, 500, 425, 225};
		int[]ypoints={375, 400, 675, 650};
		int[]xpoints2={1225, 1000, 1075, 1275};
		int[]ypoints2={375, 400, 675, 650};

		int[]ovalxpoints1={325,450,400,275};
		int[]ovalypoints1={425, 425, 625, 625};
		int[]ovalxpoints2={1175, 1050, 1100, 1225};
		int[]ovalypoints2={425, 425, 625, 625};


		//basis
		
		g.setColor(Color.white);
		g.fillPolygon(xpoints, ypoints, 4);
		g.fillPolygon(xpoints2, ypoints2, 4);
		g.fillArc(225, 575,205 , 150, 170, 180);	
		g.fillArc(1070, 575,205 , 150, 190, 180);
		//top buttons
		g.fillOval(300, 150, 200, 100);
		g.fillOval(1000, 150, 200, 100);

		g.setColor(Color.blue);
		g.fillOval(350,145, 100, 50);
		g.fillOval(1050, 145, 100, 50);

		//inside the basis
		g.setColor(Color.blue);
		g.fillPolygon(ovalxpoints2, ovalypoints2, 4);
		g.fillPolygon(ovalxpoints1, ovalypoints1, 4);
		g.fillArc(275, 575, 125, 100, 180, 180);
		g.fillArc(1100, 575, 125, 100, 180, 180);

		//big circles
		g.setColor(Color.white);
		g.fillOval(250, 175, 300, 275);
		g.fillOval(950, 175, 300, 275);


		//details
		g.setColor(Color.black);
		g.fillRect(375, 250 , 40, 120);
		g.fillRect(335, 290, 120, 40);

		g.drawOval(315, 230, 160, 160);
		g.drawOval(1020, 245, 160, 160);

		g.setColor(Color.yellow);
		g.fillOval(1075, 250, 50, 50);
		g.setColor(Color.red);
		g.fillOval(1125, 300, 50, 50);
		g.setColor(Color.green);
		g.fillOval(1075, 350, 50, 50);
		g.setColor(Color.blue);
		g.fillOval(1025, 300, 50, 50);
		
		
		



		//highscores
		g.setColor(Color.white);
		myFont=new Font("Courier New", 1000, 40);
		g.setFont(myFont);
		g.drawString("HIGHSCORES=1st:" +highscores.get(0)+" 2nd:"+highscores.get(1)+" 3rd:"+highscores.get(2), 400, 100);

		starttimer++;
		if(starttimer%50==0 && startswitch==1){
			startswitch=0;
		}
		else if(starttimer%50==0 && startswitch==0){
			startswitch=1;
		}
		
		repaint();
	}



	
	if(start && end==false){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1500, 1500);
		g.setColor(Color.WHITE);
		//left line
		g.drawLine(500, 0, 500, 900);
		//right line
		g.drawLine(1000,0, 1000, 900);
		//bottom line
		g.drawLine(500,700,1000, 700);
		Font myFont=new Font("Times New Roman", 1000, 80);
		g.setFont(myFont);
		g.drawString("Score: "+score, 100, 150);

		//ship

		g.fillRect(location-10,650 ,20,30);
		g.fillRect(location-20,665, 10, 20);
		g.fillRect(location+10,665, 10, 20);
		g.fillRect(location-30,680, 10, 20);
		g.fillRect(location+20,680, 10, 20);
		g.setColor(Color.BLUE);
		g.fillRect(location-5, 660, 10, 20);
		g.fillRect(location-25, 680,5, 15);
		g.fillRect(location+20, 680,5,15 );
		g.setColor(Color.RED);
		g.fillRect(location-20,670, 10, 15);
		g.fillRect(location+10, 670, 10, 15);
		g.setColor(Color.GREEN);
		g.drawLine(location-15,685,location-15,695);
		g.drawLine(location+15, 685, location+15, 695);
		g.drawLine(location-15,695, location+15, 695 );
		g.fillRect(location-5,645 ,10 ,5);

		//bullet
		if(hit){
			bulletArrayList.add(new bullet(location-5));
			hit=false;
		}

		for(int i=bulletArrayList.size()-1;i>-1;i--){
			bulletArrayList.get(i).drawbullet(g);
			bulletArrayList.get(i).incrementy();
			if(bulletArrayList.get(i).gety()<100)
				bulletArrayList.remove(i);
		}		
		//turrets
		
		
		//System.out.print(module%run==0 && run==module);
		//System.out.println(" "+module+" "+run+" "+hiddenscore+ " "+ score+" "+turret.getadding());

		if(module%run==0 && run==module){ //bugging line-run is not resetting
			int temp=(int)((Math.random()*400)+550);
			turretArrayList.add(new turret(temp));
			run=1;
		}
		//draws the turrets
		for(int j=turretArrayList.size()-1;j>-1;j--){
		turretArrayList.get(j).drawturret(g);
		turretArrayList.get(j).incrementturrety();
	}
		run++;
		//increases in difficulty
		if(hiddenscore%10==0 && hiddenscore!=0){
			module-=5;
			run=module;
			hiddenscore++;
			turret.increaseadd();
		}
		if((score-12)%10==0 && score!=0){
			hiddenscore=score;
		}
		//timer
		g.setColor(Color.WHITE);
		hiddentimer++;
		g.drawString("Time: "+timer, 1100, 150);
		if(hiddentimer%100==0)
			timer--;

		//instructions
		myFont=new Font("Times New Roman", 1000, 30);
		g.setFont(myFont);
		g.drawString("Space to shoot", 1100, 600);
		g.drawString("Arrows to move", 100, 600);



		//interaction
		for(int a=bulletArrayList.size()-1;a>-1;a--){
			if(turretArrayList.size()==0)
				break;
			for(int b=turretArrayList.size()-1;b>-1;b--){
				if((bulletArrayList.get(a).getx()>=turretArrayList.get(b).getx() && bulletArrayList.get(a).getx()+10<=turretArrayList.get(b).getx()+50)
				&&(bulletArrayList.get(a).gety()>=turretArrayList.get(b).gety() && bulletArrayList.get(a).gety()+5<=turretArrayList.get(b).gety()+50)){
				bulletArrayList.remove(a);
				turretArrayList.remove(b);
				score++;
				hiddenscore++;
				break;
				}
			}
		}

			
		
		//countdown-hits zero or turret hits ship or reaches floor
		for(int iterate=0;iterate<turretArrayList.size();iterate++){
			if(turretArrayList.get(iterate).gety()+50>=700){
				end=true;
				start=false;
			}
		}

		if(timer==0){
			end=true;
			start=false;
		}
		for(int i=0;i<5;i++){
			g.fillOval((int)(Math.random()*1501), (int)(Math.random()*1501), 3, 3);
			}
		repaint();
	}
	if(end && !start){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1500, 1500);

		//printing the endscreen
	try
		{
			endscreen = ImageIO.read(new File("endgame.jpg"));
		}
		catch(Exception e)	{}
		if(end){
			g.drawImage(endscreen, 0, 0, 1500, 850, null, null);
			Font myFont=new Font("Courier New", 100, 100);
			g.setFont(myFont);
			g.setColor(Color.MAGENTA);
			g.drawString("Click To Start Again", 200, 700);
		}
	//sorting the highscores
	int runthrough;
	for(runthrough=0;runthrough<3;runthrough++){
		if(score>highscores.get(runthrough))
			break;
	}
	if(runthrough==2){
		highscores.set(runthrough, score);
	}
	else if(runthrough==1){
		highscores.set(runthrough+1, highscores.get(runthrough));
		highscores.set(runthrough, score);
	}
	else if(runthrough==0){
		highscores.set(runthrough+2, highscores.get(runthrough+1));
		highscores.set(runthrough+1, highscores.get(runthrough));
		highscores.set(runthrough, score);
	}


	repaint();
	score=0;
	bulletArrayList.clear();
	turretArrayList.clear();
	module=80;
	run=module;
	timer=60;
	hiddenscore=0;
	turret.resetadding();
}

	}
	public void actionPerformed(ActionEvent e)
	{
		/*x+=10;
		if (x>=200)
			x=0;
		repaint();*/
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT&& location<=960)
			location+=40;
		if(e.getKeyCode()==KeyEvent.VK_LEFT && location>=540)
			location-=40;
	 	if(e.getKeyCode()==KeyEvent.VK_SPACE){
			hit=true;
		}

		repaint();
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		
		if(end){
			end=false;
			start=false;
		}
		else if(in &&!start && !end){
			start=true;
			in=false;
		}
		repaint();
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){
		if(!start && !end){
		int x=e.getX();
		int y=e.getY();
		if(x>=500 && x<=1000 && y>=200 && y<=400){
			in=true;
		}
		else{
			in=false;
		}
	}
	}
	public void mouseDragged(MouseEvent e){}
}

class bullet{
	private int bulletx;
	private int bullety;
	public bullet(int a){
		bulletx=a;
		bullety=640;
	}
	public void drawbullet(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(bulletx-5,bullety ,10 ,5);
	}
	public void incrementy(){
		if(bullety>=100)
			bullety-=3;
	}
	public int gety(){
		return bullety; 
	}
	public int getx(){
		return bulletx-5;
	}
}

class turret{
	private int turretx;
	private int turrety;
	private int R = (int) (Math.random( )*256);
	private int G = (int)(Math.random( )*256);
	private int B= (int)(Math.random( )*256);
	public static double adding=(int)1;
	public turret(int a){
		turretx=a;
		turrety=0;
	}
	public void drawturret(Graphics g){//draw a new turret 
		Color temp=new Color(R,G,B);
		g.setColor(temp);
		g.fillOval(turretx+5, turrety,30 , 30);
		g.setColor(Color.DARK_GRAY);
		g.fillOval(turretx, turrety+20, 40, 15 );
		g.setColor(Color.gray);
		//draw lines on bottom of ufo
		g.drawLine(turretx+10, turrety+20,turretx+10, turrety+35 );
		g.drawLine(turretx+20, turrety+20,turretx+20, turrety+35 );
		g.drawLine(turretx+30, turrety+20,turretx+30, turrety+35 );
		//beam
		g.setColor(Color.YELLOW);
		int[] xpoints={turretx+10, turretx+30, turretx+35, turretx+5};
		int[]ypoints={turrety+35, turrety+35, turrety+50, turrety+50};
		g.fillPolygon(xpoints, ypoints, 4);
		//draw antenna
		g.setColor(Color.WHITE);
		g.fillOval(turretx+10, turrety-5, 10, 10);
		g.fillOval(turretx+20, turrety-5, 10, 10);
		//draw feet
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(turretx+10, turrety+35, turretx, turrety+40);
		g.drawLine(turretx+30, turrety+35, turretx+40, turrety+40);
		
	}
	public void incrementturrety(){
		if(turrety<=650)
			turrety+=adding;
	}
	public int gety(){
		return turrety;
	}
	public int getx(){
		return turretx;
	}
	public static void increaseadd(){
		adding+=0.5;
	}
	public static double getadding(){
		return adding;
	}
	public static void resetadding(){
		adding=1;
	}
}

