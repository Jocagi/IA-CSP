package com.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSP <V, D>
{
    private List<V> variables;
    private Map<V, List<D>> domains;
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public CSP(List<V> variables, Map<V, List<D>> domains)
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
        return backtrack(new HashMap<>());
    }

    public Map<V, D> backtrack(Map<V, D> assignment)
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
            System.out.println("Variable: " + unassigned + " - Valor: " + value);

            //Probar una asigancion

            //1 - Crear una copia de uan asigancion anterior
            Map<V, D> localAssigmnet = new HashMap<>(assignment);

            //2 - Probar (aka asiganr un valor)
            localAssigmnet.put(unassigned, value);

            //3 - Verificar consistencia de la asignacion
            if (consistent(unassigned, localAssigmnet))
            {
                Map<V,D> result = backtrack(localAssigmnet);

                if(result != null)
                {
                    return result;
                }
            }
        }
        return null;
    }
}
