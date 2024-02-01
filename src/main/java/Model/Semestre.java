package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Semestre {
    public enum Mois {
        JANVIER, FEVRIER, MARS, AVRIL, MAI, JUIN, JUILLET, AOUT, SEPTEMBRE, OCTOBRE, NOVEMBRE, DECEMBRE;

        public static List<Mois> moisAPartirDe(Mois moisActuel) {
            List<Mois> moisList = new ArrayList<>(Arrays.asList(values()));
            int index = moisList.indexOf(moisActuel);

            if (index == -1) {
                return moisList;
            }

            List<Mois> moisAPartirDe = new ArrayList<>(moisList.subList(index, moisList.size()));

            // Si le tableau n'a pas 12 éléments, ajoutez les mois manquants en recommençant du début
            while (moisAPartirDe.size() < 12) {
                moisAPartirDe.addAll(Arrays.asList(values()));
            }

            // Retournez les 6 prochains mois à partir du mois actuel
            return moisAPartirDe.subList(0, 12);
        }
    }
    public static Mois obtenirMoisActuel() {
        LocalDate dateActuelle = LocalDate.now();
        int moisActuel = dateActuelle.getMonthValue();

        // Mois en Java commencent à 1 (JANUARY = 1, FEBRUARY = 2, etc.)
        return Mois.values()[moisActuel - 1];
    }

}
