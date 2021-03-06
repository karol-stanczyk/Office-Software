package com.karol.presentation.navigation;

import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.cache.views.ViewCache;
import com.karol.presentation.forms.contractors.contractor.ContractorPresenter;
import com.karol.presentation.forms.contractors.contractorlist.ContractorListPresenter;
import com.karol.presentation.forms.contracts.contract.ContractPresenter;
import com.karol.presentation.forms.contracts.contractlist.ContractListPresenter;
import com.karol.presentation.forms.invoices.invoice.InvoicePresenter;
import com.karol.presentation.forms.invoices.invoicelist.InvoiceListPresenter;
import com.karol.presentation.forms.invoices.payment.TransferPresenter;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // lista faktur -> lista umow
        navigationRules.add(new NavigationRule(InvoiceListPresenter.class, ContractListPresenter.class, true));
        // nowa faktura -> lista faktur
        navigationRules.add(new NavigationRule(InvoicePresenter.class, InvoiceListPresenter.class, true));
        // nowa płatność -> lista faktur
        navigationRules.add(new NavigationRule(TransferPresenter.class, InvoiceListPresenter.class, true));
    }

    @SuppressWarnings("ConstantConditions")
    public Parent getGoBackView(Initializable presenter) {
        Optional<NavigationRule> navigationRule = navigationRules.stream()
                .filter(nav -> nav.from.equals(presenter.getClass()))
                .findAny();
        if (navigationRule.isPresent()) {
            NavigationRule rule = navigationRule.get();
            ViewCache viewCache = ViewsCache.getView(rule.to);
            if (rule.clean) viewCache.refresh();
            return viewCache.getView();
        }
        return null;
    }

    class NavigationRule {
        private Class from;
        private Class to;
        private boolean clean;

        public NavigationRule(Class from, Class to, boolean clean) {
            this.from = from;
            this.to = to;
            this.clean = clean;
        }
    }
}
