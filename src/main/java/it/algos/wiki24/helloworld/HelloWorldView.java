package it.algos.wiki24.helloworld;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import it.algos.base24.ui.view.*;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class HelloWorldView extends Composite<VerticalLayout> {

    public HelloWorldView() {
        getContent().setHeightFull();
        getContent().setWidthFull();
    }
}
