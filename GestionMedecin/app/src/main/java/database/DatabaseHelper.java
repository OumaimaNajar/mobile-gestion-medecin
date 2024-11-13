package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esprit.gestionmedecin.models.Medicament;
import com.esprit.gestionmedecin.models.Reclamation;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MedicationApp.db";
    private static final String TABLE_RECLAMATIONS = "Reclamations";
    private static final String TABLE_MEDECINS = "Medecins";
    private static final String TABLE_HISTORIQUE_MEDICAMENTS = "HistoriqueMedicaments";
    private static final String TABLE_MEDICAMENTS = "Medicaments"; // Nouvelle table Medicaments

    private static final int DATABASE_VERSION = 5;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating database and tables...");

        // Création de la table des médecins
        String CREATE_MEDECINS_TABLE = "CREATE TABLE " + TABLE_MEDECINS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "specialite TEXT, " +
                "numero TEXT)";
        db.execSQL(CREATE_MEDECINS_TABLE);
        Log.d("DatabaseHelper", "Medecins table created.");

        // Vérification et ajout des médecins initiaux si la table est vide
        if (getMedecinsCount(db) == 0) {
            addInitialMedecin(db, "Dr. Jean Dupont", "Dentist", "+216-90999999");
            addInitialMedecin(db, "Dr. Marie Curie", "Cardiologist", "+216-90000000");
            addInitialMedecin(db, "Dr. Albert Einstein", "Neurologist", "+216-90111111");
        }

        // Création de la table des réclamations
        String CREATE_RECLAMATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RECLAMATIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "reclamation TEXT)";
        db.execSQL(CREATE_RECLAMATIONS_TABLE);
        Log.d("DatabaseHelper", "Reclamations table created.");



        String CREATE_HISTORIQUE_MEDICAMENTS_TABLE = "CREATE TABLE " + TABLE_HISTORIQUE_MEDICAMENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +                   // Nom du médicament
                "datePrise TEXT, " +              // Date de la prise
                "dose TEXT, " +                   // Dose prise
                "pris INTEGER)";                  // 1 si pris, 0 si non pris
        db.execSQL(CREATE_HISTORIQUE_MEDICAMENTS_TABLE);
        Log.d("DatabaseHelper", "HistoriqueMedicaments table created.");


        // Création de la table des médicaments
        String CREATE_MEDICAMENTS_TABLE = "CREATE TABLE " + TABLE_MEDICAMENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +                // Nom du médicament
                "datePrise TEXT, " +          // Date de prise du médicament
                "dose TEXT, " +               // Dose prise
                "pris INTEGER)";              // 1 si pris, 0 si non pris
        db.execSQL(CREATE_MEDICAMENTS_TABLE);
        Log.d("DatabaseHelper", "Medicaments table created.");

        // Ajout des médicaments initiaux
        if (getMedicamentsCount(db) == 0) {
            addInitialMedicament(db, "Paracetamol", "2023-11-10", "500 mg", false);
            addInitialMedicament(db, "Ibuprofen", "2023-11-11", "200 mg", false);
            addInitialMedicament(db, "Aspirin", "2023-11-12", "100 mg", true);
        }


    }

    // Méthode pour ajouter un médicament initial
    private void addInitialMedicament(SQLiteDatabase db, String nom, String datePrise, String dose, boolean pris) {
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("datePrise", datePrise);
        values.put("dose", dose);
        values.put("pris", pris ? 1 : 0);  // 1 pour pris, 0 pour non pris
        db.insert(TABLE_MEDICAMENTS, null, values);
        Log.d("DatabaseHelper", "Initial medicament added: " + nom);
    }

    // Méthode pour compter les médicaments
    private int getMedicamentsCount(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MEDICAMENTS, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    private int getMedecinsCount(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MEDECINS, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    private void addInitialMedecin(SQLiteDatabase db, String nom, String specialite, String numero) {
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("specialite", specialite);
        values.put("numero", numero);
        db.insert(TABLE_MEDECINS, null, values);
        Log.d("DatabaseHelper", "Initial medecin added: " + nom);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDECINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECLAMATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORIQUE_MEDICAMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTS); // Supprime la table Medicaments si elle existe

        onCreate(db);
    }

    // Méthode pour ajouter une réclamation
    public boolean addReclamation(String reclamationText) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("reclamation", reclamationText);

            long result = db.insert(TABLE_RECLAMATIONS, null, values);

            if (result == -1) {
                Log.e("DatabaseHelper", "Insertion of reclamation failed.");
                return false;  // Retourne false en cas d'échec
            } else {
                Log.d("DatabaseHelper", "Reclamation inserted successfully with ID: " + result);
                return true;  // Retourne true si l'insertion réussit
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error adding reclamation", e);
            return false;  // Retourne false en cas d'erreur
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    // Méthode pour récupérer toutes les réclamations sous forme de liste d'objets Reclamation
    public Cursor getAllReclamations() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DatabaseHelper", "Fetching all reclamations...");
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECLAMATIONS, null);

        if (cursor != null) {
            Log.d("DatabaseHelper", "Number of reclamations fetched: " + cursor.getCount());
        } else {
            Log.e("DatabaseHelper", "Failed to fetch reclamations, cursor is null.");
        }
        return cursor;
    }


    // Méthode pour récupérer tous les médecins
    public Cursor getAllMedecins() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DatabaseHelper", "Fetching all medecins...");
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDECINS, null);

        if (cursor != null) {
            Log.d("DatabaseHelper", "Number of medecins fetched: " + cursor.getCount());
        } else {
            Log.e("DatabaseHelper", "Failed to fetch medecins, cursor is null.");
        }
        return cursor;
    }


    // méthode supprimer reclamation
    public boolean deleteReclamation(int id) {
        boolean isDeleted = false;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int rowsDeleted = db.delete(TABLE_RECLAMATIONS, "id = ?", new String[]{String.valueOf(id)});
            isDeleted = rowsDeleted > 0;
            Log.d("DatabaseHelper", "Reclamation with ID " + id + (isDeleted ? " deleted successfully." : " failed to delete."));
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting reclamation with ID: " + id, e);
        }
        return isDeleted;
    }

    // méthode supprimer médicament
    public boolean deleteMedicament(int id) {
        boolean isDeleted = false;
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            // Vérification de l'existence du médicament
            Cursor cursor = db.query(TABLE_MEDICAMENTS, new String[]{"id"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // ID trouvé, on peut procéder à la suppression
                int rowsDeleted = db.delete(TABLE_MEDICAMENTS, "id = ?", new String[]{String.valueOf(id)});
                isDeleted = rowsDeleted > 0;
                Log.d("DatabaseHelper", "Medicament with ID " + id + (isDeleted ? " deleted successfully." : " failed to delete."));
            } else {
                Log.d("DatabaseHelper", "Medicament with ID " + id + " not found.");
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error deleting medicament with ID: " + id, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return isDeleted;
    }




    // ajout historique
    public boolean addHistoriqueMedicament(String nom, String datePrise, String dose, boolean pris) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nom", nom);
            values.put("datePrise", datePrise);
            values.put("dose", dose);
            values.put("pris", pris ? 1 : 0);  // 1 pour pris, 0 pour non pris

            long result = db.insert(TABLE_HISTORIQUE_MEDICAMENTS, null, values);
            if (result == -1) {
                Log.e("DatabaseHelper", "Insertion of historique failed.");
                return false;
            } else {
                Log.d("DatabaseHelper", "HistoriqueMedicament inserted successfully with ID: " + result);
                return true;
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error adding historique medicament", e);
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // get historique
    public List<Medicament> getHistoriqueMedicaments() {
        List<Medicament> medicamentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DatabaseHelper", "Fetching historique des medicaments...");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORIQUE_MEDICAMENTS, null);

        if (cursor.moveToFirst()) {
            do {
                String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                String datePrise = cursor.getString(cursor.getColumnIndexOrThrow("datePrise"));
                String dose = cursor.getString(cursor.getColumnIndexOrThrow("dose"));
                boolean pris = cursor.getInt(cursor.getColumnIndexOrThrow("pris")) == 1;

                // Ajout à la liste avec le statut pris/non pris
                Medicament medicament = new Medicament(nom, datePrise, dose, pris);
                medicamentList.add(medicament);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return medicamentList;
    }


    // Méthode pour récupérer tous les médicaments
    public List<Medicament> getAllMedicaments() {
        List<Medicament> medicamentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("DatabaseHelper", "Fetching all medicaments...");

        // Requête pour récupérer tous les médicaments de la table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MEDICAMENTS, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                String datePrise = cursor.getString(cursor.getColumnIndexOrThrow("datePrise"));
                String dose = cursor.getString(cursor.getColumnIndexOrThrow("dose"));
                boolean pris = cursor.getInt(cursor.getColumnIndexOrThrow("pris")) == 1;

                Medicament medicament = new Medicament(nom, datePrise, dose, pris);
                medicamentList.add(medicament);
            }
            cursor.close();
        }
        return medicamentList;
    }


    public void updateMedicamentStatus(Medicament medicament) {
        // Exemple de mise à jour dans la base de données
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pris", medicament.isPris());  // Mise à jour du champ 'pris'

        // Utilisez un identifiant unique (par exemple, nom ou un autre identifiant unique) pour trouver le médicament à mettre à jour
        db.update("medicaments", contentValues, "nom = ?", new String[]{medicament.getNom()});
    }






}
