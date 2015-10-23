package com.karol.presentation.navigation;

import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public class GoBackNavigator {

    private List<NavigationRule> navigationRules;

    public GoBackNavigator() {
        navigationRules = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        // edycja kontrahenta -> lista kontrahentow
        navigationRules.add(new NavigationRule(ContractorPresenter.class, ContractorListPresenter.class, true));
        // lista umow -> lista kontrahentow
        navigationRules.add(new NavigationRule(ContractListPresenter.class, ContractorListPresenter.class, false));
        // nowa umowa -> lista umow
        navigationRules.add(new NavigationRule(ContractPresenter.class, ContractListPresenter.class, true));
    }

    public Parent getGoBackView(Initializable presenter) {
        if (ViewsCache.contractListView().presenter().getClass().equals(presenter.getClass())) {

        }
        return null;
    }

    class NavigationRule {
        private Class from;
        private Class to;
        private boolean clean;

        public NavigationRule(Class from, Class to) {
            this.from = from;
            this.to = to;
            this.clean = false;
        }

        public NavigationRule(Class from, Class to, boolean clean) {
            this.from = from;
            this.to = to;
            this.clean = clean;
        }
    }
}
