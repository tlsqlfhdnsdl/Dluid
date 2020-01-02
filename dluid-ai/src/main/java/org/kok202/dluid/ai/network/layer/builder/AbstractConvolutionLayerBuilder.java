package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

public abstract class AbstractConvolutionLayerBuilder extends AbstractLayerBuilder {

    @Override
    protected void setAddOnProperties(Layer layer, BaseLayer.Builder builder) {
        ConvolutionLayer.Builder convolutionLayerBuilder = (ConvolutionLayer.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            convolutionLayerBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            convolutionLayerBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            convolutionLayerBuilder.setStride(layer.getProperties().getStrideSize());
    }

    @Override
    protected void setCommonProperties(Layer layer, BaseLayer.Builder builder) {
        ConvolutionLayer.Builder convolutionLayerBuilder = (ConvolutionLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            convolutionLayerBuilder.nIn(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1]);
        if(layer.getProperties().getOutputSize() != null)
            convolutionLayerBuilder.nOut(layer.getProperties().getOutputSize()[0] * layer.getProperties().getOutputSize()[1]);
        if(layer.getProperties().getWeightInit() != null)
            WeightInitWrapperUtil.applyWeightInit(layer, convolutionLayerBuilder);
        if(layer.getProperties().getActivationFunction() != null)
            convolutionLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            convolutionLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}