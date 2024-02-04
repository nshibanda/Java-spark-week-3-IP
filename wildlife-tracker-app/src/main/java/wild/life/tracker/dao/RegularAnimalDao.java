package wild.life.tracker.dao;

import org.sql2o.Connection;

import wild.life.tracker.config.Database;
import wild.life.tracker.model.RegularAnimal;

import java.util.ArrayList;
import java.util.List;

public class RegularAnimalDao {
    //THIS CODE CREATES THE REGULAR ANIMAL TABLE AUTOMATICALLY ON THE DATABASE ON STARTING THE APP
    public static void getStarted (){

        try(Connection db = Database.getConnect().open()){
            String createTable = "CREATE TABLE IF NOT EXISTS regular_animal(regular_animal_id SERIAL PRIMARY KEY, animalName varchar, health varchar, age varchar, deleted boolean default false);";
            db.createQuery(createTable).executeUpdate();
        } catch (Exception error) {System.out.println(error.getMessage());}
    }

    //ADDS A REGULAR ANIMAL INTO THE DATABASE
    public static void addRegularAnimal(RegularAnimal additionalRegularAnimal) {
        try (Connection db = Database.getConnect().open()) {
            // Database action
            String regularAnimalAdd = "INSERT INTO regular_animal(animalName, health, age, deleted) VALUES (UPPER(:animalName), :health, :age, false)";
            db.createQuery(regularAnimalAdd).bind(additionalRegularAnimal).executeUpdate();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    //RETRIEVES A LIST OF ALL THE ANIMALS FROM THE DATABASE
    public static List<RegularAnimal> getAllRegularAnimals() {
        List<RegularAnimal> allRegularAnimals = null;
        try (Connection db = Database.getConnect().open()) {
            String RegularAnimals = "SELECT * FROM regular_animal WHERE not deleted";
            allRegularAnimals = db.createQuery(RegularAnimals).executeAndFetch(RegularAnimal.class);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            return allRegularAnimals;
        }
        return allRegularAnimals;
    }
    //DELETES A REGULAR ANIMAL FROM THE DATABASE
    public static void deleteRegularAnimal(String animalName){
        try(Connection db = Database.getConnect().open()){
            String deletedRegularAnimal = "UPDATE regular_animal SET deleted = (true) WHERE animalName = (:animalName);";
            db.createQuery(deletedRegularAnimal).addParameter("animalName", animalName).executeUpdate();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}
