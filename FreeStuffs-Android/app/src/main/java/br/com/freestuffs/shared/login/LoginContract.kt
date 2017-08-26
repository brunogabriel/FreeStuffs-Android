package br.com.freestuffs.shared.login

import br.com.freestuffs.shared.application.BasePresenter
import br.com.freestuffs.shared.application.BaseView

/**
 * Created by bruno on 24/08/2017.
 */
interface LoginContract {
    interface View: BaseView<Presenter> {
        fun startView()
    }

    interface Presenter: BasePresenter
}