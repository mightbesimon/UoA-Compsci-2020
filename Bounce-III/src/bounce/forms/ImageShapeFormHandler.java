package bounce.forms;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import bounce.ImageRectangleShape;
import bounce.NestingShape;
import bounce.ShapeModel;
import bounce.forms.util.Form;
import bounce.forms.util.FormHandler;

/**
 * FormHandler implementation for reading form data and using this to
 * instantiate class ImageRectangleShape.
 *
 * @author Simon Shan
 *
 */
public class ImageShapeFormHandler implements FormHandler {

	private ShapeModel _model;
	private NestingShape _parentOfNewShape;
	private SwingWorker<ImageRectangleShape,Void> _worker;

	/**
	 * Creates an ImageShapeFormHandler.
	 *
	 * @param model the ShapeModel to which the handler should add a newly
	 *        constructed ImageRectangleShape object.
	 * @param parent the NestingShape object that will serve as the parent for
	 *        a new ImageRectangleShape instance.
	 */
	public ImageShapeFormHandler(ShapeModel model,
									NestingShape parent) {
		_model = model;
		_parentOfNewShape = parent;
	}

	long startTime;
	File imageFile;
	int width, deltaX, deltaY;
	/**
	 * Reads form data that describes an ImageRectangleShape. Based on the
	 * data, this ImageShapeFormHandler creates a new ImageRectangleShape
	 * object, adds it to a ShapeModel and to a NestingShape within the model.
	 *
	 * @param form the Form that contains the ImageRectangleShape data.
	 */
	@Override
	public void processForm(Form form) {
		startTime = System.currentTimeMillis();

		// Read field values from the form.
		imageFile = (File)form.getFieldValue(File.class, ImageFormElement.IMAGE);
		width = form.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
		deltaX = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
		deltaY = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);

		_worker = new Worker();
		_worker.execute();
	}


	private class Worker extends SwingWorker<ImageRectangleShape,Void> {

		@Override
		protected ImageRectangleShape doInBackground() throws Exception {
			// Load the original image (ImageIO.read() is a blocking call).
			BufferedImage fullImage = null;
			try {
				fullImage = ImageIO.read(imageFile);
			} catch(IOException e) {
				e.printStackTrace();
			}

			int fullImageWidth = fullImage.getWidth();
			int fullImageHeight = fullImage.getHeight();

			BufferedImage scaledImage = fullImage;

			// Scale the image if necessary.
			if(fullImageWidth > width) {
				double scaleFactor = (double)width / (double)fullImageWidth;
				int height = (int)((double)fullImageHeight * scaleFactor);

				scaledImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = scaledImage.createGraphics();

				// Method drawImage() scales an already loaded image. The
				// ImageObserver argument is null because we don't need to monitor
				// the scaling operation.
				g.drawImage(fullImage, 0, 0, width, height, null);
			}

			// Create the new Shape and add it to the model.
			return new ImageRectangleShape(deltaX, deltaY, scaledImage);
		}

		protected void done() {
			try {
				ImageRectangleShape imageShape = get();
				_model.add(imageShape, _parentOfNewShape);

				long elapsedTime = System.currentTimeMillis() - startTime;
				System.out.println("Image loading and scaling took " + elapsedTime + "ms.");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
