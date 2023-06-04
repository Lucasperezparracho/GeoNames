package geonames;

import geonames.controllers.ApplicationController;
import geonames.views.View;

public class Main {
    public static void main(String[] args) {
        ApplicationController controller = new ApplicationController();
        View view = new View(controller);
        view.setVisible(true);
    }
}
