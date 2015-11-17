import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Seurat extends JPanel implements ChangeListener, MouseListener {
	ArrayList<BufferedImage> images;
	ArrayList<Circle> dots;
	int diameter;
	JSlider dotsize;
	int picnum;
	boolean pause;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Dots");
		frame.setSize(800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		Seurat se = new Seurat();
		frame.getContentPane().add(se);
		frame.addMouseListener(se);
		frame.setVisible(true);
	}
	
	public Seurat() {
		images = new ArrayList<BufferedImage>();
		
		try {
		//	images.add(ImageIO.read(new File("green.jpg")));
		//	images.add(ImageIO.read(new File("beluga.jpg")));
			images.add(ImageIO.read(new File("beach.jpg")));
		//	images.add(ImageIO.read(new File("abarca.jpg")));
			images.add(ImageIO.read(new File("pikachu.jpg")));
			images.add(ImageIO.read(new File("bulbasaur.jpg")));
			images.add(ImageIO.read(new File("mario.jpg")));
			images.add(ImageIO.read(new File("narwhal.jpg")));
		} catch (FileNotFoundException f) {
			System.out.println("Could not find picture.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error - IOException.");
			System.exit(1);
		}
		
		dots = new ArrayList<Circle>();
		diameter = 20;
		
		setLayout(null);
		
		dotsize = new JSlider(5, 35);
		dotsize.setMajorTickSpacing(5);
		dotsize.setPaintLabels(true);
		dotsize.addChangeListener(this);
		dotsize.setSize(new Dimension(150, 40));
		
		JButton open = new JButton("open picture");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause = true;
				String fn = FCT.pickImageFile();
				if (fn != "") {
					try {
						
						images.add(ImageIO.read(new File(fn)));
						picnum = images.size() - 1;
						dots.clear();
					} catch (FileNotFoundException f2) {
						System.out.println("Could not find picture.");
					} catch (IOException e2) {
						System.out.println("Error - IOException.");
					}
				}
				pause = false;
				repaint();
			}
		});
		/*
		JButton pause = new JButton("pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause = true;
				String fn = FCT.pickImageFile();
				if (fn != "") {
					try {
						
						images.add(ImageIO.read(new File(fn)));
						picnum = images.size() - 1;
						dots.clear();
					} catch (FileNotFoundException f2) {
						System.out.println("Could not find picture.");
					} catch (IOException e2) {
						System.out.println("Error - IOException.");
					}
				}
				pause = false;
				repaint();
			}
		});*/
		
		JPanel right = new JPanel();
		right.add(new JLabel("Splotch size"));
		right.add(dotsize);
		right.add(open);
		
		right.setBounds(550, 0, 250, 550);
		add(right);
		
		picnum = 0;
		
		pause = false;
	}
	
	public void stateChanged(ChangeEvent e) {
		dots.clear();
		diameter = dotsize.getValue();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int x = (int)(Math.random() * (images.get(picnum).getWidth() - 1)) + 1;
		int y = (int)(Math.random() * (images.get(picnum).getHeight() - 1)) + 1;
		Circle newdot = new Circle(getColor(x, y), x, y, diameter);
		dots.add(newdot);
		
		for (int i = 0; i < dots.size(); i++) {
			Circle c = dots.get(i);
			g.setColor(c.color);
			g.fillOval(c.x, c.y, c.diameter, c.diameter);
		}
		
		if (!pause) repaint();
	}
	
	public Color getColor(int x, int y) {
		int clr = images.get(picnum).getRGB(x, y); 
		
		int red = (clr >> 16) & 0xff;
		int green = (clr >>  8) & 0xff;
		int blue = clr & 0xff;
		
		Color col = new Color(red, green, blue);
		return col;
	}
	
	public void mousePressed(MouseEvent e) {
		dots.clear();
		int oldpic = picnum;
		while (oldpic == picnum) {
			picnum = (int)(Math.random() * images.size());
		}
	}
	
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

class Circle {
	Color color;
	int x, y;
	int diameter;
	
	public Circle(Color c, int a, int b, int d) {
		color = c;
		x = a;
		y = b;
		diameter = d;
	}
	
}