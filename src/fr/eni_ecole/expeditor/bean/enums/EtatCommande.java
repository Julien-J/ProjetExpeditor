
package fr.eni_ecole.expeditor.bean.enums;

/**
 * ProjectExpeditor Version 1.0
 * @author d1408davidj
 * 20 juin 2016
 */
public enum EtatCommande 
{
	ATTENTE("En attente"),
    ENCOURS("En cours de traitement"),
    TRAITEE("Traitée");
	
	private String displayName;

	EtatCommande(String displayName) 
	{
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }
}
