/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi.apo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author JC
 */
public class Plateau{
    
    //Niveau de difficulté
    private int niveau;
    
    //Tableau (JTable)
    private JTable fenetre;
    
    //Images
    private ImageIcon blanc;
    private ImageIcon noir;
    
    //Matrice
    //Grille de poids :
    private int grille[][] = new int[8][8];
    //Grille de pions
    private Pion plateau[][] = new Pion[8][8];
    //Grille de calcul
    private Pion matriceCalcul[][] = new Pion[8][8];
    
    
    public Plateau(JTable fenetre, int niveau)
    {
        this.fenetre = fenetre;
        this.niveau = niveau;
        blanc = new ImageIcon("D:\\Projets\\reversi-apo\\images\\blanc.png");
        noir = new ImageIcon("D:\\Projets\\reversi-apo\\images\\noir.png");
        initialise();
    }
    
    public void initialise()
    {
        //Matrice
        //Initialisation de la matrice de poids
        int[][] g = 
        {
            {3, -1, 1, 1, 1, 1, -1, 3},
            {-1, -1, 1, 1, 1, 1, -1, -1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {-1, -1, 1, 1, 1, 1, -1, -1},
            {3, -1, 1, 1, 1, 1, -1, 3}
         };
        grille = g;
        
        //Initialisation de la matrice de pions
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                plateau[i][j] = null;
            }
        }
        
        //Initialisation de la matrice de calcul
        matriceCalcul = plateau;
        
        //Initialisation des images sur le plateau
        for(int i = 0; i < 8; i++)
            fenetre.getColumnModel().getColumn(i).setCellRenderer(new ImagePiece());
        
        //Pieces blanches
        fenetre.setValueAt(blanc, 3, 4);
        fenetre.setValueAt(blanc, 4, 3);
        
        plateau[3][4] = new Pion(3, 4, "Blanc");
        plateau[4][3] = new Pion(4, 3, "Blanc");
        
        //Pieces noires
        fenetre.setValueAt(noir, 4, 4);
        fenetre.setValueAt(noir, 3, 3);
        
        plateau[4][4] = new Pion(4, 4, "Noir");
        plateau[3][3] = new Pion(3, 3, "Noir");
        
        afficheMatrice(plateau);
    }        

    //Ajoute un pion joueur
    public void addPion(int row, int col) {
        if(free(row, col))
        {
            fenetre.setValueAt(noir, row, col); 
            Pion p = new Pion(row, col, "Noir"); 
            plateau[row][col] = p;  
            matriceCalcul = plateau;
            afficheMatrice(plateau);
            //ami(p, 1);
        }
    }
    
    boolean free(int row, int column)
    {
        Pion[] listeCoups;
        listeCoups = coupsPossible(matriceCalcul, "Noir");

        if(matriceCalcul[row][column] == null) {
            if(listeCoups != null) {
                for(int i = 0; i < listeCoups.length; i++)
                {
                    if((listeCoups[i].getRow() == row) && (listeCoups[i].getColumn() == column))
                    {
                        retourner(row, column, "Noir");
                        return true;
                    }
                }
            }
            else
                return false;
        } else {
            System.out.println("Impossible de poser un pion ici");
            return false;
        }
        return true;
    }
    
    public void retourner(int row, int col, String couleur)
    {
        String couleurOpposee;
        if(couleur == "Noir")
            couleurOpposee = "Blanc";
        else
            couleurOpposee = "Noir";
        
        Pion[] liste = pionsAutour(plateau, row, col, couleurOpposee);
        int i = row;
        int j = col;
        int x;
        int y;
        
        for(int k = 0; k < liste.length; k++)
        {

            if(liste[k] != null)
            {
                int pX = liste[k].getRow();
                int pY = liste[k].getColumn();

                //en bas
                if(i == pX && j < pY)
                {
                    y = j + 1;
                    while(y<=7 && plateau[pX][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, pX, y);
                        else
                            fenetre.setValueAt(blanc, pX, y);
                            
                        plateau[pX][y].setCouleur(couleur);
                        y++;
                    }
                }

                //en haut
                if(i == pX && j > pY)
                {
                    y = j - 1;
                    while(y>=0 && plateau[pX][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, pX, y);
                        else
                            fenetre.setValueAt(blanc, pX, y);
                        
                        plateau[pX][y].setCouleur(couleur);
                        y--;
                    }
                }

                //à droite
                if(i < pX && j == pY)
                {
                    x = i + 1;
                    while(x<=7 && plateau[x][pY].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, pY);
                        else
                            fenetre.setValueAt(blanc, x, pY);
                        
                        plateau[x][pY].setCouleur(couleur);
                        x++;
                    }
                }

                //à gauche
                if(i > pX && j == pY)
                {
                    x = i - 1;
                    while(x>=0 && plateau[x][pY].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, pY);
                        else
                            fenetre.setValueAt(blanc, x, pY);
                        
                        plateau[x][pY].setCouleur(couleur);
                        x--;
                    }
                }

                //en diagonale bas gauche
                if(i > pX && j < pY)
                {
                    x = i -1;
                    y = j + 1;
                    while(x>=0 && y<=7 && plateau[x][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, y);
                        else
                            fenetre.setValueAt(blanc, x, y);
                        
                        plateau[x][y].setCouleur(couleur);
                        x--;
                        y++;
                    }
                }

                //en diagonale bas droite
                if(i < pX && j < pY)
                {
                    x = i + 1;
                    y = j + 1;
                    while(x<=7 && y<=7 && plateau[x][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, y);
                        else
                            fenetre.setValueAt(blanc, x, y);
                        
                        plateau[x][y].setCouleur(couleur);
                        x++;
                        y++;
                    }
                }

                //en diagonale haut droite
                if(i < pX && j > pY)
                {
                    x = i + 1;
                    y = j - 1;
                    while(x<=7 && y>=7 && plateau[x][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, y);
                        else
                            fenetre.setValueAt(blanc, x, y);
                        
                        plateau[x][y].setCouleur(couleur);
                        x++;
                        y--;
                    }
                }

                //en diagonale haut gauche
                if(i > pX && j > pY)
                {
                    x = i - 1;
                    y = j - 1;
                    while(x<=7 && y>=7 && plateau[x][y].getCouleur() == couleurOpposee)
                    {
                        if(couleur == "Noir")
                            fenetre.setValueAt(noir, x, y);
                        else
                            fenetre.setValueAt(blanc, x, y);
                        
                        plateau[x][y].setCouleur(couleur);
                        x--;
                        y--;
                    }
                }

            }
        }
        
    }
    
    public void afficheMatrice(Pion[][] matrice)
    {
        for(int i = 0 ; i < 8 ; i++)
        {
            for(int j = 0 ; j < 8 ; j++)
            {
                if(matrice[i][j] == null) {
                    System.out.print(" 0 ");
                }
                else if(matrice[i][j].getCouleur() == "Noir")
                    System.out.print(" N ");
                else
                    System.out.print(" B ");
            }
            
                System.out.println("");
        }    
    }
    
    public int ami(Pion p, int niveau)
    {
        Pion coupCourant = p;
        Pion listeCoups[];
        Pion coupFutur;
        int eval = 0;

        if(niveau == 2)
        {
            //eval = evaluer();
        }
        else
        {
            listeCoups = coupsPossible(matriceCalcul, "Blanc");
            eval = -10;
            for(int i = 0; i < listeCoups.length; i++)
            {
                coupFutur = listeCoups[i];
                System.out.println("Position : X : " + coupFutur.getRow() + ", Y : " + coupFutur.getColumn());
                /*creerNewMatrice(coupCourant, coupFutur);
                int oldEval = eval;
                eval = graphe[coupFutur->x()][coupFutur->y()];
                eval = MAX(eval, ennemi(coupFutur, niveau + 1));

                if(coupFutur == meilleurNoeud)
                {
                    meilleurNoeud = coupCourant;
                }

                if(eval > oldEval)
                {
                    meilleurNoeud = coupCourant;
                }*/
            }
        }

        return eval;
    }

    public int ennemi(Pion p, int niveau)
    {
        Pion coupCourant = p;
        Pion listeCoups[];
        Pion coupFutur;
        int eval = 0;

        if(niveau == 2)
        {
            //eval = evaluer();
        }
        else
        {
            listeCoups = coupsPossible(matriceCalcul, "Blanc");
            eval = -10;
            for(int i = 0; i <= listeCoups.length; i++)
            {
                coupFutur = listeCoups[i];
                System.out.println("Position : X : " + coupFutur.getRow() + ", Y : " + coupFutur.getColumn());
                /*creerNewMatrice(coupCourant, coupFutur);
                int oldEval = eval;
                eval = graphe[coupFutur->x()][coupFutur->y()];
                eval = MAX(eval, ennemi(coupFutur, niveau + 1));

                if(coupFutur == meilleurNoeud)
                {
                    meilleurNoeud = coupCourant;
                }

                if(eval > oldEval)
                {
                    meilleurNoeud = coupCourant;
                }*/
            }
        }

        return eval;
    }

    public int MIN(int eval, int evalInf)
    {
        if(eval > evalInf)
            return evalInf;
        else
            return eval;
    }

    public int MAX(int eval, int evalInf)
    {
        if(eval < evalInf)
            return evalInf;
        else
            return eval;

    }
    
    public Pion[] coupsPossible(Pion matrice[][], String couleur)
    {
        int x = 0, y = 0;
        Pion[] listeCoups = new Pion[8];
        int compteurCoup = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Pion[] pions;
                if(couleur == "Noir")
                    pions = pionsAutour(matrice, i, j, "Blanc");
                else
                    pions = pionsAutour(matrice, i, j, "Noir");

                for(int k = 0; k < pions.length; k++)
                {

                    if(pions[k] != null)
                    {
                        int pX = pions[k].getRow();
                        int pY = pions[k].getColumn();

                        //en bas
                        if(i == pX && j < pY)
                        {
                            y = pY + 1;
                            while(y<=7 && matrice[pX][y] != null)
                            {
                                if(matrice[pX][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);                                        
                                    compteurCoup++;
                                }
                                y++;
                            }
                        }

                        //en haut
                        if(i == pX && j > pY)
                        {
                            y = pY - 1;
                            while(y>=0 && matrice[pX][y] != null)
                            {
                                if(matrice[pX][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                y--;
                            }
                        }

                        //à droite
                        if(i < pX && j == pY)
                        {
                            x = pX + 1;
                            while(x<=7 && matrice[x][pY] != null)
                            {
                                if(matrice[x][pY].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x++;
                            }
                        }

                        //à gauche
                        if(i > pX && j == pY)
                        {
                            x = pX - 1;
                            while(x>=0 && matrice[x][pY] != null)
                            {
                                if(matrice[x][pY].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x--;
                            }
                        }

                        //en diagonale bas gauche
                        if(i > pX && j < pY)
                        {
                            x = pX -1;
                            y = pY + 1;
                            while(x>=0 && y<=7 && matrice[x][y] != null)
                            {
                                if(matrice[x][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x--;
                                y++;
                            }
                        }

                        //en diagonale bas droite
                        if(i < pX && j < pY)
                        {
                            x = pX + 1;
                            y = pY + 1;
                            while(x<=7 && y<=7 && matrice[x][y] != null)
                            {
                                if(matrice[x][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x++;
                                y++;
                            }
                        }

                        //en diagonale haut droite
                        if(i < pX && j > pY)
                        {
                            x = pX + 1;
                            y = pY - 1;
                            while(x<=7 && y>=7 && matrice[x][y] != null)
                            {
                                if(matrice[x][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x++;
                                y--;
                            }
                        }

                        //en diagonale haut gauche
                        if(i > pX && j > pY)
                        {
                            x = pX - 1;
                            y = pY - 1;
                            while(x<=7 && y>=7 && matrice[x][y] != null)
                            {
                                if(matrice[x][y].getCouleur() == couleur)
                                {
                                    listeCoups[compteurCoup] = new Pion(i, j, couleur);
                                    compteurCoup++;
                                }
                                x--;
                                y--;
                            }
                        }

                    }
                }
            }
        }



        return listeCoups;
    }


    
    public Pion[] pionsAutour(Pion[][] matrice, int i, int j, String couleurOppose)
    {
        Pion pion[] = new Pion[8];
        
        int compteur = 0;

        if(matrice[i][j] == null)
        {
            if(i < 7)
            {
                if(matrice[i+1][j] != null)
                {
                    if(matrice[i+1][j].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i+1][j];
                        compteur++;
                    }
                }
            }
            
            if(i > 0)
            {
                if(matrice[i-1][j] != null)
                {
                    if(matrice[i-1][j].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i-1][j];
                        compteur++;

                    }
                }
            }
            
            //haut
            if(j > 0)
            {
                if(matrice[i][j-1] != null)
                {
                    if(matrice[i][j-1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i][j-1];
                        compteur++;
                    }
                }
            }
            
            //bas
            if(j < 7)
            {
                if(matrice[i][j+1] != null)
                {
                    if(matrice[i][j+1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i][j+1];
                        compteur++;
                    }
                }
            }

            //droite bas
            if((i < 7) && (j < 7))
            {
                if(matrice[i+1][j+1] != null)
                {
                    if(matrice[i+1][j+1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i+1][j+1];
                        compteur++;
                    }
                }
            }
            
            //droite haut
            if((i < 7) && (j > 0))
            {
                if(matrice[i+1][j-1] != null)
                {
                    if(matrice[i+1][j-1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i+1][j-1];
                        compteur++;
                    }
                }
            }
            
            //gauche bas
            if((i > 0) && (j < 7))
            {
                if(matrice[i-1][j+1] != null)
                {
                    if(matrice[i-1][j+1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i-1][j+1];
                        compteur++;
                    }
                }
            }

            //gauche haut
            if((i > 0) && (j > 0))
            {
                if(matrice[i-1][j-1] != null)
                {
                    if(matrice[i-1][j-1].getCouleur() == couleurOppose)
                    {
                        pion[compteur] = matrice[i-1][j-1];
                        compteur++;
                    }
                }
            }
        }

        return pion;
    }

}
