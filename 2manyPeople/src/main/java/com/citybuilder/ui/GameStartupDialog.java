package com.citybuilder.ui;

import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.modelBis.City;

import javax.swing.*;
import java.awt.*;

public class GameStartupDialog extends JDialog {
    private final GameStartupFactory factory;
    private City createdCity;
    private JTextField nameField;
    private JSpinner hazardRateSpinner;

    public GameStartupDialog(Frame parent, GameStartupFactory factory) {
        super(parent, "Nouvelle Ville", true);
        this.factory = factory;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nom de la ville
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nom de la ville:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Taux de risque
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Taux de risque (0.0 - 1.0):"), gbc);

        gbc.gridx = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(0.1, 0.0, 1.0, 0.1);
        hazardRateSpinner = new JSpinner(model);
        add(hazardRateSpinner, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Créer");
        JButton cancelButton = new JButton("Annuler");

        createButton.addActionListener(e -> createCity());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void createCity() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de ville", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float hazardRate = ((Number) hazardRateSpinner.getValue()).floatValue();
        createdCity = factory.createCity(name, hazardRate);
        dispose();
    }

    public City getCreatedCity() {
        return createdCity;
    }
} 