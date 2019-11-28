package org.kok202.dluid.application.content.material.list;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.kok202.dluid.application.content.material.block.MaterialMergeController;
import org.kok202.dluid.application.content.material.block.MaterialReshapeController;
import org.kok202.dluid.application.content.material.block.MaterialTestInputController;
import org.kok202.dluid.application.content.material.block.MaterialTrainInputController;
import org.kok202.dluid.application.content.material.insertion.MaterialInsertionFollower;
import org.kok202.dluid.application.content.material.insertion.MaterialInsertionManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MaterialListAdvancedLayer extends AbstractMaterialList {
    @FXML
    private VBox layerAdvancedListBox;

    private MaterialInsertionManager materialInsertionManager;

    public MaterialListAdvancedLayer(MaterialInsertionManager materialInsertionManager) {
        this.materialInsertionManager = materialInsertionManager;
    }

    @Override
    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/material/list/layer_advanced_list.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        ((AnchorPane)itself).getChildren().add(new MaterialInsertionFollower().createView());
        addAbstractMaterialController(new MaterialTrainInputController());
        addAbstractMaterialController(new MaterialTestInputController());
        addAbstractMaterialController(new MaterialReshapeController());
        addAbstractMaterialController(new MaterialMergeController());
        addAbstractMaterialControllerToVBox(layerAdvancedListBox, materialInsertionManager);
        titledPane.setText(AppPropertiesSingleton.getInstance().get("frame.material.layers.advanced.title"));
    }
}