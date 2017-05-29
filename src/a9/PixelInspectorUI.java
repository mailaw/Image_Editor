package a9;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import a9.ImageEditorController;

public class PixelInspectorUI extends JPanel implements ActionListener{

	private JLabel x_label;
	private JLabel y_label;
	private JLabel pixel_info;
	private JButton eyedropper;
	
	private double r_val;
	private double g_val;
	private double b_val;
	
	public PixelInspectorUI() {
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		eyedropper = new JButton("Eyedropper Tool");
		
		eyedropper.setPreferredSize(new Dimension(40, 40));
		eyedropper.addActionListener(this);
		
		setLayout(new GridLayout(4,1));
		add(x_label);
		add(y_label);
		add(pixel_info);
		add(eyedropper);
	}
	
	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getGreen(), p.getBlue()));
		r_val = p.getRed();
		g_val = p.getGreen();
		b_val = p.getBlue();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton eyedropper = (JButton) e.getSource();
		this.copyInfoToPaintBrush();
	}
	
	private void copyInfoToPaintBrush() {
		ImageEditorController.getImageEditorController().getPaintBrushTool().getPaintBrushToolUI().
			setBrushColor(r_val, g_val, b_val);
	}
}
