package it.algos.base24.backend.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.server.*;
import lombok.*;

public class DownloadAnchor extends Anchor {

    @Getter
    private Button button;

    private DownloadStartedListener downloadStartedListener;

    public DownloadAnchor(AbstractStreamResource href, String text) {
        super(href, "");
        getElement().setAttribute("download", true);
        button = new Button(text, new Icon(VaadinIcon.DOWNLOAD_ALT));
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (downloadStartedListener != null) {
                downloadStartedListener.downloadStarted();
            }
        });
        add(button);
    }

    public void addDownloadStartedListener(DownloadStartedListener downloadStartedListener) {
        this.downloadStartedListener = downloadStartedListener;
    }

    public interface DownloadStartedListener {

        void downloadStarted();

    }

}
