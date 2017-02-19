package br.com.friendlydonations.application.donate;

import br.com.friendlydonations.shared.models.PictureDiskModel;

/**
 * Created by brunogabriel on 16/02/17.
 */
public interface DonateView {
    void emitGooglePlayServicesError(int titleId, int contentId, int yesId, boolean isScrollable);
    void onClickChangePicture(PictureDiskModel pictureDiskModel);
}
