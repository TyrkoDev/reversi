/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi.apo;

/**
 *
 * @author JC
 */
public class Pion {
    
    private int x;
    private int y;
    private String couleur;
    
    public Pion(int x, int y, String couleur)
    {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }
    
    //Getters
    public int getRow()
    {
        return this.x;
    }
    
    public int getColumn()
    {
        return this.y;
    }
    
    public String getCouleur()
    {
        return this.couleur;
    }
    
    //Setters
    public void setRow(int x)
    {
        this.x = x;
    }
    
    public void setColumn(int y)
    {
        this.y = y;
    }
    
    public void setCouleur(String couleur)
    {
        this.couleur = couleur;
    }
}
