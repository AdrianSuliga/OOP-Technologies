package controller;

import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;

public class GalleryController {

    private Gallery galleryModel;

    @FXML
    private TextField imageNameField;

    @FXML
    public ImageView imageView;

    @FXML
    public ListView<Photo> imagesListView;

    @FXML
    public TextField searchTextField;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(_ -> new ListCell<>() {
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

        imagesListView.getSelectionModel().selectedItemProperty().addListener((_, oldPhoto, newPhoto) -> {
            if (oldPhoto != null) {
                imageNameField.textProperty().unbindBidirectional(oldPhoto.nameProperty());
            }

            if (newPhoto != null) {
                bindSelectedPhoto(newPhoto);
            }
        });
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
    }

    @FXML
    public void searchButtonClicked(ActionEvent event) {
        PhotoDownloader downloader = new PhotoDownloader();

        galleryModel.clear();
        downloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(photo -> galleryModel.addPhoto(photo));
    }
}

