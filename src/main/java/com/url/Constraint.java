package com.url;

import java.util.List;
import java.util.Map;

public abstract class Constraint <V, D>
{
    protected List<V> variables;

    public Constraint(List<V> variables)
    {
        this.variables = variables;
    }
    public List<V> getVariables() { return variables; }
    public abstract boolean satisfied(Map<V,D> assigment);
}
