package org.poker.graphics;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;

public interface GameSounds extends ClientBundle {

        @Source("org/poker/graphics/sounds/BalloonPopping.mp3")
        DataResource betMp3();

        @Source("org/poker/graphics/sounds/BalloonPopping.wav")
        DataResource betWav();

        @Source("org/poker/graphics/sounds/BalloonPopping.mp3")
        DataResource callMp3();

        @Source("org/poker/graphics/sounds/BalloonPopping.wav")
        DataResource callWav();
        
        @Source("org/poker/graphics/sounds/BalloonPopping.mp3")
        DataResource foldMp3();

        @Source("org/poker/graphics/sounds/BalloonPopping.wav")
        DataResource foldWav();

        @Source("org/poker/graphics/sounds/Shotgun.mp3")
        DataResource raiseMp3();

        @Source("org/poker/graphics/sounds/Shotgun.wav")
        DataResource raiseWav();
        
        @Source("org/poker/graphics/sounds/BalloonPopping.wav")
        DataResource checkWav();

        @Source("org/poker/graphics/sounds/BalloonPopping.mp3")
        DataResource checkMp3();

        
        @Source("org/poker/graphics/sounds/BalloonPopping.mp3")
        DataResource wrongMoveMp3();

        @Source("org/poker/graphics/sounds/BalloonPopping.wav")
        DataResource wrongMoveWav();
}

