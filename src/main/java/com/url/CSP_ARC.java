package com.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSP_ARC<V, D>
{
    private List<V> variables;
    private Map<V, List<D>> domains;
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP_ARC(List<V> variables, Map<V, List<D>> domains)
    {
        this.variables = variables;
        this.domains = domains;

        for (V variable: variables)
        {
            constraints.put(variable, new ArrayList<Constraint<V, D>>());
            //Cada variable debe de tener un dominio asignado
            if (!domains.containsKey(variable))
            {
                throw new IllegalArgumentException("La variable " + variable + " no contiene un dominio");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint)
    {
        for (V variable: constraint.variables)
        {
            //Variable que se encuentra en el constraint tambien sea parte del CSP
            if (!this.variables.contains(variable))
            {
                throw new IllegalArgumentException("La variable " + variable + " no es parte del CSP");
            }

            constraints.get(variable).add(constraint);
        }
    }

    public boolean consistent(V variable, Map<V, D> assignment)
    {
        for (Constraint<V, D> constraint: this.constraints.get(variable))
        {
            if (!constraint.satisfied(assignment))
            {
                return false;
            }
        }
        return true;
    }

    public Map<V, D> backtrack()
    {
        return backtrack(new HashMap<>(), this.domains);
    }

    public Map<V, D> backtrack(Map<V, D> assignment, Map<V, List<D>> domains)
    {
        // Si la asigancion es completa (Si cada variable tiene un valor)
        if (variables.size() == assignment.size())
        {
            return assignment;
        }

        //Seleccionar una variable no asignada
        V unassigned = variables.stream()
                .filter(v -> !assignment.containsKey(v))
                .findFirst().get();

        for (D value: domains.get(unassigned))
        {
            //System.out.println("Variable: " + unassigned + " - Valor: " + value);

            //Probar una asigancion

            //1 - Crear una copia de una asigancion anterior
            Map<V, D> localAssigmnet = new HashMap<>(assignment);

            //2 - Probar (aka asiganr un valor)
            localAssigmnet.put(unassigned, value);

            //3 - Verificar consistencia de la asignacion
            if (consistent(unassigned, localAssigmnet))
            {
                Map<V, List<D>> localDomain = computeNewDomain(new HashMap<>(domains), localAssigmnet);

                if(localDomain != null)
                {
                    Map<V,D> result = backtrack(localAssigmnet, localDomain);

                    if(result != null)
                    {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private Map<V, List<D>> computeNewDomain(Map<V, List<D>> domains, Map<V, D> assignment)
    {
        Map<V, List<D>> Newdomain = new HashMap<>();

        //Validar nuevo dominio para todos los nodos adyacentes
        for(var constraint : constraints.entrySet())
        {
            for (var adjacentPlaces: constraint.getValue())
            {
                for (var place1: adjacentPlaces.getVariables())
                {
                    //Color asignado en lugar 1
                    if (assignment.containsKey(place1))
                    {

                        //El dominio del valor asignado es su propio color
                        List<D> tmpDomain = new ArrayList<>(domains.get(place1));
                        tmpDomain.removeIf(x -> x != assignment.get(place1));
                        Newdomain.put(place1, tmpDomain);
                        if (tmpDomain.size() == 0)
                        {
                            return null;
                        }
                        
                        //Remover este color del dominio de todos los adyacentes
                        for (var place2: adjacentPlaces.getVariables())
                        {
                            List<D> tmpDomain2 = new ArrayList<>();

                            if (place1 != place2)
                            {
                                if (Newdomain.containsKey(place2))
                                {
                                    tmpDomain2 = new ArrayList<>(Newdomain.get(place2));
                                    tmpDomain2.removeIf(x -> assignment.get(place1).equals(x));
                                    Newdomain.put(place2, tmpDomain2);
                                }
                                else
                                {
                                    tmpDomain2 = new ArrayList<>(domains.get(place2));
                                    tmpDomain2.removeIf(x -> Newdomain.get(place1).contains(x));
                                    Newdomain.put(place2, tmpDomain2);
                                }

                                if (tmpDomain2.size() == 0)
                                {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Agregar dominios no considerados
        for (var domain: domains.entrySet())
        {
            if (!Newdomain.containsKey(domain.getKey()))
            {
                Newdomain.put(domain.getKey(), domain.getValue());
            }
        }
        return Newdomain;
    }
}
