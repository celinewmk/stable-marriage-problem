/**
 * @author Céline Wan Min Kee
 * Numéro étudiant: 300193369
 * Classe Paire qui représente une paire de 2 nombres (first, second)
 */

public class Paire implements Comparable<Paire> {
    
    //variables d'instances
    private int first;
    private int second;

    /**
     * Constructeur
     * @param first qui est le premier numéro de la paire
     * @param second qui est le deuxième numéro de la paire
     */
    public Paire(int first, int second){
        this.first = first;
        this.second = second;
    }

    /**
     * Getter qui permet d'accéder à la valeur du premier nombre
     * @return un entier
     */
    public int getFirst() {
        return first;
    }

    /**
     * Getter qui permet d'accéder à la valeur du deuxième nombre
     * @return un entier
     */
    public int getSecond() {
        return second;
    }

    /**
     * Imprime la Paire(first, second)
     * @return un String représentant la paire
     */
    public String toString() {
        return "Paire: ("+first+","+second+")";
    }
    
    @Override
    /**
     * Compare une Paire avec une autre Paire
     * @param o represente la paire à comparer
     * @return un entier qui donne une indication si le this.Paire est supérieur ou pas 
     *         à l'autre Paire
     */
    public int compareTo(Paire o){
        
        if (this.first > o.first){
            return 1;
        }
        else if (this.first < o.first){
            return -1;
        } 
        else {
            return 0;
        }
    }
}
