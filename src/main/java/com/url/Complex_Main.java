package com.url;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Complex_Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //CSP
        //Variables
        String      A = "A", B = "B", C = "C", D = "D", E = "E", F = "F", G = "G", H = "H",
                    I = "I", J = "J", K = "K", L = "L", M = "M", N = "N", O = "O", P = "P",
                    Q = "Q", R = "R", S = "S", T = "T", U = "U", V = "V", W = "W", X = "X",
                    Y = "Y", Z = "Z", AA = "AA";
        List<String> variables = List.of(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, AA);

        //Dominios
        Map<String, List<String>> domains = new HashMap<>();
        for (var variable: variables)
        {
                domains.put(variable, List.of("Cyan", "Magenta", "Amarillo"));
        }

        //Restricciones
        CSP_ARC<String, String> problem = new CSP_ARC<>(variables, domains);
        problem.addConstraint(new ComplexConstraint(A, V));
        problem.addConstraint(new ComplexConstraint(A, B));
        problem.addConstraint(new ComplexConstraint(B, V));
        problem.addConstraint(new ComplexConstraint(C, F));
        problem.addConstraint(new ComplexConstraint(C, H));
        problem.addConstraint(new ComplexConstraint(C, D));
        problem.addConstraint(new ComplexConstraint(D, G));
        problem.addConstraint(new ComplexConstraint(D, H));
        problem.addConstraint(new ComplexConstraint(E, I));
        problem.addConstraint(new ComplexConstraint(F, K));
        problem.addConstraint(new ComplexConstraint(G, K));
        problem.addConstraint(new ComplexConstraint(H, V));
        problem.addConstraint(new ComplexConstraint(H, M));
        problem.addConstraint(new ComplexConstraint(I, M));
        problem.addConstraint(new ComplexConstraint(J, N));
        problem.addConstraint(new ComplexConstraint(J, K));
        problem.addConstraint(new ComplexConstraint(K, O));
        problem.addConstraint(new ComplexConstraint(L, P));
        problem.addConstraint(new ComplexConstraint(L, Q));
        problem.addConstraint(new ComplexConstraint(M, Q));
        problem.addConstraint(new ComplexConstraint(P, S));
        problem.addConstraint(new ComplexConstraint(P, T));
        problem.addConstraint(new ComplexConstraint(P, U));
        problem.addConstraint(new ComplexConstraint(R, W));
        problem.addConstraint(new ComplexConstraint(R, X));
        problem.addConstraint(new ComplexConstraint(R, S));
        problem.addConstraint(new ComplexConstraint(S, X));
        problem.addConstraint(new ComplexConstraint(S, Y));
        problem.addConstraint(new ComplexConstraint(S, Z));
        problem.addConstraint(new ComplexConstraint(T, Z));
        problem.addConstraint(new ComplexConstraint(T, U));
        problem.addConstraint(new ComplexConstraint(T, AA));
        problem.addConstraint(new ComplexConstraint(U, AA));
        problem.addConstraint(new ComplexConstraint(V, W));
        problem.addConstraint(new ComplexConstraint(W, X));
        problem.addConstraint(new ComplexConstraint(X, Y));
        problem.addConstraint(new ComplexConstraint(Y, Z));
        problem.addConstraint(new ComplexConstraint(Z, AA));

        var solution = problem.backtrack();
        System.out.println("Solucion:\n");
        for (var value: solution.entrySet())
        {
            System.out.println(value.getKey() + " = " + value.getValue());
        }
    }
}
