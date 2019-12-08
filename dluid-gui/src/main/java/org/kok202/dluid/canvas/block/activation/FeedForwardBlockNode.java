package org.kok202.dluid.canvas.block.activation;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class FeedForwardBlockNode extends ActivationBlockNode {
    public FeedForwardBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_BLUE,
                        CanvasConstant.COLOR_BLUE_,
                        CanvasConstant.COLOR_BLUE_,
                        CanvasConstant.COLOR_BLUE_,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                },
                new Color[]{
                        CanvasConstant.COLOR_BLUE_,
                        CanvasConstant.COLOR_BLUE__,
                        CanvasConstant.COLOR_BLUE__,
                        CanvasConstant.COLOR_BLUE__,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                });
    }
}
