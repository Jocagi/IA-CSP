package com.url;

import java.util.List;
import java.util.Map;

public class AustraliaColoringConstraint extends Constraint<String, String>
{
    private String place1, place2;

    public AustraliaColoringConstraint(String place1, String place2)
    {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assigment)
    {
        //Validar...
        //La variable no se aencuentra aun asignada
        if (!assigment.containsKey(place1) || !assigment.containsKey(place2))
        {
            return true;
        }
        //Lugar 1 <> Lugar 2
        return !assigment.get(place1).equals(assigment.get(place2));
    }
}
