package com.example.hb.zoojumanji.enclosure;

import com.example.hb.zoojumanji.animal.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hb on 07/06/2016.
 */
public class Enclosure {

    protected int id;
    protected String name;
    protected int max;
    protected EnclosureType type;
    protected List<Animal> animals = new ArrayList<>();

    private static int currentId = 0;

    private static int getCurrentId() {
        currentId++;
        return currentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Enclosure setName(String name) {
        this.name = name;
        return this;
    }

    public int getMax() {
        return max;
    }

    public Enclosure setMax(int max) {
        this.max = max;
        return this;
    }

    public EnclosureType getType() {
        return type;
    }

    public Enclosure setType(EnclosureType type) {
        this.type = type;
        return this;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void deleteAnimal(Animal animal) {
        if (animals.contains(animal)) {
            animals.remove(animal);
        }
    }

    public int getAnimalsCount() {
        return animals.size();
    }

    public Enclosure(String name, int max, EnclosureType type) {
        this.id = getCurrentId();
        this.name = name;
        this.max = max;
        this.type = type;
    }
}
