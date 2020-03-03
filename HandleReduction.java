import java.awt.Color;
import javax.swing.JFrame;


public class HandleReduction{
    public static void main(String args[]) {
	JFrame  handleFrame= new JFrame(); 
	handleFrame.setTitle("Handle reduction");
	handleFrame.setBackground(Color.white);
	handleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	handleFrame.setContentPane(new Components());
	handleFrame.setSize(1000,900);
	handleFrame.setVisible(true);
    }
}
