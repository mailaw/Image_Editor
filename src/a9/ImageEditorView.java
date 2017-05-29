package a9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;

public class ImageEditorView extends JPanel implements KeyListener, ActionListener {

	private JFrame main_frame;
	private PictureView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JPanel load_url_panel;
	private static JToggleButton loadButton;
	private static JTextField url;
	private static String urlText;
	private static boolean toggleSelect;
	private static boolean urlEntered;
	
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		toggleSelect = false;
		
		setLayout(new BorderLayout());
		
		//this is the picture being edited
		frame_view = new PictureView(model.getCurrent());
		
		add(frame_view, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		load_url_panel = new JPanel();
		load_url_panel.setLayout(new GridLayout(1,3));
		
		JToggleButton loadButton = new JToggleButton("Load URL");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				url = new JTextField("Enter URL Here");
				ImageEditorController.getImageEditorController().getImageEditorView().unsafeAddKeyListenerToURL(url);
				load_url_panel.add(url);
				
				JToggleButton loadBtn = (JToggleButton) e.getSource();
				if(loadBtn.isSelected()){
					toggleSelect = true;
					loadBtn.setText("Enter URL:");
					urlText = url.getText();
				}
				else {
					toggleSelect = false;
					System.out.println("Button not selected");
					loadBtn.setText("Using Default URL");
				}
			}
		});

		load_url_panel.add(loadButton);
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		
		add(load_url_panel, BorderLayout.NORTH);
		add(tool_ui_panel, BorderLayout.SOUTH);
		
		tool_ui = null;
	}
	
	public void unsafeAddKeyListenerToURL(JTextField url) {
		url.addKeyListener(this);
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}
	
	public static String getURL(){
		return urlText;
	}
	
	public static boolean getToggleSelect(){
		return toggleSelect;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
			Picture f = null;
			try {
				f = PictureImpl.readFromURL(urlText); //returns picture 
				System.out.println("urlText");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(f != null) {
				main_frame.dispose(); 
				ImageEditorController.getImageEditorController().getModel().setNewPicture(f);
			}
		} 
	}
	
//	@Override
//	public void keyPressed(KeyEvent e) {
//		
//		//ImageEditorModel current = ImageEditorController.getImageEditorController().getModel();
//		
//		model.suspendObservable();
//		if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER){ 
//			Picture f = PictureImpl.readFromURL(urlText);
//			ImageEditorController.getImageEditorController().getModel().setNewPicture(f);	
//		}
//		model.resumeObservable();
//	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
