import java.util.*;
import java.io.*;
/**
 *  Projet Partie 1
 *  @author Céline Wan Min Kee
 *  Numéro étudiant: 300193369
 *  Cette classe est la classe Gale Shapley qui permet de résoudre le 
 *  problème des mariages stables (associer des étudiants avec des employeurs)
 */ 

public class GaleShapley {

    // Déclaration des variables
    private int n; // nombre d'étudiants et d'employés au total
    private Stack<Integer> Sue = new Stack<Integer>(); //pile où on ajoute les employeurs
    private int[] students; // 2 tableaux qui nous aident à garder une trace de l'appariement
    private int[] employers;

    private String[] studentsName; // 2 tableaux qui nous permettent de garder les noms des étudiants et employeurs
    private String[] employersName; 

    private int[][] A; // matrice 2D de dimension nXn avec A[s][e] qui est le rang donné par un étudiant s à un employeur e
    private String file; // String qui garde le nom du fichier d'entrée

    private PriorityQueue<Paire>[] PQ; // tableau de file de priorité qui contiendra les files de priorité de chaque employeur
    
    private Paire[] stableMatches; // ensemble des mariages stables 
    // tableau de Paire(x,y) où x l'employeur et y est l'étudiant 
  
    /**
     * Constructeur
     * @param filename qui est le nom du fichier
     * @throws Exception
     */
    public GaleShapley(String filename) throws Exception {
        this.file = filename;
    }

    @SuppressWarnings("unchecked")
    /**
     * Fait la lecture du fichier d'entrée et initialise les variables nécessaires
     * @param fileName qui est le nom du fichier
     * @throws Exception
     */
    public void initialize(String fileName) throws Exception {
        if(fileName == null || this.file == null){
            throw new NullPointerException("No file provided");
        }
        int line = 0; // variable qui indique où on se trouve dans le fichier
        int employerIndex = 0; // variable qui garde une trace du nombre d'employeur ajouté au tableau employers[]
        int studentsIndex = 0; // variable qui garde une trace du nombre d'étudiant ajouté au tableau students[]
        int index = 0; // variable qui nous permettra de savoir où on se trouve dans la matrice nxn afin de pouvoir remplir A et PQ
        
        // lecture du fichier ligne par ligne
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNext()) {
            String str = scanner.nextLine(); // garde le contenu de la ligne actuelle
            if(line == 0) { 
                /* si on se trouve à la première ligne du document, on initialise toutes les
                 variables d'instance déclarées comme expliqué dans le projet */
                
                // Initialisation de n (nombre d'étudiants et nombre d'employé au total)
                n = Integer.parseInt(str.trim()); 
                
                // Initialisation des tableaux students et employers, et du stack Sue
                students = new int[n];
                employers = new int[n];                
                for(int i = 0; i < n; i++){
                    students[i] = -1;
                    employers[i] = -1;
                    Sue.push(i); // La méthode push de Java est bien de O(1)
                }
                // Initialisation du tableau qui garde les noms des étudiants et employeurs
                studentsName = new String[n];
                employersName = new String[n];

                //Initialisation de A
                A = new int[n][n];

                // Initialisation du PQ
                PQ = new PriorityQueue[n];
                for(int i = 0; i < n; i++){
                    PQ[i] = new PriorityQueue<Paire>(n);
                }

                // Initialise le stableMatches à retourner dans execute()
                stableMatches = new Paire[n];
            } 
            else {
                // Ce bout de code se réalise après qu'on ait lu la première ligne du fichier d'entrée
                
                if(employerIndex < n){
                    // On remplit le tableau des noms d'employeurs avant car ils apparaissent en premier
                    employersName[employerIndex] = str.trim();
                    employerIndex++;
                } 
                else if (studentsIndex < n) {
                    /* Dès que le tableau contenant les noms d'employeurs est rempli,
                     on remplit celle avec les noms des étudiants */
                    studentsName[studentsIndex] = str.trim();
                    studentsIndex++;
                } 
                else if (employerIndex == n && studentsIndex == n && str!=null && index < n){
                    /* Ce bout de code est exécuté dès que les tableaux gardant les noms des employeurs
                    et étudiants sont remplis, et qu'on se trouve dans la matrice nxn
                    exemple de la ligne où on se trouve actuellement: 1,1 2,1 3,2
                    */
                    fillAandPQ(str,index); // appelle la méthode privée pour remplir A et PQ
                    index++; // met à jour la ligne de la matrice où on se trouve
                }
            }
            line++; // met à jour le numéro de ligne où on se trouve
        }
    }

    /**
     * Exécute l’algorithme de Gale-Shapley et associe un étudiant à un employeur
     * @return un tableau de Paire represéntant l'ensemble des mariages stables
     */
    public Paire[] execute() {
        // Partie procédure
        while(!Sue.empty()) {
            int e = Sue.pop(); // La méthode pop de Java est bien de O(1)
            int s = PQ[e].remove().getSecond(); // La méthode remove() de Java est bien de O(log n) (attention à ne pas confondre avec remove(Object o) qui lui est O(n))
            int e1 = students[s];               // https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
            if(students[s] == -1) {
                students[s] = e;
                employers[e] = s;
            } else if (A[s][e] < A[s][e1]) {
                students[s] = e;
                employers[e] = s;
                employers[e1] = -1;
                Sue.push(e1); // La méthode push de Java est bien de O(1)
            } else {
                Sue.push(e); // La méthode push de Java est bien de O(1)
            }
        }

        for(int i = 0; i < n; i++) {
            stableMatches[i] = new Paire(i,employers[i]);
            //réuni l'employeur et l'étudiant avec qui il est associé, qu'on ajoute dans le tableau
        }
        return stableMatches;
    }

    /**
     * Sauvegarde la solution trouvée avec execute() dans un document texte
     * @param filename qui est le nom du fichier
     * @throws Exception
     */
    public void save(String filename) throws Exception {
        
        // Création du ficher de sortir
        OutputStreamWriter outputFile = new OutputStreamWriter(new FileOutputStream("matches_" + filename));

        // On écrit maintenant chaque match dans le document
        for(int i = 0; i < n; i++){
            outputFile.write("Match " + i + ": " + employersName[stableMatches[i].getFirst()] + " - " + studentsName[stableMatches[i].getSecond()] + "\n");
        }
        outputFile.flush();
        outputFile.close();
        System.out.println("Succès! Nous avons réglé le problème des mariages stables pour le fichier "+file);
    }

    /**
     * Méthode privée qui permet de remplir les TAD A et PQ
     * @param line qui est le contenu de la ligne où on se trouve
     * @param index qui est la ligne de la matrice nxn où on se trouve
     */
    private void fillAandPQ(String line, int index) {

        // Divise la ligne où il y a les espaces
        String[] str = line.split(" ");  //(ex: 1,1 2,1 3,2 => ["1,1","2,1","3,2"])
      
        for(int i = 0; i < str.length; i++){
            // On redivise où il y a les virgules pour pouvoir avoir le employer ranking et student ranking
           String[] x = str[i].split(","); //(ex: pour i = 0: 1,1 => ["1","1"])
           A[i][index] = Integer.parseInt(x[1]); // On remplit A à la colonne index de A et le row i avec la valeur du student ranking
           PQ[index].add(new Paire(Integer.parseInt(x[0]),i)); // On remplit PQ avec le rang (x[0]) attribué par l'employeur index à l'étudiant i
           // La méthode add de Java est bien de O(log n)
        }
    }

    /**
     * Fonction main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Demande à l'utilisateur de donner le nom d'un fichier
        System.out.print("S'il vous plaît, entrez le nom du fichier texte à lire (ex. test_N3.txt): \n");
        Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
        
        // Création du Gale Shapley afin de pouvoir régler le problème des mariages stables
        GaleShapley match = new GaleShapley(fileName);
        match.initialize(fileName);
        match.execute();
        match.save(fileName);

        scanner.close();
    }
}