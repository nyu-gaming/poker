package org.poker.graphics;


import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;

public class CardTurnAnimation extends Animation {
  
  private Image oldCard;
  private Image newCard;
  
  private Image transitCard;
  private int startWidth = 55;
  private int startHeight = 75;
  
  private RoundPanel imagePanel;
  
  public CardTurnAnimation(Image oldCard, Image newCard,
      RoundPanel oldCardContainer) {
    this.oldCard = oldCard;
    this.newCard = newCard;
    transitCard = oldCard == null ? newCard : oldCard;
    //startWidth = oldCard != null ? oldCard.getWidth() : 0;
    //startHeight = newCard.getHeight();
    imagePanel = oldCardContainer;
    //ContainerConnector.alert("new animation: " + startWidth + ", " + startHeight);
  }

  @Override
  protected void onUpdate(double progress) {
    //ContainerConnector.alert("update.. " + progress);
    int width;
    if (oldCard != null) {
      if (progress < 0.5) {
        width = (int)(startWidth * 2 * (0.5 - progress));
      }
      else {
        transitCard = newCard;
        width = (int)(startWidth * 2 * (progress - 0.5));
      }
      if (imagePanel.getWidgetCount() > 0) {
        imagePanel.remove(0);
      }
      transitCard.setPixelSize(width, startHeight);
      imagePanel.add(transitCard);
      //imagePanel.setCellHorizontalAlignment(transitCard, HasHorizontalAlignment.ALIGN_CENTER);
    }
    else {
      width =(int)(startWidth * progress);
      if (imagePanel.getWidgetCount() > 0) {
        imagePanel.remove(0);
      }
      transitCard.setPixelSize(width, startHeight);
      imagePanel.add(transitCard);
      //imagePanel.setCellHorizontalAlignment(transitCard, HasHorizontalAlignment.ALIGN_CENTER);
    }
    
  }

}
