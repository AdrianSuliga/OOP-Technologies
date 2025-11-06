package controller;


import javafx.fxml.FXML;

import model.Gallery;
import model.Photo;

public class GalleryController {

    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        bindSelectedPhoto(gallery.getPhotos().get(0));
        imagesListView.setItems(gallery.getPhotos());
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        // TODO view <-> model bindings configuration
    }
}

