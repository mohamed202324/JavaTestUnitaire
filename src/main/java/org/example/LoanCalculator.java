package org.example;



public class LoanCalculator {

    public double calculMensualite(double capital, double tauxAnnuel, int dureeAnnees) {

        if (capital <= 0) {
            throw new IllegalArgumentException("Capital invalide");
        }
        if (tauxAnnuel < 0) {
            throw new IllegalArgumentException("Taux invalide");
        }
        if (dureeAnnees <= 0) {
            throw new IllegalArgumentException("Durée invalide");
        }

        double tauxMensuel = tauxAnnuel / 12;
        int nbMois = dureeAnnees * 12;

        if (tauxMensuel == 0) {
            return capital / nbMois;
        }

        return (capital * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -nbMois));
    }
}