package org.poker.graphics;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;



public interface DragAndDropImages extends ClientBundle {
	@Source("images/dd/drag.gif")
	  ImageResource drag();

	  @Source("images/dd/drop.gif")
	  ImageResource drop();

}
