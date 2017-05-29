package a9;

public class ImageEditorModel {

	private Picture original;
	private ObservablePicture current;
	
	public ImageEditorModel(Picture f) {
		original = f;
		current = original.copy().createObservable();
	}

	public void setNewPicture(Picture p){
		original = p;
		current = original.copy().createObservable();
		System.out.println("set was accessed");
	}
	
	public ObservablePicture getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public ColorPixel avgRGB(Picture p) {
		int tick = 0;
		double redAmt = 0.0;
		double greenAmt = 0.0;
		double blueAmt = 0.0;
		
		for (int i = 0; i < p.getWidth(); i++ ){
			for (int j = 0; j <p.getHeight(); j++){
				redAmt += p.getPixel(i,j).getRed();
				greenAmt += p.getPixel(i,j).getGreen();
				blueAmt += p.getPixel(i,j).getBlue();
				tick++;
			}
		}
		
		//redAmt is total of red, and divide that by how many ticks on the slider 
		redAmt = redAmt/tick;
		greenAmt = greenAmt/tick;
		blueAmt = blueAmt/tick;
		
		return new ColorPixel(redAmt, greenAmt, blueAmt);
	}
	
	public void paintAt(int x, int y, Pixel brushColor, int brush_size) {
		current.suspendObservable();
		
		double opacity = ImageEditorController.getImageEditorController().
				getPaintBrushTool().getPaintBrushToolUI().getOpacity();
		if (opacity == 0) {
			current.resumeObservable();
			return;
		}
		
		double dst = 1.0 - opacity; //destination color, where the pixel is gunna go
		double src = opacity; //brush color
		for (int xpos=x-brush_size+1; xpos <=x+brush_size-1; xpos++) {
			for (int ypos=y-brush_size+1; ypos <=y+brush_size-1; ypos++) {
				if (xpos >= 0 &&
					xpos < current.getWidth() &&
					ypos >= 0 &&
					ypos < current.getHeight()) {
					
					ColorPixel srcPixel = (ColorPixel)current.getPixel(xpos, ypos); // pixel under the brush
					//blended pixel
					ColorPixel newPixel = new ColorPixel(srcPixel.getRed() * dst + 
							brushColor.getRed() * src, srcPixel.getGreen() * dst + 
							brushColor.getGreen() * src, srcPixel.getBlue() * dst + 
							brushColor.getBlue() * src);
					current.setPixel(xpos, ypos, newPixel);
				}
			}
		}
		current.resumeObservable();
	//}
	

	
	ObservablePicture changedPic = new ObservablePictureImpl(new PictureImpl(current.getWidth(), current.getHeight()));
	//current.suspendObservable();

	if (opacity == 0) {
		current.resumeObservable();
		return;
	}
	
	
	changedPic.resumeObservable();
	//current.setNewPicture(changedPic);
}
	
}
