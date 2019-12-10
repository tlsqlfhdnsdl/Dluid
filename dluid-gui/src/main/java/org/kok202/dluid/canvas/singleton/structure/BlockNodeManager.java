package org.kok202.dluid.canvas.singleton.structure;

import lombok.Data;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.application.Util.MathUtil;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.entity.MergeBlockProperty;
import org.kok202.dluid.domain.exception.CanNotFindGraphNodeException;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
public class BlockNodeManager extends GraphManager<BlockNode>{

    public void removeGraphNode(long layerId) {
        // Remove all directly connected pipe layer
        Stream.concat(
                findGraphNodeByLayerId(layerId).getIncomingNodes().stream(),
                findGraphNodeByLayerId(layerId).getOutgoingNodes().stream())
                .forEach(blockNodeGraphNode -> {
                    if (blockNodeGraphNode.getData().getBlockInfo().getLayer().getType() == LayerType.PIPE_LAYER)
                        removeGraphNode(blockNodeGraphNode.getData().getBlockInfo().getLayer().getId());
                });

        // Remove me
        removeGraphNode(
                blockNodeObj -> {
                    BlockNode blockNode = (BlockNode) blockNodeObj;
                    return blockNode.getBlockInfo().getLayer().getId() == layerId;
                },
                graphNode -> {
                    GraphNode<BlockNode> blockGraphNode = (GraphNode<BlockNode>) graphNode;
                    blockGraphNode.getData().deleteHexahedrons();
                });

        reshapeAllBlockByType();
    }

    public GraphNode<BlockNode> findTestInputGraphNode(){
        for(BlockNode blockNode : getDataNodes()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if (layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Test input block node");
    }

    public GraphNode<BlockNode> findTrainInputGraphNode(){
        Collection<BlockNode> blockNodes = getDataNodes();
        for(BlockNode blockNode : blockNodes) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Train input block node");
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(long layerId) {
        return findGraphNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockInfo().getLayer().getId() == layerId;
        });
    }

    public void notifyLayerDataChanged(long layerId){
        GraphNode<BlockNode> graphNode = findGraphNodeByLayerId(layerId);
        BlockNode blockNode = graphNode.getData();
        blockNode.reshapeBlockModel();
        reshapeAllBlockByType();
    }

    public void reshapeAllBlockByType(){
        reshapeAllMergeBlock();
        reshapeAllPipeBlock();
    }

    private void reshapeAllMergeBlock(){
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockInfo().getLayer().getType() == LayerType.MERGE_LAYER)
                .forEach(graphNodeBlockNode -> {
                    Layer layer = graphNodeBlockNode.getData().getBlockInfo().getLayer();
                    List<Layer> incomingLayers = CanvasFacade.findIncomingLayers(layer.getId());

                    int inputSize = 0;
                    for (Layer incomingLayer : incomingLayers) {
                        inputSize += incomingLayer.getProperties().getOutputSize()[0] * incomingLayer.getProperties().getOutputSize()[1];
                    }
                    inputSize = Math.max(inputSize, 1);

                    List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
                    MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) layer.getExtra();
                    int outputSizeY = recommendedDivisors.get(mergeBlockProperty.getPointingIndex(recommendedDivisors.size()));
                    int outputSizeX = inputSize / outputSizeY;
                    layer.getProperties().setInputSize(outputSizeX, outputSizeY);
                    layer.getProperties().setOutputSize(outputSizeX, outputSizeY);
                    graphNodeBlockNode.getData().reshapeBlockModel();
                });
    }

    private void reshapeAllPipeBlock(){
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockInfo().getLayer().getType() == LayerType.PIPE_LAYER)
                .forEach(graphNodeBlockNode -> {
                    GraphNode<BlockNode> sourceGraphNodeBlockNode = graphNodeBlockNode.getIncomingNodes().get(0); // Exist only one. because it is pipe block.
                    GraphNode<BlockNode> destinationGraphNodeBlockNode = graphNodeBlockNode.getOutgoingNodes().get(0); // Exist only one. because it is pipe block.

                    if(destinationGraphNodeBlockNode.getData().getBlockInfo().getLayer().getType() == LayerType.MERGE_LAYER){
                        int[] sourceOutputSize = sourceGraphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().getOutputSize();
                        int[] destinationInputSize = destinationGraphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().getInputSize();
                        graphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().setInputSize(sourceOutputSize[0], sourceOutputSize[1]);
                        graphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().setOutputSize(destinationInputSize[0], destinationInputSize[1]);
                    }
                    else{
                        int[] sourceOutputSize = sourceGraphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().getOutputSize();
                        graphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().setInputSize(sourceOutputSize[0], sourceOutputSize[1]);
                        graphNodeBlockNode.getData().getBlockInfo().getLayer().getProperties().setOutputSize(sourceOutputSize[0], sourceOutputSize[1]);
                    }
                    graphNodeBlockNode.getData().reshapeBlockModel();
                });
    }
}
