package br.com.friendlydonations.application.account;

/**
 * Created by brunogabriel on 16/03/17.
 */

public class AccountPresenter {

    private AccountView view;

    public AccountPresenter(AccountView view) {
        this.view = view;
    }

    public void tryToChangeProfileImage() {
        view.showImageUpdater();
    }

    public void openCamera() {
        view.openDeviceCamera();
    }

    public void openGallery() {
        view.openDeviceGallery();
    }
}
