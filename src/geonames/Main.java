package geonames;

import geonames.controllers.ApplicationController;
import geonames.views.View;

public class Main {
    public static void main(String[] args) {
        String username = "luquinhan03";
        ApplicationController controller = new ApplicationController(username);
        View view = new View(controller);
        view.setVisible(true);
    }
}
