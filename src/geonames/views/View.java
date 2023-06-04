package geonames.views;

import geonames.controllers.ApplicationController;
import geonames.models.Place;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {
    private ApplicationController controller;

    private JTextField searchTextField;
    private JTextArea resultTextArea;

    public View(ApplicationController controller) {
        this.controller = controller;

        setTitle("GeoNames Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        resultTextArea = new JTextArea();
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        add(resultScrollPane, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Search:");
        searchPanel.add(searchLabel);

        searchTextField = new JTextField(20);
        searchPanel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = searchTextField.getText();
                if (!searchName.isEmpty()) {
                    List<Place> places = controller.searchPlaces(searchName);
                    displayPlaces(places);
                }
            }
        });
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private void displayPlaces(List<Place> places) {
        resultTextArea.setText("");
        if (places.isEmpty()) {
            resultTextArea.append("No places found.");
        } else {
            for (Place place : places) {
                resultTextArea.append("Name: " + place.getName() + "\n");
                resultTextArea.append("Latitude: " + place.getLatitude() + "\n");
                resultTextArea.append("Longitude: " + place.getLongitude() + "\n");
                resultTextArea.append("Distance: " + place.getDistance() + " meters\n");
                resultTextArea.append("\n");
            }
        }
    }
}
